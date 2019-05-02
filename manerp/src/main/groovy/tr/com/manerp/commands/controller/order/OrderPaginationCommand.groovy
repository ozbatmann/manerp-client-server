package tr.com.manerp.commands.controller.order

import tr.com.manerp.commands.controller.common.PaginationCommand

class OrderPaginationCommand extends PaginationCommand
{
    String orderStateCode
    String company

    OrderPaginationCommand(def params)
    {
        super(params)
        this.orderStateCode = params.orderStateCode as String ?: 'COMP'
        this.company = params.company as String ?: null
    }

    static constraints = {

        importFrom(PaginationCommand)

        orderStateCode nullable: false, blank: false
        company nullable: true, blank: true
    }

}
