package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefDriverStateService extends BaseService
{
    def getSysrefDriverStateList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(SysrefDriverState, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, SysrefDriverState)

        return result
    }

    def getSysrefDriverState(String id, String fields = null)
    {
        def driverState = SysrefDriverState.createCriteria().get {
            eq('id', id)
        } as SysrefDriverState

        driverState = formatResultForShow(driverState)
        if ( fields ) driverState = filterDataByFields(driverState, fields, SysrefDriverState)

        return driverState
    }

    List formatResultForList(List data)
    {
        List formattedData = data.collect {
            return [
                id  : it.id,
                name: it.name
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id  : data.id,
            name: data.name
        ]
    }
}
