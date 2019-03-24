package tr.com.manerp.business.main.company

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.ref.RefCompanySector
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCompanyType
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefNaceCode
import tr.com.manerp.common.MCompany

class Company implements BaseDomain, MCompany {

    static auditable = true

    SysCompany sysCompany
    SysrefCompanyType sysrefCompanyType

    static hasMany = [
        vendors: Vendor,            // Company bayileri
        orders : Order,
        voyages: Voyage
    ]

    static constraints = {
        importFrom(SysCompany)
        title nullable: false, blank: false, unique: ['sysCompany', 'sysrefCompanyType'], maxSize: 50
        sysCompany nullable: false, unique: false
        sysrefCompanyType nullable: false, blank: false, unique: false
    }

    static mapping = {
        vendors cascade: 'all-delete-orphan'
        orders cascade: 'all-delete-orphan'
        voyages cascade: 'all-delete-orphan'
    }

    def beforeValidate() {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }
}
