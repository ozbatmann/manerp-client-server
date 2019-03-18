package tr.com.manerp.business.main.resource

import grails.converters.JSON

class StaffController {

    static namespace = "v1"
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "getAllStaffs"

    def staffService

    def getAllStaffs() {
        HashMap jsonMap = staffService.getAllStaffs(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.firstName,
                request.JSON.lastName,
                request.JSON.tcIdNumber,
                request.JSON.refStaffTitleName,
                request.JSON.awcCompanyId
        )
        render jsonMap as JSON
    }

    def getDetailOfStaff() {
        HashMap jsonMap = staffService.getDetailOfStaff(request.JSON.staffId.toLong())
        render jsonMap as JSON
    }

    def addStaff() {
        HashMap jsonMap = staffService.addStaff(request.JSON.staffJson)
        render jsonMap as JSON
    }

    def updateStaff() {
        HashMap jsonMap = staffService.updateStaff(request.JSON.staffJson)
        render jsonMap as JSON
    }

    def deleteStaff() {
        HashMap jsonMap = staffService.deleteStaff(request.JSON.staffId.toLong())
        render jsonMap as JSON
    }

    def getDropDownSources() {
        HashMap jsonMap = staffService.getDropDownSources()
        render jsonMap as JSON
    }

}
