package tr.com.manerp.business.main.resource

import tr.com.manerp.auth.SysCompany
import tr.com.manerp.auth.User
import tr.com.manerp.base.domain.BaseDomain
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefDrivingType
import tr.com.manerp.business.sysref.SysrefStaffContractType
import tr.com.manerp.common.Person

class Staff implements BaseDomain, Person {

    static auditable = true

    String code
    SysCompany sysCompany
    User user
    RefStaffTitle refStaffTitle
    SysrefStaffContractType sysrefStaffContractType
    String drivingLicenseNumber
    Boolean hasFuelAdvance // yakit avansi var mi?
    SysrefDrivingType sysrefDrivingType

    static hasMany = [
        staffDocuments: StaffDocument
    ]
    static belongsTo = []

    static constraints = {

        importFrom(User)

        sysCompany nullable: false, unique: false
        code nullable: true, blank: true, unique: ['sysCompany'], maxSize: 36
        user nullable: true, unique: false
        refStaffTitle nullable: false, unique: false
        sysrefStaffContractType nullable: true, unique: false

        drivingLicenseNumber nullable: true, unique: false, maxSize: 30,
            validator: { val, obj ->
                obj?.refStaffTitle?.code == 'DRV' ? val != null : true
            }
        hasFuelAdvance nullable: true, unique: false,
            validator: { val, obj ->
                obj?.refStaffTitle?.code == 'DRV' ? val != null : true
            }
        sysrefDrivingType nullable: true, unique: false
    }

    static mapping = {
        staffDocuments cascade: 'all-delete-orphan'
    }

    String getFullName() {
        return "${this.firstName}${this.middleName != null ? ' ' + this.middleName : ''} ${this.lastName}"
    }

    // TODO: change
    def beforeValidate() {
        this.sysCompany = SysCompany.findByName('Bumerang Lojistik')
    }


}
