package tr.com.manerp.business.sysref

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain

class SysrefCompanyType implements BaseDomain {

    String name
    String code
    SysCompany sysCompany
    String businessCode
    String description

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: ['sysCompany'], maxSize: 100
        code nullable: true, blank: true, unique: ['sysCompany'], maxSize: 10
        sysCompany nullable: false, unique: false
        description nullable: true, blank: true, unique: false
    }

    static mapping = {
//        table name: "sysref_company_class", schema: "business"
//        id generator: 'sequence', params: [sequence: 'business.SEQ_SYSREF_COMPANY_CLASS']
    }

    static mappedBy = {
    }

}
