package tr.com.manerp.business.sysref

import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.PaginationCommand
import tr.com.manerp.commands.controller.common.ShowCommand

class SysrefRevenueTypeController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET"]

    def sysrefRevenueTypeService

    def index()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = sysrefRevenueTypeService.getSysrefRevenueTypeList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields))
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
        def revenueType

        try {

            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {

                revenueType = sysrefRevenueTypeService.getSysrefRevenueType(cmd.id, cmd.fields)
                if ( !revenueType ) throw new Exception()

            } else {

                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = revenueType
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !revenueType ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen gelir tipi sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }
}
