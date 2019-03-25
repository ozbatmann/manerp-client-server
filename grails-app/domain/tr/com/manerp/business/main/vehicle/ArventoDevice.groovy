package tr.com.manerp.business.main.vehicle

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BusinessDomain

class ArventoDevice implements BusinessDomain
{
    static auditable = true

    String deviceId
    String name
    Vehicle vehicle
    SemiTruck dorset

    static constraints = {
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        deviceId nullable: false, unique: false, maxSize: 100
        name nullable: true, blank: true, unique: false, maxSize: 50
        vehicle nullable: true, unique: false
        dorset nullable: true, unique: false
    }

    //TODO:change
    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

    def setRandomCode()
    {
        setRandomCode(ArventoDevice)
    }

}
