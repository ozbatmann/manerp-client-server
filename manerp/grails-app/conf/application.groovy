grails.databinding.dateFormats = ["dd/MM/yyyy", "dd/MM/yyyy HH:mm"]

grails.gorm.default.mapping = {
    version false
    id column: 'id', generator: 'uuid', length: 32
    table schema: 'manerp'
}
grails.gorm.default.constraints = {
    // default global constraints here...
    '*'(nullable: true)
}

manerp {
    randomCode {
        length = 8
        charset = '0123456789abcdef'
    }
    // custom config variables/constants here...
    postgresql {
        maxByteSize = 20 * 1024 * 1024 // 20MB
    }
    domain {
        excludedFields = ['sysCompany'] as HashSet
    }
    routing {
        key = "AhWheMPRKfucF4QUSzDJ7avIMgpQDwL6t0C_NufIKUUeOJHwgpMiAWV3tE0Qhiw1"
    }
}

environments {
    development {

        server {
            port = System.getenv('MANERP_SERVER_PORT') ?: 8082
        }
        dataSource {
            dbCreate = 'update'
            url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://157.230.125.223:5432/manerp'
        }
    }
    production {

        server {
            port = System.getenv('MANERP_SERVER_PORT') ?: 8082
        }
        dataSource {
            dbCreate = 'update'
            url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://157.230.125.223:5432/manerp'
        }
    }
}
