package tr.com.manerp.business.main.vehicle


import tr.com.manerp.base.domain.BaseDomain

class VehicleDocument implements BaseDomain
{

    Vehicle vehicle
    byte[] document
    String documentName
    String documentMimeType

    static constraints = {
        vehicle nullable: false, unique: false
        document nullable: false, unique: false
        documentName nullable: false, blank: false, unique: false
        documentMimeType nullable: false, blank: false, unique: false
    }

    def beforeValidate()
    {
        documentMimeType = documentMimeType?.trim()
    }

}
