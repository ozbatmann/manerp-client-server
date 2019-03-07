package tr.com.manerp.auth

import grails.databinding.BindingFormat


class AwcUser {

    static auditable = true
    transient springSecurityService

    Person person
    AwcCompany awcCompany
    String username
    String password
    Long version
    Boolean accountExpired
    Boolean accountLocked
    @BindingFormat('dd/MM/yyyy hh:MM')
    Date createTime
    Boolean active
    Boolean passwordExpired
    String salt
    Boolean isDeletable
    @BindingFormat('dd/MM/yyyy')
    Date opDate = new Date()

    static hasMany = []
    static belongsTo = []

    static constraints = {
        person nullable: false, unique: false
        awcCompany nullable: false, unique: false
        username nullable: false, blank: false, unique: true, maxSize: 50
        password nullable: false, blank: false, unique: false, maxSize: 255
        version nullable: true, unique: false
        accountExpired nullable: false, unique: false
        accountLocked nullable: false, unique: false
        createTime nullable: false, unique: false
        active nullable: false, unique: false
        passwordExpired nullable: false, unique: false
        salt nullable: true, blank: true, unique: false
        isDeletable nullable: false, unique: false
        opDate nullable: false, unique: false
    }

    static mapping = {
        table name: "awc_user", schema: "auth"
        id generator: 'sequence', params: [sequence: 'auth.SEQ_AWC_USER']
    }

    static mappedBy = {
    }

    def beforeInsert() {
        createTime = new Date()
    }


    def beforeUpdate() {
        opDate = new Date()
    }

}
