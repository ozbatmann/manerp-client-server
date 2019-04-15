package tr.com.manerp.business.main.order

import tr.com.manerp.base.domain.BusinessDomain
import tr.com.manerp.business.main.voyage.Voyage

class VoyageOrder implements BusinessDomain
{
    Voyage voyage
    Order order // get voyage's companies by order's company

    static constraints = {
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        voyage nullable: false, unique: false
        order nullable: false, unique: false
    }

    @Override
    def setRandomCode()
    {
        setRandomCode(VoyageOrder)
    }
}
