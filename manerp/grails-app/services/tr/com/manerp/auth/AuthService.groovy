package tr.com.manerp.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import grails.gorm.transactions.Transactional
import tr.com.manerp.base.service.BaseService

import java.nio.charset.StandardCharsets

@Transactional
class AuthService extends BaseService{

    def signIn(String username, String password) {

        boolean validationStatus = isValidUser(username, password)

        if(validationStatus){
            User user = User.findByUsername(username)
            return user
        }

        return validationStatus

    }

    boolean isValidUser(String username, String password) {

        User incomingUser = User.findByUsername(username)

        if (incomingUser == null) {
            throw new Exception("general.user.noValidUser")
        }


        byte[] encryptedPassword = BCrypt.withDefaults().hash(6, incomingUser.getSalt(), password.getBytes(StandardCharsets.UTF_8))

        User user = User.createCriteria().get {

            eq("username", username)
            eq("passwordHash", encryptedPassword)

        } as User

        if (user == null) {

            throw new Exception("general.user.noValidUser")
        } else {

            if (!user.active) {

                throw new Exception("general.user.noActiveUser")
            }

            if (user.accountExpired) {

                throw new Exception("general.user.accountExpired")
            } else if (user.accountLocked) {

                throw new Exception("general.user.accountLocked")
            } else if (user.passwordExpired) {

                throw new Exception("general.user.passwordExpired")
            }
        }

        true
    }

}
