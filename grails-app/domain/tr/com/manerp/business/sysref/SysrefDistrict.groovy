package tr.com.manerp.business.sysref

import tr.com.manerp.base.domain.SysrefDomain

class SysrefDistrict implements SysrefDomain
{

    String name
    SysrefCity sysrefCity

    static constraints = {
        code nullable: true, blank: true, unique: true, maxSize: 8
        name nullable: false, blank: false, unique: false, maxSize: 100
        sysrefCity nullable: false, unique: false
    }

}
