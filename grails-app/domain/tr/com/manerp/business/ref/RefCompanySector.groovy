package tr.com.manerp.business.ref

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain

class RefCompanySector implements BaseDomain
{

    static auditable = true

    SysCompany sysCompany
    String name
    String code
    String description

    static hasMany = []
    static belongsTo = []

    static constraints = {
        code nullable: true, blank: true, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        name nullable: false, blank: false, unique: ['sysCompany'], maxSize: 50
        description nullable: true, blank: true, unique: false
    }

    static mapping = {
    }

    static mappedBy = {
    }

//TODO:change
    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }
}
