package tr.com.manerp.business.main.resource

import grails.databinding.BindingFormat


class StaffDocument {

    static auditable = true

    Staff staff
    byte[] document
    String documentName
    String documentMimeType
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static constraints = {
        staff nullable: false, unique: false
        document nullable: false, unique: false
        documentName nullable: false, blank: false, unique: false
        documentMimeType nullable: false, blank: false, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "staff_document", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_STAFF_DOCUMENT']
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
