package manerp

class UrlMappings
{

    static mappings = {

        "/api/$namespace/$controller/$action"() {
            action = $action
        }

        "/api/$namespace/$controller"() {
            action = [GET: "index", POST: "save", PUT: "update", PATCH: "patch", DELETE: "delete"]
        }

        "/api/$namespace/$controller/action"() {
            action = [POST: "index"]
        }

        // TODO: find better solution. conflict with show
        "/api/$namespace/$controller/custom/validateEmail"() {
            action = [POST: "validateEmail"]
        }
        "/api/$namespace/$controller/custom/reset"() {
            action = [POST: "reset"]
        }

        "/api/$namespace/$controller/getAllVendorsByOrderId/$id"() {
            action = [GET: "getAllVendorsByOrderId"]
        }

        "/api/$namespace/$controller/custom/getWaypointsByDriverId/$id"() {
            action = [GET: "getWaypointsByDriverId"]
        }

        "/api/$namespace/$controller/custom/getVendorsByDriverId/$id"() {
            action = [GET: "getVendorsByDriverId"]
        }

        "/api/$namespace/$controller/custom/getDriverNotifications/$id"() {
            action = [GET: "getDriverNotifications"]
        }

        // TODO: find better solution. conflict with show

        "/api/$namespace/$controller/$id"() {
            action = [GET: "show", POST: "save", PUT: "update", PATCH: "patch", DELETE: "delete"]
        }

        "/api/$namespace/$controller/$id/$sub"(method: "GET") {
            action = { params.sub }
        }

        "/$namespace/$controller/$action?/$id?(.$format)?" {
            namespace = {
                params.namespace
            }
        }

        "/"(uri: "/index.html")
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
