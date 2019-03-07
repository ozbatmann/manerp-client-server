package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject
import tr.com.manerp.business.main.vehicle.Vehicle

@Transactional
class RefWorkingAreaService {

    def getAllRefWorkingAreas(
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
        def refWorkingAreaList = []

        def refWorkingAreaTotalSize
        def refWorkingAreaFilteredSize
        def orderColumnString
        refWorkingAreaTotalSize = RefWorkingArea.count
        refWorkingAreaFilteredSize = refWorkingAreaTotalSize

        try {

            switch (orderColumn) {
                case 0: orderColumnString = "opDate"; break
                case 1: orderColumnString = "name"; break
                case 2: orderColumnString = "code"; break
                case 3: orderColumnString = "active"; break
            }

            name = name.toLowerCase(new Locale("TR", "tr"))
            code = code.toLowerCase(new Locale("TR", "tr"))

            def refWorkingAreaCriteria = RefWorkingArea.createCriteria()
            refWorkingAreaList = refWorkingAreaCriteria.list(max: length, offset: start) {
                if (!name.equals('')) {
                    ilike('name', '%' + name + '%')
                }
                if (!code.equals('')) {
                    ilike('code', '%' + code + '%')
                }
                order(orderColumnString, orderDirection)
            }

            def refWorkingAreaCountCriteria = RefWorkingArea.createCriteria()
            refWorkingAreaFilteredSize = refWorkingAreaCountCriteria.count {
                if (!name.equals('')) {
                    ilike('name', '%' + name + '%')
                }
                if (!code.equals('')) {
                    ilike('code', '%' + code + '%')
                }
            }

            refWorkingAreaList.collect {
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
                        recordsTotal   : refWorkingAreaTotalSize,
                        recordsFiltered: refWorkingAreaFilteredSize,
                        refWorkingAreas: refWorkingAreaList
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }


    def getDetailOfRefWorkingArea(Long refWorkingAreaId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        HashMap refWorkingAreaData = new HashMap()

        try {

            def refWorkingArea = RefWorkingArea.get(refWorkingAreaId)

            refWorkingAreaData = [
                    id    : refWorkingArea.id,
                    name  : refWorkingArea.name,
                    code  : refWorkingArea.code,
                    active: refWorkingArea.active
            ]

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        refWorkingArea: refWorkingAreaData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap
    }

    def addRefWorkingArea(JSONObject refWorkingAreaJson) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true
        def id = null

        try {

            def uniqueObj = RefWorkingArea.findByCode(refWorkingAreaJson.code)

            if (uniqueObj != null) {

                errors = "uniqueError"

            } else {

                RefWorkingArea refWorkingArea = new RefWorkingArea()
                refWorkingArea.name = refWorkingAreaJson.name
                refWorkingArea.code = refWorkingAreaJson.code
                refWorkingArea.active = refWorkingAreaJson.active
                refWorkingArea.save(flush: true, failOnError: true)

                id = refWorkingArea.id

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

    def updateRefWorkingArea(JSONObject refWorkingAreaJson) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            def uniqueObj = RefWorkingArea.findByCode(refWorkingAreaJson.code)

            if (uniqueObj != null) {

                errors = "uniqueError"

            } else {

                RefWorkingArea refWorkingArea = RefWorkingArea.get(refWorkingAreaJson.id)
                refWorkingArea.name = refWorkingAreaJson.name
                refWorkingArea.code = refWorkingAreaJson.code
                refWorkingArea.active = refWorkingAreaJson.active
                refWorkingArea.save(flush: true, failOnError: true)

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

    def removeRefWorkingArea(Long refWorkingAreaId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            RefWorkingArea refWorkingArea = RefWorkingArea.get(refWorkingAreaId)
            def vehicle = Vehicle.findAllByRefWorkingArea(refWorkingArea)

            if (vehicle.size() == 0) {

                refWorkingArea.delete(flush: true, failOnError: true)

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
