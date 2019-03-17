package tr.com.manerp.business.sysref

import tr.com.manerp.base.domain.BaseDomain

class SysrefCity implements BaseDomain {

    String name
    SysrefCountry sysrefCountry

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: false, maxSize: 100
        code nullable: false, blank: false, unique: true
        sysrefCountry nullable: false, unique: true
    }

    static mapping = {
//        table name: "sysref_country", schema: "business"
//        id generator: 'sequence', params: [sequence: 'business.SEQ_SYSREF_COUNTRY']
    }

    static mappedBy = {
    }
}
