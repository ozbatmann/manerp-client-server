package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
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

        ManePaginatedResult result = paginate(SysrefTransportationType, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, SysrefTransportationType)
        return result
    }

    def getSysrefTransportationType(String id, String fields = null)
    {
        def transportationType = SysrefTransportationType.createCriteria().get {
            eq('id', id)
        } as SysrefTransportationType

        transportationType = formatResultForShow(transportationType)
        if ( fields ) transportationType = filterDataByFields(transportationType, fields, SysrefTransportationType)

        return transportationType
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
