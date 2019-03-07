package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject
import tr.com.manerp.business.main.company.Company

@Transactional
class RefCompanySectorService {

    def getAllRefCompanySectors(
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
        def staffList = []

        def refCompanySectorTotalSize
        def refCompanySectorFilteredSize
        def orderColumnString
        refCompanySectorTotalSize = RefCompanySector.count
        refCompanySectorFilteredSize = refCompanySectorTotalSize

        try {

            switch (orderColumn) {
                case 0: orderColumnString = "opDate"; break
                case 1: orderColumnString = "name"; break
                case 2: orderColumnString = "code"; break
                case 3: orderColumnString = "active"; break
            }

            name = name.toLowerCase(new Locale("TR", "tr"))
            code = code.toLowerCase(new Locale("TR", "tr"))

            def refCompanySectorCriteria = RefCompanySector.createCriteria()
            def refCompanySectorList = refCompanySectorCriteria.list(max: length, offset: start) {
                if (!name.equals('')) {
                    ilike('name', '%' + name + '%')
                }
                if (!code.equals('')) {
                    ilike('code', '%' + code + '%')
                }
                order(orderColumnString, orderDirection)
            }

            def refCompanySectorCountCriteria = RefCompanySector.createCriteria()
            refCompanySectorFilteredSize = refCompanySectorCountCriteria.count {
                if (!name.equals('')) {
                    ilike('name', '%' + name + '%')
                }
                if (!code.equals('')) {
                    ilike('code', '%' + code + '%')
                }
            }

            refCompanySectorList.collect {
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
                        recordsTotal     : refCompanySectorTotalSize,
                        recordsFiltered  : refCompanySectorFilteredSize,
                        refCompanySectors: refCompanySectorList
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def getDetailOfRefCompanySector(Long refCompanySectorId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        HashMap refCompanySectorData = new HashMap()

        try {

            def refCompanySector = RefCompanySector.get(refCompanySectorId)

            refCompanySectorData = [
                    id    : refCompanySector.id,
                    name  : refCompanySector.name,
                    code  : refCompanySector.code,
                    active: refCompanySector.active
            ]

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        refCompanySector: refCompanySectorData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap
    }

    def addRefCompanySector(JSONObject refCompanySectorJson) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true
        def id = null

        try {

            def uniqueObj = RefCompanySector.findByCode(refCompanySectorJson.code)

            if (uniqueObj != null) {

                errors = "uniqueError"

            } else {

                RefCompanySector refCompanySector = new RefCompanySector()
                refCompanySector.name = refCompanySectorJson.name
                refCompanySector.code = refCompanySectorJson.code
                refCompanySector.active = refCompanySectorJson.active
                refCompanySector.save(flush: true, failOnError: true)

                id = refCompanySector.id

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

    def updateRefCompanySector(JSONObject refCompanySectorJson) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            def uniqueObj = RefCompanySector.findByCode(refCompanySectorJson.code)

            if (uniqueObj != null) {

                errors = "uniqueError"

            } else {

                RefCompanySector refCompanySector = RefCompanySector.get(refCompanySectorJson.id)
                refCompanySector.name = refCompanySectorJson.name
                refCompanySector.code = refCompanySectorJson.code
                refCompanySector.active = refCompanySectorJson.active
                refCompanySector.save(flush: true, failOnError: true)

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

    def removeRefCompanySector(Long refCompanySectorId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            RefCompanySector refCompanySector = RefCompanySector.get(refCompanySectorId)
            def company = Company.findAllByRefCompanySector(refCompanySector)

            if (company.size() == 0) {

                refCompanySector.delete(flush: true, failOnError: true)

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
