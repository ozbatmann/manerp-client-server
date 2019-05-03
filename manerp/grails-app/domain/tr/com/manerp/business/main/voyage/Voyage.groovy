package tr.com.manerp.business.main.voyage

import grails.databinding.BindingFormat
import net.kaleidos.hibernate.usertype.JsonbMapType
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.domain.BusinessDomain
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.main.vehicle.SemiTrailer
import tr.com.manerp.business.main.vehicle.Vehicle
import tr.com.manerp.business.sysref.SysrefDeliveryStatus
import tr.com.manerp.business.sysref.SysrefTransportationType
import tr.com.manerp.business.sysref.SysrefVoyageDirection
import tr.com.manerp.optimization.OptimizationParameters

class Voyage implements BusinessDomain
{
    static auditable = true

    @BindingFormat('dd/MM/yyyy hh:MM')
    Date startDate
    @BindingFormat('dd/MM/yyyy hh:MM')
    Date endDate
    Vehicle vehicle
    Staff driver
    SemiTrailer trailer
    SysrefTransportationType sysrefTransportationType
    SysrefVoyageDirection sysrefVoyageDirection
    Location loadingLocation
    Location dumpingLocation
    Staff substitudeDriver
    String transportWaybillNo
    String deliveryNoteNo
    String sasNumber
    SysrefDeliveryStatus sysrefDeliveryStatus
    OptimizationParameters optimizationParameters
    Map calculatedRoute // calculated according to optimizationParameters.waypoints

//    static hasMany = [orders: Order] many-to-many relationship with Voyage defined in VoyageOrder cross domain
    static hasMany = [
        routes: VoyageRoute // waypoints
    ]

    static constraints = {
        code nullable: false, blank: false, unique: ['sysCompany'], maxSize: 8
        sysCompany nullable: false, unique: false
        startDate nullable: false, unique: false
        endDate nullable: true, unique: false
        vehicle nullable: false, unique: false
        driver nullable: false, unique: false
        trailer nullable: true, unique: false
        sysrefTransportationType nullable: true, unique: false
        sysrefVoyageDirection nullable: false, unique: false
        loadingLocation nullable: true, unique: false
        dumpingLocation nullable: true, unique: false
        substitudeDriver nullable: true, unique: false
        transportWaybillNo nullable: true, blank: true, unique: false
        deliveryNoteNo nullable: true, blank: true, unique: false
        sasNumber nullable: true, blank: true, unique: false
        sysrefDeliveryStatus nullable: true, unique: false
        optimizationParameters nullable: true, unique: false
        calculatedRoute nullable: true, blank: true, unique: false
    }

    static mapping = {
        routes cascade: 'all-delete-orphan'
        calculatedRoute type: JsonbMapType
    }

    // TODO: change
    def beforeValidate()
    {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }

    def setRandomCode()
    {
        setRandomCode(Voyage)
    }
}
