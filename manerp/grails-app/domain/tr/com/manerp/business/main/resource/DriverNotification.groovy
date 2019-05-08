package tr.com.manerp.business.main.resource

import tr.com.manerp.base.domain.BaseDomain

class DriverNotification implements BaseDomain
{
    Staff driver
    String startDate
    Boolean status

    static constraints = {
        driver nullable: false, blank: false
        startDate nullable: false, blank: false
        status nullable: false, blank: false
    }
}
