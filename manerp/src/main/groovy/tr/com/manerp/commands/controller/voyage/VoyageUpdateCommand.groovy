package tr.com.manerp.commands.controller.voyage

import grails.validation.Validateable
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.main.vehicle.SemiTrailer
import tr.com.manerp.business.main.vehicle.Vehicle
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefDeliveryStatus
import tr.com.manerp.business.sysref.SysrefTransportationType
import tr.com.manerp.business.sysref.SysrefVoyageDirection

class VoyageUpdateCommand implements Validateable
{
    String id
    Order _order
    Vehicle vehicle
    Staff driver
    SemiTrailer trailer
    SysrefTransportationType sysrefTransportationType
    SysrefVoyageDirection sysrefVoyageDirection
    SysrefDeliveryStatus sysrefDeliveryStatus
    Staff substitudeDriver // yedek sofor
    String transportWaybillNo
    String deliveryNoteNo
    String sasNumber
    Map calculatedRoute

    static constraints = {
        id nullable: false, blank: false, maxSize: 32
    }

    Voyage rightShift(Voyage voyage)
    {
        if ( this.vehicle ) voyage.vehicle = this.vehicle
        if ( this.driver ) voyage.driver = this.driver
        if ( this.trailer ) voyage.trailer = this.trailer
        if ( this.sysrefTransportationType ) voyage.sysrefTransportationType = this.sysrefTransportationType
        if ( this.sysrefVoyageDirection ) voyage.sysrefVoyageDirection = this.sysrefVoyageDirection
        if ( this.substitudeDriver ) voyage.substitudeDriver = this.substitudeDriver
        if ( this.transportWaybillNo ) voyage.transportWaybillNo = this.transportWaybillNo
        if ( this.deliveryNoteNo ) voyage.deliveryNoteNo = this.deliveryNoteNo
        if ( this.sasNumber ) voyage.sasNumber = this.sasNumber
        if ( this.sysrefDeliveryStatus ) voyage.sysrefDeliveryStatus = this.sysrefDeliveryStatus
        if ( this.calculatedRoute ) voyage.calculatedRoute = calculatedRoute
        voyage
    }
}
