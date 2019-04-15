package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class RefStaffTitleService extends BaseService
{

    ManePaginatedResult getRefStaffTitleList(ManePaginationProperties properties)
    {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(RefStaffTitle, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, RefStaffTitle)
        return result
    }

    def getRefStaffTitle(String id, String fields=null)
    {
        def refStaffTitle = RefStaffTitle.createCriteria().get {
            eq('id', id)
        } as RefStaffTitle

        refStaffTitle = formatResultForShow(refStaffTitle)
        if ( fields ) refStaffTitle = filterDataByFields(refStaffTitle, fields, RefStaffTitle)

        return refStaffTitle
    }

    def save(RefStaffTitle refStaffTitle)
    {

        refStaffTitle.save(flush: true, failOnError: true)
    }

    def delete(RefStaffTitle refStaffTitle)
    {

        refStaffTitle.delete(flush: true, failOnError: true)
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
