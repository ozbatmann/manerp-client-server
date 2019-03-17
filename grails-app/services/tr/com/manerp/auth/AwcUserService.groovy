package tr.com.manerp.auth

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONObject
import tr.com.manerp.business.sysref.SysrefGender

@Transactional
class AwcUserService {

    def addAwcUser(JSONObject awcUserJson) {

        HashMap jsonMap = new HashMap()
        def status = true
        def errors = null
        def id = null

        try {

            User username = User.findByUsername(awcUserJson.username)
            Person email = Person.findByEmail(awcUserJson.email)

            if (username != null) {

                errors = "usernameExist"

            } else if (email != null) {

                errors = "emailExist"

            } else {

                Person person = new Person()
                person.name = awcUserJson.name
                person.lastName = awcUserJson.lastName
                person.email = awcUserJson.email
                person.sysrefGender = SysrefGender.get(awcUserJson.sysrefGender)
                person.tcIdNumber = awcUserJson.tcIdNumber
                // TODO:
                person.isStaff = false
                person = person.save(flush: true, failOnError: true)

                User awcUser = new User()
                awcUser.username = awcUserJson.username
                awcUser.password = awcUserJson.password
                awcUser.awcCompany = SysCompany.get(awcUserJson.awcCompany)
                // TODO: awcUser.version = awcUserJson.version
                awcUser.accountExpired = false
                awcUser.accountLocked = false
                awcUser.createTime = new Date()
                awcUser.active = true
                awcUser.passwordExpired = false
                // TODO: awcUser.salt = awcUserJson.salt
                awcUser.isDeletable = true
                awcUser.person = person
                awcUser.save(flush: true, failOnError: true)

                id = awcUser.id

            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        id: id
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

}
