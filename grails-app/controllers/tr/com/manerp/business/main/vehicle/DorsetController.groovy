package tr.com.manerp.business.main.vehicle

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.PaginationCommand

class DorsetController extends BaseController {

    static namespace = "v1"
    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE", getListForDropDown: "GET"]

    def dorsetService

    def index() {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = dorsetService.getDorsetList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(Dorset dorset) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            dorsetService.save(dorset)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = dorset.id
            maneResponse.message = 'Dorse başarıyla kaydedildi.'

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

    def update(Dorset dorset) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            if ( !dorset ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Güncellenmek istenen dorse sistemde bulunmamaktadır.')
            }
            dorsetService.save(dorset)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Dorse başarıyla güncellendi.'

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

            Dorset dorset = Dorset.get(id)
            if ( !dorset ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Silinmek istenen dorse sistemde bulunmamaktadır.')
            }

            dorsetService.delete(dorset)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Dorse başarıyla silindi.'

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

            ManePaginatedResult result = dorsetService.getDorsetList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            result.data = dorsetService.formatPaginatedResultForDropDown(result.data)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}
