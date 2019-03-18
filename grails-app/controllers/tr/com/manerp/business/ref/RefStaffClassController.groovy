package tr.com.manerp.business.ref

import grails.converters.JSON

class RefStaffClassController {

    static namespace = "v1"
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "getAllRefStaffClasss"

    def refStaffTitleService

    def getAllRefStaffClasss() {
        HashMap jsonMap = refStaffTitleService.getAllRefStaffClasss(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.name,
                request.JSON.code
        )
        render jsonMap as JSON
    }

    def getDetailOfRefStaffClass() {
        HashMap jsonMap = refStaffTitleService.getDetailOfRefStaffClass(request.JSON.refStaffClassId.toLong())
        render jsonMap as JSON
    }

    def addRefStaffClass() {
        HashMap jsonMap = refStaffTitleService.addRefStaffClass(request.JSON.refStaffClassJson)
        render jsonMap as JSON
    }

    def updateRefStaffClass() {
        HashMap jsonMap = refStaffTitleService.updateRefStaffClass(request.JSON.refStaffClassJson)
        render jsonMap as JSON
    }

    def removeRefStaffClass() {
        HashMap jsonMap = refStaffTitleService.removeRefStaffClass(request.JSON.refStaffClassId.toLong())
        render jsonMap as JSON
    }

}
