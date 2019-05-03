package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import java.text.SimpleDateFormat

@Transactional
class StaffService extends BaseService
{

    ManePaginatedResult getStaffList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

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

    def delete(Staff staff)
    {
        staff.delete(flush: true, failOnError: true)
    }

    List formatResultForList(List data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat('dd/MM/yyyy')
        List formattedData = data.collect {
            [
                id                     : it.id,
                drivingLicenseNumber   : it?.drivingLicenseNumber,
                dateCreated            : it?.dateCreated,
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
                sysrefDriverState      : it.sysrefDriverState ? [id: it.sysrefDriverState.id, name: it.sysrefDriverState.name] : null
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
            drivingLicenseNumber   : data?.drivingLicenseNumber,
            dateCreated            : data?.dateCreated,
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
            sysrefDriverState      : data.sysrefDriverState ? [id: data.sysrefDriverState.id, name: data.sysrefDriverState.name] : null
        ]
    }
}
