package tr.com.manerp.business.main.company

import grails.converters.JSON

class CompanyController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static scaffold = true

    def companyService










    def getAllCompanies() {
        HashMap jsonMap = companyService.getAllCompanies(
                request.JSON.orderColumn.toInteger(),
                request.JSON.orderDirection,
                request.JSON.length.toInteger(),
                request.JSON.start.toInteger(),
                request.JSON.code,
                request.JSON.name,
                request.JSON.title,
                request.JSON.tradeRegistrationNo,
                request.JSON.refCompanySectorName,
                request.JSON.awcCompanyId.toLong()
        )
        render jsonMap as JSON
    }

    def getDetailOfCompany() {
        HashMap jsonMap = companyService.getDetailOfCompany(request.JSON.companyId.toLong())
        render jsonMap as JSON
    }

    def addCompany() {
        HashMap jsonMap = companyService.addCompany(request.JSON.companyJson)
        render jsonMap as JSON
    }

    def updateCompany() {
        HashMap jsonMap = companyService.updateCompany(request.JSON.companyJson)
        render jsonMap as JSON
    }

    def deleteCompanies() {
        HashMap jsonMap = companyService.deleteCompanies(request.JSON.ids)
        render jsonMap as JSON
    }

    def deleteCompany() {
        HashMap jsonMap = companyService.deleteCompany(request.JSON.companyId.toLong())
        render jsonMap as JSON
    }

    def getDropDownSources() {
        HashMap jsonMap = companyService.getDropDownSources()
        render jsonMap as JSON
    }

    def getCustomerRepresentativesQuery() {
        HashMap jsonMap = companyService.getCustomerRepresentativesQuery(request.JSON.queryString)
        render jsonMap as JSON
    }

}
