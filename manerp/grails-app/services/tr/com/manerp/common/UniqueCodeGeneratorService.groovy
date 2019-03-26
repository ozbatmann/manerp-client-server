package tr.com.manerp.common

import grails.transaction.Transactional

@Transactional
class UniqueCodeGeneratorService {

    private static Integer codeLength = 11
    private static final Integer defaultCodeLength = 11

    static def generateUniqueCode(String code, id) {

        try {

            if (id.toString().length() > (codeLength - code.length())) {

                throw new Exception("ID's character length cannot be greater than " + codeLength)

            } else {

                code = code.concat("-")
                id = String.format("%0" + (codeLength - code.length()) + "d", id)
                code = code.concat(id)
            }

        } catch (Exception ex) {

            ex.printStackTrace()
            ex.getMessage()

        }

        return code

    }

    static void setCodeLength(Integer codeLength) {

        if (codeLength < defaultCodeLength) {

            this.codeLength = defaultCodeLength

        } else {

            this.codeLength = codeLength

        }

    }

    static String getCodeLength() {
        return codeLength
    }

}
