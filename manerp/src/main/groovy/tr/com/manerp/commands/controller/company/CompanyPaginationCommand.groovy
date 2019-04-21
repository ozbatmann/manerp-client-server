package tr.com.manerp.commands.controller.company

import tr.com.manerp.commands.controller.common.PaginationCommand

class CompanyPaginationCommand extends PaginationCommand
{
    String sysrefCompanyTypeCode

    CompanyPaginationCommand(def params)
    {
        super(params)
        this.sysrefCompanyTypeCode = params.companyTypeCode ?: 'CST'
    }

    static constraints = {
        sysrefCompanyTypeCode nullable: false, blank: false
    }

}
