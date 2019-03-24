package tr.com.manerp.commands.controller.vendor

import grails.validation.Validateable
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict

class VendorSaveCommand implements Validateable {

    String id
    Company company
    SysCompany sysCompany
    String title
    SysrefCity sysrefCity
    SysrefCountry sysrefCountry
    SysrefDistrict sysrefDistrict
    String address
    String phone
    Boolean active
    String locationName
    String locationLatitude
    String locationLongitude

    static constraints = {
        importFrom(Vendor)

        id maxSize: 32
        locationName nullable: false, maxSize: 100
        locationLatitude nullable: true, maxSize: 100
        locationLongitude nullable: true, maxSize: 100
    }

    Vendor rightShift(Vendor vendor) {

        Location location = new Location()

        location.name = this.locationName
        location.latitude = this.locationLatitude
        location.longitude = this.locationLongitude
        location.sysCompany = this.sysCompany

        vendor.company = this.company
        vendor.sysCompany = this.sysCompany
        vendor.title = this.title
        vendor.sysrefCity = this.sysrefCity
        vendor.sysrefCountry = this.sysrefCountry
        vendor.sysrefDistrict = this.sysrefDistrict
        vendor.address = this.address
        vendor.phone = this.phone
        vendor.location = location

        vendor
    }

    def beforeValidate() {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

}
