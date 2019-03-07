package tr.com.manerp.business.ref

import org.grails.databinding.BindingFormat
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.main.company.Company

class RefStaffTitle {

    static auditable = true

    AwcCompany awcCompany
    String name
    String code
    Boolean active
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static hasMany = []
    static belongsTo = []

    static constraints = {
        awcCompany nullable: false, unique: false
        name nullable: false, blank: false, unique: false, maxSize: 50
        code nullable: false, blank: false, unique: true, maxSize: 3
        active nullable: false, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "ref_staff_class", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_REF_STAFF_CLASS']
    }

    static mappedBy = {
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
