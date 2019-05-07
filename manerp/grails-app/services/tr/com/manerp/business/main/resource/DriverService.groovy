package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefDriverState

import java.text.SimpleDateFormat

@Transactional
class DriverService extends BaseService
{
    def informationRestService
    def orderService

    ManePaginatedResult getDriverList(ManePaginationProperties properties, String driverStateCode)
    {

        def closure = {

            eq('active', true)

            refStaffTitle {
                eq('active', true)
                eq('code', 'DRV')
            }

            if ( driverStateCode ) {
                sysrefDriverState {
                    eq('code', driverStateCode)
                }
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(Staff, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, Staff, ['fullName'] as HashSet)

        return result
    }

    def getDriver(String id, String fields = null)
    {
        def driver = Staff.createCriteria().get {
            refStaffTitle {
                eq('code', 'DRV')
            }
            eq('id', id)
        } as Staff

        driver = formatResultForShow(driver)
        if ( fields ) driver = filterDataByFields(driver, fields, Staff)

        return driver
    }

    def save(Staff driver)
    {
        driver.save(flush: true, failOnError: true)
    }

    def delete(Staff driver)
    {
        driver.delete(flush: true, failOnError: true)
    }

    def saveDriverWithState(Staff driver, SysrefDriverState state)
    {
        driver.sysrefDriverState = state
        save(driver)
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
                sysrefDrivingType      : it.sysrefDrivingType ? [id: it.sysrefDrivingType.id, name: it.sysrefDrivingType.name] : null
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
            sysrefDrivingType      : data.sysrefDrivingType ? [id: data.sysrefDrivingType.id, name: data.sysrefDrivingType.name] : null
        ]
    }

    def getWaypointsByDriver(Staff driver)
    {
        Voyage voyage = Voyage.createCriteria().get {
            sysrefDeliveryStatus {
                or {
                    eq('code', 'YUK')
                    eq('code', 'REZ')
                }
            }
            eq('driver', driver)
        } as Voyage

        if ( !voyage ) {
            throw new Exception("${driver.fullName} için sistemde tanımlı sevkiyat bulunmamaktadır")
        }

        return voyage?.calculatedRoute
    }

    def getSortedVendorsByDriver(Staff driver)
    {
        Voyage voyage = Voyage.createCriteria().get {
            sysrefDeliveryStatus {
                or {
                    eq('code', 'YUK')
                    eq('code', 'REZ')
                }
            }
            eq('driver', driver)
        } as Voyage

        if ( !voyage ) {
            throw new Exception("${driver.fullName} için sistemde tanımlı sevkiyat bulunmamaktadır")
        }

        return voyage?.sortedVendors
    }

    def sendVoyageMailToDriver(Voyage voyage)
    {
        SimpleDateFormat sdf = new SimpleDateFormat('dd/MM/yyyy HH:mm')
        Order order = orderService.getOrderByVoyage(voyage)
        def body = "Tarafınıza ${sdf.format(voyage.startDate)} tarihinde ${order.fullName} için sevkiyat atanmıştır."
        Map paramsData = ['user': voyage.driver.fullName, 'body': body, 'signature': 'MANERP Yazılım']

        informationRestService.sendMail('GENERAL_INFO',
            "Sevkiyat Bilgilendirmesi",
            paramsData,
            [voyage.driver.email] as List,
            ["beratpostalci@gmail.com"] as List,
            ["bpostalci@gmail.com"] as List,
            1,
            2
        )
    }

    def sendVoyageCancelMailToDriver(Voyage voyage)
    {
        SimpleDateFormat sdf = new SimpleDateFormat('dd/MM/yyyy HH:mm')
        Order order = orderService.getOrderByVoyage(voyage)
        def body = "Tarafınıza ${sdf.format(voyage.startDate)} tarihinde ${order.fullName} için atanan sevkiyat iptal edilmiştir."
        Map paramsData = ['user': voyage.driver.fullName, 'body': body, 'signature': 'MANERP Yazılım']

        informationRestService.sendMail('GENERAL_INFO',
            "Sevkiyat Bilgilendirmesi",
            paramsData,
            [voyage.driver.email] as List,
            ["beratpostalci@gmail.com"] as List,
            ["bpostalci@gmail.com"] as List,
            1,
            2
        )
    }

    def getDriverNotifications(Staff driver)
    {
        SimpleDateFormat sdf = new SimpleDateFormat('dd/MM/yyyy HH:mm')

        Voyage voyage = Voyage.createCriteria().get {
            sysrefDeliveryStatus {
                or {
                    eq('code', 'YUK')
                    eq('code', 'REZ')
                }
            }
            eq('driver', driver)
        } as Voyage

        if ( !voyage ) {
            throw new Exception("${driver.fullName} için sistemde tanımlı sevkiyat bulunmamaktadır")
        }

        Boolean status = voyage.sysrefDeliveryStatus.code != 'IPT'

        return [startDate: sdf.format(voyage.startDate), status: status]
    }

}
