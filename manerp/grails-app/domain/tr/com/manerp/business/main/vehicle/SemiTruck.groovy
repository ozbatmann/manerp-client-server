package tr.com.manerp.business.main.vehicle

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BusinessDomain

class SemiTruck implements BusinessDomain
{
    static auditable = true

    String plateNumber
    String name
    String brand

    static constraints = {
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        plateNumber nullable: false, blank: false, unique: false
        name nullable: true, blank: true, unique: false, maxSize: 50
        brand nullable: false, blank: false, unique: false, maxSize: 50
    }

    // TODO: change
    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

    def setRandomCode()
    {
        setRandomCode(SemiTruck)
    }

}
