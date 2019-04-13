package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import grails.util.Holders
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

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(Staff, properties, closure, excludedFields)
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
}
