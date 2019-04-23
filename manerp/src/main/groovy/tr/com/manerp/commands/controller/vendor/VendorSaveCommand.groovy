package tr.com.manerp.commands.controller.vendor

import grails.validation.Validateable
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict

class VendorSaveCommand implements Validateable
{

    String id
    Company company
    String title
    SysrefCity sysrefCity
    SysrefCountry sysrefCountry
    SysrefDistrict sysrefDistrict
    String address
    String phone
    Boolean active
    String locationName
    String lat
    String lng

    static constraints = {
        importFrom(Vendor)

        id maxSize: 32
        locationName nullable: true, maxSize: 100
        lat nullable: true, maxSize: 100
        lng nullable: true, maxSize: 100
    }

    Vendor rightShift(Vendor vendor)
    {
        Location location = new Location()

        location.active = true
        location.name = this.locationName
        location.latitude = this.lat
        location.longitude = this.lng
        location.sysCompany = this.company.sysCompany

        vendor.company = this.company
        vendor.sysCompany = this.company.sysCompany
        vendor.title = this.title
        vendor.sysrefCity = this.sysrefCity
        vendor.sysrefCountry = this.sysrefCountry
        vendor.sysrefDistrict = this.sysrefDistrict
        vendor.address = this.address
        vendor.phone = this.phone
        vendor.location = location
        vendor.active = this.active

        vendor
    }

}
