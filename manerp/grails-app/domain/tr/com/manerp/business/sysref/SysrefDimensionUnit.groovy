package tr.com.manerp.business.sysref

import tr.com.manerp.base.domain.SysrefDomain

class SysrefDimensionUnit implements SysrefDomain
{
    static constraints = {
        name nullable: false, blank: false, unique: false, maxSize: 10
        code nullable: true, blank: true, unique: true, maxSize: 5
    }
}
