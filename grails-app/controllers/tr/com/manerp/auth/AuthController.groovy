package tr.com.manerp.auth

import grails.converters.JSON
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tr.com.manerp.business.sysref.SysrefGender

import java.text.SimpleDateFormat

class AuthController {

   /* protected final Logger log = LoggerFactory.getLogger(getClass().name)

    def springSecurityService
    def statelessTokenProvider
    def statelessAuthenticationProvider
    def awcTokenTrackService

    def index() {

        try {
*//*
            Person person = new Person()
            person.name = "Berat"
            person.lastName = "Postalcioglu"
            person.email = "bpostalci@gmail.com"
            person.sysrefGender = SysrefGender.findByCode("MAL")
            person.tcIdNumber = 25621561716
            person.isStaff = true
            person.save(flush: true, failOnError: true)

            AwcUser awcUser = new AwcUser()
            awcUser.awcCompany = AwcCompany.get(1)
            awcUser.username = "admin"
            awcUser.password = "1234"
            awcUser.accountLocked = false
            awcUser.accountExpired = false
            awcUser.active = true
            awcUser.passwordExpired = false
            awcUser.isDeletable = false
            awcUser.person = person
            awcUser.createTime = new Date()

            awcUser.save(flush: true, failOnError: true)
            render "Success!!!"*//*

        } catch (Exception ex) {

            ex.printStackTrace()
            render "Error!!!"

        }


    }

    def signIn = {

        HashMap jsonMap = new HashMap()
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM")

        def status = true
        def errors = null
        def awcUserData = new HashMap()

        def token = null
        log.debug "sign in username  : " + request.JSON.username
        log.debug "sign in password  : " + request.JSON.password

        try {

            AwcUser awcUser = AwcUser.findByUsername(request.JSON.username)

            if (awcUser != null) {

                if (awcUser.password == request.JSON.password) {

                    token = statelessTokenProvider.generateToken(awcUser.username)

                    awcUserData = [
                            id          : awcUser.id,
                            awcCompanyId: awcUser.awcCompany.id,
                            name        : awcUser.person.name,
                            lastName    : awcUser.person.lastName,
                            email       : awcUser.person.email,
                            createTime  : sdf.format(awcUser.createTime),
                            token       : token
                    ]

                } else {

                    status = "usernamePasswordMismatch"

                }

            } else {

                status = "usernameNotFound"

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

        render jsonMap as JSON
    }

    def testToken(token) {
        def map = statelessTokenProvider.validateAndExtractToken(tokenValue)

        log.debug "Token authenticated. tokenValue : " + tokenValue
        log.debug "testToken Map: " + map
    }*/

}
