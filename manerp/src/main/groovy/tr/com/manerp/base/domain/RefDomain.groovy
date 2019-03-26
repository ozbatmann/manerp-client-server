package tr.com.manerp.base.domain

import grails.validation.Validateable
import tr.com.manerp.auth.SysCompany

trait RefDomain implements BaseDomain, Validateable
{
    SysCompany sysCompany
    String name
    String code
    String description

    static constraints = {
        code nullable: true, blank: true, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        name nullable: false, blank: false, unique: ['sysCompany'], maxSize: 50
        description nullable: true, blank: true, unique: false
    }
}