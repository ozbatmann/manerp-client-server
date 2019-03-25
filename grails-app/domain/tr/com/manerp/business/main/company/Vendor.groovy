package tr.com.manerp.business.main.company

import grails.util.Holders
import org.apache.commons.lang.RandomStringUtils
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict

class Vendor implements BaseDomain
{

    static auditable = true

    String code
    Company company
    SysCompany sysCompany
    String title
    SysrefCity sysrefCity
    SysrefCountry sysrefCountry
    SysrefDistrict sysrefDistrict
    String address
    String phone
    Location location

    static constraints = {
        sysCompany nullable: false, unique: false
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
        int length = Holders.config.manerp.randomCode.length
        String charset = Holders.config.manerp.randomCode.charset

        String randomCode = RandomStringUtils.random(length, charset).toString()
        Vendor vendor = Vendor.findByCode(randomCode)

        while ( vendor ) {
            randomCode = RandomStringUtils.random(length, charset).toString()
            vendor = Vendor.findByCode(randomCode)
        }

        this.code = randomCode
    }
}
