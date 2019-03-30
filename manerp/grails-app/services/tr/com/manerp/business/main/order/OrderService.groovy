package tr.com.manerp.business.main.order

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import java.text.SimpleDateFormat

@Transactional
class OrderService extends BaseService
{

    ManePaginatedResult getOrderList(ManePaginationProperties properties, String orderStateCode)
    {

        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }

            sysrefOrderState {
                eq('active', true)
                eq('code', orderStateCode)
            }

        }

        return paginate(Order, properties, closure, ['sysCompany'] as HashSet)
    }

    Order getOrder(String id)
    {
        Order order = Order.createCriteria().get {

            eq('id', id)

        } as Order

        return order
    }

    def save(Order order)
    {

        order.save(flush: true, failOnError: true)
    }

    def delete(Order order)
    {

        order.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForList(def data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm")

        List formattedData = data.collect {
            [
                id               : it.id,
                code             : it.code,
                sysrefRevenueType: [id: it.sysrefRevenueType.id, name: it.sysrefRevenueType.name],
                orderDate        : sdf.format(it.orderDate),
                billingNo        : it.billingNo,
                company          : it.company.title
            ]
        }

        formattedData
    }

    List formatPaginatedResultForDropDown(def data)
    {

        List formattedData = data.collect {
            return [
                id  : it.id,
                name: it.name
            ]
        }

        return formattedData
    }

}
