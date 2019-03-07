package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject
import tr.com.manerp.business.main.resource.Staff

@Transactional
class RefStaffTitleService {

    def getAllRefStaffTitles(
            Integer orderColumn,
            String orderDirection,
            Integer length,
            Integer start,
            String name,
            String code
    ) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        def refStaffTitleList = []

        def refStaffTitleTotalSize
        def refStaffTitleFilteredSize
        def orderColumnString
        refStaffTitleTotalSize = RefStaffTitle.count
        refStaffTitleFilteredSize = refStaffTitleTotalSize

        try {

            switch (orderColumn) {
                case 0: orderColumnString = "opDate"; break
                case 1: orderColumnString = "name"; break
                case 2: orderColumnString = "code"; break
                case 3: orderColumnString = "active"; break
            }

            name = name.toLowerCase(new Locale("TR", "tr"))
            code = code.toLowerCase(new Locale("TR", "tr"))

            def refStaffTitleCriteria = RefStaffTitle.createCriteria()
            refStaffTitleList = refStaffTitleCriteria.list(max: length, offset: start) {
                if (!name.equals('')) {
                    ilike('name', '%' + name + '%')
                }
                if (!code.equals('')) {
                    ilike('code', '%' + code + '%')
                }
                order(orderColumnString, orderDirection)
            }

            def refStaffTitleCountCriteria = RefStaffTitle.createCriteria()
            refStaffTitleFilteredSize = refStaffTitleCountCriteria.count {
                if (!name.equals('')) {
                    ilike('name', '%' + name + '%')
                }
                if (!code.equals('')) {
                    ilike('code', '%' + code + '%')
                }
            }

            refStaffTitleList.collect {
                return [
                        id    : it.id,
                        name  : it.name,
                        code  : it.code,
                        active: it.active,
                        opDate: it.opDate
                ]
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        recordsTotal   : refStaffTitleTotalSize,
                        recordsFiltered: refStaffTitleFilteredSize,
                        refStaffTitles : refStaffTitleList
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }


    def getDetailOfRefStaffTitle(Long refStaffTitleId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        HashMap refStaffTitleData = new HashMap()

        try {

            def refStaffTitle = RefStaffTitle.get(refStaffTitleId)

            refStaffTitleData = [
                    id    : refStaffTitle.id,
                    name  : refStaffTitle.name,
                    code  : refStaffTitle.code,
                    active: refStaffTitle.active
            ]

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        refStaffTitle: refStaffTitleData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap
    }

    def addRefStaffTitle(JSONObject refStaffTitleJson) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true
        def id = null

        try {

            def uniqueObj = RefStaffTitle.findByCode(refStaffTitleJson.code)

            if (uniqueObj != null) {

                errors = "uniqueError"

            } else {

                RefStaffTitle refStaffTitle = new RefStaffTitle()
                refStaffTitle.name = refStaffTitleJson.name
                refStaffTitle.code = refStaffTitleJson.code
                refStaffTitle.active = refStaffTitleJson.active
                refStaffTitle.save(flush: true, failOnError: true)

                id = refStaffTitle.id

            }
        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        id: id
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def updateRefStaffTitle(JSONObject refStaffTitleJson) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            def uniqueObj = RefStaffTitle.findByCode(refStaffTitleJson.code)

            if (uniqueObj != null) {

                errors = "uniqueError"

            } else {

                RefStaffTitle refStaffTitle = RefStaffTitle.get(refStaffTitleJson.id)
                refStaffTitle.name = refStaffTitleJson.name
                refStaffTitle.code = refStaffTitleJson.code
                refStaffTitle.active = refStaffTitleJson.active
                refStaffTitle.save(flush: true, failOnError: true)

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

    def removeRefStaffTitle(Long refStaffTitleId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            RefStaffTitle refStaffTitle = RefStaffTitle.get(refStaffTitleId)
            def staff = Staff.findAllByRefStaffTitle(refStaffTitle)

            if (staff.size() == 0) {

                refStaffTitle.delete(flush: true, failOnError: true)

            } else {

                errors = "exist"

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
}
