package tr.com.manerp.business.main.order

import tr.com.manerp.base.domain.BusinessDomain
import tr.com.manerp.business.main.company.Vendor

class OrderVendor implements BusinessDomain
{
    Vendor vendor
    Order _order

    static constraints = {
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        vendor nullable: false, unique: false
        _order nullable: false, unique: false
    }

    @Override
    def setRandomCode()
    {
        setRandomCode(VoyageOrder)
    }
}
