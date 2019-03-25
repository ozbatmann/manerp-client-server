package tr.com.manerp.business.main.resource

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.PaginationCommand

class StaffController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE", getListForDropDown: "GET"]

    def staffService

    def index()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = staffService.getStaffList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(Staff staff)
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            staff.setRandomCode()
            staffService.save(staff)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = staff.id
            maneResponse.message = 'Personel başarıyla kaydedildi.'

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

    def update(Staff staff)
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            staffService.save(staff)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Personel başarıyla güncellendi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            if ( !staff ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Güncellenmek istenen personel sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            if ( !maneResponse.message ) maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def delete(String id)
    {

        ManeResponse maneResponse = new ManeResponse()
        Staff staff = Staff.get(id)

        try {

            staffService.delete(staff)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Personel başarıyla silindi.'

        } catch (Exception ex) {

            if ( !staff ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen personel sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            if ( !maneResponse.message ) maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def getListForDropDown()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = staffService.getStaffList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            result.data = staffService.formatPaginatedResultForDropDown(result.data)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}