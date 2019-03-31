package tr.com.manerp.commands.controller.vehicle

import tr.com.manerp.commands.controller.common.PaginationCommand

class VehiclePaginationCommand extends PaginationCommand
{
    String vehicleStateCode

    VehiclePaginationCommand(def params)
    {
        super(params)
        this.vehicleStateCode = params.vehicleStateCode as String ?: 'IDLE'
    }

    static constraints = {

        importFrom(PaginationCommand)

        vehicleStateCode nullable: false, blank: false
    }
}
