package tr.com.manerp.business.main.vehicle

import grails.databinding.BindingFormat
import tr.com.manerp.auth.AwcCompany

class Trailer {

    static auditable = true

    String code
    AwcCompany awcCompany
    String plateNumber
    String name
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static constraints = {
        code nullable: true, blank: true, unique: true, maxSize: 11
        awcCompany nullable: false, unique: false
        plateNumber nullable: false, blank: false, unique: false
        name nullable: true, blank: true, unique: false, maxSize: 50
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "trailer", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_TRAILER']
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
