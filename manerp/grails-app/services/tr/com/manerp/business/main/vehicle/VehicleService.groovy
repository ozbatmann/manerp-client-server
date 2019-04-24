package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import grails.util.Holders
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

            if ( vehicleStateCode ) {
                sysrefVehicleState {
                    eq('active', true)
                    eq('code', vehicleStateCode)
                }
            }
        }

        ManePaginatedResult result = paginate(Vehicle, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, Vehicle)

        return result
    }

    def getVehicle(String id, String fields = null)
    {
        def vehicle = Vehicle.createCriteria().get { eq('id', id) } as Vehicle
        vehicle = formatResultForShow(vehicle)
        if ( fields ) vehicle = filterDataByFields(vehicle, fields, Vehicle)

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

    List formatResultForList(def data)
    {
        List formattedData = data.collect {
            [
                id                : it.id,
                code              : it.code,
                plateNumber       : it.plateNumber,
                fleetCardNumber   : it.fleetCardNumber,
                sysrefVehicleType : it.sysrefVehicleType ? [id: it.sysrefVehicleType.id, name: it.sysrefVehicleType.name] : null,
                sysrefVehicleOwner: it.sysrefVehicleOwner ? [id: it.sysrefVehicleOwner.id, name: it.sysrefVehicleOwner.name] : null
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat('dd/MM/yyyy HH:mm')
        return [
            id                            : data.id,
            code                          : data.code,
            plateNumber                   : data?.plateNumber,
            fleetCardNumber               : data?.fleetCardNumber,
            sysrefVehicleType             : data.sysrefVehicleType ? [id: data.sysrefVehicleType.id, name: data.sysrefVehicleType.name] : null,
            sysrefVehicleOwner            : data.sysrefVehicleOwner ? [id: data.sysrefVehicleOwner.id, name: data.sysrefVehicleOwner.name] : null,
            brand                         : data?.brand,
            purchaseDate                  : data.purchaseDate ? sdf.format(data.purchaseDate) : null,
            numberOfSensors               : data?.numberOfSensors,
            km                            : data?.km,
            isDualRegime                  : data?.isDualRegime,
            refWorkingArea                : data.refWorkingArea ? [id: data.refWorkingArea.id, name: data.refWorkingArea.name] : null,
            vehicleOwnerFullName          : data?.vehicleOwnerFullName,
            insuranceStartDate            : data.insuranceStartDate ? sdf.format(data.insuranceStartDate) : null,
            insuranceEndDate              : data.insuranceEndDate ? sdf.format(data.insuranceEndDate) : null,
            kgsNo                         : data?.kgsNo,
            ogsNo                         : data?.ogsNo,
            fuelKit                       : data?.fuelKit,
            description                   : data?.description,
            operationInsuranceNotification: data?.operationInsuranceNotification,
            annualInsurance               : data?.annualInsurance

        ]
    }
}
