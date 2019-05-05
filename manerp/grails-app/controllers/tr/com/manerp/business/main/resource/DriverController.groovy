package tr.com.manerp.business.main.resource

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefDriverState
import tr.com.manerp.commands.controller.common.PaginationCommand
import tr.com.manerp.commands.controller.common.ShowCommand

class DriverController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def driverService

    def index()
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = driverService.getDriverList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields), params.driverStateCode as String)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def show()
    {
        ManeResponse maneResponse = new ManeResponse()
        def driver

        try {
            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {
                driver = driverService.getDriver(cmd.id, cmd.fields)
            } else {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = driver
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !driver ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen şoför sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(Staff driver)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            driver.setRandomCode()
            driver.active = true
            driver.refStaffTitle = RefStaffTitle.findByCode('DRV')
            driverService.saveDriverWithState(driver, SysrefDriverState.findByCode('IDLE'))
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = driver.id
            maneResponse.message = 'Şoför başarıyla kaydedildi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def update(Staff driver)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            driverService.save(driver)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Şoför başarıyla güncellendi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            if ( !driver ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Güncellenmek istenen şoför sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def delete(String id)
    {
        ManeResponse maneResponse = new ManeResponse()
        Staff driver = Staff.get(id)

        try {

            if ( driver.refStaffTitle.code != 'DRV' ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception("Silinmek istenen '${driver.getFullName()}' şoför unvanına sahip olmadığı için silinemedi.")
            }

            driverService.delete(driver)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Şoför başarıyla silindi.'

        } catch (Exception ex) {

            if ( !driver ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen şoför sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def getWaypointsByDriverId(String id)
    {
        ManeResponse maneResponse = new ManeResponse()
        def driver

        try {
            driver = Staff.get(id)
            if ( !driver ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception("Sistemde böyle bir şoför bulunmamaktadır")
            }

            def data = driverService.getWaypointsByDriver(driver)
            if ( !data ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception("İstenilen sevkiyata ait rota sistemde bulunmamaktadır")
            }
            maneResponse.data = data
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def getVendorsByDriverId(String id)
    {
        ManeResponse maneResponse = new ManeResponse()
        def driver

        try {
            driver = Staff.get(id)
            if ( !driver ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception("Sistemde böyle bir şoför bulunmamaktadır")
            }

            def data = driverService.getSortedVendorsByDriver(driver)
            if ( !data ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception("İstenilen sevkiyata ait rota sistemde bulunmamaktadır")
            }
            maneResponse.data = data
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}
