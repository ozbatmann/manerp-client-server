package tr.com.manerp.business.main.voyage

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain

class Location implements BaseDomain {

    static auditable = true

    String name
    String latitude
    String longitude
    SysCompany sysCompany

    static constraints = {
        sysCompany nullable: false, unique: false
        name nullable: true, blank: true, unique: false, maxSize: 100
        latitude nullable: true, blank: true, unique: false, maxSize: 100
        longitude nullable: true, blank: true, unique: false, maxSize: 100
    }

    static mapping = {
    }

}
