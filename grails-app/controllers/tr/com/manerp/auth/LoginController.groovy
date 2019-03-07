package tr.com.manerp.auth

import grails.converters.JSON

class LoginController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def loginService

    def singIn() {
        HashMap jsonMap = loginService.signIn(request.JSON.username, request.JSON.password)
        render jsonMap as JSON
    }

}
