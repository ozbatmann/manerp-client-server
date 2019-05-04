package tr.com.manerp.commands.controller.voyage

import grails.validation.Validateable
import tr.com.manerp.business.main.vehicle.VehicleSpec
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefDimensionUnit
import tr.com.manerp.business.sysref.SysrefWeightUnit
import tr.com.manerp.optimization.OptimizationParameters

class OptimizationParametersUpdateCommand implements Validateable
{
    String id
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
        id nullable: false, blank: false, maxSize: 32
    }

    OptimizationParameters rightShift(OptimizationParameters parameters)
    {
        if ( this.avoid ) parameters.avoid = this.avoid ?: null
        if ( this.optimize ) parameters.optimize = this.optimize ?: null
        if ( this.routeAttributes ) parameters.routeAttributes = this.routeAttributes ?: null
        if ( this.distanceUnit ) parameters.distanceUnit = this.distanceUnit ?: null
        if ( this.waypoints ) parameters.waypoints = this.waypoints ?: null

        if ( this.vehicleHeight ) parameters.vehicleSpec.vehicleHeight = this.vehicleHeight
        if ( this.vehicleWeight ) parameters.vehicleSpec.vehicleWeight = this.vehicleWeight
        if ( this.vehicleWidth ) parameters.vehicleSpec.vehicleWidth = this.vehicleWidth
        if ( this.vehicleLength ) parameters.vehicleSpec.vehicleLength = this.vehicleLength
        if ( this.vehicleAxles ) parameters.vehicleSpec.vehicleAxles = this.vehicleAxles
        if ( this.vehicleTrailers ) parameters.vehicleSpec.vehicleTrailers = this.vehicleTrailers

        parameters.vehicleSpec.save(flush: true, failOnError: true)

        parameters
    }
}
