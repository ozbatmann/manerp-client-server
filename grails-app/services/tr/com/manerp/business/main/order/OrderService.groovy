package tr.com.manerp.business.main.order

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import org.hibernate.sql.JoinType
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefRevenueType

import java.text.SimpleDateFormat

@Transactional
class OrderService {

    def uniqueCodeGeneratorService
    def jsonArrayParserService

    def getAllOrders(
            Integer orderColumn,
            String orderDirection,
            Integer length,
            Integer start,
            String companyName,
            String code,
            String name,
            String workOrderNo,
            String billingNo,
            String sysrefRevenueTypeName,
            Long awcCompanyId
    ) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        def orderList = []
        HashMap orderListMap = new HashMap()

        def orderTotalSize
        def orderFilteredSize
        def orderColumnString
        orderTotalSize = Order.count
        orderFilteredSize = orderTotalSize

        try {

            switch (orderColumn) {
                case 0: orderColumnString = "opDate"; break
                case 1: orderColumnString = "_company.id"; break
                case 2: orderColumnString = "code"; break
                case 3: orderColumnString = "name"; break
                case 4: orderColumnString = "workOrderNo"; break
                case 5: orderColumnString = "billingNo"; break
                case 6: orderColumnString = "_sysrefRevenueType.id"; break
                case 7: orderColumnString = "active"; break
            }

            companyName = companyName.toLowerCase(new Locale("TR", "tr"))
            code = code.toLowerCase(new Locale("TR", "tr"))
            name = name.toLowerCase(new Locale("TR", "tr"))
            workOrderNo = workOrderNo.toLowerCase(new Locale("TR", "tr"))
            billingNo = billingNo.toLowerCase(new Locale("TR", "tr"))
            sysrefRevenueTypeName = sysrefRevenueTypeName.toLowerCase(new Locale("TR", "tr"))

            def orderCriteria = Order.createCriteria()
            orderList = orderCriteria.list(max: length, offset: start) {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("company", "_company", JoinType.LEFT_OUTER_JOIN)
                createAlias("sysrefRevenueType", "_sysrefRevenueType", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompanyId)

                if (!companyName.equals("")) {
                    ilike('_company.name', '%' + companyName + '%')
                }
                if (!code.equals("")) {
                    ilike('code', '%' + code + '%')
                }
                if (!name.equals("")) {
                    ilike('name', '%' + name + '%')
                }
                if (!workOrderNo.equals("")) {
                    ilike('workOrderNo', '%' + workOrderNo + '%')
                }
                if (!billingNo.equals("")) {
                    ilike('billingNo', '%' + billingNo + '%')
                }
                if (!sysrefRevenueTypeName.equals("")) {
                    ilike('_sysrefRevenueType.name', '%' + sysrefRevenueTypeName + '%')
                }

                order(orderColumnString, orderDirection)
            }

            def orderCountCriteria = Order.createCriteria()
            orderFilteredSize = orderCountCriteria.count {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("company", "_company", JoinType.LEFT_OUTER_JOIN)
                createAlias("sysrefRevenueType", "_sysrefRevenueType", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompanyId)

                if (!companyName.equals("")) {
                    ilike('_company.name', '%' + companyName + '%')
                }
                if (!code.equals("")) {
                    ilike('code', '%' + code + '%')
                }
                if (!name.equals("")) {
                    ilike('name', '%' + name + '%')
                }
                if (!workOrderNo.equals("")) {
                    ilike('workOrderNo', '%' + workOrderNo + '%')
                }
                if (!billingNo.equals("")) {
                    ilike('billingNo', '%' + billingNo + '%')
                }
                if (!sysrefRevenueTypeName.equals("")) {
                    ilike('_sysrefRevenueType.name', '%' + sysrefRevenueTypeName + '%')
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:MM")

            orderListMap.orderList = orderList.collect {
                return [
                        id         : it.id,
                        company    : it.company.title,
                        code       : it.code,
                        name       : it.name,
                        orderDate  : sdf.format(it.orderDate),
                        workOrderNo: it.workOrderNo,
                        billingNo  : it.billingNo,
                        active     : it.active
                ]
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        recordsTotal   : orderTotalSize,
                        recordsFiltered: orderFilteredSize,
                        orders         : orderListMap.orderList
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def getDetailOfOrder(Long orderId) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:MM")
        def errors = null
        boolean status = true
        HashMap orderData = new HashMap()

        try {

            Order order = Order.get(orderId)

            orderData = [
                    id               : order.id,
                    code             : order.code,
                    company          : order.company.title,
                    name             : order.name,
                    orderDate        : sdf.format(order.orderDate),
                    workOrderNo      : order.workOrderNo,
                    sysrefRevenueType: order?.sysrefRevenueType?.id,
                    billingNo        : order?.billingNo,
                    active           : order.active
            ]

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        order: orderData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def addOrder(JSONObject orderJson) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM")
        def errors = null
        boolean status = true
        def id = null
        def code = null

        try {

            Order order = new Order()
            order.awcCompany = orderJson.isNull("awcCompanyId") ? AwcCompany.get(1) : AwcCompany.get(orderJson.awcCompanyId)
            order.company = Company.get(orderJson.company)
            order.name = orderJson.name
            order.orderDate = new Date()
            order.workOrderNo = orderJson.isNull("workOrderNo") ? null : orderJson.workOrderNo
            order.sysrefRevenueType = orderJson.isNull("sysrefRevenueType") ? null : SysrefRevenueType.get(orderJson.sysrefRevenueType)
            order.billingNo = orderJson.isNull("billingNo") ? null : orderJson.billingNo
            order.active = orderJson.active

            order.save(flush: true, failOnError: true)
            order.code = uniqueCodeGeneratorService.generateUniqueCode("ORD", order.id)
            order.save(flush: true, failOnError: true)

            id = order.id
            code = order.code

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

    def updateOrder(JSONObject orderJson) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM")
        def errors = null
        boolean status = true

        try {

            Order order = new Order()
            order.awcCompany = AwcCompany.get(orderJson.awcCompany)
            order.company = Company.get(orderJson.company)
            order.name = orderJson.name
            order.orderDate = sdf.parse(orderJson.orderDate)
            order.workOrderNo = orderJson.isNull("workOrderNo") ? null : orderJson.workOrderNo
            order.sysrefRevenueType = orderJson.isNull("sysrefRevenueType") ? null : SysrefRevenueType.get(orderJson.sysrefRevenueType)
            order.billingNo = orderJson.isNull("billingNo") ? null : orderJson.billingNo
            order.active = orderJson.active

            order.save(flush: true, failOnError: true)

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

    def deleteOrders(JSONArray ids) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            long[] _ids = jsonArrayParserService.jsonArray2LongArray(ids)

            def orderList = Order.createCriteria().list {
                inList("id", _ids)
            }

            orderList.collect {
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

    def deleteOrder(Long orderId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            Order order = Order.get(orderId)
            def voyages = Voyage.findAllByOrder(order)

            if (voyages.size() != 0) {

                errors = "hasVoyages"

            } else {

                order.delete(flush: true, failOnError: true)

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
        def sysrefRevenueTypes = []
        def companies = []

        try {

            // TODO: Request awcCompanyId
            AwcCompany awcCompany = AwcCompany.get(1)

            sysrefRevenueTypes = SysrefRevenueType.findAllByActive(true).collect {
                return [id: it.id, name: it.name, code: it.code]
            }
            companies = Company.findByAwcCompany(awcCompany).collect {
                return [id: it.id, text: it.title]
            }


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        sysrefRevenueTypes: sysrefRevenueTypes,
                        companies         : companies
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def getVendors(Long companyId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true
        def vendors = []

        try {

            Company company = Company.get(companyId)

            vendors = Vendor.findAllByCompany(company).collect {
                return [id: it.id, text: it.title]
            }

        } catch(Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        vendors: vendors
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

}
