package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class DorsetService extends BaseService {

    ManePaginatedResult getDorsetList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)
        }

        return paginate(Dorset, properties, closure)
    }

    def save(Dorset dorset) {

        dorset.save(failOnError: true)
    }

    def delete(Dorset dorset) {

        dorset.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                id         : it.id,
                plateNumber: it.plateNumber
            ]
        }

        return formattedData
    }

}
