package tr.com.manerp.business.main.company

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BusinessDomain
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict

class Vendor implements BusinessDomain
{

    static auditable = true

    Company company
    String title
    SysrefCity sysrefCity
    SysrefCountry sysrefCountry
    SysrefDistrict sysrefDistrict
    String address
    String phone
    Location location

    static constraints = {
        sysrefCountry nullable: true, unique: false
        sysrefCity nullable: true, unique: false
        sysrefDistrict nullable: true, unique: false
        company nullable: false, unique: false
        title nullable: false, blank: false, unique: ['sysCompany'], maxSize: 150
        address nullable: true, blank: true, unique: false, maxSize: 255
        phone nullable: true, blank: true, unique: false, maxSize: 20
        location nullable: true, unique: false
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
    }

    static mapping = {
    }

    //TODO:change
    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

    def setRandomCode()
    {
        setRandomCode(Vendor)
    }
}
