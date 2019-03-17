package tr.com.manerp.business.sysref

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain

class SysrefVoyageDirection implements BaseDomain {

    String name
    SysCompany sysCompany
    String businessCode
    String description

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: ['sysCompany'], maxSize: 100
        code nullable: false, blank: false, unique: ['sysCompany']
        sysCompany nullable: false, unique: false
        businessCode nullable: true, blank: true, unique: ['sysCompany'], maxSize: 10
        description nullable: true, blank: true, unique: false
    }

    static mapping = {
//        table name: "sysref_voyage_direction", schema: "business"
//        id generator: 'sequence', params: [sequence: 'business.SEQ_SYSREF_VOYAGE_DIRECTION']
    }

    static mappedBy = {
    }

}
