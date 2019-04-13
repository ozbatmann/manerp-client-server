package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class ArventoDeviceService extends BaseService
{

    ManePaginatedResult getArventoDeviceList(ManePaginationProperties properties)
    {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(ArventoDevice, properties, closure, excludedFields)
    }

    ArventoDevice getArventoDevice(String id)
    {
        ArventoDevice arventoDevice = ArventoDevice.createCriteria().get {

            eq('id', id)

        } as ArventoDevice

        return arventoDevice
    }

    def save(ArventoDevice arventoDevice)
    {

        arventoDevice.save(flush: true, failOnError: true)
    }

    def delete(ArventoDevice arventoDevice)
    {

        arventoDevice.delete(flush: true, failOnError: true)
    }

}
