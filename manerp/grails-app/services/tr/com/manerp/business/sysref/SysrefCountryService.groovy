package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.commands.controller.common.ShowCommand

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

    def getSysrefCountry(ShowCommand cmd)
    {
        SysrefCountry country = SysrefCountry.createCriteria().get {

            eq('id', cmd.id)

        } as SysrefCountry

        def _country = country
        if ( cmd.fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(cmd.fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _country = filterDomainInstance(country, fieldList, excludedFields)
        }
        return _country
    }
}
