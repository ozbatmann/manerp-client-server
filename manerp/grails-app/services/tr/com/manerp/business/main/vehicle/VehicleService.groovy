package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.resource.Staff

@Transactional
class VehicleService extends BaseService {

    ManePaginatedResult getVehicleList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)
        }

        return paginate(Vehicle, properties, closure)
    }

    Vehicle getVehicle(String id)
    {
        Vehicle vehicle = Vehicle.createCriteria().get {

            eq('id', id)

        } as Vehicle

        return vehicle
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
