package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefVoyageDirectionService extends BaseService
{

    def getSysrefVoyageDirectionList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefVoyageDirection, properties, closure, excludedFields)
    }

    def getSysrefVoyageDirection(String id, String fields = null)
    {
        SysrefVoyageDirection voyageDirection = SysrefVoyageDirection.createCriteria().get {

            eq('id', id)

        } as SysrefVoyageDirection

        def _voyageDirection = voyageDirection
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _voyageDirection = filterDomainInstance(voyageDirection, fieldList, excludedFields)
        }
        return _voyageDirection
    }
}
