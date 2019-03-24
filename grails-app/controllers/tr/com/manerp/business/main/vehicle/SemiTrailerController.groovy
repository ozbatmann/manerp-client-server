package tr.com.manerp.business.main.vehicle

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.PaginationCommand

class SemiTrailerController extends BaseController {

    static namespace = "v1"
    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE", getListForDropDown: "GET"]

    def semiTrailerService

    def index() {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = semiTrailerService.getSemiTrailerList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(SemiTrailer trailer) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            semiTrailerService.save(trailer)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = trailer.id
            maneResponse.message = 'Römork başarıyla kaydedildi.'

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

    def update(SemiTrailer trailer) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            if ( !trailer ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Güncellenmek istenen römork sistemde bulunmamaktadır.')
            }
            semiTrailerService.save(trailer)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Römork başarıyla güncellendi.'

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

            SemiTrailer trailer = SemiTrailer.get(id)
            if ( !trailer ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Silinmek istenen römork sistemde bulunmamaktadır.')
            }

            semiTrailerService.delete(trailer)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Römork başarıyla silindi.'

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

            ManePaginatedResult result = semiTrailerService.getTrailerList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            result.data = semiTrailerService.formatPaginatedResultForDropDown(result.data)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }


}
