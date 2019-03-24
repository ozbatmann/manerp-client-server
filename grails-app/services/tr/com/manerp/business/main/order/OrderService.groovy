package tr.com.manerp.business.main.order

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class OrderService extends BaseService {

    ManePaginatedResult getOrderList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)
        }

        return paginate(Order, properties, closure)
    }

    def save(Order order) {

        order.save(failOnError: true)
    }

    def delete(Order order) {

        order.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                id  : it.id,
                code: it.code
            ]
        }

        return formattedData
    }

}
