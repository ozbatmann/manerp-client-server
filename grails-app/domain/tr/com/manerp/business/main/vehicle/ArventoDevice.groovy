package tr.com.manerp.business.main.vehicle

import org.grails.databinding.BindingFormat
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.main.company.Company

class ArventoDevice {

    static auditable = true

    String code
    AwcCompany awcCompany
    Long deviceId
    String name
    Vehicle vehicle
    Dorset dorset
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static constraints = {
        code nullable: true, blank: true, unique: true, maxSize: 11
        awcCompany nullable: false, unique: false
        deviceId nullable: false, unique: false
        name nullable: true, blank: true, unique: false, maxSize: 50
        vehicle nullable: true, unique: false
        dorset nullable: true, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "arvento_device", schema: "business"
        id generator: 'sequence', params: [sequence: 'business.SEQ_ARVENTO_DEVICE']
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
