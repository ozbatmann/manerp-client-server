package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
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

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefVehicleState, properties, closure, excludedFields)
    }

    def getSysrefVehicleState(String id, String fields = null)
    {
        SysrefVehicleState vehicleState = SysrefVehicleState.createCriteria().get {

            eq('id', id)

        } as SysrefVehicleState

        def _vehicleState = vehicleState
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _vehicleState = filterDomainInstance(vehicleState, fieldList, excludedFields)
        }
        return _vehicleState
    }
}
