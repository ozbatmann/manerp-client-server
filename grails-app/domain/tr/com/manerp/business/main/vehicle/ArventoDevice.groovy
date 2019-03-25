package tr.com.manerp.business.main.vehicle

import grails.util.Holders
import org.apache.commons.lang.RandomStringUtils
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain

class ArventoDevice implements BaseDomain {

    static auditable = true

    String code
    SysCompany sysCompany
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
        int length = Holders.config.manerp.randomCode.length
        String charset = Holders.config.manerp.randomCode.charset

        String randomCode = RandomStringUtils.random(length, charset).toString()
        ArventoDevice device = ArventoDevice.findByCode(randomCode)

        while ( device ) {
            randomCode = RandomStringUtils.random(length, charset).toString()
            device = ArventoDevice.findByCode(randomCode)
        }

        this.code = randomCode
    }

}
