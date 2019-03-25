package tr.com.manerp.business.ref

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.base.domain.RefDomain

class RefStaffTitle implements RefDomain
{

    static auditable = true

    static constraints = {
        code nullable: true, blank: true, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        name nullable: false, blank: false, unique: ['sysCompany'], maxSize: 50
        description nullable: true, blank: true, unique: false
    }

    // TODO: change
    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }
}
