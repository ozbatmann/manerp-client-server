package tr.com.manerp.business.main.voyage

import grails.converters.JSON


class VoyageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static scaffold = true

    def voyageService

    def getAllVoyages() {
        HashMap jsonMap = voyageService.getAllVoyages(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.companyName,
                request.JSON.code,
                request.JSON.plateNumber,
                request.JSON.driverName,
                request.JSON.orderCode,
                request.JSON.loadingLocation,
                request.JSON.dumpingLocation,
                request.JSON.awcCompanyId.toLong(),
        )
        render jsonMap as JSON
    }

    def getDetailOfVoyage() {
        HashMap jsonMap = voyageService.getDetailOfVoyage(request.JSON.voyageId.toLong())
        render jsonMap as JSON
    }

    def addVoyage() {
        HashMap jsonMap = voyageService.addVoyage(request.JSON.voyageJson)
        render jsonMap as JSON
    }

    def updateVoyage() {
        HashMap jsonMap = voyageService.updateVoyage(request.JSON.voyageJson)
        render jsonMap as JSON
    }

    def deleteVoyages() {
        HashMap jsonMap = voyageService.deleteVoyages(request.JSON.ids)
        render jsonMap as JSON
    }

    def deleteVoyage() {
        HashMap jsonMap = voyageService.deleteVoyage(request.JSON.voyageId.toLong())
        render jsonMap as JSON
    }

    def getDropDownSources() {
        HashMap jsonMap = voyageService.getDropDownSources()
        render jsonMap as JSON
    }

}
