package tr.com.manerp.business.main.company

import org.grails.databinding.BindingFormat
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict

class Vendor {

    static auditable = true

    Company company
    String code
    String title
   /* TODO: SysrefCity sysrefCity
    SysrefCountry sysrefCountry
    SysrefDistrict sysrefDistrict*/
    String city
    String district
    String address
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static constraints = {
        company nullable: false, unique: false
        code nullable: true, blank: true, unique: true, maxSize: 11
        title nullable: true, blank: true, unique: true, maxSize: 150
        city nullable: true, blank: true, unique: true, maxSize: 150
        district nullable: true, blank: true, unique: true, maxSize: 150
        address nullable: true, blank: true, unique: true, maxSize: 255
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "vendor", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_VENDOR']
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
