package tr.com.manerp.business.main.company

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.business.sysref.SysrefCompanyType
import tr.com.manerp.commands.controller.common.PaginationCommand

class CustomerCompanyController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def companyService

    def index()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = companyService.getCompanyList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields), 'CST')
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def show(String id)
    {

        ManeResponse maneResponse = new ManeResponse()

        // TODO: request sysrefCompanyTypeCode from client
        Company company = companyService.getCompany(id, 'CST')

        try {

            if ( !company ) throw new Exception()

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
            company.sysrefCompanyType = SysrefCompanyType.findByCode('CST')
            companyService.save(company)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = company.id
            maneResponse.message = 'Müşteri iş yeri başarıyla kaydedildi.'

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
            maneResponse.message = 'Müşteri iş yeri başarıyla güncellendi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            if ( !company ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Güncellenmek istenen müşteri iş yeri sistemde bulunmamaktadır.'
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

            if ( company.sysrefCompanyType.code != 'CST' ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception("Silinmek istenen '${company.title}' müşteri tipinde olmadığı için silinemedi.")
            }

            companyService.delete(company)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Müşteri iş yeri başarıyla silindi.'

        } catch (Exception ex) {

            if ( !company ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen müşteri iş yeri sistemde bulunmamaktadır.'
            }

            if ( ex.getMessage().contains('foreign') ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen müşteri iş yeri sistemde bulunan bir sevkiyat tarafından kullanılmaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }
}
