package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class RefCompanySectorService extends BaseService {

    ManePaginatedResult getRefCompanySectorList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        return paginate(RefCompanySector, properties, closure)
    }

    RefCompanySector getRefCompanySector(String id)
    {
        RefCompanySector refCompanySector = RefCompanySector.createCriteria().get {

            eq('id', id)

        } as RefCompanySector

        return refCompanySector
    }

    def save(RefCompanySector refCompanySector) {

        refCompanySector.save(flush: true, failOnError: true)
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
