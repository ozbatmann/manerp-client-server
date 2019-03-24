package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class SemiTruckService extends BaseService {

    ManePaginatedResult getDorsetList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)
        }

        return paginate(SemiTruck, properties, closure)
    }

    def save(SemiTruck dorset) {

        dorset.save(failOnError: true)
    }

    def delete(SemiTruck dorset) {

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