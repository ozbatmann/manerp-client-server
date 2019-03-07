package tr.com.manerp.auth

import grails.converters.JSON


class AwcUserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def awcUserService

    def addAwcUser() {
        HashMap jsonMap = awcUserService.addAwcUser(request.JSON.awcUserJson)
        render jsonMap as JSON
    }

}
