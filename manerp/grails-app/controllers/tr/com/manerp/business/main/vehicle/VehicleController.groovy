package tr.com.manerp.business.main.vehicle

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.business.sysref.SysrefVehicleState
import tr.com.manerp.commands.controller.common.ShowCommand
import tr.com.manerp.commands.controller.vehicle.VehiclePaginationCommand

class VehicleController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def vehicleService

    def index()
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            VehiclePaginationCommand cmd = new VehiclePaginationCommand(params)

            ManePaginatedResult result = vehicleService.getVehicleList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields), cmd.vehicleStateCode)
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
        def vehicle

        try {

            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {
                vehicle = vehicleService.getVehicle(cmd.id, cmd.fields)
            } else {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = vehicle
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !vehicle ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen araç sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(Vehicle vehicle)
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            vehicle.active = true
            vehicle.sysrefVehicleState = SysrefVehicleState.findByCode('IDLE')
            vehicle.setRandomCode()
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

    def update(Vehicle vehicle)
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            vehicleService.save(vehicle)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Araç başarıyla güncellendi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            if ( !vehicle ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Güncellenmek istenen araç sistemde bulunmamaktadır.'
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
        Vehicle vehicle = Vehicle.get(id)

        try {

            vehicleService.delete(vehicle)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Araç başarıyla silindi.'

        } catch (Exception ex) {

            if ( !vehicle ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen araç sistemde bulunmamaktadır.'
            }

            if ( ex.getMessage().contains('foreign') ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen araç sistemde bulunan bir sevkiyat tarafından kullanılmaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}
