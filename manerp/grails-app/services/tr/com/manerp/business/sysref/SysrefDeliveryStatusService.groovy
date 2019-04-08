package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.commands.controller.common.ShowCommand

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

    def getSysrefDeliveryStatus(ShowCommand cmd)
    {
        SysrefDeliveryStatus deliveryStatus = SysrefDeliveryStatus.createCriteria().get {

            eq('id', cmd.id)

        } as SysrefDeliveryStatus

        def _deliveryStatus = deliveryStatus
        if ( cmd.fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(cmd.fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _deliveryStatus = filterDomainInstance(deliveryStatus, fieldList, excludedFields)
        }
        return _deliveryStatus
    }
}
