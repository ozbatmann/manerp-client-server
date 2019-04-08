package tr.com.manerp.business.sysref

import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.PaginationCommand
import tr.com.manerp.commands.controller.common.ShowCommand

class SysrefVoyageDirectionController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET"]

    def sysrefVoyageDirectionService

    def index()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = sysrefVoyageDirectionService.getSysrefVoyageDirectionList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields))
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
        def voyageDirection

        try {

            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {

                voyageDirection = sysrefVehicleTypeService.getSysrefVehicleType(cmd)
                if ( !voyageDirection ) throw new Exception()

            } else {

                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = voyageDirection
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !voyageDirection ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen sevkiyat yönü sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }
}
