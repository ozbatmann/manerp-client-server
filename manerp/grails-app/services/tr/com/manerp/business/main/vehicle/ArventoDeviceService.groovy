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

        ManePaginatedResult result = paginate(ArventoDevice, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, ArventoDevice)

        return result
    }

    def getArventoDevice(String id, String fields = null)
    {
        def arventoDevice = ArventoDevice.createCriteria().get {
            eq('id', id)
        } as ArventoDevice

        arventoDevice = formatResultForShow(arventoDevice)
        if ( fields ) arventoDevice = filterDataByFields(arventoDevice, fields, ArventoDevice)

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

    List formatResultForList(List data)
    {
        List formattedData = data.collect {
            return [
                id      : it.id,
                code    : it.code,
                deviceId: it?.deviceId,
                name    : it?.name,
                vehicle : it.vehicle ? [id: it.vehicle.id, name: it.vehicle.name] : null
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id      : data.id,
            code    : data.code,
            deviceId: data?.deviceId,
            name    : data?.name,
            vehicle : data.vehicle ? [id: data.vehicle.id, name: data.vehicle.name] : null
        ]
    }
}
