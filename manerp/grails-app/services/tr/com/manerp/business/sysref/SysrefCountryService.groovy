package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefCountryService extends BaseService
{
    def getSysrefCountryList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefCountry, properties, closure, excludedFields)
    }

    def getSysrefCountry(String id, String fields = null)
    {
        SysrefCountry country = SysrefCountry.createCriteria().get {

            eq('id', id)

        } as SysrefCountry

        def _country = country
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _country = filterDomainInstance(country, fieldList, excludedFields)
        }
        return _country
    }
}
