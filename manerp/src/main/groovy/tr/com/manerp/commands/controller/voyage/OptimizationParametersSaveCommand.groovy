package tr.com.manerp.commands.controller.voyage

import grails.validation.Validateable
import tr.com.manerp.business.main.vehicle.VehicleSpec
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefDimensionUnit
import tr.com.manerp.business.sysref.SysrefWeightUnit
import tr.com.manerp.optimization.OptimizationParameters

class OptimizationParametersSaveCommand implements Validateable
{
    Voyage voyage
    String avoid
    String optimize
    String routeAttributes
    String distanceUnit
    Map waypoints

    SysrefDimensionUnit sysrefDimensionUnit
    SysrefWeightUnit sysrefWeightUnit
    Double vehicleHeight
    Double vehicleWeight
    Double vehicleWidth
    Double vehicleLength
    Integer vehicleAxles
    Integer vehicleTrailers


    static constraints = {

    }

    OptimizationParameters rightShift(OptimizationParameters parameters)
    {
        parameters.avoid = this.avoid ?: null
        parameters.optimize = this.optimize ?: null
        parameters.routeAttributes = this.routeAttributes ?: null
        parameters.distanceUnit = this.distanceUnit ?: null
        parameters.waypoints = this.waypoints ?: null

        VehicleSpec vehicleSpec = new VehicleSpec()
        vehicleSpec.sysrefDimensionUnit = SysrefDimensionUnit.findByCode("m")
        vehicleSpec.sysrefWeightUnit = SysrefWeightUnit.findByCode("kg")
        vehicleSpec.vehicleHeight = this.vehicleHeight
        vehicleSpec.vehicleWeight = this.vehicleWeight
        vehicleSpec.vehicleWidth = this.vehicleWidth
        vehicleSpec.vehicleLength = this.vehicleLength
        vehicleSpec.vehicleAxles = this.vehicleAxles
        vehicleSpec.vehicleTrailers = this.vehicleTrailers

        vehicleSpec.save(flush: true, failOnError: true)
        parameters.vehicleSpec = vehicleSpec

        parameters
    }
}
