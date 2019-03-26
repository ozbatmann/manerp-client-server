package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class StaffService extends BaseService
{

    ManePaginatedResult getStaffList(ManePaginationProperties properties)
    {

        def closure = {
            eq('active', true)
        }

        return paginate(Staff, properties, closure)
    }

    Staff getStaff(String id)
    {
        Staff staff = Staff.createCriteria().get {

            eq('id', id)

        } as Staff

        return staff
    }

    def save(Staff staff)
    {

        staff.save(failOnError: true)
    }

    def delete(Staff staff)
    {

        staff.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForList(def data)
    {
        List formattedData = data.collect {
            [
                id                     : it.id,
                firstName              : it.getFirstAndMiddleName(),
                lastName               : it.lastName,
                gsmNo                  : it.gsmNo,
                tcIdNumber             : it.tcIdNumber,
                sysrefStaffContractType: it.sysrefStaffContractType.name,
                refStaffTitle          : it.refStaffTitle.name
            ]
        }

        formattedData
    }

    List formatPaginatedResultForDropDown(def data)
    {

        List formattedData = data.collect {
            [
                id  : it.id,
                name: it.getFullName()
            ]
        }

        formattedData
    }
}