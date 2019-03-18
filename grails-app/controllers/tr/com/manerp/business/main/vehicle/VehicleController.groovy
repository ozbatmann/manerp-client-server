package tr.com.manerp.business.main.vehicle

import grails.converters.JSON

class VehicleController {

    static namespace = "v1"
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "getAllVehicles"

    def vehicleService

    def getAllVehicles() {
        HashMap jsonMap = vehicleService.getAllVehicles(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.brand,
                request.JSON.plateNumber,
                request.JSON.fleetCardNumber,
                request.JSON.sysrefVehicleTypeName,
                request.JSON.sysrefVehicleOwnerName,
                request.JSON.awcCompanyId.toLong()
        )
        render jsonMap as JSON
    }

    def getDetailOfVehicle() {
        HashMap jsonMap = vehicleService.getDetailOfVehicle(request.JSON.vehicleId.toLong())
        render jsonMap as JSON
    }

    def addVehicle() {
        HashMap jsonMap = vehicleService.addVehicle(request.JSON.vehicleJson)
        render jsonMap as JSON
    }

    def updateVehicle() {
        HashMap jsonMap = vehicleService.updateVehicle(request.JSON.vehicleJson)
        render jsonMap as JSON
    }

    def deleteVehicles() {
        HashMap jsonMap = vehicleService.deleteVehicles(request.JSON.ids)
        render jsonMap as JSON
    }

    def deleteVehicle() {
        HashMap jsonMap = vehicleService.deleteVehicle(request.JSON.vehicleId.toLong())
        render jsonMap as JSON
    }

    def getDropDownSources() {
        HashMap jsonMap = vehicleService.getDropDownSources()
        render jsonMap as JSON
    }

}
