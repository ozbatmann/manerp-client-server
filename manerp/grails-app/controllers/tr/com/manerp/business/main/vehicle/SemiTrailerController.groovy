package tr.com.manerp.business.main.vehicle

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.PaginationCommand
import tr.com.manerp.commands.controller.common.ShowCommand

class SemiTrailerController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def semiTrailerService

    def index()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = semiTrailerService.getSemiTrailerList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields))
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
        def semiTrailer

        try {

            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {
                semiTrailer = semiTrailerService.getSemiTrailer(cmd.id, cmd.fields)
            } else {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = semiTrailer
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !semiTrailer ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen römork sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(SemiTrailer trailer)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            trailer.active = true
            trailer.setRandomCode()
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

    def update(SemiTrailer trailer)
    {

        ManeResponse maneResponse = new ManeResponse()

        try {


            semiTrailerService.save(trailer)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Römork başarıyla güncellendi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            if ( !trailer ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Güncellenmek istenen römork sistemde bulunmamaktadır.'
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
        SemiTrailer trailer = SemiTrailer.get(id)

        try {


            semiTrailerService.delete(trailer)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Römork başarıyla silindi.'

        } catch (Exception ex) {

            if ( !trailer ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen römork sistemde bulunmamaktadır.'
            }

            if ( ex.getMessage().contains('foreign') ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen römork sistemde bulunan bir sevkiyat tarafından kullanılmaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}
