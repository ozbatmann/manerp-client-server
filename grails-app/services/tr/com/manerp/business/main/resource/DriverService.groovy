package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class DriverService extends BaseService {

    ManePaginatedResult getDriverList(ManePaginationProperties properties) {

        def closure = {

            eq('active', true)

            refStaffTitle {
                eq('active', true)
                eq('code', 'DRV')
            }
        }

        return paginate(Staff, properties, closure)
    }

    def save(Staff driver) {

        driver.save(failOnError: true)
    }

    def delete(Staff driver) {

        driver.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                    id  : it.id,
                    name: it.getFullName()
            ]
        }

        return formattedData
    }
}
