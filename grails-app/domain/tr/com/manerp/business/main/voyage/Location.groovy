package tr.com.manerp.business.main.voyage

import org.grails.databinding.BindingFormat

class Location {

    static auditable = true

    String googleKey
    String locationName
    String latitude
    String longitude
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static constraints = {
        googleKey nullable: false, blank: false, unique: false
        locationName nullable: false, blank: false, unique: false
        latitude nullable: false, blank: false, unique: false
        longitude nullable: false, blank: false, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "location", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_LOCATION']
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
