package tr.com.manerp.business.main.company

import org.grails.databinding.BindingFormat
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.ref.RefCompanySector
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCompanyClass
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefNaceCode

class Company {

    static auditable = true

    String code
    AwcCompany awcCompany
    String name
    String title
    SysrefCountry sysrefCountry
    SysrefCity sysrefCity
    SysrefDistrict sysrefDistrict
    String address
    String customerRepresentativeName // sil
    String phone
    String phone2
    String fax
    String gsm
    String webAddress
    String email
    RefCompanySector refCompanySector
    Staff customerRepresentative
    Integer numberOfStaff
    String employerRegistrationNo
    String tradeRegistrationNo
    String taxOffice
    String taxNumber
    SysrefNaceCode sysrefNaceCode
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static hasMany = [
            vendors: Vendor
    ]

    static constraints = {
        code nullable: true, blank: true, unique: true, maxSize: 11
        awcCompany nullable: false, unique: false
        name nullable: false, blank: false, unique: false, maxSize: 50
        title nullable: false, blank: false, unique: true, maxSize: 50
        customerRepresentativeName nullable: false, blank: false, unique: true, maxSize: 150 // sil
        sysrefCountry nullable: true, unique: false
        sysrefCity nullable: true, unique: false
        sysrefDistrict nullable: true, unique: false
        address nullable: false, blank: false, unique: false
        phone nullable: false, blank: false, unique: false, maxSize: 20
        phone2 nullable: true, blank: true, unique: false, maxSize: 20
        fax nullable: true, blank: true, unique: false, maxSize: 20
        gsm nullable: true, blank: true, unique: false, maxSize: 20
        webAddress nullable: true, blank: true, unique: false, maxSize: 50
        email nullable: false, blank: false, unique: false, maxSize: 50
        refCompanySector nullable: true, unique: false
        customerRepresentative nullable: true, unique: false
        numberOfStaff nullable: true, unique: false
        employerRegistrationNo nullable: true, blank: true, unique: true, maxSize: 30
        tradeRegistrationNo nullable: false, blank: false, unique: true, maxSize: 30
        taxOffice nullable: true, blank: true, unique: false, maxSize: 255
        taxNumber nullable: true, blank: true, unique: true, maxSize: 50
        sysrefNaceCode nullable: true, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "company", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_COMPANY']
        vendors cascade: 'all-delete-orphan'
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
