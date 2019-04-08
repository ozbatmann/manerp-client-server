package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.commands.controller.common.ShowCommand

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

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefVehicleOwner, properties, closure, excludedFields)
    }

    def getSysrefVehicleOwner(ShowCommand cmd)
    {
        SysrefVehicleOwner vehicleOwner = SysrefVehicleOwner.createCriteria().get {

            eq('id', cmd.id)

        } as SysrefVehicleOwner

        def _vehicleOwner = vehicleOwner
        if ( cmd.fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(cmd.fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _vehicleOwner = filterDomainInstance(vehicleOwner, fieldList, excludedFields)
        }
        return _vehicleOwner
    }
}
