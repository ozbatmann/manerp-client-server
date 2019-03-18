package tr.com.manerp.business.main.order

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

class OrderController {

    static namespace = "v1"
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static defaultAction = "getAllOrders"

    def orderService

    def getAllOrders() {
        HashMap jsonMap = orderService.getAllOrders(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.companyName,
                request.JSON.code,
                request.JSON.name,
                request.JSON.workOrderNo,
                request.JSON.billingNo,
                request.JSON.sysrefRevenueTypeName,
                request.JSON.awcCompanyId.toLong()

        )
        render jsonMap as JSON
    }

    def getDetailOfOrder() {
        HashMap jsonMap = orderService.getDetailOfOrder(request.JSON.orderId.toLong())
        render jsonMap as JSON
    }

    def addOrder() {
        HashMap jsonMap = orderService.addOrder(request.JSON.orderJson)
        render jsonMap as JSON
    }

    def updateOrder() {
        HashMap jsonMap = orderService.updateOrder(request.JSON.orderJson)
        render jsonMap as JSON
    }

    def deleteOrders() {
        HashMap jsonMap = orderService.deleteOrders(request.JSON.ids)
        render jsonMap as JSON
    }

    def deleteOrder() {
        HashMap jsonMap = orderService.deleteOrder(request.JSON.orderId.toLong())
        render jsonMap as JSON
    }

    def getDropDownSources() {
        HashMap jsonMap = orderService.getDropDownSources()
        render jsonMap as JSON
    }

    def getVendors() {
        HashMap jsonMap = orderService.getVendors(request.JSON.companyId.toLong())
        render jsonMap as JSON
    }

}
