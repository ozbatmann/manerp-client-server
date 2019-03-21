package manerp

class UrlMappings {

    static mappings = {

        "/api/$namespace/$controller/$action"() {
            action = $action
        }

        "/api/$namespace/$controller"() {
            action = [GET: "index", POST: "save", PUT: "update", PATCH: "patch", DELETE: "delete"]
        }

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
