package tr.com.manerp.business.main.vehicle

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain

class SemiTrailer implements BaseDomain {

    static auditable = true

    String code
    SysCompany sysCompany
    String plateNumber
    String brand
    String name

    static constraints = {
        code nullable: true, blank: true, unique: ['sysCompany'], maxSize: 36
        sysCompany nullable: false, unique: false
        plateNumber nullable: false, blank: false, unique: false
        name nullable: true, blank: true, unique: false, maxSize: 50
        brand nullable: true, blank: true, unique: false, maxSize: 50
    }

    static mapping = {
    }

    // TODO: change
    def beforeValidate() {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }
}
