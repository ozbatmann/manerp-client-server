package tr.com.manerp.optimization

import grails.validation.ValidationException
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.business.optimization.OptimizationService
import tr.com.manerp.commands.controller.voyage.OptimizationParametersSaveCommand
import tr.com.manerp.commands.controller.voyage.OptimizationParametersUpdateCommand

class OptimizationController extends BaseController
{
    static namespace = "v1"
    static allowedMethods = [index: "POST", save: "POST", update: "PUT"]

    OptimizationService optimizationService

    // optimize received routes and saves them to VoyageRoute
    def index()
    {
        ManeResponse maneResponse = new ManeResponse()

        try {


        } catch (Exception ex) {

        }

        render maneResponse
    }

    // persist selected optimization parameters and received waypoints
    def save(OptimizationParametersSaveCommand cmd)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            if ( !cmd.validate() ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = parseValidationErrors(cmd.errors)
                throw new Exception(maneResponse.message)
            }

            OptimizationParameters optimizationParameters = new OptimizationParameters()
            cmd >> optimizationParameters

            optimizationService.saveOptimizationParametersWithVoyage(optimizationParameters, cmd.voyage)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.message = 'Optimizasyon parametreleri başarıyla kaydedildi.'

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

    // update optimization parameters and received waypoints
    def update(OptimizationParametersUpdateCommand cmd)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            if ( !cmd.validate() ) {
                maneResponse.statusCode = StatusCode.BAD_REQUEST
                maneResponse.message = parseValidationErrors(cmd.errors)
                throw new Exception(maneResponse.message)
            }

            OptimizationParameters optimizationParameters = OptimizationParameters.get(cmd.id)
            cmd >> optimizationParameters

            optimizationService.saveOptimizationParametersWithVoyage(optimizationParameters, cmd.voyage)
            maneResponse.statusCode = StatusCode.CREATED
            maneResponse.message = 'Optimizasyon parametreleri başarıyla güncellendi.'

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
}
