package tr.com.manerp.business.main.vehicle

import grails.util.Holders
import org.apache.commons.lang.RandomStringUtils
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain

class SemiTruck implements BaseDomain
{

    static auditable = true

    String code
    SysCompany sysCompany
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
        SemiTruck truck = SemiTruck.findByCode(randomCode)

        while ( truck ) {
            randomCode = RandomStringUtils.random(length, charset).toString()
            truck = SemiTruck.findByCode(randomCode)
        }

        this.code = randomCode
    }

}
