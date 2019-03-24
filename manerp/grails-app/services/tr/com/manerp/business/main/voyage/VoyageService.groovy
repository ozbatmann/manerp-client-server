package tr.com.manerp.business.main.voyage

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class VoyageService extends BaseService {
    
    ManePaginatedResult getVoyageList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)
        }

        return paginate(Voyage, properties, closure)
    }

    def save(Voyage voyage) {

        voyage.save(failOnError: true)
    }

    def delete(Voyage voyage) {

        voyage.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                id  : it.id,
                code: it.code
            ]
        }

        return formattedData
    }

}
