package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefVehicleOwnerService extends BaseService
{

    def getSysrefVehicleOwnerList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(SysrefVehicleOwner, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, SysrefVehicleOwner)

        return result
    }

    def getSysrefVehicleOwner(String id, String fields = null)
    {
        def vehicleOwner = SysrefVehicleOwner.createCriteria().get {
            eq('id', id)
        } as SysrefVehicleOwner

        vehicleOwner = formatResultForShow(vehicleOwner)
        if ( fields ) vehicleOwner = filterDataByFields(vehicleOwner, fields, SysrefVehicleOwner)

        return vehicleOwner
    }

    List formatResultForList(List data)
    {
        List formattedData = data.collect {
            return [
                id  : it.id,
                name: it.name
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id  : data.id,
            name: data.name
        ]
    }
}
