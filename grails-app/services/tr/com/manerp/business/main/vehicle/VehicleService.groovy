package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class VehicleService extends BaseService {

    ManePaginatedResult getVehicleList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)
        }

        return paginate(Vehicle, properties, closure)
    }

    def save(Vehicle vehicle) {

        vehicle.save(failOnError: true)
    }

    def delete(Vehicle vehicle) {

        vehicle.delete(flush: true, failOnError: true)
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
