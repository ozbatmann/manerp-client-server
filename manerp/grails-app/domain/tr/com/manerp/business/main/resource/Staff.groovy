package tr.com.manerp.business.main.resource

import grails.util.Holders
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.auth.User
import tr.com.manerp.base.domain.BusinessDomain
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefDriverState
import tr.com.manerp.business.sysref.SysrefDrivingType
import tr.com.manerp.business.sysref.SysrefStaffContractType
import tr.com.manerp.common.Person

class Staff implements BusinessDomain, Person
{

    static auditable = true

    User user
    RefStaffTitle refStaffTitle
    SysrefStaffContractType sysrefStaffContractType
    String drivingLicenseNumber
    SysrefDrivingType sysrefDrivingType
    SysrefDriverState sysrefDriverState

    static hasMany = [
        staffDocuments: StaffDocument
    ]
    static belongsTo = []

    static constraints = {

        // Person constraints, these constraints shared with Staff class
        photo nullable: true, blank: true, unique: false, maxSize: Holders.config.manerp.postgresql.maxByteSize
        photoName nullable: true, blank: true, unique: false
        photoMimeType nullable: true, blank: true, unique: false
        firstName nullable: false, blank: false, unique: false, maxSize: 30
        middleName nullable: true, blank: true, unique: false, maxSize: 30
        lastName nullable: false, blank: false, unique: false, maxSize: 50
        email email: true, blank: false, nullable: false, unique: ['sysCompany'], maxSize: 50
        sysrefGender nullable: true, unique: false
        tcIdNumber nullable: false, unique: ['sysCompany'], maxSize: 11
        birthDate nullable: true, unique: false
        sysrefCountry nullable: true, unique: false
        sysrefCity nullable: true, unique: false
        sysrefDistrict nullable: true, unique: false
        address nullable: true, blank: true, unique: false, maxSize: 255
        homePhone nullable: true, blank: true, unique: false, maxSize: 15
        gsmNo nullable: false, blank: false, unique: false, maxSize: 15

        // BusinessDomain constraints
        sysCompany nullable: false, unique: false
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8

        user nullable: true, unique: false
        refStaffTitle nullable: false, unique: false
        sysrefStaffContractType nullable: false, unique: false

        drivingLicenseNumber nullable: true, unique: false, maxSize: 30,
            validator: { val, obj ->
                obj?.refStaffTitle?.code == 'DRV' ? val != null : true
            }
        sysrefDrivingType nullable: true, unique: false
        sysrefDriverState nullable: true, unique: false,
            validator: { val, obj ->
                obj?.refStaffTitle?.code == 'DRV' ? val != null : true
            }
    }

    static mapping = {
        staffDocuments cascade: 'all-delete-orphan'
    }

    String getFullName()
    {
        return "${this.firstName}${this.middleName != null ? ' ' + this.middleName : ''} ${this.lastName}"
    }

    String getFirstAndMiddleName()
    {
        return "${this.firstName}${this.middleName != null ? ' ' + this.middleName : ''}"
    }

    //TODO:change
    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

    def setRandomCode()
    {
        setRandomCode(Staff)
    }


}
