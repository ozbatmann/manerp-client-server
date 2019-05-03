package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.sysref.SysrefVehicleState

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

        ManePaginatedResult result = paginate(SemiTrailer, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, SemiTrailer)

        return result
    }

    def getSemiTrailer(String id, String fields = null)
    {
        def trailer = SemiTrailer.createCriteria().get {
            eq('id', id)
        } as SemiTrailer

        trailer = formatResultForShow(trailer)
        if ( fields ) trailer = filterDataByFields(trailer, fields, SemiTrailer)

        return trailer
    }

    def save(SemiTrailer trailer)
    {
        trailer.save(flush: true, failOnError: true)
    }

    def delete(SemiTrailer trailer)
    {
        trailer.delete(flush: true, failOnError: true)
    }

    def saveVehicleWithState(SemiTrailer trailer, SysrefVehicleState state)
    {
        trailer.sysrefVehicleState = state
        save(trailer)
    }

    List formatResultForList(List data)
    {
        List formattedData = data.collect {
            return [
                id         : it.id,
                code       : it.code,
                plateNumber: it?.plateNumber,
                brand      : it?.brand,
                name       : it?.name
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id         : data.id,
            code       : data.code,
            plateNumber: data?.plateNumber,
            brand      : data?.brand,
            name       : data?.name
        ]
    }
}
