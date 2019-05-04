package tr.com.manerp.optimization

import net.kaleidos.hibernate.usertype.JsonbMapType
import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.business.main.vehicle.VehicleSpec

class OptimizationParameters implements BaseDomain
{
    Map avoid
    Map optimize
    String routeAttributes
    String distanceUnit
    VehicleSpec vehicleSpec

    static constraints = {
        avoid nullable: true, blank: false
        optimize nullable: true, blank: false
        routeAttributes nullable: true, blank: false
        distanceUnit nullable: true, blank: false
        vehicleSpec nullable: true
    }

    static mapping = {
        avoid type: JsonbMapType
        optimize type: JsonbMapType
    }
}
