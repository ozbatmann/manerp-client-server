package tr.com.manerp.business.sysref

import tr.com.manerp.base.domain.SysrefDomain

class SysrefNaceCode implements SysrefDomain
{

    SysrefGeneralHazardClass sysrefGeneralHazardClass

    static constraints = {
        code nullable: true, blank: true, unique: true, maxSize: 9
        name nullable: false, blank: false, unique: false, maxSize: 310
        sysrefGeneralHazardClass nullable: true, unique: false
    }

}
