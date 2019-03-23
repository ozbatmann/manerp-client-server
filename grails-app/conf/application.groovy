grails.databinding.dateFormats = ["dd/MM/yyyy", "dd/MM/yyyy HH:mm"]

grails.gorm.default.mapping = {
    version false
    id column: 'id', generator: 'uuid', length: 32
    table schema: 'manerp'
}

grails.gorm.default.constraints = {
    // default global constraints here...
    '*'(nullable: true)
    necessary(nullable: false, blank: false)
    optional(nullable: true, blank: true)
}

manerp {
    // custom config variables/constants here...
    postgresql {
        maxByteSize = 20 * 1024 * 1024 // 20MB
    }
}

environments {
    development {

        server {
            port = System.getenv('MANERP_SERVER_PORT') ?: 8081
        }
        dataSource {
            dbCreate = 'update'
            url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://157.230.125.223:5432/manerp'
        }
    }
    production {

        server {
            port = System.getenv('MANERP_SERVER_PORT') ?: 8081
        }
        dataSource {
            dbCreate = 'update'
            url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://157.230.125.223:5432/manerp'
        }
    }
}
