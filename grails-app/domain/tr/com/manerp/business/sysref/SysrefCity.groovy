package tr.com.manerp.business.sysref

import tr.com.manerp.base.domain.BaseDomain

class SysrefCity implements BaseDomain {

    String name
    SysrefCountry sysrefCountry

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: false, maxSize: 100
        sysrefCountry nullable: false, unique: false
    }

}
