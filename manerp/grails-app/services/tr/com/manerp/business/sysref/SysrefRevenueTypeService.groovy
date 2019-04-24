package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefRevenueTypeService extends BaseService
{

    def getSysrefRevenueTypeList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }
        ManePaginatedResult result = paginate(SysrefRevenueType, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, SysrefRevenueType)
        return result
    }

    def getSysrefRevenueType(String id, String fields = null)
    {
        def revenueType = SysrefRevenueType.createCriteria().get {
            eq('id', id)
        } as SysrefRevenueType

        revenueType = formatResultForShow(revenueType)
        if ( fields ) revenueType = filterDataByFields(revenueType, fields, SysrefRevenueType)

        return revenueType
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
