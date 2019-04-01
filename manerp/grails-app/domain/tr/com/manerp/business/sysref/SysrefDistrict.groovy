package tr.com.manerp.business.sysref

import tr.com.manerp.base.domain.SysrefDomain

class SysrefDistrict implements SysrefDomain
{

    SysrefCity sysrefCity

    static constraints = {
        code nullable: true, blank: true, unique: true, maxSize: 4
        name nullable: false, blank: false, unique: false, maxSize: 21
        sysrefCity nullable: false, unique: false
    }

}
