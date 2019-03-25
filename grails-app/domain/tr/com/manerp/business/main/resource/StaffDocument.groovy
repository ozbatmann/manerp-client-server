package tr.com.manerp.business.main.resource

import tr.com.manerp.base.domain.BaseDomain

class StaffDocument implements BaseDomain
{

    Staff staff
    byte[] document
    String documentName
    String documentMimeType

    static constraints = {
        staff nullable: false, unique: false
        document nullable: false, unique: false
        documentName nullable: false, blank: false, unique: false
        documentMimeType nullable: false, blank: false, unique: false
    }

    def beforeValidate()
    {
        documentMimeType = documentMimeType?.trim()
    }

}
