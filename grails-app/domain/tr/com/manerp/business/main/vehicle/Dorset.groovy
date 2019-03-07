package tr.com.manerp.business.main.vehicle

import org.grails.databinding.BindingFormat
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.main.company.Company

class Dorset {

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
        table name: "dorset", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_DORSET']
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
