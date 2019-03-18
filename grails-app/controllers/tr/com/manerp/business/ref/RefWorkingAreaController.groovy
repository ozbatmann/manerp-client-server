package tr.com.manerp.business.ref

import grails.converters.JSON

class RefWorkingAreaController {

    static namespace = "v1"
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "getAllRefWorkingAreas"

    def refWorkingAreaService

    def getAllRefWorkingAreas() {
        HashMap jsonMap = refWorkingAreaService.getAllRefWorkingAreas(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.name,
                request.JSON.code
        )
        render jsonMap as JSON
    }

    def getDetailOfRefWorkingArea() {
        HashMap jsonMap = refWorkingAreaService.getDetailOfRefWorkingArea(request.JSON.refWorkingAreaId.toLong())
        render jsonMap as JSON
    }

    def addRefWorkingArea() {
        HashMap jsonMap = refWorkingAreaService.addRefWorkingArea(request.JSON.refWorkingAreaJson)
        render jsonMap as JSON
    }

    def updateRefWorkingArea() {
        HashMap jsonMap = refWorkingAreaService.updateRefWorkingArea(request.JSON.refWorkingAreaJson)
        render jsonMap as JSON
    }

    def removeRefWorkingArea() {
        HashMap jsonMap = refWorkingAreaService.removeRefWorkingArea(request.JSON.refWorkingAreaId.toLong())
        render jsonMap as JSON
    }

}
