package tr.com.manerp.business.sysref

import org.grails.databinding.BindingFormat

class SysrefCountry {

    static auditable = true

    String name
    String code
    boolean active
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: false, maxSize: 100
        code nullable: false, blank: false, unique: true, maxSize: 10
        active unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "sysref_country", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_SYSREF_COUNTRY']
    }

    static mappedBy = {
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
