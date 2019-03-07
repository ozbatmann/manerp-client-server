package tr.com.manerp.business.sysref

import grails.databinding.BindingFormat


class SysrefDistrict {

    static auditable = true

    String name
    String code
    SysrefCountry sysrefCity
    boolean active
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: false, maxSize: 100
        code nullable: false, blank: false, unique: true, maxSize: 10
        sysrefCity unique: true
        active unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "sysref_city", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_SYSREF_CITY']
    }

    static mappedBy = {
    }

    def beforeUpdate() {
        opDate = new Date()
    }
}
