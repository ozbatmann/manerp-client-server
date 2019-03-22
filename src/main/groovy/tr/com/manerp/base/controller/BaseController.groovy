package tr.com.manerp.base.controller

import manerp.response.plugin.response.ManeResponseController
import org.springframework.validation.Errors

class BaseController extends ManeResponseController {

    private static String parseDefaultMessage(String defaultMessage) {

        String result = ''
        String uniquePattern = 'unique'
        String nullPattern = 'null'

        if ( defaultMessage.contains(uniquePattern) ) {

            result = 'eşsiz olmadığı için reddedildi.'

        } else if (defaultMessage.contains(nullPattern)) {

            result = 'değer zorunlu olduğu için reddedildi.'
        }

        return result
    }

    protected static String parseValidationErrors(Errors errors) {

        StringBuilder stringBuilder = new StringBuilder()

        errors.allErrors.each { item ->

            stringBuilder.append("${item.field} alanı için sağlanan ${item.rejectedValue} ${parseDefaultMessage(item.defaultMessage)}\n")
        }

        return stringBuilder.toString()
    }
}
