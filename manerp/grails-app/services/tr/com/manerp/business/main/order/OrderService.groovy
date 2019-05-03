package tr.com.manerp.business.main.order

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.company.VendorService
import tr.com.manerp.business.main.resource.Staff

import java.text.SimpleDateFormat

@Transactional
class OrderService extends BaseService
{
    VendorService vendorService

    ManePaginatedResult getOrderList(ManePaginationProperties properties, String orderStateCode, String companyId)
    {

        def closure = {
            eq('active', true)
            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
            if ( companyId ) {
                company {
                    eq('active', true)
                    eq('id', companyId)
                }
            }
            if ( orderStateCode ) {
                sysrefOrderState {
                    eq('active', true)
                    eq('code', orderStateCode)
                }
            }
        }

        ManePaginatedResult result = paginate(Order, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, Order, ["fullName"] as HashSet)

        return result
    }

    def getOrder(String id, String fields = null)
    {
        def order = Order.createCriteria().get {
            eq('id', id)
        } as Order

        order = formatResultForShow(order)
        if ( fields ) order = filterDataByFields(order, fields, Order)

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

    List formatResultForList(def data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm")

        List formattedData = data.collect {
            [
                id               : it.id,
                fullName         : it.getFullName(),
                name             : it?.name,
                code             : it.code,
                sysrefRevenueType: it.sysrefRevenueType ? [id: it.sysrefRevenueType.id, name: it.sysrefRevenueType.name] : null,
                orderDate        : it.orderDate ? sdf.format(it.orderDate) : null,
                billingNo        : it?.billingNo,
                workOrderNo      : it?.workOrderNo,
                company          : it?.company?.title,
                sysrefOrderState : it.sysrefOrderState ? [id: it.sysrefOrderState.id, name: it.sysrefOrderState.name] : null
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm")

        return [
            id               : data.id,
            fullName         : data.getFullName(),
            name             : data?.name,
            code             : data.code,
            sysrefRevenueType: data.sysrefRevenueType ? [id: data.sysrefRevenueType.id, name: data.sysrefRevenueType.name] : null,
            orderDate        : data.orderDate ? sdf.format(data.orderDate) : null,
            billingNo        : data?.billingNo,
            workOrderNo      : data?.workOrderNo,
            company          : data?.company?.title,
            sysrefOrderState : data.sysrefOrderState ? [id: data.sysrefOrderState.id, name: data.sysrefOrderState.name] : null
        ]
    }

    List getAllVendorsByOrderId(String orderId)
    {
        def orderVendorList = OrderVendor.createCriteria().list {
            _order {
                eq('id', orderId)
            }
        }.collect {
            vendorService.getVendor(it.vendor.id, "title,address,location")
        }

        return orderVendorList as List
    }
}
