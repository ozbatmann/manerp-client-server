package tr.com.manerp.optimization

import net.kaleidos.hibernate.usertype.JsonbMapType
import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.business.main.vehicle.VehicleSpec

class OptimizationParameters implements BaseDomain
{
    String avoid
    String optimize
    String routeAttributes
    String distanceUnit
    VehicleSpec vehicleSpec
    Map waypoints // waypoints to be optimized in lat lng

    static constraints = {
        avoid nullable: false, blank: false
        optimize nullable: false, blank: false
        routeAttributes nullable: false, blank: false
        distanceUnit nullable: false, blank: false
        vehicleSpec nullable: false
        waypoints nullable: false, blank: false, unique: false
    }

    static mapping = {
        waypoints type: JsonbMapType
    }
}
