package tr.com.manerp.business.main.vehicle

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.PaginationCommand

class VehicleController extends BaseController {

    static namespace = "v1"
    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE", getListForDropDown: "GET"]

    def vehicleService

    def index() {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = vehicleService.getVehicleList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(Vehicle vehicle) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            vehicleService.save(vehicle)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = vehicle.id
            maneResponse.message = 'Araç başarıyla kaydedildi.'

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

    def update(Vehicle vehicle) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            if ( !vehicle ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Güncellenmek istenen araç sistemde bulunmamaktadır.')
            }
            vehicleService.save(vehicle)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Araç başarıyla güncellendi.'

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

            Vehicle vehicle = Vehicle.get(id)
            if ( !vehicle ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Silinmek istenen araç sistemde bulunmamaktadır.')
            }

            vehicleService.delete(vehicle)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Araç başarıyla silindi.'

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

            ManePaginatedResult result = vehicleService.getVehicleList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            result.data = vehicleService.formatPaginatedResultForDropDown(result.data)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }
}
