package tr.com.manerp.business.sysref

import tr.com.manerp.base.domain.SysrefDomain

class SysrefCountry implements SysrefDomain
{

    static constraints = {
        code nullable: true, blank: true, unique: true, maxSize: 8
        name nullable: false, blank: false, unique: false, maxSize: 100
    }

}
