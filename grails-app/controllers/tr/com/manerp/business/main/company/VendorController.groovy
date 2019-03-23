package tr.com.manerp.business.main.company

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.vendor.VendorPaginationCommand

class VendorController extends BaseController {

    static namespace = "v1"
    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE", getListForDropDown: "GET"]

    def vendorService

    def index() {

        ManeResponse maneResponse = new ManeResponse()

        try {

            VendorPaginationCommand cmd = new VendorPaginationCommand(params)

            ManePaginatedResult result = vendorService.getVendorList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort), cmd.companyId)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(Vendor vendor) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            vendorService.save(vendor)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = vendor.id
            maneResponse.message = 'Bayi başarıyla kaydedildi.'

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

    def update(Vendor vendor) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            if ( !vendor ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Güncellenmek istenen bayi sistemde bulunmamaktadır.')
            }
            vendorService.save(vendor)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Bayi başarıyla güncellendi.'

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

            Vendor vendor = Vendor.get(id)
            if ( !vendor ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Silinmek istenen bayi sistemde bulunmamaktadır.')
            }

            vendorService.delete(vendor)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Bayi başarıyla silindi.'

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

            VendorPaginationCommand cmd = new VendorPaginationCommand(params)

            ManePaginatedResult result = vendorService.getVendorList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort), cmd.companyId)
            result.data = vendorService.formatPaginatedResultForDropDown(result.data)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }
}
