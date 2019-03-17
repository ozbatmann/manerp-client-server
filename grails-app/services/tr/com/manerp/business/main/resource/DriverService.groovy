package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.hibernate.sql.JoinType
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefDrivingType
import tr.com.manerp.business.sysref.SysrefGender
import tr.com.manerp.business.sysref.SysrefStaffContractType
import java.text.SimpleDateFormat

@Transactional
class DriverService {

    def uniqueCodeGeneratorService
    def jsonArrayParserService

    def getAllDrivers(
            Integer orderColumn,
            String orderDirection,
            Integer length,
            Integer start,
            String firstName,
            String lastName,
            String tcIdNumber,
            String drivingLicenseNumber,
            String sysrefStaffContractTypeName,
            Long awcCompamnyId
    ) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        def driverList = []
        HashMap driverListMap = new HashMap()

        def driverTotalSize
        def driverFilteredSize
        def orderColumnString
        driverTotalSize = Staff.findAllByRefStaffTitle(RefStaffTitle.findByCode("DRV")).size()
        driverFilteredSize = driverTotalSize

        try {

            switch (orderColumn) {
                case 0: orderColumnString = "opDate"; break
                case 1: orderColumnString = "firstName"; break
                case 2: orderColumnString = "lastName"; break
                case 3: orderColumnString = "tcIdNumber"; break
                case 4: orderColumnString = "drivingLicenseNumber"; break
                case 5: orderColumnString = "_sysrefDrivingType.id"; break
                case 6: orderColumnString = "active"; break
            }

            firstName = firstName.toLowerCase(new Locale("TR", "tr"))
            lastName = lastName.toLowerCase(new Locale("TR", "tr"))
            sysrefStaffContractTypeName = sysrefStaffContractTypeName.toLowerCase(new Locale("TR", "tr"))
            tcIdNumber = tcIdNumber.toLowerCase(new Locale("TR", "tr"))
            drivingLicenseNumber = drivingLicenseNumber.toLowerCase(new Locale("TR", "tr"))

            def driverCriteria = Staff.createCriteria()
            driverList = driverCriteria.list(max: length, offset: start) {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("refStaffTitle", "_refStaffTitle", JoinType.LEFT_OUTER_JOIN)
                createAlias("sysrefStaffContractType", "_sysrefStaffContractType", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompamnyId)
                eq("_refStaffTitle.code", "DRV")

                if (!firstName.equals("")) {
                    ilike('firstName', '%' + firstName + '%')
                }
                if (!lastName.equals("")) {
                    ilike('lastName', '%' + lastName + '%')
                }
                if (!firstName.equals("")) {
                    ilike('tcIdNumber', '%' + tcIdNumber + '%')
                }
                if (!drivingLicenseNumber.equals("")) {
                    ilike('drivingLicenseNumber', '%' + drivingLicenseNumber + '%')
                }
                if (!sysrefStaffContractTypeName.equals("")) {
                    ilike('_sysrefStaffContractType.name', '%' + sysrefStaffContractTypeName + '%')
                }

                order(orderColumnString, orderDirection)
            }

            def driverCountCriteria = Staff.createCriteria()
            driverFilteredSize = driverCountCriteria.count {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("refStaffTitle", "_refStaffTitle", JoinType.LEFT_OUTER_JOIN)
                createAlias("sysrefStaffContractType", "_sysrefStaffContractType", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompamnyId)
                eq("_refStaffTitle.code", "DRV")

                if (!firstName.equals("")) {
                    ilike('firstName', '%' + firstName + '%')
                }
                if (!lastName.equals("")) {
                    ilike('lastName', '%' + lastName + '%')
                }
                if (!firstName.equals("")) {
                    ilike('tcIdNumber', '%' + tcIdNumber + '%')
                }
                if (!drivingLicenseNumber.equals("")) {
                    ilike('drivingLicenseNumber', '%' + drivingLicenseNumber + '%')
                }
                if (!sysrefStaffContractTypeName.equals("")) {
                    ilike('_sysrefStaffContractType.name', '%' + sysrefStaffContractTypeName + '%')
                }

            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")

            driverListMap.driverList = driverList.collect {
                return [
                        id                     : it.id,
                        code                   : it.code,
                        firstName              : it.firstName,
                        lastName               : it.lastName,
                        tcIdNumber             : it.tcIdNumber,
                        drivingLicenseNumber   : it.drivingLicenseNumber,
                        sysrefStaffContractType: it?.sysrefStaffContractType?.name,
                        gsm                    : it?.gsm,
                        createTime             : sdf.format(it.createTime),
                        active                 : it.active
                ]
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }



        jsonMap.operationResult = [
                data  : [
                        recordsTotal   : driverTotalSize,
                        recordsFiltered: driverFilteredSize,
                        drivers        : driverListMap.driverList
                ],
                status: status,
                errors: errors
        ]

        return jsonMap
    }

    def getDetailOfDriver(Long driverId) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        def errors = null
        boolean status = true
        HashMap driverData = new HashMap()

        try {

            Staff staff = Staff.get(driverId)

            driverData = [
                    id                     : staff.id,
                    firstName              : staff.firstName,
                    middleName             : staff?.middleName,
                    lastName               : staff.lastName,
                    photo                  : staff?.photo,
                    photoName              : staff?.photoName,
                    photoMimeType          : staff?.photoMimeType,
                    gsm                    : staff.gsm,
                    tcIdNumber             : staff.tcIdNumber,
                    drivingLicenseNumber   : staff?.drivingLicenseNumber,
                    sysrefStaffContractType: staff?.sysrefStaffContractType?.name,
                    hasFuelAdvance         : staff?.hasFuelAdvance,
                    homePhone              : staff?.homePhone,
                    sysrefCountry          : staff?.sysrefCountry?.id,
                    sysrefCity             : staff?.sysrefCity?.id,
                    sysrefDistrict         : staff?.sysrefDistrict?.id,
                    sysrefGender           : staff.sysrefGender.id,
                    address                : staff?.address,
                    email                  : staff?.email,
                    sysrefDrivingType      : staff?.sysrefDrivingType?.id,
                    active                 : staff.active,
                    createTime             : staff.createTime,
                    birthDate              : staff.birthDate == null ? null : sdf.format(staff.birthDate)
            ]


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        driver: driverData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def addDriver(JSONObject driverJson) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        def errors = null
        boolean status = true
        def id = null
        def code = null

        try {

            if (Staff.findByTcIdNumber(driverJson.tcIdNumber) != null) {

                status = false
                errors = "Girdiğiniz TC Kimlik Numarası sistemde mevcut. Lütfen tekrar deneyin."

            } else {

                def uniqueEmail = driverJson.isNull("email") ? null : Staff.findByEmail(driverJson.email)

                if (uniqueEmail == null) {

                    Staff staff = new Staff()
                    staff.createTime = new Date()
                    staff.awcCompany = driverJson.isNull("awcCompanyId") ? SysCompany.get(1) : SysCompany.get(driverJson.awcCompanyId)
                    staff.firstName = driverJson.firstName
                    staff.middleName = driverJson.isNull("middleName") ? null : driverJson.middleName
                    staff.lastName = driverJson.lastName
                    staff.sysrefStaffContractType = driverJson.isNull("sysrefStaffContractType") ? null : SysrefStaffContractType.get(driverJson.sysrefStaffContractType)
                    // TODO: Uploading photo
                    staff.gsm = driverJson.gsm
                    staff.tcIdNumber = driverJson.tcIdNumber
                    staff.refStaffTitle = RefStaffTitle.findByCode('DRV')
                    staff.drivingLicenseNumber = driverJson.isNull("drivingLicenseNumber") ? null : driverJson.drivingLicenseNumber
                    staff.hasFuelAdvance = driverJson.isNull("hasFuelAdvance") ? null : driverJson.hasFuelAdvance
                    staff.homePhone = driverJson.isNull("homePhone") ? null : driverJson.homePhone
                    staff.sysrefCountry = driverJson.isNull("sysrefCountry") ? null : SysrefCountry.get(driverJson.sysrefCountry)
                    staff.sysrefCity = driverJson.isNull("sysrefCity") ? null : SysrefCity.get(driverJson.sysrefCity)
                    staff.sysrefDistrict = driverJson.isNull("sysrefDistrict") ? null : SysrefDistrict.get(driverJson.sysrefDistrict)
                    staff.sysrefGender = SysrefGender.get(driverJson.sysrefGender)
                    staff.address = driverJson.isNull("address") ? null : driverJson.address
                    staff.email = driverJson.isNull("email") ? null : driverJson.email
                    staff.sysrefDrivingType = driverJson.isNull("sysrefDrivingType") ? null : SysrefDrivingType.get(driverJson.sysrefDrivingType)
                    staff.active = driverJson.active
                    staff.birthDate = driverJson.isNull("birthDate") ? null : sdf.parse(driverJson.birthDate)

                    staff.save(flush: true, failOnError: true)
                    staff.code = uniqueCodeGeneratorService.generateUniqueCode("SFR", staff.id)
                    staff.save(flush: true, failOnError: true)

                    id = staff.id
                    code = staff.code

                } else {

                    status = false
                    errors = "Girdiğiniz e-mail sistemde mevcut. Lütfen tekrar deneyin."

                }

            }


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

    def updateDriver(JSONObject driverJson) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        def errors = null
        boolean status = true

        try {

            Staff staff = Staff.get(driverJson.id)

            if (staff.tcIdNumber != driverJson.tcIdNumber && Staff.findByTcIdNumber(driverJson.tcIdNumber) != null) {

                status = false
                errors = "Girdiğiniz TC Kimlik Numarası sistemde mevcut. Lütfen tekrar deneyin."

            } else {

                def uniqueEmail = driverJson.isNull("email") ? null : Staff.findByEmail(driverJson.email)

                if (uniqueEmail != null && staff.email != driverJson.email) {

                    errors = "Girdiğiniz e-mail sistemde mevcut. Lütfen tekrar deneyin."

                } else {

                    staff.firstName = driverJson.firstName
                    staff.middleName = driverJson.isNull("middleName") ? null : driverJson.middleName
                    staff.lastName = driverJson.lastName
                    staff.sysrefStaffContractType = driverJson.isNull("sysrefStaffContractType") ? null : SysrefStaffContractType.get(driverJson.sysrefStaffContractType)
                    // TODO: Uploading photo
                    staff.gsm = driverJson.gsm
                    staff.tcIdNumber = driverJson.tcIdNumber
                    staff.refStaffTitle = RefStaffTitle.findByCode('DRV')
                    staff.drivingLicenseNumber = driverJson.isNull("drivingLicenseNumber") ? null : driverJson.drivingLicenseNumber
                    staff.hasFuelAdvance = driverJson.isNull("hasFuelAdvance") ? null : driverJson.hasFuelAdvance
                    staff.homePhone = driverJson.isNull("homePhone") ? null : driverJson.homePhone
                    staff.sysrefCountry = driverJson.isNull("sysrefCountry") ? null : SysrefCountry.get(driverJson.sysrefCountry)
                    staff.sysrefCity = driverJson.isNull("sysrefCity") ? null : SysrefCity.get(driverJson.sysrefCity)
                    staff.sysrefDistrict = driverJson.isNull("sysrefDistrict") ? null : SysrefDistrict.get(driverJson.sysrefDistrict)
                    staff.sysrefGender = SysrefGender.get(driverJson.sysrefGender)
                    staff.address = driverJson.isNull("address") ? null : driverJson.address
                    staff.email = driverJson.isNull("email") ? null : driverJson.email
                    staff.sysrefDrivingType = driverJson.isNull("sysrefDrivingType") ? null : SysrefDrivingType.get(driverJson.sysrefDrivingType)
                    staff.active = driverJson.active
                    staff.birthDate = driverJson.isNull("birthDate") ? null : sdf.parse(driverJson.birthDate)

                    staff.save(flush: true, failOnError: true)

                }

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

    def jsonArray2LongArray(JSONArray jsonArray) {
        long[] longArray = new long[jsonArray.length()]
        for (int i = 0; i < longArray.length; ++i) {
            longArray[i] = jsonArray.optInt(i)
        }
        return longArray
    }

    def deleteDrivers(JSONArray ids) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            long[] _ids = jsonArrayParserService.jsonArray2LongArray(ids)

            def driverList = Staff.createCriteria().list {
                inList("id", _ids)
            }

            driverList.collect {
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

    def deleteDriver(Long driverId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            // TODO: Check if the staff is a driver and search for voyages of driver if he has a voyage do not delete him or delete voyage also
            Staff driver = Staff.get(driverId)
            def voyages = Voyage.findAllByDriver(driver)

            if (voyages.size() != 0) {

                status = false
                errors = "Silmek istediğiniz şoförün sevkiyat kaydı bulunmaktadır."

            } else {

                driver.delete(flush: true, failOnError: true)

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
        def sysrefCountries = []
        def sysrefGender = []
        def sysrefDrivingTypes = []
        def sysrefStaffContractTypes = []

        try {

            // TODO: Change cities and districts according to sysrefCountry
            sysrefCountries = SysrefCountry.findAllByActive(true).collect {
                return [id: it.id, name: it.name]
            }
            sysrefGender = SysrefGender.findAllByActive(true).collect {
                return [id: it.id, name: it.name]
            }
            sysrefDrivingTypes = SysrefDrivingType.findAllByActive(true).collect {
                return [id: it.id, name: it.name]
            }
            sysrefStaffContractTypes = SysrefStaffContractType.findAllByActive(true).collect {
                return [id: it.id, name: it.name]
            }


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        sysrefCountries         : sysrefCountries,
                        sysrefGender            : sysrefGender,
                        sysrefDrivingTypes      : sysrefDrivingTypes,
                        sysrefStaffContractTypes: sysrefStaffContractTypes
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

}
