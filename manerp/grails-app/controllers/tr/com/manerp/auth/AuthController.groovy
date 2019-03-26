package tr.com.manerp.auth

import grails.converters.JSON
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import tr.com.manerp.base.controller.BaseController

class AuthController extends BaseController{

    static namespace = "v1"
    def authService

    def signIn() {

        ManeResponse maneResponse = new ManeResponse()

        try {
            def signInResponse = authService.signIn(request.JSON.username.toString(), request.JSON.password.toString())
            maneResponse.statusCode = StatusCode.ACCEPTED
            maneResponse.data = signInResponse

        } catch (Exception ex) {
            maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.data = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

}
