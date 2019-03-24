package tr.com.manerp.auth

import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.common.MCompany

class SysCompany implements BaseDomain, MCompany {

    static auditable = true

    static constraints = {
        // MCompany constraints
        name nullable: false, blank: false, unique: false, maxSize: 50
        title nullable: true, blank: true, unique: false, maxSize: 50
        sysrefCountry nullable: true, unique: false
        sysrefCity nullable: true, unique: false
        sysrefDistrict nullable: true, unique: false
        address nullable: false, blank: false, unique: false, maxSize: 255
        phone nullable: false, blank: false, unique: false, maxSize: 20
        phone2 nullable: true, blank: true, unique: false, maxSize: 20
        fax nullable: true, blank: true, unique: false, maxSize: 20
        gsm nullable: true, blank: true, unique: false, maxSize: 20
        webAddress nullable: true, blank: true, unique: false, maxSize: 50
        email email: true, nullable: true, blank: true, unique: true, maxSize: 50
        refCompanySector nullable: true, unique: false
        customerRepresentative nullable: true, unique: false
        numberOfStaff nullable: true, unique: false
        employerRegistrationNo nullable: true, blank: true, unique: false, maxSize: 30
        tradeRegistrationNo nullable: true, blank: true, unique: false, maxSize: 30
        taxOffice nullable: true, blank: true, unique: false, maxSize: 255
        taxNumber nullable: true, blank: true, unique: false, maxSize: 50
        sysrefNaceCode nullable: true, unique: false
    }

    static mapping = {
    }


}
