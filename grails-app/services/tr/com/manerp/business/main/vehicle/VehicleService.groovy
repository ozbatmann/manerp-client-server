package tr.com.manerp.business.main.vehicle

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import org.hibernate.sql.JoinType
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.ref.RefWorkingArea
import tr.com.manerp.business.sysref.SysrefVehicleOwner
import tr.com.manerp.business.sysref.SysrefVehicleType

import java.text.SimpleDateFormat

@Transactional
class VehicleService {

    def uniqueCodeGeneratorService
    def jsonArrayParserService

    def getAllVehicles(
            Integer orderColumn,
            String orderDirection,
            Integer length,
            Integer start,
            String brand,
            String plateNumber,
            String fleetCardNumber,
            String sysrefVehicleTypeName,
            String sysrefVehicleOwnerName,
            Long awcCompanyId
    ) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        def vehicleList = []
        HashMap vehicleListMap = new HashMap()

        def vehicleTotalSize
        def vehicleFilteredSize
        def orderColumnString
        vehicleTotalSize = Vehicle.count
        vehicleFilteredSize = vehicleTotalSize

        try {

            switch (orderColumn) {
                case 0: orderColumnString = "opDate"; break
                case 1: orderColumnString = "brand"; break
                case 2: orderColumnString = "plateNumber"; break
                case 3: orderColumnString = "fleetCardNumber"; break
                case 4: orderColumnString = "_sysrefVehicleType.id"; break
                case 5: orderColumnString = "_sysrefVehicleOwner.id"; break
                case 6: orderColumnString = "licenseNumber"; break
                case 7: orderColumnString = "active"; break
            }

            brand = brand.toLowerCase(new Locale("TR", "tr"))
            plateNumber = plateNumber.toLowerCase(new Locale("TR", "tr"))
            fleetCardNumber = fleetCardNumber.toLowerCase(new Locale("TR", "tr"))
            sysrefVehicleTypeName = sysrefVehicleTypeName.toLowerCase(new Locale("TR", "tr"))
            sysrefVehicleOwnerName = sysrefVehicleOwnerName.toLowerCase(new Locale("TR", "tr"))

            def vehicleCriteria = Vehicle.createCriteria()
            vehicleList = vehicleCriteria.list(max: length, offset: start) {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("sysrefVehicleType", "_sysrefVehicleType", JoinType.LEFT_OUTER_JOIN)
                createAlias("sysrefVehicleOwner", "_sysrefVehicleOwner", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompanyId)

                if (!brand.equals("")) {
                    ilike('brand', '%' + brand + '%')
                }
                if (!plateNumber.equals("")) {
                    ilike('plateNumber', '%' + plateNumber + '%')
                }
                if (!fleetCardNumber.equals("")) {
                    ilike('fleetCardNumber', '%' + fleetCardNumber + '%')
                }
                if (!sysrefVehicleTypeName.equals("")) {
                    ilike('_sysrefVehicleType.name', '%' + sysrefVehicleTypeName + '%')
                }
                if (!sysrefVehicleOwnerName.equals("")) {
                    ilike('_sysrefVehicleOwner.name', '%' + sysrefVehicleOwnerName + '%')
                }

                order(orderColumnString, orderDirection)
            }

            def vehicleCountCriteria = Vehicle.createCriteria()
            vehicleFilteredSize = vehicleCountCriteria.count {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("sysrefVehicleType", "_sysrefVehicleType", JoinType.LEFT_OUTER_JOIN)
                createAlias("sysrefVehicleOwner", "_sysrefVehicleOwner", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompanyId)

                if (!brand.equals("")) {
                    ilike('brand', '%' + brand + '%')
                }
                if (!plateNumber.equals("")) {
                    ilike('plateNumber', '%' + plateNumber + '%')
                }
                if (!fleetCardNumber.equals("")) {
                    ilike('fleetCardNumber', '%' + fleetCardNumber + '%')
                }
                if (!sysrefVehicleTypeName.equals("")) {
                    ilike('_sysrefVehicleType.name', '%' + sysrefVehicleTypeName + '%')
                }
                if (!sysrefVehicleOwnerName.equals("")) {
                    ilike('_sysrefVehicleOwner.name', '%' + sysrefVehicleOwnerName + '%')
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")

            vehicleListMap.vehicleList = vehicleList.collect {
                return [
                        id                    : it.id,
                        code                  : it.code,
                        createTime            : sdf.format(it.createTime),
                        brand                 : it.brand,
                        kgsNo                 : it?.kgsNo,
                        vehicleOwnerName      : it?.vehicleOwnerName,
                        plateNumber           : it.plateNumber,
                        fleetCardNumber       : it.fleetCardNumber,
                        sysrefVehicleTypeName : it.sysrefVehicleType.name,
                        sysrefVehicleOwnerName: it.sysrefVehicleOwner.name,
                        insuranceEndDate      : sdf.format(it.insuranceEndDate),
                        active                : it.active
                ]
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        recordsTotal   : vehicleTotalSize,
                        recordsFiltered: vehicleFilteredSize,
                        vehicles       : vehicleListMap.vehicleList
                ],
                status: status,
                errors: errors
        ]

        return jsonMap
    }

    def getDetailOfVehicle(Long vehicleId) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")
        def errors = null
        boolean status = true
        HashMap vehicleData = new HashMap()

        try {

            Vehicle vehicle = Vehicle.get(vehicleId)

            vehicleData = [
                    id                            : vehicle.id,
                    code                          : vehicle.code,
                    brand                         : vehicle.brand,
                    fleetCardNumber               : vehicle.fleetCardNumber,
                    plateNumber                   : vehicle.plateNumber,
                    numberOfSensors               : vehicle?.numberOfSensors,
                    hasLogo                       : vehicle.hasLogo,
                    km                            : vehicle?.km,
                    isDualRegime                  : vehicle.isDualRegime,
                    refWorkingArea                : vehicle?.refWorkingArea?.name,
                    sysrefVehicleType             : vehicle.sysrefVehicleType.name,
                    sysrefVehicleOwner            : vehicle.sysrefVehicleOwner.name,
                    vehicleOwnerName              : vehicle?.vehicleOwnerName,
                    insuranceStartDate            : vehicle.insuranceStartDate == null ? null : sdf.format(vehicle.insuranceStartDate),
                    insuranceEndDate              : vehicle.insuranceEndDate == null ? null : sdf.format(vehicle.insuranceEndDate),
                    kgsNo                         : vehicle?.kgsNo,
                    ogsNo                         : vehicle?.ogsNo,
                    description                   : vehicle?.description,
                    operationInsuranceNotification: vehicle?.operationInsuranceNotification,
                    annualInsurance               : vehicle?.annualInsurance,
                    active                        : vehicle.active
            ]


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        vehicle: vehicleData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def addVehicle(JSONObject vehicleJson) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        def errors = null
        boolean status = true
        def id = null
        def code = null

        try {

            Vehicle vehicle = new Vehicle()
            vehicle.createTime = new Date()
            vehicle.awcCompany = vehicleJson.isNull("awcCompanyId") ? AwcCompany.get(1) : AwcCompany.get(vehicleJson.awcCompanyId)
            vehicle.brand = vehicleJson.brand
            vehicle.fleetCardNumber = vehicleJson.fleetCardNumber
            vehicle.plateNumber = vehicleJson.plateNumber
            vehicle.purchaseDate = vehicleJson.isNull("purchaseDate") ? null : sdf.parse(vehicleJson.purchaseDate)
            vehicle.numberOfSensors = vehicleJson.isNull("numberOfSensors") ? null : vehicleJson.numberOfSensors
            vehicle.hasLogo = vehicleJson.hasLogo
            vehicle.km = vehicleJson.isNull("km") ? null : vehicleJson.km
            vehicle.isDualRegime = vehicleJson.isDualRegime
            vehicle.refWorkingArea = vehicleJson.isNull("refWorkingArea") ? null : RefWorkingArea.get(vehicleJson.refWorkingArea)
            vehicle.sysrefVehicleType = SysrefVehicleType.get(vehicleJson.sysrefVehicleType)
            vehicle.sysrefVehicleOwner = SysrefVehicleOwner.get(vehicleJson.sysrefVehicleOwner)
            vehicle.vehicleOwnerName = vehicleJson.isNull("vehicleOwnerName") ? null : vehicleJson.vehicleOwnerName
            vehicle.insuranceStartDate = vehicleJson.isNull("insuranceStartDate") ? null : sdf.parse(vehicleJson.insuranceStartDate)
            vehicle.insuranceEndDate = vehicleJson.isNull("insuranceEndDate") ? null : sdf.parse(vehicleJson.insuranceEndDate)
            vehicle.kgsNo = vehicleJson.isNull("kgsNo") ? null : vehicleJson.kgsNo
            vehicle.ogsNo = vehicleJson.isNull("ogsNo") ? null : vehicleJson.ogsNo
            vehicle.fuelKit = vehicleJson.isNull("fuelKit") ? null : vehicleJson.fuelKit
            vehicle.description = vehicleJson.isNull("description") ? null : vehicleJson.description
            vehicle.operationInsuranceNotification = vehicleJson.isNull("operationInsuranceNotification") ? null : vehicleJson.operationInsuranceNotification
            vehicle.annualInsurance = vehicleJson.isNull("annualInsurance") ? null : vehicleJson.annualInsurance
            vehicle.active = vehicleJson.active

            vehicle.save(flush: true, failOnError: true)
            vehicle.code = uniqueCodeGeneratorService.generateUniqueCode("ARC", vehicle.id)
            vehicle.save(flush: true, failOnError: true)

            id = vehicle.id
            code = vehicle.code


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

    def updateVehicle(JSONObject vehicleJson) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        def errors = null
        boolean status = true

        try {

            Vehicle vehicle = Vehicle.get(vehicleJson.id)
            vehicle.brand = vehicleJson.brand
            vehicle.fleetCardNumber = vehicleJson.fleetCardNumber
            vehicle.plateNumber = vehicleJson.plateNumber
            vehicle.purchaseDate = vehicleJson.isNull("purchaseDate") ? null : sdf.parse(vehicleJson.purchaseDate)
            vehicle.numberOfSensors = vehicleJson.isNull("numberOfSensors") ? null : vehicleJson.numberOfSensors
            vehicle.hasLogo = vehicleJson.hasLogo
            vehicle.km = vehicleJson.isNull("km") ? null : vehicleJson.km
            vehicle.isDualRegime = vehicleJson.isDualRegime
            vehicle.refWorkingArea = vehicleJson.isNull("refWorkingArea") ? null : RefWorkingArea.get(vehicleJson.refWorkingArea)
            vehicle.sysrefVehicleType = SysrefVehicleType.get(vehicleJson.sysrefVehicleType)
            vehicle.sysrefVehicleOwner = SysrefVehicleOwner.get(vehicleJson.sysrefVehicleOwner)
            vehicle.vehicleOwnerName = vehicleJson.isNull("vehicleOwnerName") ? null : vehicleJson.vehicleOwnerName
            vehicle.insuranceStartDate = vehicleJson.isNull("insuranceStartDate") ? null : sdf.parse(vehicleJson.insuranceStartDate)
            vehicle.insuranceEndDate = vehicleJson.isNull("insuranceEndDate") ? null : sdf.parse(vehicleJson.insuranceEndDate)
            vehicle.kgsNo = vehicleJson.isNull("kgsNo") ? null : vehicleJson.kgsNo
            vehicle.ogsNo = vehicleJson.isNull("ogsNo") ? null : vehicleJson.ogsNo
            vehicle.fuelKit = vehicleJson.isNull("fuelKit") ? null : vehicleJson.fuelKit
            vehicle.description = vehicleJson.isNull("description") ? null : vehicleJson.description
            vehicle.operationInsuranceNotification = vehicleJson.isNull("operationInsuranceNotification") ? null : vehicleJson.operationInsuranceNotification
            vehicle.annualInsurance = vehicleJson.isNull("annualInsurance") ? null : vehicleJson.annualInsurance
            vehicle.active = vehicleJson.active

            vehicle.save(flush: true, failOnError: true)

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

    def deleteVehicles(JSONArray ids) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            long[] _ids = jsonArrayParserService.jsonArray2LongArray(ids)

            def vehicleList = Vehicle.createCriteria().list {
                inList("id", _ids)
            }

            vehicleList.collect {
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

    def deleteVehicle(Long vehicleId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            Vehicle vehicle = Vehicle.get(vehicleId)
            def voyages = Voyage.findAllByVehicle(vehicle)

            if (voyages.size() != 0) {

                status = false
                errors = "Silmek istediğiniz aracın sevkiyat kaydı bulunmaktadır."

            } else {

                vehicle.delete(flush: true, failOnError: true)

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

    def getDropDownSources() {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true
        def refWorkingAreas = []
        def sysrefVehicleTypes = []
        def sysrefVehicleOwners = []

        try {

            refWorkingAreas = RefWorkingArea.findAllByActive(true).collect {
                return [id: it.id, name: it.name]
            }
            sysrefVehicleTypes = SysrefVehicleType.findAllByActive(true).collect {
                return [id: it.id, name: it.name]
            }
            sysrefVehicleOwners = SysrefVehicleOwner.findAllByActive(true).collect {
                return [id: it.id, name: it.name]
            }


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        refWorkingAreas    : refWorkingAreas,
                        sysrefVehicleTypes : sysrefVehicleTypes,
                        sysrefVehicleOwners: sysrefVehicleOwners,
                ],
                status: status,
                errors: errors
        ]


        return jsonMap

    }

}
