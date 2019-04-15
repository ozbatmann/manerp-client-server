package tr.com.manerp.business.main.order

import grails.databinding.BindingFormat
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BusinessDomain
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefOrderState
import tr.com.manerp.business.sysref.SysrefRevenueType

class Order implements BusinessDomain
{

    static auditable = true

    Company company
    Voyage voyage
    String name
    @BindingFormat('dd/MM/yyyy hh:MM')
    Date orderDate
    String workOrderNo // iş emri numarası
    SysrefRevenueType sysrefRevenueType
    String billingNo // fatura numarası
    SysrefOrderState sysrefOrderState

//    static hasMany = [voyages: Voyage] many-to-many relationship with Voyage defined in VoyageOrder cross domain

    static constraints = {
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        company nullable: false, unique: false
        voyage nullable: true, unique: false
        name nullable: false, blank: false, unique: false, maxSize: 50
        orderDate nullable: false, unique: false
        workOrderNo nullable: true, blank: true, unique: false, maxSize: 30
        sysrefRevenueType nullable: true, unique: false
        billingNo nullable: false, blank: false, unique: false, maxSize: 50
        sysrefOrderState nullable: false, blank: false, unique: false
    }

    // TODO: change
    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

    def setRandomCode()
    {
        setRandomCode(Order)
    }
}
