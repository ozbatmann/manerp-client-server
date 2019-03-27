package tr.com.manerp.business.main.voyage

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class VoyageService extends BaseService
{

    ManePaginatedResult getVoyageList(ManePaginationProperties properties)
    {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        return paginate(Voyage, properties, closure)
    }

    Voyage getVoyage(String id)
    {
        Voyage voyage = Voyage.createCriteria().get {

            eq('id', id)

        } as Voyage

        return voyage
    }

    def save(Voyage voyage)
    {

        voyage.save(failOnError: true)
    }

    def delete(Voyage voyage)
    {

        voyage.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForList(def data)
    {
        List formattedData = data.collect {
            [
                id                      : it.id,
                code                    : it.code,
                company                 : [id: it.company.id, name: it.company.title],
                driver                  : [id: it.driver.id, name: it.driver.title],
                order                   : [id: it.order.id, name: it.order.title],
                sysrefTransportationType: [id: it.sysrefTransportationType.id, name: it.sysrefTransportationType.title],
                sysrefVoyageDirection   : [id: it.sysrefVoyageDirection.id, name: it.sysrefVoyageDirection.title]
            ]
        }

        formattedData
    }

    List formatPaginatedResultForDropDown(def data)
    {

        List formattedData = data.collect {
            return [
                id  : it.id,
                code: it.code
            ]
        }

        return formattedData
    }

}
