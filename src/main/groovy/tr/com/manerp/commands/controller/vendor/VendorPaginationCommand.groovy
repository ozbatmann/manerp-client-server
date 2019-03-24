package tr.com.manerp.commands.controller.vendor

import tr.com.manerp.commands.controller.common.PaginationCommand

class VendorPaginationCommand extends PaginationCommand {

    String company

    VendorPaginationCommand(def params) {
        super(params)
        this.company = params.company as String
    }

    static constraints = {

        importFrom(PaginationCommand)

        company nullable: false, blank: false
    }

}
