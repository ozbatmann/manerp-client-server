package tr.com.manerp.business.sysref

import tr.com.manerp.base.domain.BaseDomain

class SysrefGender implements BaseDomain {

    String name
    String code

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: false, maxSize: 36
        code nullable: false, blank: false, unique: true, maxSize: 3
    }

}
