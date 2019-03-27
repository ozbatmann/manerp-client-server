package tr.com.manerp.commands.controller.voyage

import grails.validation.Validateable
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.main.vehicle.SemiTrailer
import tr.com.manerp.business.main.vehicle.Vehicle
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefDeliveryStatus
import tr.com.manerp.business.sysref.SysrefTransportationType
import tr.com.manerp.business.sysref.SysrefVoyageDirection

class VoyageSaveCommand implements Validateable {

    String id
    String code
    SysCompany sysCompany
    Company company
    Vehicle vehicle
    Staff driver
    SemiTrailer trailer
    Order order
    SysrefTransportationType sysrefTransportationType
    SysrefVoyageDirection sysrefVoyageDirection
    Staff substitudeDriver // yedek sofor
    String transportWaybillNo
    String deliveryNoteNo
    String sasNumber
    SysrefDeliveryStatus sysrefDeliveryStatus
    String loadingLocationName
    String loadingLocationLatitude
    String loadingLocationLongitude
    String dumpingLocationName
    String dumpingLocationLatitude
    String dumpingLocationLongitude


    static constraints = {
        id maxSize: 32
        loadingLocationName nullable: true, maxSize: 100
        loadingLocationLatitude nullable: true, maxSize: 100
        loadingLocationLongitude nullable: true, maxSize: 100
        dumpingLocationName nullable: true, maxSize: 100
        dumpingLocationLatitude nullable: true, maxSize: 100
        dumpingLocationLongitude nullable: true, maxSize: 100
    }

    Voyage rightShift(Voyage voyage) {

        Location loadingLocation = new Location()
        loadingLocation.name = this.loadingLocationName
        loadingLocation.latitude = this.loadingLocationLatitude
        loadingLocation.longitude = this.loadingLocationLongitude
        loadingLocation.sysCompany = this.sysCompany

        Location dumpingLocation = new Location()
        dumpingLocation.name = this.dumpingLocationName
        dumpingLocation.latitude = this.dumpingLocationLatitude
        dumpingLocation.longitude = this.dumpingLocationLongitude
        dumpingLocation.sysCompany = this.sysCompany

        voyage.loadingLocation = loadingLocation
        voyage.dumpingLocation = dumpingLocation
        voyage.sysCompany = this.sysCompany
        voyage.code = this.code
        voyage.company = this.company
        voyage.vehicle = this.vehicle
        voyage.driver = this.driver
        voyage.trailer = this.trailer
        voyage.order = this.order
        voyage.sysrefTransportationType = this.sysrefTransportationType
        voyage.sysrefVoyageDirection = this.sysrefVoyageDirection
        voyage.substitudeDriver = this.substitudeDriver
        voyage.transportWaybillNo = this.transportWaybillNo
        voyage.deliveryNoteNo = this.deliveryNoteNo
        voyage.sasNumber = this.sasNumber
        voyage.sysrefDeliveryStatus = this.sysrefDeliveryStatus

        voyage
    }

    def beforeValidate() {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

}
