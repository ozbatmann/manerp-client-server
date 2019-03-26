package tr.com.manerp.base.domain

import grails.validation.Validateable

trait SysrefDomain implements BaseDomain, Validateable
{
    String code
    String name

    static constraints = {
        name nullable: false, blank: false, unique: false, maxSize: 100
    }
}