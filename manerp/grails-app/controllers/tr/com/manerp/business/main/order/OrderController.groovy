package tr.com.manerp.business.main.order

import grails.validation.ValidationException
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.common.PaginationCommand
import tr.com.manerp.commands.controller.order.OrderPaginationCommand

class OrderController extends BaseController
{

    static namespace = "v1"
    static allowedMethods = [index: "GET", show: "GET", save: "POST", update: "PUT", delete: "DELETE", getListForDropDown: "GET"]

    def orderService

    def index()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            OrderPaginationCommand cmd = new OrderPaginationCommand(params)

            ManePaginatedResult result = orderService.getOrderList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort), cmd.orderStateCode)
            result.data = orderService.formatPaginatedResultForList(result.data)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def show(String id)
    {

        ManeResponse maneResponse = new ManeResponse()
        Order order = orderService.getOrder(id)

        try {

            if ( !order ) throw new Exception()

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

    def save(Order order)
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            order.setRandomCode()
            // TODO: change orderData
            order.orderDate = new Date()
            orderService.save(order)
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

    def update(Order order)
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            orderService.save(order)
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

    def getListForDropDown()
    {

        ManeResponse maneResponse = new ManeResponse()

        try {

            PaginationCommand cmd = new PaginationCommand(params)

            ManePaginatedResult result = orderService.getOrderList(new ManePaginationProperties(cmd.limit, cmd.offset, cmd.sort))
            result.data = orderService.formatPaginatedResultForDropDown(result.data)
            maneResponse.data = result.toMap()

        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}
