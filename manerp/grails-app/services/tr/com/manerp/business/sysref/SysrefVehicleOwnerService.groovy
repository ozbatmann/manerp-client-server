package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
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

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefVehicleOwner, properties, closure, excludedFields)
    }

    def getSysrefVehicleOwner(String id, String fields = null)
    {
        SysrefVehicleOwner vehicleOwner = SysrefVehicleOwner.createCriteria().get {

            eq('id', id)

        } as SysrefVehicleOwner

        def _vehicleOwner = vehicleOwner
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _vehicleOwner = filterDomainInstance(vehicleOwner, fieldList, excludedFields)
        }
        return _vehicleOwner
    }
}
