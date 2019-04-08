package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefService extends BaseService
{

    ManePaginatedResult getList(ManePaginationProperties properties, Class type, Closure customClosure = null)
    {

        def closure = {

            if ( customClosure ) customClosure

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        return paginate(type, properties, closure)
    }

}
