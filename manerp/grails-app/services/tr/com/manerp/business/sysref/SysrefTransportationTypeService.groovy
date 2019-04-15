package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefTransportationTypeService extends BaseService
{

    def getSysrefTransportationTypeList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefTransportationType, properties, closure, excludedFields)
    }

    def getSysrefTransportationType(String id, String fields = null)
    {
        SysrefTransportationType transportationType = SysrefTransportationType.createCriteria().get {

            eq('id', id)

        } as SysrefTransportationType

        def _transportationType = transportationType
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _transportationType = filterDomainInstance(transportationType, fieldList, excludedFields)
        }
        return _transportationType
    }
}