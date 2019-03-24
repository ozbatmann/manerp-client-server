package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class ArventoDeviceService extends BaseService {

    ManePaginatedResult getArventoDeviceList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)
        }

        return paginate(ArventoDevice, properties, closure)
    }

    def save(ArventoDevice arventoDevice) {

        arventoDevice.save(failOnError: true)
    }

    def delete(ArventoDevice arventoDevice) {

        arventoDevice.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                id      : it.id,
                deviceId: it.deviceId
            ]
        }

        return formattedData
    }
}
