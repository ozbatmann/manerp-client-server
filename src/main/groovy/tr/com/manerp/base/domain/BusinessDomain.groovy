package tr.com.manerp.base.domain

import grails.util.Holders
import grails.validation.Validateable
import org.apache.commons.lang.RandomStringUtils
import tr.com.manerp.auth.SysCompany

trait BusinessDomain implements BaseDomain, Validateable
{
    SysCompany sysCompany
    String code

    abstract def setRandomCode()

    def setRandomCode(Class domain)
    {
        int length = Holders.config.manerp.randomCode.length
        String charset = Holders.config.manerp.randomCode.charset

        String randomCode = RandomStringUtils.random(length, charset).toString()
        def obj = domain.findByCode(randomCode)

        while ( obj ) {
            randomCode = RandomStringUtils.random(length, charset).toString()
            obj = domain.findByCode(randomCode)
        }

        this.code = randomCode.toUpperCase()
    }

    static constraints = {
        sysCompany nullable: false, unique: false
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
    }

}