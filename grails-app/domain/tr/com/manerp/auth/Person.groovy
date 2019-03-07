package tr.com.manerp.auth

import org.grails.databinding.BindingFormat
import tr.com.manerp.business.sysref.SysrefGender

class Person {

    static auditable = true

    String name
    String lastName
    String email
    SysrefGender sysrefGender
    Long tcIdNumber
    Boolean isStaff
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static hasMany = []
    static belongsTo = []

    static constraints = {
        name nullable: false, blank: false, unique: false
        lastName nullable: false, blank: false, unique: false
        email email: true, blank: false, nullable: false, unique: true
        sysrefGender nullable: true, unique: false
        tcIdNumber nullable: false, unique: false
        isStaff nullable: false, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "person", schema: "auth"
        id generator: 'sequence', params: [sequence: 'auth.SEQ_PERSON']
    }

    static mappedBy = {
    }

    def beforeUpdate() {
        opDate = new Date()
    }

}
