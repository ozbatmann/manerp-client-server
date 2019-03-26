package tr.com.manerp.business.main.company

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.resource.Staff

@Transactional
class CompanyService extends BaseService
{

    ManePaginatedResult getCompanyList(ManePaginationProperties properties, String sysrefCompanyTypeCode)
    {

        def closure = {

            eq('active', true)

            sysrefCompanyType {
                eq('code', sysrefCompanyTypeCode)
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }

        }

        return paginate(Company, properties, closure)
    }

    Company getCompany(String id, String sysrefCompanyTypeCode)
    {
        Company company = Company.createCriteria().get {

            sysrefCompanyType {
                eq('code', sysrefCompanyTypeCode)
            }

            eq('id', id)

        } as Company

        return company
    }

    def save(Company company)
    {

        company.save(failOnError: true)
    }

    def delete(Company company)
    {

        company.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForList(def data)
    {

        List formattedData = data.collect {
            return [
                id       : it.id,
                code     : it.code,
                title    : it.title,
                phone    : it.phone,
                email    : it.email,
                taxNumber: it.taxNumber,
                taxOffice: it.taxOffice
            ]
        }

        return formattedData
    }

    List formatPaginatedResultForDropDown(def data)
    {

        List formattedData = data.collect {
            return [
                id   : it.id,
                title: it.title
            ]
        }

        return formattedData
    }

}
