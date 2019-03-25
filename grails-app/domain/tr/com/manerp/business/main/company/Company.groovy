package tr.com.manerp.business.main.company

import grails.util.Holders
import org.apache.commons.lang.RandomStringUtils
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefCompanyType
import tr.com.manerp.common.MCompany

class Company implements BaseDomain, MCompany
{

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
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
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
        int length = Holders.config.manerp.randomCode.length
        String charset = Holders.config.manerp.randomCode.charset

        String randomCode = RandomStringUtils.random(length, charset).toString()
        Company company = Company.findByCode(randomCode)

        while ( company ) {
            randomCode = RandomStringUtils.random(length, charset).toString()
            company = Company.findByCode(randomCode)
        }

        this.code = randomCode
    }

}
