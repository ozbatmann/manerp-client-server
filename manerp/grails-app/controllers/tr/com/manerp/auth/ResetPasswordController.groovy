package tr.com.manerp.auth

import grails.plugins.rest.client.RestResponse
import grails.util.Holders
import manerp.response.plugin.response.ManeResponse
import manerp.response.plugin.response.StatusCode
import org.grails.web.json.JSONObject
import tr.com.manerp.base.controller.BaseController
import tr.com.manerp.information.util.RestUtil

class ResetPasswordController extends BaseController
{
    static namespace = "v1"
    static allowedMethods = [validateEmail: "POST", reset: "POST"]

    def informationRestService

    def validateEmail()
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            println request.JSON
            def requestData = request.JSON

            if ( !requestData.email ) {
                throw new Exception("Sistemde böyle bir mail adresi bulunmamaktadır")
            }

            String authUrl = Holders.config.manerp.auth.url + "/api/v1/auth/custom/validateEmail"

            JSONObject json = new JSONObject()
            json.email = requestData.email

            println "authUrl: " + authUrl
            println "json: " + json

            RestResponse restResponse = RestUtil.callRestService(authUrl, json)
            JSONObject responseJson = restResponse.json as JSONObject

            println "response: " + responseJson

            if ( responseJson.status == 200 ) {
                String responseUrl = "http://localhost:8080/auth/reset-password/${responseJson.data.id}"
                def body = "Parolanızı sıfırlamanız için gerekli olan link aşağıdadır:\n${responseUrl}"
                Map paramsData = ['user': responseJson.data.name, 'body': body, 'signature': 'MANERP Yazılım']

                informationRestService.sendMail('GENERAL_INFO',
                    "Parola Sıfırlama",
                    paramsData,
                    [requestData.email] as List,
                    ["beratpostalci@gmail.com"] as List,
                    ["bpostalci@gmail.com"] as List,
                    1,
                    2
                )

            } else {
                throw new Exception("Sistemde böyle bir mail adresi bulunmamaktadır")
            }


        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }

    def reset()
    {
        ManeResponse maneResponse = new ManeResponse()

        try {

            println request.JSON
            def requestData = request.JSON

//            if ( !(requestData.userId as String).isEmpty() || !(requestData.password as String).isEmpty() ) {
//                throw new Exception("Sistemde böyle bir kullanıcı bulunmamaktadır")
//            }

            String authUrl = Holders.config.manerp.auth.url + "/api/v1/auth/custom/reset"

            JSONObject json = new JSONObject()
            json.userId = requestData.userId
            json.password = requestData.password

            println "authUrl: " + authUrl
            println "json: " + json

            RestResponse restResponse = RestUtil.callRestService(authUrl, json)
            JSONObject responseJson = restResponse.json as JSONObject

            println "response: " + responseJson

            if ( responseJson.status == 200 ) {
                maneResponse.statusCode = StatusCode.OK
                maneResponse.message = "Parola başarıyla sıfırlandı"
            } else {
                throw new Exception("Sistemde böyle bir kullanıcı bulunmamaktadır")
            }


        } catch (Exception ex) {

            if ( maneResponse.statusCode.code <= StatusCode.NO_CONTENT.code ) maneResponse.statusCode = StatusCode.INTERNAL_ERROR
            maneResponse.message = ex.getMessage()
            ex.printStackTrace()
        }

        render maneResponse
    }
}
