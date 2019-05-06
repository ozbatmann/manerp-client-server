package tr.com.manerp.commands.controller.order

import grails.validation.Validateable
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.sysref.SysrefRevenueType

class OrderUpdateCommand implements Validateable
{
    String id
    Company company
    String name
    String workOrderNo
    SysrefRevenueType sysrefRevenueType
    String billingNo
    List<Vendor> selectedVendors

    static constraints = {
        id nullable: false, blank: false, maxSize: 32
        company nullable: true, unique: false
        name nullable: true, blank: true, unique: false, maxSize: 50
        workOrderNo nullable: true, blank: true, unique: false, maxSize: 30
        sysrefRevenueType nullable: true, unique: false
        billingNo nullable: true, blank: true, unique: false, maxSize: 50
        selectedVendors nullable: true, blank: true
    }

    Order rightShift(Order order)
    {
        if ( this.company ) order.company = this.company
        if ( this.name ) order.name = this.name
        if ( this.workOrderNo ) order.workOrderNo = this.workOrderNo
        if ( this.sysrefRevenueType ) order.sysrefRevenueType = this.sysrefRevenueType
        if ( this.billingNo ) order.billingNo = this.billingNo

        order
    }

}
