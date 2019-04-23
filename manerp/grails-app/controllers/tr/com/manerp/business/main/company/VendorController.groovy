package tr.com.manerp.business.main.company

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.ShowCommand
import tr.com.manerp.commands.controller.vendor.VendorPaginationCommand
import tr.com.manerp.commands.controller.vendor.VendorSaveCommand

class VendorController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def vendorService

    def index()
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            VendorPaginationCommand cmd = new VendorPaginationCommand(params)

            ManePaginatedResult result = vendorService.getVendorList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields), cmd.companyId)
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
        def vendor

        try {

            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {
                vendor = vendorService.getVendor(cmd.id, cmd.fields)
            } else {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = vendor
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !vendor ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen bayi sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(VendorSaveCommand cmd)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            cmd.active = true
            if ( !cmd.validate() ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = parseValidationErrors(cmd.errors)
                throw new Exception(maneResponse.message)
            }

            Vendor vendor = new Vendor()
            cmd >> vendor

            vendor.setRandomCode()
            vendorService.save(vendor)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = vendor.id
            maneResponse.message = 'Bayi başarıyla kaydedildi.'

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

    def update(VendorSaveCommand cmd)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            if ( !cmd.validate() ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = parseValidationErrors(cmd.errors)
                throw new Exception(maneResponse.message)
            }

            Vendor vendor = Vendor.get(cmd.id)
            cmd >> vendor

            vendorService.save(vendor)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Bayi başarıyla güncellendi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            //TODO
//            if ( !vendor ) {
//                maneResponse.statusCode = StatusCode.BAD_REQUEST
//                maneResponse.message = 'Güncellenmek istenen bayi sistemde bulunmamaktadır.'
//            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def delete(String id)
    {
        ManeResponse maneResponse = new ManeResponse()
        Vendor vendor = Vendor.get(id)

        try {

            vendorService.delete(vendor)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Bayi başarıyla silindi.'

        } catch (Exception ex) {

            if ( !vendor ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen bayi sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}
