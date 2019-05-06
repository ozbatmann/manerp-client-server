package tr.com.manerp.commands.controller.order

import grails.databinding.BindingFormat
import grails.validation.Validateable
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.sysref.SysrefOrderState
import tr.com.manerp.business.sysref.SysrefRevenueType

class OrderSaveCommand implements Validateable
{
    Company company
    String name
    @BindingFormat('dd/MM/yyyy hh:MM')
    Date orderDate
    String workOrderNo
    SysrefRevenueType sysrefRevenueType
    String billingNo
    List<Vendor> selectedVendors

    static constraints = {
        company nullable: false, unique: false
        name nullable: false, blank: false, unique: false, maxSize: 50
        orderDate nullable: true, unique: false
        workOrderNo nullable: true, blank: true, unique: false, maxSize: 30
        sysrefRevenueType nullable: true, unique: false
        billingNo nullable: false, blank: false, unique: false, maxSize: 50
        selectedVendors nullable: false, blank: false
    }

    Order rightShift(Order order)
    {
        order.company = this.company
        order.name = this.name
        order.workOrderNo = this.workOrderNo ?: null
        order.sysrefRevenueType = this.sysrefRevenueType ?: null
        order.billingNo = this.billingNo
        order.sysrefOrderState = SysrefOrderState.findByCode('WAIT')
        order.orderDate = new Date()
        order.setRandomCode()
        order.active = true

        order
    }
}
