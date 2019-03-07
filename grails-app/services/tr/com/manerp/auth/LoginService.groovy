package tr.com.manerp.auth

import grails.transaction.Transactional

import java.text.SimpleDateFormat

@Transactional
class LoginService {

    def signIn(String username, String password) {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM")

        def status = true
        def errors = null
        def awcUserData = new HashMap()

        try {

            AwcUser awcUser = AwcUser.findByUsername(username)

            if (awcUser != null) {

                if (awcUser.password == password) {

                    awcUserData = [
                            id          : awcUser.id,
                            awcCompanyId: awcUser.awcCompany.id,
                            name        : awcUser.person.name,
                            lastName    : awcUser.person.lastName,
                            email       : awcUser.person.email,
                            createTime  : sdf.format(awcUser.createTime)
                    ]

                } else {

                    errors = "usernamePasswordMismatch"

                }

            } else {

                errors = "usernameNotFound"

            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        awcUser: awcUserData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap
    }

}
