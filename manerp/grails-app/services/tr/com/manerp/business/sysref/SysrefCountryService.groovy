package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginationProperties
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

        return paginate(SysrefCountry, properties, closure)
    }

    def getSysrefCountry(String id)
    {
        SysrefCountry country = SysrefCountry.createCriteria().get {

            eq('id', id)

        } as SysrefCountry

        return country
    }
}
