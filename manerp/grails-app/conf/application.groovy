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
    information {
        url = "http://localhost:8083"
    }
    domain {
        excludedFields = ['sysCompany'] as HashSet
    }
    rest {
        manerpPortal {
            //url = 'http://157.230.125.223/'
            url = 'http://localhost:8091/'
        }
    }
    auth {
        url = 'http://localhost:8091/'
        filter {
            enabled = false
            exclude = [
                [controller: 'actionItem', action: 'synchronizeActionItemRedis'],
                [controller: 'actionItem', action: 'existAuthorization'],
                [controller: 'actionItem', action: 'existActionItemByCalledMethod'],
                [controller: 'actionItem', action: 'synchronizeActionItemDB'],
                [controller: 'auth', action: 'login'],
                [controller: 'auth', action: 'logout'],
                [controller: 'redis', action: 'synchronizeRedisWithDB'],
                [controller: 'passwordOperation', action: 'sendGeneratedLink'],
                [controller: 'passwordOperation', action: 'checkLinkValidation'],
                [controller: 'passwordOperation', action: 'changePassword'],
            ]

        }
        restMethod {
            synchronizeRedisWithDB = "api/v1/redis/synchronizeRedisWithDB"

            user {
                getAllUserList = "api/v1/rest/getAllUserList"
                getAllUserListBySearchParam = "api/v1/rest/getAllUserListBySearchParam"
                addUser = "api/v1/rest/addUser"
                updateUser = "api/v1/rest/updateUser"
                deleteUser = "api/v1/rest/deleteUser"
            }
            role {
                getAllRoleList = "api/v1/rest/getAllRoleList"
                addRole = "api/v1/rest/addRole"
                updateRole = "api/v1/rest/updateRole"
                deleteRole = "api/v1/rest/deleteRole"
            }
            rolePermission {
                getAllRolePermissionList = "api/v1/rest/getAllRolePermissionList"
                addRolePermission = "api/v1/rest/addRolePermission"
                deleteRolePermission = "api/v1/rest/deleteRolePermission"
            }
            securitySubjectPermission {
                getAllSecuritySubjectPermissionList = "api/v1/rest/getAllSecuritySubjectPermissionList"
            }
            userOrganizationRole {
                addUserOrganizationRole = "api/v1/rest/addUserOrganizationRole"
            }
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
            url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://localhost:5432/manerp'
            //   url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://157.230.125.223:5432/manerp'
            //url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://192.168.1.40:5432/manerp'
        }
        grails {
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
            url = System.getenv('POSTGRESQL_URL') ?: 'jdbc:postgresql://localhost:5432/manerp'
        }
    }
}
