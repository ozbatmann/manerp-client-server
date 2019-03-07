package tr.com.manerp.business.sysref

import grails.databinding.BindingFormat


class SysrefNaceCode {

    static auditable = true

    String name
    String code
    boolean active
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: false, maxSize: 5
        code nullable: false, blank: false, unique: true, maxSize: 3
        active  unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "sysref_nace_code", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_SYSREF_NACE_CODE']
    }

    static mappedBy = {
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
