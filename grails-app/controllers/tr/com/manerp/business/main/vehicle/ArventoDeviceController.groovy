package tr.com.manerp.business.main.vehicle

import grails.converters.JSON

class ArventoDeviceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static scaffold = true

    def arventoDeviceService

    def getAllArventoDevices() {
        HashMap jsonMap = arventoDeviceService.getAllArventoDevices(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.firstName,
                request.JSON.lastName,
                request.JSON.tcIdNumber,
                request.JSON.drivingLicenseNumber,
                request.JSON.sysrefDrivingTypeName,
                request.JSON.organizationId.toLong()
        )
        render jsonMap as JSON
    }

    def getDetailOfArventoDevice() {
        HashMap jsonMap = arventoDeviceService.getDetailfOfArventoDevice(request.JSON.arventoDeviceId.toLong())
        render jsonMap as JSON
    }

    def addArventoDevice() {
        HashMap jsonMap = arventoDeviceService.addArventoDevice(request.JSON.arventoDeviceJson)
        render jsonMap as JSON
    }

    def updateArventoDevice() {
        HashMap jsonMap = arventoDeviceService.updateArventoDevice(request.JSON.arventoDeviceJson)
        render jsonMap as JSON
    }

    def deleteArventoDevice() {
        HashMap jsonMap = arventoDeviceService.deleteArventoDevice(request.JSON.arventoDeviceId.toLong())
        render jsonMap as JSON
    }

    def getArventoDeviceDropDownSources() {
        HashMap jsonMap = arventoDeviceService.getArventoDeviceDropDownSources()
        render jsonMap as JSON
    }

}
