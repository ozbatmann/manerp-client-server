package tr.com.manerp.business.main.order

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.ShowCommand
import tr.com.manerp.commands.controller.order.OrderPaginationCommand
import tr.com.manerp.commands.controller.order.OrderSaveCommand
import tr.com.manerp.commands.controller.order.OrderUpdateCommand

class OrderController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE"]

    def orderService

    def getAllVendorsByOrderId(String id)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {
            List result = orderService.getAllVendorsByOrderId(id)
            maneResponse.data = result

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def index()
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            OrderPaginationCommand cmd = new OrderPaginationCommand(params)

            ManePaginatedResult result = orderService.getOrderList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort, cmd.fields), cmd.orderStateCode, cmd.company, cmd.hasVoyage)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def show()
    {
        ManeResponse maneResponse = new ManeResponse()
        def order

        try {
            ShowCommand cmd = new ShowCommand(params)

            if ( cmd.validate() ) {

                order = orderService.getOrder(cmd.id, cmd.fields)

            } else {

                maneResponse.statusCode = StatusCode.BAD_REQUEST
                throw new Exception('Parametreler uygun değil')
            }

            maneResponse.data = order
            maneResponse.statusCode = StatusCode.OK

        } catch (Exception ex) {

            if ( !order ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Görüntülenmek istenen sipariş sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def save(OrderSaveCommand cmd)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {
            if ( !cmd.validate() ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = parseValidationErrors(cmd.errors)
                throw new Exception(maneResponse.message)
            }

            Order order = new Order()
            cmd >> order
            orderService.saveOrderWithVendors(order, cmd.selectedVendors)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.data = order.id
            maneResponse.message = 'Sipariş başarıyla kaydedildi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def update(OrderUpdateCommand cmd)
    {
        ManeResponse maneResponse = new ManeResponse()
        Order order

        try {
            if ( !cmd.validate() ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = parseValidationErrors(cmd.errors)
                throw new Exception(maneResponse.message)
            }

            order = Order.get(cmd.id)
            cmd >> order
            orderService.saveOrderWithVendors(order, cmd.selectedVendors)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Sipariş başarıyla güncellendi.'

        } catch (ValidationException ex) {

            maneResponse.statusCode = StatusCode.BAD_REQUEST
            maneResponse.message = parseValidationErrors(ex.errors)
            ex.printStackTrace()

        } catch (Exception ex) {

            if ( !order ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Güncellenmek istenen sipariş sistemde bulunmamaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def delete(String id)
    {
        ManeResponse maneResponse = new ManeResponse()
        Order order = Order.get(id)

        try {

            orderService.delete(order)
            maneResponse.statusCode = StatusCode.NO_CONTENT
            maneResponse.message = 'Sipariş başarıyla silindi.'

        } catch (Exception ex) {

            if ( !order ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen sipariş sistemde bulunmamaktadır.'
            }

            if ( ex.getMessage().contains('foreign') ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = 'Silinmek istenen sipariş sistemde bulunan bir sevkiyat tarafından kullanılmaktadır.'
            }

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = maneResponse.message ?: ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}
