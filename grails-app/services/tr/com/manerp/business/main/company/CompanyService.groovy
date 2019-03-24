package tr.com.manerp.business.main.company

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class CompanyService extends BaseService {

    ManePaginatedResult getCompanyList(ManePaginationProperties properties, String sysrefCompanyTypeCode) {

        def closure = {

            eq('active', true)

            sysrefCompanyType {
                eq('code', sysrefCompanyTypeCode)
            }

        }

        return paginate(Company, properties, closure)
    }

    def save(Company company) {

        company.save(failOnError: true)
    }

    def delete(Company company) {

        company.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                    id   : it.id,
                    title: it.title
            ]
        }

        return formattedData
    }

}
