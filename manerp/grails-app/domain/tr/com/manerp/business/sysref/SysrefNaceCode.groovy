package tr.com.manerp.business.sysref

import tr.com.manerp.base.domain.SysrefDomain

class SysrefNaceCode implements SysrefDomain
{

    static constraints = {
        code nullable: true, blank: true, unique: true, maxSize: 50
        name nullable: false, blank: false, unique: false, maxSize: 100
    }

}
