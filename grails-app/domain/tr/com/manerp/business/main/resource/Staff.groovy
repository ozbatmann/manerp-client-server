package tr.com.manerp.business.main.resource

import org.grails.databinding.BindingFormat
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.auth.AwcUser
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefDrivingType
import tr.com.manerp.business.sysref.SysrefGender
import tr.com.manerp.business.sysref.SysrefStaffContractType

class Staff {

    static auditable = true

    String code
    AwcCompany awcCompany
    AwcUser awcUser
    String firstName
    String middleName
    String lastName
    byte[] photo
    String photoName
    String photoMimeType
    String gsm
    String tcIdNumber
    RefStaffTitle refStaffTitle
    SysrefStaffContractType sysrefStaffContractType
    String drivingLicenseNumber
    Boolean hasFuelAdvance
    String homePhone
    SysrefCountry sysrefCountry
    SysrefCity sysrefCity
    SysrefDistrict sysrefDistrict
    SysrefGender sysrefGender
    String address
    String email
    SysrefDrivingType sysrefDrivingType
    Boolean active
    @BindingFormat('dd/MM/yyyy')
    Date createTime
    @BindingFormat('dd/MM/yyyy')
    Date birthDate
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static hasMany = [
            staffDocuments: StaffDocument
    ]
    static belongsTo = []

    static constraints = {
        code nullable: true, blank: true, unique: true, maxSize: 11
        awcCompany nullable: false, unique: false
        awcUser nullable: true, unique: false
        firstName nullable: false, blank: false, unique: false, maxSize: 50
        middleName nullable: true, blank: true, unique: false, maxSize: 50
        lastName nullable: false, blank: false, unique: false, maxSize: 50
        photo nullable: true, unique: false
        photoName nullable: true, blank: true, unique: false
        photoMimeType nullable: true, blank: true, unique: false
        gsm nullable: false, blank: false, unique: false, maxSize: 50
        tcIdNumber nullable: false, unique: true, maxSize: 15
        refStaffTitle nullable: false, unique: false
        sysrefStaffContractType nullable: true, unique: false
        drivingLicenseNumber nullable: true, unique: false, maxSize: 30
        hasFuelAdvance nullable: true, unique: false
        homePhone nullable: true, blank: true, unique: false, maxSize: 50
        sysrefCountry nullable: true, unique: false
        sysrefCity nullable: true, unique: false
        sysrefDistrict nullable: true, unique: false
        sysrefGender nullable: false, unique: false
        address nullable: true, blank: true, unique: false
        email nullable: true, blank: true, unique: true, maxSize: 50
        sysrefDrivingType nullable: true, unique: false
        active nullable: false, unique: false
        birthDate nullable: true, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "staff", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_STAFF']
        staffDocuments cascade: 'all-delete-orphan'
    }

    static mappedBy = {
    }

    def beforeInsert() {
        createTime = new Date()
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
