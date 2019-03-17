package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import org.hibernate.sql.JoinType
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefGender
import java.text.SimpleDateFormat

@Transactional
class StaffService {

    def uniqueCodeGeneratorService

    def getAllStaffs(
            Integer orderColumn,
            String orderDirection,
            Integer length,
            Integer start,
            String firstName,
            String lastName,
            String tcIdNumber,
            String refStaffTitleName,
            Long awcCompanyId
    ) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        def staffList = []

        def staffTotalSize
        def staffFilteredSize
        def orderColumnString
        staffTotalSize = Staff.count
        staffFilteredSize = staffTotalSize

        try {

            switch (orderColumn) {
                case 0: orderColumnString = "opDate"; break
                case 1: orderColumnString = "firstName"; break
                case 2: orderColumnString = "lastName"; break
                case 3: orderColumnString = "tcIdNumber"; break
                case 4: orderColumnString = "_refStaffTitle.id"; break
                case 5: orderColumnString = "active"; break
            }

            firstName = firstName.toLowerCase(new Locale("TR", "tr"))
            lastName = lastName.toLowerCase(new Locale("TR", "tr"))
            tcIdNumber = tcIdNumber.toLowerCase(new Locale("TR", "tr"))
            refStaffTitleName = refStaffTitleName.toLowerCase(new Locale("TR", "tr"))

            def staffCriteria = Staff.createCriteria()
            staffList = staffCriteria.list(max: length, offset: start) {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("refStaffTitle", "_refStaffTitle", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompanyId.toLong())

                if (!firstName.equals("")) {
                    ilike('firstName', '%' + firstName + '%')
                }
                if (!lastName.equals("")) {
                    ilike('lastName', '%' + lastName + '%')
                }
                if (!firstName.equals("")) {
                    ilike('tcIdNumber', '%' + tcIdNumber + '%')
                }
                if (!refStaffTitleName.equals("")) {
                    ilike('_refStaffTitle.name', '%' + refStaffTitleName + '%')
                }

                order(orderColumnString, orderDirection)
            }

            def staffCountCriteria = Staff.createCriteria()
            staffFilteredSize = staffCountCriteria.count {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("refStaffTitle", "_refStaffTitle", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompanyId.toLong())

                if (!firstName.equals("")) {
                    ilike('firstName', '%' + firstName + '%')
                }
                if (!lastName.equals("")) {
                    ilike('lastName', '%' + lastName + '%')
                }
                if (!firstName.equals("")) {
                    ilike('tcIdNumber', '%' + tcIdNumber + '%')
                }
                if (!refStaffTitleName.equals("")) {
                    ilike('_refStaffTitle.name', '%' + refStaffTitleName + '%')
                }

            }

            staffList.collect {
                return [
                        id               : it.id,
                        firstName        : it.firstName,
                        lastName         : it.lastName,
                        tcIdNumber       : it.tcIdNumber,
                        refStaffTitleName: it.refStaffTitle.name,
                        active           : it.active
                ]
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        recordsTotal   : staffTotalSize,
                        recordsFiltered: staffFilteredSize,
                        staffs         : staffList
                ],
                status: status,
                errors: errors
        ]

        return jsonMap
    }

    def getDetailOfStaff(Long staffId) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        def errors = null
        boolean status = true
        HashMap staffData = new HashMap()

        try {

            Staff staff = Staff.get(staffId)

            staffData = [
                    id            : staff.id,
                    code          : staff.code,
                    firstName     : staff.firstName,
                    middleName    : staff?.middleName,
                    lastName      : staff.lastName,
                    photo         : staff?.photo,
                    photoName     : staff?.photoName,
                    photoMimeType : staff?.photoMimeType,
                    gsm           : staff.gsm,
                    tcIdNumber    : staff.tcIdNumber,
                    refStaffTitle : staff.refStaffTitle.id,
                    homePhone     : staff?.homePhone,
                    sysrefCountry : staff?.sysrefCountry?.id,
                    sysrefCity    : staff?.sysrefCity?.id,
                    sysrefDistrict: staff?.sysrefDistrict?.id,
                    sysrefGender  : staff.sysrefGender.id,
                    address       : staff?.address,
                    email         : staff?.email,
                    active        : staff.active,
                    birthDate     : staff.birthDate == null ? null : sdf.format(staff.birthDate)
            ]


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        staff: staffData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def addStaff(JSONObject staffJson) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        def errors = null
        def status = true
        def id = null
        def code = null

        try {


            if (Staff.findByTcIdNumber(staffJson.tcIdNumber) != null) {

                errors = "tcIdNumberExist"

            } else {

                def uniqueEmail = staffJson.isNull("email") ? null : Staff.findByEmail(staffJson.email)

                if (uniqueEmail == null) {

                    Staff staff = new Staff()
                    staff.awcCompany = SysCompany.get(staffJson.awcCompany)
                    staff.firstName = staffJson.firstName
                    staff.middleName = staffJson.isNull("middleName") ? null : staffJson.middleName
                    staff.lastName = staffJson.lastName
                    // TODO: Uploading photo

                    staff.gsm = staffJson.gsm
                    staff.tcIdNumber = staffJson.tcIdNumber
                    staff.refStaffTitle = RefStaffTitle.get(staffJson.refStaffTitle)
                    staff.homePhone = staffJson.isNull("homePhone") ? null : staffJson.homePhone
                    staff.sysrefCountry = staffJson.isNull("sysrefCountry") ? null : SysrefCountry.get(staffJson.sysrefCountry)
                    staff.sysrefCity = staffJson.isNull("sysrefCity") ? null : SysrefCity.get(staffJson.sysrefCity)
                    staff.sysrefDistrict = staffJson.isNull("sysrefDistrict") ? null : SysrefDistrict.get(staffJson.sysrefDistrict)
                    staff.sysrefGender = SysrefGender.get(staffJson.sysrefGender)
                    staff.address = staffJson.isNull("address") ? null : staffJson.address
                    staff.email = staffJson.isNull("email") ? null : staffJson.email
                    staff.active = staffJson.active
                    staff.birthDate = staffJson.isNull("birthDate") ? null : sdf.parse(staffJson.birthDate)

                    staff.save(flush: true, failOnError: true)
                    staff.code = uniqueCodeGeneratorService.generateUniqueCode("PRN", staff.id)
                    staff.save(flush: true, failOnError: true)

                    id = staff.id
                    code = staff.code

                } else {

                    errors = "emailExist"

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

    def updateStaff(JSONObject staffJson) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        def errors = null
        boolean status = true

        try {

            if (Staff.findByTcIdNumber(staffJson.tcIdNumber) != null) {

                errors = "tcIdNumberExist"

            } else {

                def uniqueEmail = staffJson.isNull("email") ? null : Staff.findByEmail(staffJson.email)

                if (uniqueEmail == null) {

                    Staff staff = Staff.get(staffJson.id)
                    staff.awcCompany = SysCompany.get(staffJson.awcCompany)
                    staff.firstName = staffJson.firstName
                    staff.middleName = staffJson.isNull("middleName") ? null : staffJson.middleName
                    staff.lastName = staffJson.lastName
                    // TODO: Uploading photo

                    staff.gsm = staffJson.gsm
                    staff.tcIdNumber = staffJson.tcIdNumber
                    staff.refStaffTitle = RefStaffTitle.get(staffJson.refStaffTitle)
                    staff.homePhone = staffJson.isNull("homePhone") ? null : staffJson.homePhone
                    staff.sysrefCountry = staffJson.isNull("sysrefCountry") ? null : SysrefCountry.get(staffJson.sysrefCountry)
                    staff.sysrefCity = staffJson.isNull("sysrefCity") ? null : SysrefCity.get(staffJson.sysrefCity)
                    staff.sysrefDistrict = staffJson.isNull("sysrefDistrict") ? null : SysrefDistrict.get(staffJson.sysrefDistrict)
                    staff.sysrefGender = SysrefGender.get(staffJson.sysrefGender)
                    staff.address = staffJson.isNull("address") ? null : staffJson.address
                    staff.email = staffJson.isNull("email") ? null : staffJson.email
                    staff.active = staffJson.active
                    staff.birthDate = staffJson.isNull("birthDate") ? null : sdf.parse(staffJson.birthDate)

                    staff.save(flush: true, failOnError: true)

                } else {

                    errors = "emailExist"

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

    def deleteStaff(Long staffId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            // TODO: Check if the staff is a driver and search for voyages of driver if he has a voyage do not delete him or delete voyage also
            Staff driver = Staff.get(staffId)
            def voyages = Voyage.findAllByDriver(driver)

            if (voyages.size() != 0) {

                errors = "hasVoyages"

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
        def refStaffTitles = []
        def sysrefCountries = []
        def sysrefGender = []

        try {

            refStaffTitles = RefStaffTitle.findAllByActive(true).collect {
                return [id: it.id, name: it.name, code: it.code]
            }
            // TODO: Change cities and districts according to sysrefCountry
            sysrefCountries = SysrefCountry.findAllByActive(true).collect {
                return [id: it.id, name: it.name, code: it.code]
            }
            sysrefGender = SysrefGender.findAllByActive(true).collect {
                return [id: it.id, name: it.name, code: it.code]
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        refStaffTitles : refStaffTitles,
                        sysrefCountries: sysrefCountries,
                        sysrefGender   : sysrefGender,
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

}
