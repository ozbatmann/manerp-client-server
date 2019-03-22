package manerp.client.server

class BootStrap {

    def dataService

    def init = { servletContext ->

        dataService.initApplicationData()
    }
    def destroy = {
    }
}
