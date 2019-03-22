package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class StaffService extends BaseService {

    ManePaginatedResult getStaffList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)
        }

        return paginate(Staff, properties, closure)
    }

    def save(Staff staff) {

        staff.save(failOnError: true)
    }

    def delete(Staff staff) {

        staff.delete(flush: true, failOnError: true)
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
