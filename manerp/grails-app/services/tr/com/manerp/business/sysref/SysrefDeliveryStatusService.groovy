package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefDeliveryStatusService extends BaseService
{

    def getSysrefDeliveryStatusList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefDeliveryStatus, properties, closure, excludedFields)
    }

    def getSysrefDeliveryStatus(String id, String fields = null)
    {
        SysrefDeliveryStatus deliveryStatus = SysrefDeliveryStatus.createCriteria().get {

            eq('id', id)

        } as SysrefDeliveryStatus

        def _deliveryStatus = deliveryStatus
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _deliveryStatus = filterDomainInstance(deliveryStatus, fieldList, excludedFields)
        }
        return _deliveryStatus
    }
}
