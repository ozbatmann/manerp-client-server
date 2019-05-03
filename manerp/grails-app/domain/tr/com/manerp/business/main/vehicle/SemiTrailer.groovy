package tr.com.manerp.business.main.vehicle

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BusinessDomain
import tr.com.manerp.business.sysref.SysrefVehicleState

class SemiTrailer implements BusinessDomain
{
    static auditable = true

    String plateNumber
    String brand
    String name
    SysrefVehicleState sysrefVehicleState
    // TODO: sysrefVehicleOwner gerekli mi??? Ozmal, kiralik

    static constraints = {
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        plateNumber nullable: false, blank: false, unique: false
        name nullable: true, blank: true, unique: false, maxSize: 50
        brand nullable: true, blank: true, unique: false, maxSize: 50
        sysrefVehicleState nullable: false, unique: false
    }

    // TODO: change
    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

    def setRandomCode()
    {
        setRandomCode(SemiTrailer)
    }
}
