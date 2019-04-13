package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class RefCompanySectorService extends BaseService
{

    ManePaginatedResult getRefCompanySectorList(ManePaginationProperties properties)
    {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(RefCompanySector, properties, closure, excludedFields)
    }

    def getRefCompanySector(String id, String fields)
    {
        RefCompanySector refCompanySector = RefCompanySector.createCriteria().get {

            eq('id', id)

        } as RefCompanySector

        def _refCompanySector = refCompanySector
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _refCompanySector = filterDomainInstance(refCompanySector, fieldList, excludedFields)
        }

        return _refCompanySector
    }

    def save(RefCompanySector refCompanySector)
    {

        refCompanySector.save(flush: true, failOnError: true)
    }

    def delete(RefCompanySector refCompanySector)
    {

        refCompanySector.delete(flush: true, failOnError: true)
    }

}
