package tr.com.manerp.business.sysref

import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.PaginationCommand
import tr.com.manerp.commands.controller.common.ShowCommand
import tr.com.manerp.commands.controller.sysrefDistrict.SysrefDistrictPaginationCommand

class SysrefDistrictController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET"]

    def sysrefDistrictService

    def index()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            SysrefDistrictPaginationCommand cmd = new SysrefDistrictPaginationCommand(params)

            if ( !cmd.validate() ) {

                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('cityId parametresi gereklidir.')
            }

            ManePaginatedResult result = sysrefDistrictService.getSysrefDistrictList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields), cmd.cityId)
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
        def district

        try {

            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {

                district = sysrefDistrictService.getSysrefDistrict(cmd.id, cmd.fields)
                if ( !district ) throw new Exception()

            } else {

                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = district
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !district ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen ilçe sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }
}
