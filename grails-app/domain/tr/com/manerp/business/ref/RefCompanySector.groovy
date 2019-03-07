package tr.com.manerp.business.ref

import grails.databinding.BindingFormat
import tr.com.manerp.auth.AwcCompany

class RefCompanySector {

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
        name nullable: false, blank: false, unique: false, maxSize: 50
        awcCompany nullable: true, unique: false
        code nullable: false, blank: false, unique: true, maxSize: 3
        active nullable: false, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "ref_company_sector", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_REF_COMPANY_SECTOR']
    }

    static mappedBy = {
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
