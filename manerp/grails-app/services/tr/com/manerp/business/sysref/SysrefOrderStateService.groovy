package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefOrderStateService extends BaseService
{
    def getSysrefOrderStateList(ManePaginationProperties properties)
    {
        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(SysrefOrderState, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, SysrefOrderState)
        return result
    }

    def getSysrefOrderState(String id, String fields = null)
    {
        def orderState = SysrefOrderState.createCriteria().get { eq('id', id) } as SysrefOrderState
        orderState = formatResultForShow(orderState)
        if ( fields ) orderState = filterDataByFields(orderState, fields, SysrefOrderState)

        return orderState
    }

    List formatResultForList(List data)
    {
        List formattedData = data.collect {
            [
                id  : it.id,
                name: it.name,
                code: it?.code
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id  : data.id,
            name: data.name,
            code: data?.code
        ]
    }
}
