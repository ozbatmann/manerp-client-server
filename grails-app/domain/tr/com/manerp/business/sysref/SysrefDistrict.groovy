package tr.com.manerp.business.sysref

import tr.com.manerp.base.domain.BaseDomain


class SysrefDistrict implements BaseDomain {

    String name
    SysrefCity sysrefCity

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: false, maxSize: 100
        sysrefCity nullable: false, unique: false
    }

}
