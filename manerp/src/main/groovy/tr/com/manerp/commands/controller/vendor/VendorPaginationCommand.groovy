package tr.com.manerp.commands.controller.vendor

import tr.com.manerp.commands.controller.common.PaginationCommand

class VendorPaginationCommand extends PaginationCommand {

    String companyId

    VendorPaginationCommand(def params) {
        super(params)
        this.companyId = params.companyId as String
    }

    static constraints = {

        importFrom(PaginationCommand)

        company nullable: false, blank: false
    }

}
