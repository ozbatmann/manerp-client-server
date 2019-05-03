package tr.com.manerp.optimization

import manerp.response.plugin.response.ManeResponse
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.commands.controller.voyage.OptimizationParametersSaveCommand
import tr.com.manerp.commands.controller.voyage.OptimizationParametersUpdateCommand

class OptimizationController extends BaseController
{
    static namespace = "v1"
    static allowedMethods = [index: "POST", save: "POST", update: "PUT"]

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

        } catch (Exception ex) {

        }

        render maneResponse
    }

    // update optimization parameters and received waypoints
    def update(OptimizationParametersUpdateCommand cmd)
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

        } catch (Exception ex) {

        }

        render maneResponse
    }
}
