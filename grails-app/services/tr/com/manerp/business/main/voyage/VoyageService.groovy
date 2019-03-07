package tr.com.manerp.business.main.voyage

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.hibernate.sql.JoinType
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.main.vehicle.Vehicle
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefDeliveryStatus
import tr.com.manerp.business.sysref.SysrefTransportationType
import tr.com.manerp.business.sysref.SysrefVoyageDirection

import java.text.SimpleDateFormat

@Transactional
class VoyageService {

    def uniqueCodeGeneratorService
    def jsonArrayParserService

    def getAllVoyages(
            Integer orderColumn,
            String orderDirection,
            Integer length,
            Integer start,
            String companyName,
            String code,
            String plateNumber,
            String driverName,
            String orderCode,
            String loadingLocation,
            String dumpingLocation,
            Long awcCompanyId
    ) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        def voyageList = []
        HashMap voyageListMap = new HashMap()

        def voyageTotalSize
        def voyageFilteredSize
        def orderColumnString
        voyageTotalSize = Voyage.count
        voyageFilteredSize = voyageTotalSize

        try {

            switch (orderColumn) {
                case 0: orderColumnString = "opDate"; break
                case 1: orderColumnString = "_company.id"; break
                case 2: orderColumnString = "code"; break
                case 3: orderColumnString = "plateNumber"; break
                case 4: orderColumnString = "_driver.id"; break
                case 5: orderColumnString = "_order.id"; break
                case 6: orderColumnString = "loadingLocation"; break
                case 7: orderColumnString = "dumpingLocation"; break
                case 8: orderColumnString = "active"; break
            }

            companyName = companyName.toLowerCase(new Locale("TR", "tr"))
            code = code.toLowerCase(new Locale("TR", "tr"))
            plateNumber = plateNumber.toLowerCase(new Locale("TR", "tr"))
            driverName = driverName.toLowerCase(new Locale("TR", "tr"))
            orderCode = orderCode.toLowerCase(new Locale("TR", "tr"))
            loadingLocation = loadingLocation.toLowerCase(new Locale("TR", "tr"))
            dumpingLocation = dumpingLocation.toLowerCase(new Locale("TR", "tr"))

            def voyageCriteria = Voyage.createCriteria()
            voyageList = voyageCriteria.list(max: length, offset: start) {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("company", "_company", JoinType.LEFT_OUTER_JOIN)
                createAlias("vehicle", "_vehicle", JoinType.LEFT_OUTER_JOIN)
                createAlias("trailer", "_trailer", JoinType.LEFT_OUTER_JOIN)
                createAlias("dorset", "_dorset", JoinType.LEFT_OUTER_JOIN)
                createAlias("driver", "_driver", JoinType.LEFT_OUTER_JOIN)
                createAlias("order", "_order", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompanyId)

                if (!companyName.equals("")) {
                    ilike('_company.name', '%' + companyName + '%')
                }
                if (!code.equals("")) {
                    ilike('code', '%' + code + '%')
                }
                if (!plateNumber.equals("")) {
                    ilike('_vehicle.plateNumber', '%' + plateNumber + '%')
                    ilike('_trailer.plateNumber', '%' + plateNumber + '%')
                    ilike('_dorset.plateNumber', '%' + plateNumber + '%')
                }
                if (!driverName.equals("")) {
                    ilike('_driver.firstName', '%' + driverName + '%')
                    ilike('_driver.lastName', '%' + driverName + '%')
                }
                if (!orderCode.equals("")) {
                    ilike('_order.code', '%' + orderCode + '%')
                }
                if (!loadingLocation.equals("")) {
                    ilike('loadingLocation', '%' + loadingLocation + '%')
                }
                if (!dumpingLocation.equals("")) {
                    ilike('dumpingLocation', '%' + dumpingLocation + '%')
                }

                order(orderColumnString, orderDirection)
            }

            def voyageCountCriteria = Voyage.createCriteria()
            voyageFilteredSize = voyageCountCriteria.count {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("company", "_company", JoinType.LEFT_OUTER_JOIN)
                createAlias("vehicle", "_vehicle", JoinType.LEFT_OUTER_JOIN)
                createAlias("trailer", "_trailer", JoinType.LEFT_OUTER_JOIN)
                createAlias("dorset", "_dorset", JoinType.LEFT_OUTER_JOIN)
                createAlias("driver", "_driver", JoinType.LEFT_OUTER_JOIN)
                createAlias("order", "_order", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompanyId)

                if (!companyName.equals("")) {
                    ilike('_company.name', '%' + companyName + '%')
                }
                if (!code.equals("")) {
                    ilike('code', '%' + code + '%')
                }
                if (!plateNumber.equals("")) {
                    ilike('_vehicle.plateNumber', '%' + plateNumber + '%')
                    ilike('_trailer.plateNumber', '%' + plateNumber + '%')
                    ilike('_dorset.plateNumber', '%' + plateNumber + '%')
                }
                if (!driverName.equals("")) {
                    ilike('_driver.firstName', '%' + driverName + '%')
                    ilike('_driver.lastName', '%' + driverName + '%')
                }
                if (!orderCode.equals("")) {
                    ilike('_order.code', '%' + orderCode + '%')
                }
                if (!loadingLocation.equals("")) {
                    ilike('loadingLocation', '%' + loadingLocation + '%')
                }
                if (!dumpingLocation.equals("")) {
                    ilike('dumpingLocation', '%' + dumpingLocation + '%')
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")

            voyageListMap.voyageList = voyageList.collect {
                return [
                        id                   : it.id,
                        companyName          : it.company.name,
                        vehiclePlateNumber   : it.vehicle.plateNumber,
                        trailerPlateNumber   : it.trailer == null ? null : it.trailer.plateNumber,
                        dorsetPlateNumber    : it.dorset == null ? null : it.dorset.plateNumber,
                        driverName           : it.driver.firstName + " " + it.driver.lastName,
                        orderCode            : it.order.code,
                        sysrefVoyageDirection: it.sysrefVoyageDirection == null ? null : it.sysrefVoyageDirection.name,
                        createTime           : sdf(it.order.orderDate),
                        loadingLocation      : it.loadingLocation,
                        dumpingLocation      : it.dumpingLocation,
                        active               : it.active
                ]
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        recordsTotal   : voyageTotalSize,
                        recordsFiltered: voyageFilteredSize,
                        voyages        : voyageListMap.voyageList
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def getDetailOfVoyage(Long voyageId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        HashMap voyageData = new HashMap()

        try {

            Voyage voyage = Voyage.get(voyageId)

            voyageData = [
                    id                      : voyage.id,
                    code                    : voyage.code,
                    company                 : voyage.company.name,
                    vehicle                 : voyage.vehicle.plateNumber,
                    driver                  : voyage.driver.firstName + (voyage.driver.middleName == null ? "" : " " + voyage.driver.middleName) + " " + voyage.driver.lastName,
                    dorset                  : voyage.dorset == null ? null : voyage.dorset.plateNumber,
                    trailer                 : voyage.trailer == null ? null : voyage.trailer.plateNumber,
                    order                   : voyage.order.code,
                    sysrefTransportationType: voyage.sysrefTransportationType == null ? null : voyage.sysrefTransportationType.name,
                    sysrefVoyageDirection   : voyage.sysrefVoyageDirection == null ? null : voyage.sysrefVoyageDirection.name,
                    loadingLocation         : voyage.loadingLocation,
                    dumpingLocation         : voyage.dumpingLocation,
                    substitudeDriver        : voyage.substitudeDriver == null ? null : voyage.substitudeDriver.firstName + " " + voyage.substitudeDriver.lastName,
                    transportWaybillNo      : voyage.transportWaybillNo,
                    deliveryNoteNo          : voyage.deliveryNoteNo,
                    sasNumber               : voyage.sasNumber,
                    sysrefDeliveryStatus    : voyage.sysrefDeliveryStatus == null ? null : voyage.sysrefDeliveryStatus.name,
                    active                  : voyage.active
            ]

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        voyage: voyageData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def addVoyage(JSONObject voyageJson) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        def id = null
        def code = null

        try {

            Voyage voyage = new Voyage()
            voyage.awcCompany = AwcCompany.get(1)
            voyage.company = Company.get(voyageJson.company)
            voyage.vehicle = Vehicle.get(voyageJson.vehicle)
            voyage.driver = Staff.get(voyageJson.driver)
            voyage.order = Order.get(voyageJson.order)
            voyage.sysrefTransportationType = voyageJson.isNull("sysrefTransportationType") ? null : SysrefTransportationType.get(voyageJson.sysrefTransportationType)
            voyage.sysrefVoyageDirection = voyageJson.isNull("sysrefVoyageDirection") ? null : SysrefVoyageDirection.get(voyageJson.sysrefVoyageDirection)
            voyage.loadingLocation = voyageJson.loadingLocation
            voyage.dumpingLocation = voyageJson.dumpingLocation
            voyage.substitudeDriver = voyageJson.isNull("substitudeDriver") ? null : Staff.get(voyageJson.substitudeDriver)
            voyage.transportWaybillNo = voyageJson.transportWaybillNo
            voyage.deliveryNoteNo = voyageJson.deliveryNoteNo
            voyage.sasNumber = voyageJson.sasNumber
            voyage.sysrefDeliveryStatus = voyageJson.isNull("sysrefVoyageDirection") ? null : SysrefDeliveryStatus.get(voyageJson.sysrefVoyageDirection)
            voyage.active = voyageJson.active

            voyage.save(flush: true, failOnError: true)
            voyage.code = uniqueCodeGeneratorService.generateUniqueCode("VOY", voyage.id)
            voyage.save(flush: true, failOnError: true)

            id = voyage.id
            code = voyage.code

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        id  : id,
                        code: code
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def updateVoyage(JSONObject voyageJson) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true

        try {

            Voyage voyage = Voyage.get(voyageJson.id)
            voyage.awcCompany = AwcCompany.get(voyageJson.awcCompany)
            voyage.company = Company.get(voyageJson.company)
            voyage.vehicle = Vehicle.get(voyageJson.vehicle)
            voyage.driver = Staff.get(voyageJson.driver)
            /*        voyage.dorset = Dorset.get(voyageJson.dorset)
                    voyage.trailer = Trailer.get(voyageJson.trailer)*/
            voyage.order = Order.get(voyageJson.order)
            voyage.sysrefTransportationType = voyageJson.isNull("sysrefTransportationType") ? null : SysrefTransportationType.get(voyageJson.sysrefTransportationType)
            voyage.sysrefVoyageDirection = voyageJson.isNull("sysrefVoyageDirection") ? null : SysrefVoyageDirection.get(voyageJson.sysrefVoyageDirection)
            voyage.loadingLocation = voyageJson.loadingLocation
            voyage.dumpingLocation = voyageJson.dumpingLocation
            voyage.substitudeDriver = voyageJson.isNull("substitudeDriver") ? null : Staff.get(voyageJson.substitudeDriver)
            voyage.transportWaybillNo = voyageJson.transportWaybillNo
            voyage.deliveryNoteNo = voyageJson.deliveryNoteNo
            voyage.sasNumber = voyageJson.sasNumber
            voyage.sysrefDeliveryStatus = voyageJson.isNull("sysrefVoyageDirection") ? null : SysrefDeliveryStatus.get(voyageJson.sysrefVoyageDirection)
            voyage.active = voyageJson.active

            voyage.save(flush: true, failOnError: true)

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def deleteVoyages(JSONArray ids) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            long[] _ids = jsonArrayParserService.jsonArray2LongArray(ids)

            def voyageList = Voyage.createCriteria().list {
                inList("id", _ids)
            }

            voyageList.collect {
                it.delete(flush: true, failOnError: true)
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def deleteVoyage(Long voyageId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            Voyage voyage = Voyage.get(voyageId)
            voyage.delete(flush: true, failOnError: true)


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def getDropDownSources() {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true
        def sysrefVoyageDirection = []
        def drivers = []
        def orders = []
        def companies = []
        def vehicles = []

        try {

            // TODO: Request awcCompanyId
            AwcCompany awcCompany = AwcCompany.get(1)

            RefStaffTitle refStaffTitle = RefStaffTitle.findByCodeAndAwcCompany("DRV", awcCompany)

            sysrefVoyageDirection = SysrefVoyageDirection.findAllByActive(true).collect {
                return [id: it.id, text: it.name]
            }
            drivers = Staff.findAllByActiveAndRefStaffTitleAndAwcCompany(true, refStaffTitle, awcCompany).collect {
                return [id: it.id, text: it.firstName + (it.middleName == null ? "" : " " + it.middleName) + " " + it.lastName]
            }
            orders = Order.findAllByActiveAndAwcCompany(true, awcCompany).collect {
                return [id: it.id, text: it.name + " - " + it.code]
            }
            companies = Company.findByAwcCompany(awcCompany).collect {
                return [id: it.id, text: it.title]
            }
            vehicles = Vehicle.findByActiveAndAwcCompany(true, awcCompany).collect {
                return [id: it.id, text: it.plateNumber]
            }


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        vehicles             : vehicles,
                        drivers              : drivers,
                        orders               : orders,
                        companies            : companies,
                        sysrefVoyageDirection: sysrefVoyageDirection
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

}
