package tr.com.manerp.business.main.resource

import grails.converters.JSON

class DriverController {

    static namespace = "v1"
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "getAllDrivers"

    def driverService

    def getAllDrivers() {
        HashMap jsonMap = driverService.getAllDrivers(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.firstName,
                request.JSON.lastName,
                request.JSON.tcIdNumber,
                request.JSON.drivingLicenseNumber,
                request.JSON.sysrefStaffContractTypeName,
                request.JSON.awcCompanyId.toLong()
        )
        render jsonMap as JSON
    }

    def getDetailOfDriver() {
        HashMap jsonMap = driverService.getDetailOfDriver(request.JSON.driverId.toLong())
        render jsonMap as JSON
    }

    def addDriver() {
        HashMap jsonMap = driverService.addDriver(request.JSON.driverJson)
        render jsonMap as JSON
    }

    def updateDriver() {
        HashMap jsonMap = driverService.updateDriver(request.JSON.driverJson)
        render jsonMap as JSON
    }

    def deleteDrivers() {
        HashMap jsonMap = driverService.deleteDrivers(request.JSON.ids)
        render jsonMap as JSON
    }

    def deleteDriver() {
        HashMap jsonMap = driverService.deleteDriver(request.JSON.driverId.toLong())
        render jsonMap as JSON
    }

    def getDropDownSources() {
        HashMap jsonMap = driverService.getDropDownSources()
        render jsonMap as JSON
    }

}
