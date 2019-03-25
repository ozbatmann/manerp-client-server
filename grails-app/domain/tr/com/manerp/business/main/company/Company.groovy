package tr.com.manerp.business.main.company

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BusinessDomain
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefCompanyType
import tr.com.manerp.common.MCompany

class Company implements BusinessDomain, MCompany
{

    static auditable = true

    SysrefCompanyType sysrefCompanyType

    static hasMany = [
        vendors: Vendor,            // Company bayileri
        orders : Order,
        voyages: Voyage
    ]

    static constraints = {

        // MCompany constraints
        name nullable: false, blank: false, unique: false, maxSize: 50
        title nullable: true, blank: true, unique: false, maxSize: 50
        sysrefCountry nullable: true, unique: false
        sysrefCity nullable: true, unique: false
        sysrefDistrict nullable: true, unique: false
        address nullable: false, blank: false, unique: false, maxSize: 255
        phone nullable: false, blank: false, unique: false, maxSize: 20
        phone2 nullable: true, blank: true, unique: false, maxSize: 20
        fax nullable: true, blank: true, unique: false, maxSize: 20
        gsm nullable: true, blank: true, unique: false, maxSize: 20
        webAddress nullable: true, blank: true, unique: false, maxSize: 50
        email email: true, nullable: true, blank: true, unique: true, maxSize: 50
        refCompanySector nullable: true, unique: false
        customerRepresentative nullable: true, unique: false
        numberOfStaff nullable: true, unique: false
        employerRegistrationNo nullable: true, blank: true, unique: false, maxSize: 30
        tradeRegistrationNo nullable: true, blank: true, unique: false, maxSize: 30
        taxOffice nullable: true, blank: true, unique: false, maxSize: 255
        taxNumber nullable: true, blank: true, unique: false, maxSize: 50
        sysrefNaceCode nullable: true, unique: false

        title nullable: false, blank: false, unique: ['sysCompany', 'sysrefCompanyType'], maxSize: 50
        sysCompany nullable: false, unique: false
        sysrefCompanyType nullable: false, blank: false, unique: false
    }

    static mapping = {
        vendors cascade: 'all-delete-orphan'
        orders cascade: 'all-delete-orphan'
        voyages cascade: 'all-delete-orphan'
    }

    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

    def setRandomCode()
    {
        setRandomCode(Company)
    }

}
