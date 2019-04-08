package tr.com.manerp.commands.controller.sysrefDistrict

import tr.com.manerp.commands.controller.common.PaginationCommand

class SysrefDistrictPaginationCommand extends PaginationCommand
{

    String cityId

    SysrefDistrictPaginationCommand(def params)
    {
        super(params)
        this.cityId = params.cityId
    }

    static constraints = {

        importFrom(PaginationCommand)

        cityId nullable: false, blank: false
    }
}
