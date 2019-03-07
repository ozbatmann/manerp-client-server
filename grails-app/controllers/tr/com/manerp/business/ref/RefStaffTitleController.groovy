package tr.com.manerp.business.ref

import grails.converters.JSON

class RefStaffTitleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def refStaffTitleService

    def getAllRefStaffTitles() {
        HashMap jsonMap = refStaffTitleService.getAllRefStaffTitles(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.name,
                request.JSON.code
        )
        render jsonMap as JSON
    }

    def getDetailOfRefStaffTitle() {
        HashMap jsonMap = refStaffTitleService.getDetailOfRefStaffTitle(request.JSON.refStaffTitleId.toLong())
        render jsonMap as JSON
    }

    def addRefStaffTitle() {
        HashMap jsonMap = refStaffTitleService.addRefStaffTitle(request.JSON.refStaffTitleJson)
        render jsonMap as JSON
    }

    def updateRefStaffTitle() {
        HashMap jsonMap = refStaffTitleService.updateRefStaffTitle(request.JSON.refStaffTitleJson)
        render jsonMap as JSON
    }

    def removeRefStaffTitle() {
        HashMap jsonMap = refStaffTitleService.removeRefStaffTitle(request.JSON.refStaffTitleId.toLong())
        render jsonMap as JSON
    }

}
