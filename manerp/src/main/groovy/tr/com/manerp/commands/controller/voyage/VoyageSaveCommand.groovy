package tr.com.manerp.commands.controller.voyage

import grails.validation.Validateable
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.main.vehicle.SemiTrailer
import tr.com.manerp.business.main.vehicle.Vehicle
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefDeliveryStatus
import tr.com.manerp.business.sysref.SysrefTransportationType
import tr.com.manerp.business.sysref.SysrefVoyageDirection
import tr.com.manerp.optimization.OptimizationParameters

class VoyageSaveCommand implements Validateable
{
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
    Map<List, List> calculatedRoute
    Map sortedVendors
    Map waypoints
    OptimizationParameters optimizationParameters

    static constraints = {

    }

    Voyage rightShift(Voyage voyage)
    {
        voyage.vehicle = this.vehicle
        voyage.driver = this.driver
        voyage.trailer = this.trailer
        voyage.sysrefTransportationType = this.sysrefTransportationType
        voyage.sysrefVoyageDirection = this.sysrefVoyageDirection
        voyage.substitudeDriver = this.substitudeDriver
        voyage.transportWaybillNo = this.transportWaybillNo
        voyage.deliveryNoteNo = this.deliveryNoteNo
        voyage.sasNumber = this.sasNumber
        voyage.sysrefDeliveryStatus = this.sysrefDeliveryStatus
        voyage.calculatedRoute = this.calculatedRoute
        voyage.optimizationParameters = this.optimizationParameters
        voyage.sortedVendors = this.sortedVendors

        voyage
    }

}
