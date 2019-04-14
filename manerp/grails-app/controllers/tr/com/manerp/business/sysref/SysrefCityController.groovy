package tr.com.manerp.business.sysref

import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.commands.controller.common.PaginationCommand
import tr.com.manerp.commands.controller.common.ShowCommand
import tr.com.manerp.commands.controller.sysrefCity.SysrefCityPaginationCommand

import java.sql.Statement

class SysrefCityController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET"]

    def sysrefCityService

    def index()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            SysrefCityPaginationCommand cmd = new SysrefCityPaginationCommand(params)

            ManePaginatedResult result = sysrefCityService.getSysrefCityList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields), cmd.countryId)
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
        def city

        try {

            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {

                city = sysrefCityService.getSysrefCity(cmd.id, cmd.fields)

            } else {

                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = city
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !city ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen il sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}
