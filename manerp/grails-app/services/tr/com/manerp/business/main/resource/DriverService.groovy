package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class DriverService extends BaseService
{

    ManePaginatedResult getDriverList(ManePaginationProperties properties)
    {

        def closure = {

            eq('active', true)

            refStaffTitle {
                eq('active', true)
                eq('code', 'DRV')
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        return paginate(Staff, properties, closure, ['sysCompany'] as HashSet)
    }

    Staff getDriver(String id)
    {
        Staff driver = Staff.createCriteria().get {

            refStaffTitle {
                eq('code', 'DRV')
            }

            eq('id', id)

        } as Staff

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

    List formatPaginatedResultForDropDown(def data)
    {

        List formattedData = data.collect {
            return [
                id  : it.id,
                name: it.getFullName()
            ]
        }

        return formattedData
    }
}
