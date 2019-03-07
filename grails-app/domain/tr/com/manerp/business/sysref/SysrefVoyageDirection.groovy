package tr.com.manerp.business.sysref

import grails.databinding.BindingFormat


class SysrefVoyageDirection {

    static auditable = true

    String name
    String code
    boolean active
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: false, maxSize: 50
        code nullable: false, blank: false, unique: true, maxSize: 3
        active unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "sysref_voyage_direction", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_SYSREF_VOYAGE_DIRECTION']
    }

    static mappedBy = {
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
