package tr.com.manerp.business.main.order

import grails.databinding.BindingFormat
import grails.util.Holders
import org.apache.commons.lang.RandomStringUtils
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.sysref.SysrefRevenueType

class Order implements BaseDomain
{

    static auditable = true

    String code
    SysCompany sysCompany
    Company company
    String name
    @BindingFormat('dd/MM/yyyy hh:MM')
    Date orderDate
    String workOrderNo // iş emri numarası
    SysrefRevenueType sysrefRevenueType
    String billingNo // fatura numarası

    static constraints = {
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        company nullable: false, unique: false
        name nullable: false, blank: false, unique: false, maxSize: 50
        orderDate nullable: false, unique: false
        workOrderNo nullable: true, blank: true, unique: false
        sysrefRevenueType nullable: true, unique: false
        billingNo nullable: false, blank: false, unique: false, maxSize: 50
    }
//TODO:change
    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

    def setRandomCode()
    {
        int length = Holders.config.manerp.randomCode.length
        String charset = Holders.config.manerp.randomCode.charset

        String randomCode = RandomStringUtils.random(length, charset).toString()
        Order order = Order.findByCode(randomCode)

        while ( order ) {
            randomCode = RandomStringUtils.random(length, charset).toString()
            order = Order.findByCode(randomCode)
        }

        this.code = randomCode
    }
}
