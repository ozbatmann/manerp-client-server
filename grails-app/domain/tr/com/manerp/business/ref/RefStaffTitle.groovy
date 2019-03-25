package tr.com.manerp.business.ref

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain

class RefStaffTitle implements BaseDomain
{

    static auditable = true

    SysCompany sysCompany
    String name
    String code
    String description

    static hasMany = []
    static belongsTo = []

    static constraints = {
        importFrom(RefCompanySector)
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
