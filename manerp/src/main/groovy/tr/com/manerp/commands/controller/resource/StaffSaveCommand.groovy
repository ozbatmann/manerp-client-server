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

class StaffSaveCommand implements Validateable
{
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
        firstName nullable: false, blank: false, maxSize: 30
        middleName nullable: true, blank: true, maxSize: 30
        lastName nullable: false, blank: false, maxSize: 50
        email email: true, blank: false, nullable: false, maxSize: 50
        sysrefGender nullable: true
        tcIdNumber nullable: false, maxSize: 11
        birthDate nullable: true
        sysrefCountry nullable: true
        sysrefCity nullable: true
        sysrefDistrict nullable: true
        address nullable: true, blank: true, maxSize: 255
        homePhone nullable: true, blank: true, maxSize: 15
        gsmNo nullable: false, blank: false, maxSize: 15

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
        staff.firstName = this.firstName
        if ( this.middleName ) staff.setMiddleName(this.middleName)
        staff.lastName = this.lastName
        staff.email = this.email
        staff.sysrefGender = this.sysrefGender ?: null
        staff.tcIdNumber = this.tcIdNumber ?: null
        staff.birthDate = this.birthDate ?: null
        staff.sysrefCountry = this.sysrefCountry ?: null
        staff.sysrefCity = this.sysrefCity ?: null
        staff.sysrefDistrict = this.sysrefDistrict ?: null
        staff.address = this.address ?: null
        staff.homePhone = this.homePhone ?: null
        staff.gsmNo = this.gsmNo ?: null

        staff.refStaffTitle = this.refStaffTitle
        staff.sysrefStaffContractType = this.sysrefStaffContractType
        staff.drivingLicenseNumber = this.drivingLicenseNumber ?: null
        staff.sysrefDrivingType = this.sysrefDrivingType ?: null
        staff.sysrefDriverState = this.sysrefDriverState ?: null

        staff.setRandomCode()
        staff.active = true
        staff.sysrefDriverState = SysrefDriverState.findByCode('IDLE')

        staff.username = this.username ?: null

        staff
    }

}
