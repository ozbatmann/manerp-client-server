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

        return paginate(RefWorkingArea, properties, closure)
    }

    RefWorkingArea getRefWorkingArea(String id)
    {
        RefWorkingArea refWorkingArea = RefWorkingArea.createCriteria().get {

            eq('id', id)

        } as RefWorkingArea

        return refWorkingArea
    }

    def save(RefWorkingArea refWorkingArea)
    {

        refWorkingArea.save(failOnError: true)
    }

    def delete(RefWorkingArea refWorkingArea)
    {

        refWorkingArea.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data)
    {

        List formattedData = data.collect {
            return [
                id  : it.id,
                name: it.name
            ]
        }

        return formattedData
    }


}
