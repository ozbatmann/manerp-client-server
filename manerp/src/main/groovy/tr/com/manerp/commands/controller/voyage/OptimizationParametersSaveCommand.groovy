package tr.com.manerp.commands.controller.voyage

import grails.validation.Validateable
import tr.com.manerp.business.main.vehicle.VehicleSpec
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.optimization.OptimizationParameters

class OptimizationParametersSaveCommand implements Validateable
{
    Voyage voyage
    String avoid
    String optimize
    String routeAttributes
    String distanceUnit
    VehicleSpec vehicleSpec
    List<Location> waypoints

//    SysrefDimensionUnit sysrefDimensionUnit
//    SysrefWeightUnit sysrefWeightUnit
//    Double vehicleHeight
//    Double vehicleWeight
//    Double vehicleWidth
//    Double vehicleLength
//    Integer vehicleAxles
//    Integer vehicleTrailers


    static constraints = {

    }

    OptimizationParameters rightShift(OptimizationParameters parameters)
    {
        parameters.avoid = this.avoid ?: null
        parameters.optimize = this.optimize ?: null
        parameters.routeAttributes = this.routeAttributes ?: null
        parameters.distanceUnit = this.distanceUnit ?: null
        parameters.vehicleSpec = this.vehicleSpec ?: null

        parameters
    }
}
