package tr.com.manerp.business.main.voyage

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.business.sysref.SysrefDeliveryStatus
import tr.com.manerp.commands.controller.common.ShowCommand
import tr.com.manerp.commands.controller.voyage.VoyagePaginationCommand
import tr.com.manerp.commands.controller.voyage.VoyageSaveCommand
import tr.com.manerp.commands.controller.voyage.VoyageUpdateCommand

class VoyageController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def voyageService

    // TODO: filtering with company and order
    def index()
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            VoyagePaginationCommand cmd = new VoyagePaginationCommand(params)

            ManePaginatedResult result = voyageService.getVoyageList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields), cmd.deliveryStatusCode)
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
        def voyage

        try {

            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {

                voyage = voyageService.getVoyage(cmd.id, cmd.fields)

            } else {

                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = voyage
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !voyage ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen araç sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(VoyageSaveCommand cmd)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            if ( !cmd.validate() ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = parseValidationErrors(cmd.errors)
                throw new Exception(maneResponse.message)
            }

            Voyage voyage = new Voyage()
            voyage.startDate = new Date()
            cmd >> voyage
            voyage.active = true
            voyage.sysrefDeliveryStatus = SysrefDeliveryStatus.findByCode('REZ')
            voyage.setRandomCode()

            voyageService.saveVoyageWithOrder(voyage, cmd._order)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = voyage.id
            maneResponse.message = 'Sevkiyat başarıyla kaydedildi.'

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

    def update(VoyageUpdateCommand cmd)
    {
        ManeResponse maneResponse = new ManeResponse()
        Voyage voyage

        try {

            if ( !cmd.validate() ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = parseValidationErrors(cmd.errors)
                throw new Exception(maneResponse.message)
            }

            voyage = Voyage.get(cmd.id)
            voyage.startDate = new Date()
            cmd >> voyage
            voyage.active = true
            voyage.setRandomCode()

            voyageService.saveVoyageWithOrder(voyage, cmd._order)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Sevkiyat başarıyla güncellendi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            if ( !voyage ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Güncellenmek istenen sevkiyat sistemde bulunmamaktadır.'
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
        Voyage voyage = Voyage.get(id)

        try {


            voyageService.delete(voyage)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Sevkiyat başarıyla silindi.'

        } catch (Exception ex) {

            if ( !voyage ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen sevkiyat sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}
