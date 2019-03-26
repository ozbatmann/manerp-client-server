package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefService extends BaseService {

    ManePaginatedResult getList(ManePaginationProperties properties, Class type) {

        def closure = {
            eq('active', true)
        }

        return paginate(type, properties, closure)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                id  : it.id,
                name: it.name
            ]
        }

        return formattedData
    }

}
