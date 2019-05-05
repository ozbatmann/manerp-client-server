package tr.com.manerp.business.main.company

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.ShowCommand
import tr.com.manerp.commands.controller.company.CompanyPaginationCommand

class CompanyController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def companyService

    def index()
    {
        ManeResponse maneResponse = new ManeResponse()

        try {
            CompanyPaginationCommand cmd = new CompanyPaginationCommand(params)

            ManePaginatedResult result = companyService.getCompanyList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields), cmd.sysrefCompanyTypeCode)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def show()
    {

        ManeResponse maneResponse = new ManeResponse()
        def company

        try {
            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {
                company = companyService.getCompany(cmd.id, cmd.fields)
            } else {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = company
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !company ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen müşteri iş yeri sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(Company company)
    {

        ManeResponse maneResponse = new ManeResponse()

        try {
            company.active = true
            company.setRandomCode()
            companyService.save(company)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = company.id
            maneResponse.message = 'İş yeri başarıyla kaydedildi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def update(Company company)
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            companyService.save(company)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'İş yeri başarıyla güncellendi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            if ( !company ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Güncellenmek istenen iş yeri sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def delete(String id)
    {

        ManeResponse maneResponse = new ManeResponse()
        Company company = Company.get(id)

        try {
            companyService.delete(company)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'İş yeri başarıyla silindi.'

        } catch (Exception ex) {

            if ( !company ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen iş yeri sistemde bulunmamaktadır.'
            }

            if ( ex.getMessage().contains('foreign') ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen iş yeri sistemde bulunan bir sevkiyat tarafından kullanılmaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }
}
