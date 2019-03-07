package tr.com.manerp.business.main.voyage

import org.grails.databinding.BindingFormat

class VoyageRoute {

    static auditable = true

    Location location
    Voyage voyage
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static constraints = {
        location nullable: false, unique: false
        voyage nullable: false, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "voyage_route", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_VOYAGE_ROUTE']
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
