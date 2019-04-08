package tr.com.manerp.commands.controller.sysrefCity

import tr.com.manerp.commands.controller.common.PaginationCommand

class SysrefCityPaginationCommand extends PaginationCommand
{

    String countryId

    SysrefCityPaginationCommand(def params)
    {
        super(params)
        // change if multiple countries begin to use the application
        this.countryId = params.countryId ?: '1'
    }

    static constraints = {

        importFrom(PaginationCommand)

        countryId nullable: false, blank: false
    }
}
