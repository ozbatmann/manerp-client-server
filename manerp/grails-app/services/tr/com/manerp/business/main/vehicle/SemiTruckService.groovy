package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class SemiTruckService extends BaseService {

    ManePaginatedResult getDorsetList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }

        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SemiTruck, properties, closure)
    }

    def save(SemiTruck dorset) {

        dorset.save(flush: true, failOnError: true)
    }

    def delete(SemiTruck dorset) {

        dorset.delete(flush: true, failOnError: true)
    }

}
