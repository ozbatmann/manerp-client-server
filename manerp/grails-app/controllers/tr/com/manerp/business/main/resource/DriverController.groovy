package tr.com.manerp.business.main.resource

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.commands.controller.common.PaginationCommand

class DriverController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE", getListForDropDown: "GET"]

    def driverService

    def index()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = driverService.getDriverList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            result.data = driverService.formatPaginatedResultForList(result.data)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def show(String id)
    {

        ManeResponse maneResponse = new ManeResponse()
        Staff driver = driverService.getDriver(id)

        try {

            if ( !driver ) throw new Exception()

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

            driver.refStaffTitle = RefStaffTitle.findByCode('DRV')
            driver.setRandomCode()
            driverService.save(driver)
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

    def getListForDropDown()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = driverService.getDriverList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            result.data = driverService.formatPaginatedResultForDropDown(result.data)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }


}