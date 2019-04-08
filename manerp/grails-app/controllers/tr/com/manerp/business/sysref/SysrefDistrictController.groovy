package tr.com.manerp.business.sysref

import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.PaginationCommand
import tr.com.manerp.commands.controller.sysrefDistrict.SysrefDistrictPaginationCommand

class SysrefDistrictController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET"]

    def sysrefService

    def index()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            SysrefDistrictPaginationCommand cmd = new SysrefDistrictPaginationCommand(params)
            def closure

            if ( cmd.validate() ) {

                closure = {
                    sysrefCity {
                        eq('id', cmd.cityId)
                    }
                }
            } else {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            ManePaginatedResult result = sysrefService.getList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields), SysrefDistrict, closure)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }
}
