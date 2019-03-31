package tr.com.manerp.commands.controller.voyage

import tr.com.manerp.commands.controller.common.PaginationCommand

class VoyagePaginationCommand extends PaginationCommand
{
    String company
    String deliveryStatusCode

    VoyagePaginationCommand(def params)
    {
        super(params)
        this.company = params.company as String
        this.deliveryStatusCode = params.deliveryStatusCode as String
    }

    static constraints = {

        importFrom(PaginationCommand)

        company nullable: true, blank: true
        deliveryStatusCode nullable: true, blank: true
    }
}
