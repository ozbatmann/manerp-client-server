package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.company.Company

@Transactional
class RefCompanySectorService extends BaseService {


    ManePaginatedResult getRefCompanySectorList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)
        }

        return paginate(Company, properties, closure)
    }

    def save(RefCompanySector refCompanySector) {

        refCompanySector.save(failOnError: true)
    }

    def delete(RefCompanySector refCompanySector) {

        refCompanySector.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                id  : it.id,
                name: it.name
            ]
        }

        return formattedData
    }

}
