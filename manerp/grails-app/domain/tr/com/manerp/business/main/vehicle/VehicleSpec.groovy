package tr.com.manerp.business.main.vehicle

import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.business.sysref.SysrefDimensionUnit
import tr.com.manerp.business.sysref.SysrefWeightUnit

class VehicleSpec implements BaseDomain
{
    SysrefDimensionUnit sysrefDimensionUnit
    SysrefWeightUnit sysrefWeightUnit
    Double vehicleHeight
    Double vehicleWeight
    Double vehicleWidth
    Double vehicleLength
    Integer vehicleAxles
    Integer vehicleTrailers
    String vehicleHazardousMaterials

    static constraints = {
        vehicleHeight scale: 4
        vehicleWeight scale: 4
        vehicleWidth scale: 4
        vehicleLength scale: 4
        vehicleAxles max: 50
        vehicleTrailers max: 20
    }

    String getSysrefDimensionUnitName()
    {
        return this.sysrefDimensionUnit.name
    }

    String getSysrefWeightUnitName()
    {
        return this.sysrefWeightUnit.name
    }
}
