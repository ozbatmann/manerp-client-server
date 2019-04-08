package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.commands.controller.common.ShowCommand

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

    def getSysrefVehicleState(ShowCommand cmd)
    {
        SysrefVehicleState vehicleState = SysrefVehicleState.createCriteria().get {

            eq('id', cmd.id)

        } as SysrefVehicleState

        def _vehicleState = vehicleState
        if ( cmd.fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(cmd.fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _vehicleState = filterDomainInstance(vehicleState, fieldList, excludedFields)
        }
        return _vehicleState
    }
}
