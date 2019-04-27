package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefVehicleStateService extends BaseService
{

    def getSysrefVehicleStateList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(SysrefVehicleState, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, SysrefVehicleState)

        return result
    }

    def getSysrefVehicleState(String id, String fields = null)
    {
        def vehicleState = SysrefVehicleState.createCriteria().get {
            eq('id', id)
        } as SysrefVehicleState

        vehicleState = formatResultForShow(vehicleState)
        if ( fields ) vehicleState = filterDataByFields(vehicleState, fields, SysrefVehicleState)

        return vehicleState
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
