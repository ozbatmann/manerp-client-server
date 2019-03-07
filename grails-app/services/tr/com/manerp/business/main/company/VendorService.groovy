package tr.com.manerp.business.main.company

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.hibernate.sql.JoinType

@Transactional
class VendorService {

    def uniqueCodeGeneratorService
    def jsonArrayParserService

    def getAllVendors(
            Integer orderColumn,
            String orderDirection,
            Integer length,
            Integer start,
            String code,
            String title,
            String city,
            String district,
            Long companyId
    ) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        def vendorList = []
        HashMap vendorListMap = new HashMap()

        def vendorTotalSize
        def vendorFilteredSize
        def orderColumnString
        vendorTotalSize = Vendor.count
        vendorFilteredSize = vendorTotalSize

        try {

            switch (orderColumn) {
                case 0: orderColumnString = "opDate"; break
                case 1: orderColumnString = "code"; break
                case 2: orderColumnString = "title"; break
                case 3: orderColumnString = "city"; break
                case 4: orderColumnString = "district"; break
                case 5: orderColumnString = "active"; break
            }

            code = code.toLowerCase(new Locale("TR", "tr"))
            title = title.toLowerCase(new Locale("TR", "tr"))
            city = city.toLowerCase(new Locale("TR", "tr"))
            district = district.toLowerCase(new Locale("TR", "tr"))

            def vendorCriteria = Vendor.createCriteria()
            vendorList = vendorCriteria.list(max: length, offset: start) {
                createAlias("company", "_company", JoinType.LEFT_OUTER_JOIN)
                eq("_company.id", companyId.toLong())

                if (!code.equals("")) {
                    ilike('code', '%' + code + '%')
                }
                if (!title.equals("")) {
                    ilike('title', '%' + title + '%')
                }
                if (!city.equals("")) {
                    ilike('city', '%' + city + '%')
                }
                if (!district.equals("")) {
                    ilike('district', '%' + district + '%')
                }

                order(orderColumnString, orderDirection)
            }

            def vendorCountCriteria = Vendor.createCriteria()
            vendorFilteredSize = vendorCountCriteria.count {
                createAlias("company", "_company", JoinType.LEFT_OUTER_JOIN)
                eq("_company.id", companyId.toLong())

                if (!code.equals("")) {
                    ilike('code', '%' + code + '%')
                }
                if (!title.equals("")) {
                    ilike('title', '%' + title + '%')
                }
                if (!city.equals("")) {
                    ilike('city', '%' + city + '%')
                }
                if (!district.equals("")) {
                    ilike('district', '%' + district + '%')
                }

            }

            vendorListMap.vendorList = vendorList.collect {
                return [
                        id      : it.id,
                        code    : it.code,
                        title   : it?.title,
                        city    : it?.city,
                        district: it?.district
                ]
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        recordsTotal   : vendorTotalSize,
                        recordsFiltered: vendorFilteredSize,
                        vendors        : vendorListMap.vendorList
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def getDetailOfVendor(Long vendorId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        HashMap vendorData = new HashMap()

        try {

            Vendor vendor = Vendor.get(vendorId)

            vendorData = [
                    id      : vendor.id,
                    code    : vendor.code,
                    title   : vendor?.title,
                    city    : vendor?.city,
                    district: vendor?.district,
                    address : vendor?.address
            ]


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        vendor: vendorData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def addVendor(JSONObject vendorJson) {
        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true
        def id = null
        def code = null

        try {

            Vendor vendor = new Vendor()
            vendor.company = Company.get(vendorJson.company)
            vendor.title = vendorJson.isNull("title") ? null : vendorJson.title
            vendor.address = vendorJson.isNull("address") ? null : vendorJson.address
            vendor.city = vendorJson.isNull("city") ? null : vendorJson.city
            vendor.district = vendorJson.isNull("district") ? null : vendorJson.district

            vendor.save(flush: true, failOnError: true)
            vendor.code = uniqueCodeGeneratorService.generateUniqueCode("VND", vendor.id)
            vendor.save(flush: true, failOnError: true)

            id = vendor.id
            code = vendor.code

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

    def updateVendor(JSONObject vendorJson) {
        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true


        try {

            Vendor vendor = Vendor.get(vendorJson.id)
            //vendor.company = Company.get(vendorJson.company)
            vendor.title = vendorJson.isNull("title") ? null : vendorJson.title
            vendor.address = vendorJson.isNull("address") ? null : vendorJson.address
            vendor.city = vendorJson.isNull("city") ? null : vendorJson.city
            vendor.district = vendorJson.isNull("district") ? null : vendorJson.district

            vendor.save(flush: true, failOnError: true)

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

    def deleteVendors(JSONArray ids) {
        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            long[] _ids = jsonArrayParserService.jsonArray2LongArray(ids)

            def vendorList = Vendor.createCriteria().list {
                inList("id", _ids)
            }

            vendorList.collect {
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

    def deleteVendor(Long vendorId) {
        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            Vendor vendor = Vendor.get(vendorId)
            vendor.delete(flush: true, failOnError: true)

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

    }

}
