package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class RefWorkingAreaService extends BaseService
{

    ManePaginatedResult getRefWorkingAreaList(ManePaginationProperties properties)
    {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(RefWorkingArea, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, RefWorkingArea)

        return result
    }

    def getRefWorkingArea(String id, String fields = null)
    {
        def refWorkingArea = RefWorkingArea.createCriteria().get {
            eq('id', id)
        } as RefWorkingArea

        refWorkingArea = formatResultForShow(refWorkingArea)
        if ( fields ) refWorkingArea = filterDataByFields(refWorkingArea, fields, RefWorkingArea)

        return refWorkingArea
    }

    def save(RefWorkingArea refWorkingArea)
    {
        refWorkingArea.save(flush: true, failOnError: true)
    }

    def delete(RefWorkingArea refWorkingArea)
    {
        refWorkingArea.delete(flush: true, failOnError: true)
    }

    List formatResultForList(List data)
    {
        List formattedData = data.collect {
            [
                id         : it.id,
                name       : it.name,
                code       : it?.code,
                description: it?.description
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id         : data.id,
            name       : data.name,
            code       : data?.code,
            description: data?.description
        ]
    }
}
