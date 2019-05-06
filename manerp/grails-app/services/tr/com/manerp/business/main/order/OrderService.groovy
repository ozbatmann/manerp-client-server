package tr.com.manerp.business.main.order

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.company.VendorService
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.sysref.SysrefOrderState

import java.text.SimpleDateFormat

@Transactional
class OrderService extends BaseService
{
    VendorService vendorService

    ManePaginatedResult getOrderList(ManePaginationProperties properties, String orderStateCode, String companyId, Boolean hasVoyage = null)
    {
        def orderIdList = getAllOrderIdsThatHasVoyage()

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

            if ( hasVoyage != null && !orderIdList.isEmpty() ) {
                if ( !hasVoyage ) {
                    not { 'in'('id', orderIdList) }
                } else {
                    'in'('id', orderIdList)
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

    def saveOrderWithSysrefOrderState(Order order, SysrefOrderState sysrefOrderState)
    {
        order.sysrefOrderState = sysrefOrderState
        save(order)
    }

    def saveOrderWithVendors(Order order, List<Vendor> vendors)
    {
        save(order)
        vendors.each { vendor ->
            OrderVendor orderVendor = new OrderVendor()
            orderVendor._order = order
            orderVendor.vendor = vendor
            orderVendor.active = true
            orderVendor.sysCompany = SysCompany.findByName('Bumerang Lojistik') // TODO: change
            orderVendor.setRandomCode()
            orderVendor.save(flush: true, failOnError: true)
        }
    }

    def delete(Order order)
    {
        deleteOrderWithVendors(order)
        order.delete(flush: true, failOnError: true)
    }

    def deleteOrderWithVendors(Order order)
    {
        OrderVendor.createCriteria().list {
            eq('_order', order)
        }.each {
            it.delete(flush: true, failOnError: true)
        }
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

        List vendorList = getAllVendorsByOrderId(data.id)
        vendorList = vendorList ? vendorList.collect {
            return [
                id     : it.id,
                title  : it.title,
                address: it.address
            ]
        } : []

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
            sysrefOrderState : data.sysrefOrderState ? [id: data.sysrefOrderState.id, name: data.sysrefOrderState.name] : null,
            selectedVendors  : vendorList
        ]
    }

    List getAllVendorsByOrderId(String orderId)
    {
        def orderVendorList = OrderVendor.createCriteria().list {
            _order {
                eq('id', orderId)
            }
        }.collect {
            return [
                id      : it.vendor.id,
                title   : it?.vendor?.title,
                address : it?.vendor?.address,
                location: it?.vendor?.location ? [it.vendor.location.latitude, it.vendor.location.longitude] : null
            ]
        }

        return orderVendorList as List
    }

    def getOrderByVoyage(Voyage voyage)
    {
        return VoyageOrder.findByVoyage(voyage).order
    }

    List getAllOrderIdsThatHasVoyage()
    {
        VoyageOrder.createCriteria().list {}.collect {
            it.order.id
        }
    }
}
