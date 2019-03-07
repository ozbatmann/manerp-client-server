package tr.com.manerp.business.main.vehicle

import org.grails.databinding.BindingFormat

class VehicleDocument {

    static auditable = true

    Vehicle vehicle
    byte[] document
    String documentName
    String documentMimeType
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static constraints = {
        vehicle nullable: false, unique: false
        document nullable: false, unique: false
        documentName nullable: false, blank: false, unique: false
        documentMimeType nullable: false, blank: false, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "vehicle_document", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_VEHICLE_DOCUMENT']
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
