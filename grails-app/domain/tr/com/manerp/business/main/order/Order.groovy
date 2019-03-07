package tr.com.manerp.business.main.order

import org.grails.databinding.BindingFormat
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.sysref.SysrefRevenueType

class Order {

    static auditable = true

    String code
    AwcCompany awcCompany
    Company company
    String name
    @BindingFormat('dd/MM/yyyy hh:MM')
    Date orderDate
    String workOrderNo
    SysrefRevenueType sysrefRevenueType
    String billingNo
    Boolean active
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static constraints = {
        code nullable: true, blank: true, unique: true, maxSize: 11
        awcCompany nullable: false, unique: false
        company nullable: false, unique: false
        name nullable: false, blank: false, unique: false, maxSize: 50
        orderDate nullable: false, unique: false
        workOrderNo nullable: true, blank: true, unique: false
        sysrefRevenueType nullable: true, unique: false
        billingNo nullable: true, blank: true, unique: false, maxSize: 50
        active unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "order", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_ORDER']
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
