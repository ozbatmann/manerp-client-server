package tr.com.manerp.business.ref

import grails.databinding.BindingFormat
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.main.company.Company

class RefWorkingArea {

    static auditable = true

    AwcCompany awcCompany
    String name
    String code
    boolean active
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static hasMany = []
    static belongsTo = []

    static constraints = {
        awcCompany nullable: true, unique: false
        name nullable: false, blank: false, unique: false, maxSize: 50
        code nullable: false, blank: false, unique: true, maxSize: 3
        active unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "ref_working_area", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_REF_WORKING_AREA']
    }

    static mappedBy = {
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
