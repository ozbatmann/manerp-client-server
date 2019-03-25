package tr.com.manerp.business.sysref

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain

class SysrefRevenueType implements BaseDomain {

    String name
    String code
    SysCompany sysCompany
    String description

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: ['sysCompany'], maxSize: 100
        code nullable: true, blank: true, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        description nullable: true, blank: true, unique: false
    }

}
