package tr.com.manerp.optimization

import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.business.main.vehicle.VehicleSpec
import tr.com.manerp.business.main.voyage.Location

class OptimizationParameters implements BaseDomain
{
    String avoid
    String optimize
    String routeAttributes
    String distanceUnit
    VehicleSpec vehicleSpec

    static hasMany = [waypoints: Location] // waypoints to be optimized (vendors)

    static constraints = {
        avoid nullable: false, blank: false
        optimize nullable: false, blank: false
        routeAttributes nullable: false, blank: false
        distanceUnit nullable: false, blank: false
        vehicleSpec nullable: false
    }
}
