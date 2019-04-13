package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefCityService extends BaseService
{
    def getSysrefCityList(ManePaginationProperties properties, String countryId)
    {
        def closure = {

            eq('active', true)

            sysrefCountry {
                eq('id', countryId)
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefCity, properties, closure, excludedFields)
    }

    def getSysrefCity(String id, String fields = null)
    {
        SysrefCity city = SysrefCity.createCriteria().get {

            eq('id', id)

        } as SysrefCity

        def _city = city
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _city = filterDomainInstance(city, fieldList, excludedFields)
        }
        return _city
    }
}
