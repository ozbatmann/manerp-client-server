package tr.com.manerp.business.ref

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain

class RefCompanySector implements BaseDomain {

    static auditable = true

    SysCompany sysCompany
    String name
    String businessCode
    String description

    static hasMany = []
    static belongsTo = []

    static constraints = {
        code nullable: false, blank: false, unique: ['sysCompany']
        sysCompany nullable: false, unique: false
        name nullable: false, blank: false, unique: ['sysCompany'], maxSize: 50
        businessCode nullable: true, blank: true, unique: ['sysCompany'], maxSize: 10
        description nullable: true, blank: true, unique: false
    }

    static mapping = {
//        table name: "ref_company_sector", schema: "business"
//        id generator: 'sequence', params: [sequence: 'business.SEQ_REF_COMPANY_SECTOR']
    }

    static mappedBy = {
    }


}
