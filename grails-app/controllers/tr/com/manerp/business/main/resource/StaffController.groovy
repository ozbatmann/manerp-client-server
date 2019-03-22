package tr.com.manerp.business.main.resource

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.PaginationCommand

class StaffController extends BaseController {

    static namespace = "v1"
    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE", getListForDropDown: "GET"]

    def staffService

    def index() {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            if ( !cmd.validate() ) {

                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = parseValidationErrors(cmd.errors)
                throw new Exception(maneResponse.message)
            }

            ManePaginatedResult result = staffService.getStaffList(new ManePaginationProperties(cmd.max, cmd.offset, cmd.sort))
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(Staff staff) {

        ManeResponse maneResponse = new ManeResponse()

        try {

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

    def update(Staff staff) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            if ( !staff ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Güncellenmek istenen personel sistemde bulunmamaktadır.')
            }
            staffService.save(staff)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Personel başarıyla güncellendi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def delete(String id) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            Staff staff = Staff.get(id)
            if ( !staff ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Silinmek istenen personel sistemde bulunmamaktadır.')
            }

            staffService.delete(staff)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Personel başarıyla silindi.'

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def getListForDropDown() {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            if ( !cmd.validate() ) {

                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = parseValidationErrors(cmd.errors)
                throw new Exception(maneResponse.message)
            }

            ManePaginatedResult result = staffService.getStaffList(new ManePaginationProperties(cmd.max, cmd.offset, cmd.sort))
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