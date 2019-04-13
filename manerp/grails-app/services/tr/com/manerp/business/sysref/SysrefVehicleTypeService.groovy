package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefVehicleTypeService extends BaseService
{

    def getSysrefVehicleTypeList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefVehicleType, properties, closure, excludedFields)
    }

    def getSysrefVehicleType(String id, String fields = null)
    {
        SysrefVehicleType vehicleType = SysrefVehicleType.createCriteria().get {

            eq('id', id)

        } as SysrefVehicleType

        def _vehicleType = vehicleType
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _vehicleType = filterDomainInstance(vehicleType, fieldList, excludedFields)
        }
        return _vehicleType
    }
}
