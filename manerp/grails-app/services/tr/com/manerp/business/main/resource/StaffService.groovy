package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestResponse
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import org.apache.commons.lang.RandomStringUtils
import org.grails.web.json.JSONObject
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.information.util.RestUtil

import java.text.SimpleDateFormat

@Transactional
class StaffService extends BaseService
{

    def informationRestService

    ManePaginatedResult getStaffList(ManePaginationProperties properties)
    {
        def closure = {
            eq('active', true)

            refStaffTitle {
                eq('active', true)
                ne('code', 'DRV')
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(Staff, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, Staff)

        return result
    }

    def getStaff(String id, String fields = null)
    {
        def staff = Staff.createCriteria().get { eq('id', id) } as Staff
        staff = formatResultForShow(staff)
        if ( fields ) staff = filterDataByFields(staff, fields, Staff)

        return staff
    }

    def save(Staff staff)
    {
        staff.save(flush: true, failOnError: true)
    }

    def saveWithUser(Staff staff, String username)
    {
        staff = save(staff)

        try {

            if ( username ) {
                String staffId = staff.id
                String password = generateRandomPassword()
                println "staffId: " + staffId
                println "password: " + password

                String authUrl = Holders.config.manerp.auth.url + "/api/v1/user/"
                JSONObject json = new JSONObject()

                json.username = username

                json.person = [
                    "name"   : staff.fullName,
                    "surname": staff.lastName,
                    "tckn"   : staff.tcIdNumber,
                    "email"  : staff.email
                ] as JSONObject

                json.password = password
                json.staffId = staffId

                println "authUrl: " + authUrl
                println "json: " + json
                RestResponse restResponse = RestUtil.callRestService(authUrl, json)
                JSONObject responseJson = restResponse.json as JSONObject
                println "response: " + responseJson

                if ( responseJson.status == 201 ) {
                    staff.userId = responseJson.data
                    save(staff)
                    sendUserCreatedMail(staff, password)
                } else {
                    throw new Exception("Personel için kullanıcı oluşturulamadı.")
                }
            }

        } catch (Exception ex) {
            delete(staff)
            throw new Exception("Personel için kullanıcı oluşturulamadı.")
        }

        staff
    }

    def sendUserCreatedMail(Staff staff, String password)
    {
        SimpleDateFormat sdf = new SimpleDateFormat('dd/MM/yyyy HH:mm')
        def body = "Tarafınıza ${sdf.format(new Date())} tarihinde MANERP sisteminde kullanıcı oluşturulmuştur.\nAşağıdaki bilgilerle sisteme giriş yapabilirsiniz:\n\nKullanıcı Adı: ${staff.username}\nParola: ${password}"
        Map paramsData = ['user': staff.fullName, 'body': body, 'signature': 'MANERP Yazılım']

        informationRestService.sendMail('GENERAL_INFO',
            "Kullanıcı Oluşturma Bilgilendirmesi",
            paramsData,
            [staff.email] as List,
            ["beratpostalci@gmail.com"] as List,
            ["bpostalci@gmail.com"] as List,
            1,
            2
        )
    }

    private String generateRandomPassword()
    {
        int length = Holders.config.manerp.randomCode.length
        String charset = Holders.config.manerp.password.charset

        String randomCode = RandomStringUtils.random(length, charset).toString()

        randomCode
    }

    def delete(Staff staff)
    {
        if ( staff.userId ) {
            deleteStaffWithUserId(staff)
        } else {
            staff.delete(flush: true, failOnError: true)
        }

        DriverNotification.createCriteria().list {
            eq('driver', staff)
        }.each {
            it.delete(flush: true, failOnError: true)
        }
    }

    def deleteStaffWithUserId(Staff staff)
    {
        String authUrl = Holders.config.manerp.auth.url + "/api/v1/rest/deleteUser"
        JSONObject json = new JSONObject()

        try {

            json.id = staff.userId

            println "authUrl: " + authUrl
            println "json: " + json
            RestResponse restResponse = RestUtil.callRestService(authUrl, json)
            JSONObject responseJson = restResponse.json as JSONObject
            println "response: " + responseJson

            if ( responseJson.status == 204 ) {
                sendUserDeletedMail(staff)
                staff.delete(flush: true, failOnError: true)
            } else {
                throw new Exception("Personel şu anda silinememektedir.")
            }

        } catch (Exception ex) {
            throw new Exception("Personel şu anda silinememektedir.")
        }
    }

    def sendUserDeletedMail(Staff staff)
    {
        SimpleDateFormat sdf = new SimpleDateFormat('dd/MM/yyyy HH:mm')
        def body = "${sdf.format(new Date())} tarihi itibari ile MANERP sisteminde bulunan kullanıcı hesabınız silinmiştir."
        Map paramsData = ['user': staff.fullName, 'body': body, 'signature': 'MANERP Yazılım']

        informationRestService.sendMail('GENERAL_INFO',
            "Kullanıcı Silme Bilgilendirmesi",
            paramsData,
            [staff.email] as List,
            ["beratpostalci@gmail.com"] as List,
            ["bpostalci@gmail.com"] as List,
            1,
            2
        )
    }

    List formatResultForList(List data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat('dd/MM/yyyy')
        List formattedData = data.collect {
            [
                id                     : it.id,
                sysrefDriverState      : it.sysrefDriverState ? [id: it.sysrefDriverState.id, name: it.sysrefDriverState.name] : null,
                drivingLicenseNumber   : it?.drivingLicenseNumber,
                dateCreated            : it?.dateCreated,
                fullName               : it.getFullName(),
                firstName              : it?.firstName,
                middleName             : it?.middleName,
                lastName               : it?.lastName,
                email                  : it?.email,
                sysrefGender           : it?.sysrefGender ? [id: it.sysrefGender.id, name: it.sysrefGender.name] : null,
                tcIdNumber             : it?.tcIdNumber,
                birthDate              : it.birthDate ? sdf.format(it.birthDate) : null,
                sysrefCountry          : it?.sysrefCountry ? [id: it.sysrefCountry.id, name: it.sysrefCountry.name] : null,
                sysrefCity             : it?.sysrefCity ? [id: it.sysrefCity.id, name: it.sysrefCity.name] : null,
                sysrefDistrict         : it?.sysrefDistrict ? [id: it.sysrefDistrict.id, name: it.sysrefDistrict.name] : null,
                address                : it?.address,
                homePhone              : it?.homePhone,
                gsmNo                  : it?.gsmNo,
                code                   : it?.code,
                refStaffTitle          : it.refStaffTitle ? [id: it.refStaffTitle.id, name: it.refStaffTitle.name] : null,
                sysrefStaffContractType: it.sysrefStaffContractType ? [id: it.sysrefStaffContractType.id, name: it.sysrefStaffContractType.name] : null,
                sysrefDrivingType      : it.sysrefDrivingType ? [id: it.sysrefDrivingType.id, name: it.sysrefDrivingType.name] : null,
                username               : it?.username
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat('dd/MM/yyyy')
        // TODO: handle photo, photoName, photoMimeType
        return [
            id                     : data.id,
            sysrefDriverState      : data.sysrefDriverState ? [id: data.sysrefDriverState.id, name: data.sysrefDriverState.name] : null,
            drivingLicenseNumber   : data?.drivingLicenseNumber,
            dateCreated            : data?.dateCreated,
            fullName               : data.getFullName(),
            firstName              : data?.firstName,
            middleName             : data?.middleName,
            lastName               : data?.lastName,
            email                  : data?.email,
            sysrefGender           : data?.sysrefGender ? [id: data.sysrefGender.id, name: data.sysrefGender.name] : null,
            tcIdNumber             : data?.tcIdNumber,
            birthDate              : data.birthDate ? sdf.format(data.birthDate) : null,
            sysrefCountry          : data?.sysrefCountry ? [id: data.sysrefCountry.id, name: data.sysrefCountry.name] : null,
            sysrefCity             : data?.sysrefCity ? [id: data.sysrefCity.id, name: data.sysrefCity.name] : null,
            sysrefDistrict         : data?.sysrefDistrict ? [id: data.sysrefDistrict.id, name: data.sysrefDistrict.name] : null,
            address                : data?.address,
            homePhone              : data?.homePhone,
            gsmNo                  : data?.gsmNo,
            code                   : data?.code,
            refStaffTitle          : data.refStaffTitle ? [id: data.refStaffTitle.id, name: data.refStaffTitle.name] : null,
            sysrefStaffContractType: data.sysrefStaffContractType ? [id: data.sysrefStaffContractType.id, name: data.sysrefStaffContractType.name] : null,
            sysrefDrivingType      : data.sysrefDrivingType ? [id: data.sysrefDrivingType.id, name: data.sysrefDrivingType.name] : null,
            username               : data?.username
        ]
    }
}
