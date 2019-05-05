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
    rest{
        manerpPortal{
            url = 'http://157.230.125.223/'
        }
    }
    auth{
        restMethod {
            synchronizeRedisWithDB = "api/v1/redis/synchronizeRedisWithDB"
        }
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
//            url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://157.230.125.223:5432/manerp'
            url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://157.230.125.223:5432/manerp'
        }
        grails{
            redis {
                database = System.getenv('REDIS_DB')?.toInteger() ?: 1
                host = System.getenv('REDIS_HOST') ?: '157.230.125.223'
                port = System.getenv('REDIS_PORT')?.toInteger() ?: 6379
                timeout = System.getenv('REDIS_TIMEOUT')?.toInteger() ?: 15000
                poolConfig {
                    maxIdle = System.getenv('REDIS_POOL_MAX_IDLE')?.toInteger() ?: 20
                    doesnotexist = System.getenv('REDIS_POOL_DOES_NOT_EXIST')?.toBoolean() ?: true
                }
            }
        }

    }
    production {

        server {
            port = System.getenv('MANERP_SERVER_PORT') ?: 8082
        }
        dataSource {
            dbCreate = 'update'
//            url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://157.230.125.223:5432/manerp'
            url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://157.230.125.223:5432/manerp'
        }
    }
}
