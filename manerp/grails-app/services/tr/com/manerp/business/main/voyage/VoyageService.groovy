package tr.com.manerp.business.main.voyage

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.commands.controller.common.PaginationCommand

@Transactional
class VoyageService extends BaseService
{

    ManePaginatedResult getVoyageList(ManePaginationProperties properties, String companyId, String deliveryStatusCode)
    {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }

            if ( companyId ) {

                company {
                    eq('active', true)
                    eq('id', companyId)
                }
            }

            if ( deliveryStatusCode ) {

                sysrefDeliveryStatus {
                    eq('active', true)
                    eq('code', deliveryStatusCode)
                }
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(Voyage, properties, closure, excludedFields)
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

        voyage.save(flush: true, failOnError: true)
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
                driver                  : [id: it.driver.id, name: it.driver.getFullName()],
                order                   : [id: it.order.id, name: it.order.name],
                sysrefTransportationType: [id: it.sysrefTransportationType.id, name: it.sysrefTransportationType.name],
                sysrefVoyageDirection   : [id: it.sysrefVoyageDirection.id, name: it.sysrefVoyageDirection.name]
            ]
        }

        formattedData
    }

}
