package tr.com.manerp.commands.controller.voyage

import tr.com.manerp.commands.controller.common.PaginationCommand

class VoyagePaginationCommand extends PaginationCommand
{
    String companyId
    String deliveryStatusCode

    VoyagePaginationCommand(def params)
    {
        super(params)
        this.companyId = params.companyId as String ?: null
        this.deliveryStatusCode = params.deliveryStatusCode as String ?: null
    }

    static constraints = {

        importFrom(PaginationCommand)

        companyId nullable: true, blank: true
        deliveryStatusCode nullable: true, blank: true
    }
}
