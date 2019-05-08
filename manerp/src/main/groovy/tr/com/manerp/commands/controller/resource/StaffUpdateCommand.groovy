package tr.com.manerp.commands.controller.resource

import grails.databinding.BindingFormat
import grails.validation.Validateable
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefDriverState
import tr.com.manerp.business.sysref.SysrefDrivingType
import tr.com.manerp.business.sysref.SysrefGender
import tr.com.manerp.business.sysref.SysrefStaffContractType

class StaffUpdateCommand implements Validateable
{
    String id
    String firstName
    String middleName
    String lastName
    String email
    SysrefGender sysrefGender
    Long tcIdNumber
    @BindingFormat('dd/MM/yyyy')
    Date birthDate
    String gsmNo
    String homePhone
    SysrefCountry sysrefCountry
    SysrefCity sysrefCity
    SysrefDistrict sysrefDistrict
    String address

    RefStaffTitle refStaffTitle
    SysrefStaffContractType sysrefStaffContractType
    String drivingLicenseNumber
    SysrefDrivingType sysrefDrivingType
    SysrefDriverState sysrefDriverState

    // user attributes
    String username

    static constraints = {
        // Person constraints, these constraints shared with Staff class
        id nullable: false, blank: false, maxSize: 32
        firstName nullable: true, blank: true, maxSize: 30
        middleName nullable: true, blank: true, maxSize: 30
        lastName nullable: true, blank: true, maxSize: 50
        email email: true, blank: true, nullable: true, maxSize: 50
        sysrefGender nullable: true
        tcIdNumber nullable: true, maxSize: 11
        birthDate nullable: true
        sysrefCountry nullable: true
        sysrefCity nullable: true
        sysrefDistrict nullable: true
        address nullable: true, blank: true, maxSize: 255
        homePhone nullable: true, blank: true, maxSize: 15
        gsmNo nullable: true, blank: true, maxSize: 15

        // user attributes
        username nullable: true, blank: true

        refStaffTitle nullable: true, unique: false
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

    Staff rightShift(Staff staff)
    {
        if ( this.firstName ) staff.firstName = this.firstName
        if ( this.middleName ) staff.setMiddleName(this.middleName)
        if ( this.lastName ) staff.lastName = this.lastName
        if ( this.email ) staff.email = this.email
        if ( this.sysrefGender ) staff.sysrefGender = this.sysrefGender ?: null
        if ( this.tcIdNumber ) staff.tcIdNumber = this.tcIdNumber ?: null
        if ( this.birthDate ) staff.birthDate = this.birthDate ?: null
        if ( this.sysrefCountry ) staff.sysrefCountry = this.sysrefCountry ?: null
        if ( this.sysrefCity ) staff.sysrefCity = this.sysrefCity ?: null
        if ( this.sysrefDistrict ) staff.sysrefDistrict = this.sysrefDistrict ?: null
        if ( this.address ) staff.address = this.address ?: null
        if ( this.firstName ) staff.homePhone = this.homePhone ?: null
        if ( this.homePhone ) staff.gsmNo = this.gsmNo ?: null

        if ( this.refStaffTitle ) staff.refStaffTitle = this.refStaffTitle
        if ( this.sysrefStaffContractType ) staff.sysrefStaffContractType = this.sysrefStaffContractType
        if ( this.drivingLicenseNumber ) staff.drivingLicenseNumber = this.drivingLicenseNumber ?: null
        if ( this.sysrefDrivingType ) staff.sysrefDrivingType = this.sysrefDrivingType ?: null
        if ( this.sysrefDriverState ) staff.sysrefDriverState = this.sysrefDriverState ?: null

        staff
    }
}
