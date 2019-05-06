package tr.com.manerp.commands.controller.order

import tr.com.manerp.commands.controller.common.PaginationCommand

class OrderPaginationCommand extends PaginationCommand
{
    String orderStateCode
    String company
    Boolean hasVoyage

    OrderPaginationCommand(def params)
    {
        super(params)
        this.orderStateCode = params.orderStateCode as String
        this.company = params.company as String ?: null

        if ( params.hasVoyage && params.hasVoyage == "f" ) {
            this.hasVoyage = false
        } else if ( params.hasVoyage == "t" ) {
            this.hasVoyage = true
        }
    }

    static constraints = {

        importFrom(PaginationCommand)

        orderStateCode nullable: false, blank: false
        company nullable: true, blank: true
    }

}
