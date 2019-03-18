package tr.com.manerp.business.ref

import grails.converters.JSON

class RefCompanySectorController {

    static namespace = "v1"
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "getAllRefCompanySectors"

    def refCompanySectorService

    def getAllRefCompanySectors() {
        HashMap jsonMap = refCompanySectorService.getAllRefCompanySectors(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.name,
                request.JSON.code
        )
        render jsonMap as JSON
    }

    def getDetailOfRefCompanySector() {
        HashMap jsonMap = refCompanySectorService.getDetailOfRefCompanySector(request.JSON.refCompanySectorId.toLong())
        render jsonMap as JSON
    }

    def addRefCompanySector() {
        HashMap jsonMap = refCompanySectorService.addRefCompanySector(request.JSON.refCompanySectorJson)
        render jsonMap as JSON
    }

    def updateRefCompanySector() {
        HashMap jsonMap = refCompanySectorService.updateRefCompanySector(request.JSON.refCompanySectorJson)
        render jsonMap as JSON
    }

    def removeRefCompanySector() {
        HashMap jsonMap = refCompanySectorService.removeRefCompanySector(request.JSON.refCompanySectorId.toLong())
        render jsonMap as JSON
    }

}
