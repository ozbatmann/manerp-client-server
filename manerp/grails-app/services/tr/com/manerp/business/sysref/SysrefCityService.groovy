package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.commands.controller.common.ShowCommand

@Transactional
class SysrefCityService extends BaseService
{
    def getSysrefCityList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefCity, properties, closure, excludedFields)
    }

    def getSysrefCity(ShowCommand cmd)
    {
        SysrefCity city = SysrefCity.createCriteria().get {

            eq('id', cmd.id)

        } as SysrefCity

        def _city = city
        if ( cmd.fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(cmd.fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _city = filterDomainInstance(city, fieldList, excludedFields)
        }
        return _city
    }
}
