package tr.com.manerp.business.main.voyage

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.order.VoyageOrder
import tr.com.manerp.business.sysref.SysrefDriverState
import tr.com.manerp.business.sysref.SysrefVehicleState
import tr.com.manerp.commands.controller.common.PaginationCommand

@Transactional
class VoyageService extends BaseService
{
    def orderService
    def driverService
    def vehicleService

    ManePaginatedResult getVoyageList(ManePaginationProperties properties, String deliveryStatusCode)
    {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }

            if ( deliveryStatusCode ) {

                sysrefDeliveryStatus {
                    eq('active', true)
                    eq('code', deliveryStatusCode)
                }
            }
        }

        ManePaginatedResult result = paginate(Voyage, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, Voyage)

        return result
    }

    def getVoyage(String id, String fields = null)
    {
        def voyage = Voyage.createCriteria().get {
            eq('id', id)
        } as Voyage

        voyage = formatResultForShow(voyage)
        if ( fields ) voyage = filterDataByFields(voyage, fields, Voyage)

        return voyage
    }

    def save(Voyage voyage)
    {
        driverService.saveDriverWithState(voyage.driver, SysrefDriverState.findByCode('ONVOYAGE'))
        vehicleService.saveVehicleWithState(voyage.vehicle, SysrefVehicleState.findByCode('ONVOYAGE'))
        voyage.save(flush: true, failOnError: true)
    }

    def delete(Voyage voyage)
    {
        voyage.delete(flush: true, failOnError: true)
    }

    List formatResultForList(def data)
    {
        // TODO : collect assigned orders and voyageRoutes and companies
        List formattedData = data.collect {
            [
                id                      : it.id,
                code                    : it.code,
                driver                  : it.driver ? [id: it.driver.id, name: it.driver.getFullName()] : null,
                sysrefTransportationType: it.sysrefTransportationType ? [id: it.sysrefTransportationType.id, name: it.sysrefTransportationType.name] : null,
                sysrefVoyageDirection   : it.sysrefVoyageDirection ? [id: it.sysrefVoyageDirection.id, name: it.sysrefVoyageDirection.name] : null,
                vehicle                 : it.vehicle ? [id: it.vehicle.id, name: it.vehicle.plateNumber] : null
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id                      : data.id,
            code                    : data.code,
            driver                  : data.driver ? [id: data.driver.id, name: data.driver.getFullName()] : null,
            sysrefTransportationType: data.sysrefTransportationType ? [id: data.sysrefTransportationType.id, name: data.sysrefTransportationType.name] : null,
            sysrefVoyageDirection   : data.sysrefVoyageDirection ? [id: data.sysrefVoyageDirection.id, name: data.sysrefVoyageDirection.name] : null,
            vehicle                 : data.vehicle ? [id: data.vehicle.id, name: data.vehicle.plateNumber] : null
        ]
    }

    List getAllOrdersByVoyageId(String voyageId)
    {
        VoyageOrder voyageOrderList = VoyageOrder.createCriteria().list {
            voyage {
                eq('id', voyageId)
            }

        }.collect {

            return [
                id   : it.id,
                order: orderService.getOrder(it.order.id)
            ]
        }

        return voyageOrderList as List
    }

}
