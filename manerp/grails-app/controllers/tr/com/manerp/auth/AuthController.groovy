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
            maneResponse.statusCode = StatusCode.OK
            maneResponse.data = signInResponse

        } catch (Exception ex) {
            maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.data = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    String generateAccessToken(String username){
        String tokenValue

        try{
            //load user details
            def userDetails = User.findByUsername(username)

            //generate access token
            tokenValue = tokenGenerator.generateAccessToken(userDetails).accessToken

            //store access token
            tokenStorageService.storeToken(tokenValue, userDetails)
        } catch (Exception e){
            //Exception handling code
        }

        return tokenValue
    }

}
