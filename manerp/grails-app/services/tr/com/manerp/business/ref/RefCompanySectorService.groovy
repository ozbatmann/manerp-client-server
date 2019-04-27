package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class RefCompanySectorService extends BaseService
{

    ManePaginatedResult getRefCompanySectorList(ManePaginationProperties properties)
    {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(RefCompanySector, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, RefCompanySector)

        return result
    }

    def getRefCompanySector(String id, String fields = null)
    {
        def refCompanySector = RefCompanySector.createCriteria().get {
            eq('id', id)
        } as RefCompanySector

        refCompanySector = formatResultForShow(refCompanySector)
        if ( fields ) refCompanySector = filterDataByFields(refCompanySector, fields, RefCompanySector)

        return refCompanySector
    }

    def save(RefCompanySector refCompanySector)
    {
        refCompanySector.save(flush: true, failOnError: true)
    }

    def delete(RefCompanySector refCompanySector)
    {
        refCompanySector.delete(flush: true, failOnError: true)
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
