package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.resource.Staff

@Transactional
class SemiTrailerService extends BaseService
{

    ManePaginatedResult getSemiTrailerList(ManePaginationProperties properties)
    {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        return paginate(SemiTrailer, properties, closure, ['sysCompany'] as HashSet)
    }

    SemiTrailer getSemiTrailer(String id)
    {
        SemiTrailer trailer = SemiTrailer.createCriteria().get {

            eq('id', id)

        } as SemiTrailer

        return trailer
    }

    def save(SemiTrailer trailer)
    {

        trailer.save(fflush: true, ailOnError: true)
    }

    def delete(SemiTrailer trailer)
    {

        trailer.delete(flush: true, failOnError: true)
    }

}
