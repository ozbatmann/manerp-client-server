package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.resource.Staff

import java.text.SimpleDateFormat

@Transactional
class VehicleService extends BaseService
{

    ManePaginatedResult getVehicleList(ManePaginationProperties properties, String vehicleStateCode)
    {
        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }

            sysrefVehicleState {
                eq('active', true)
                eq('code', vehicleStateCode)
            }
        }

        return paginate(Vehicle, properties, closure, ['sysCompany'] as HashSet)
    }

    Vehicle getVehicle(String id)
    {
        Vehicle vehicle = Vehicle.createCriteria().get {

            eq('id', id)

        } as Vehicle

        return vehicle
    }

    def save(Vehicle vehicle)
    {

        vehicle.save(flush: true, failOnError: true)
    }

    def delete(Vehicle vehicle)
    {

        vehicle.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForList(def data)
    {
        List formattedData = data.collect {
            [
                id                : it.id,
                code              : it.code,
                plateNumber       : it.plateNumber,
                fleetCardNumber   : it.fleetCardNumber,
                sysrefVehicleType : [id: it.sysrefVehicleType.id, name: it.sysrefVehicleType.name],
                sysrefVehicleOwner: [id: it.sysrefVehicleOwner.id, name: it.sysrefVehicleOwner.name]
            ]
        }

        formattedData
    }

}
