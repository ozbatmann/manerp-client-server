package tr.com.manerp.commands.controller.order

import tr.com.manerp.commands.controller.common.PaginationCommand

class OrderPaginationCommand extends PaginationCommand
{
    String orderStateCode

    OrderPaginationCommand(def params)
    {
        super(params)
        this.orderStateCode = params.orderStateCode as String ?: 'COMP'
    }

    static constraints = {

        importFrom(PaginationCommand)

        orderStateCode nullable: false, blank: false
    }

}
