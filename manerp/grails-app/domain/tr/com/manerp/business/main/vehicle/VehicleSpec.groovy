package tr.com.manerp.business.main.vehicle

import net.kaleidos.hibernate.usertype.JsonbMapType
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
    Map vehicleHazardousMaterials

    static constraints = {
        vehicleHeight scale: 4
        vehicleWeight scale: 4
        vehicleWidth scale: 4
        vehicleLength scale: 4
        vehicleAxles max: 50
        vehicleTrailers max: 20
    }

    static mapping = {
        vehicleHazardousMaterials type: JsonbMapType
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
