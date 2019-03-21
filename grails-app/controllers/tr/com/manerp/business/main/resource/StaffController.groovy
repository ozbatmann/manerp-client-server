package tr.com.manerp.business.main.resource

import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.PaginationCommand

class StaffController extends BaseController {

    static namespace = "v1"
    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def staffService

    def index() {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            if ( !cmd.validate() ) {

                maneResponse.statusCode = StatusCode.BAD_REQUEST
//                throw new Exception('Parameters cannot be validated')
                throw new Exception('Parametreler doğrulanamadı.')

            }

            ManePaginatedResult result = staffService.getStaffList(new ManePaginationProperties(cmd.max, cmd.offset, cmd.sort))
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code >= StatusCode.OK.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
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
            maneResponse.message = 'Personel başarıyla kaydedildi.'

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code >= StatusCode.OK.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def update(Staff staff) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            staffService.save(staff)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Personel başarıyla güncellendi.'

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code >= StatusCode.OK.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def delete(String id) {

        ManeResponse maneResponse = new ManeResponse()

        try {

            staffService.delete(id)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Personel başarıyla silindi.'

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code >= StatusCode.OK.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}