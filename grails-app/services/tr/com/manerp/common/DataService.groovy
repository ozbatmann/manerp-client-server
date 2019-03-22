package tr.com.manerp.common

import grails.gorm.transactions.Transactional
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefGender

@Transactional
class DataService {

    def initApplicationData() {

        SysCompany sysCompanyBumerang = SysCompany.findByName('Bumerang Lojistik')
        if ( sysCompanyBumerang == null ) {

            sysCompanyBumerang = new SysCompany()
            sysCompanyBumerang.name = 'Bumerang Lojistik'
            sysCompanyBumerang.title = 'Bumerang Lojistik LTD. ŞTİ.'
//            sysCompanyBumerang.sysrefCountry = SysrefCountry.findByName('Türkiye')
//            sysCompanyBumerang.sysrefCity = SysrefCountry.findByName('İzmir')
//            sysCompanyBumerang.sysrefDistrict = SysrefCountry.findByName('Ödemiş')
            sysCompanyBumerang.address = 'İzmir'
            sysCompanyBumerang.phone = '05343023283'

            sysCompanyBumerang.save(failOnError: true)
        }

        RefStaffTitle refStaffTitleDriver = RefStaffTitle.findByName('Şoför')
        if ( refStaffTitleDriver == null ) {

            refStaffTitleDriver = new RefStaffTitle()
            refStaffTitleDriver.name = 'Şoför'
            refStaffTitleDriver.code = 'DRV'
            refStaffTitleDriver.sysCompany = sysCompanyBumerang
            refStaffTitleDriver.description = 'Şoför unvanı'

            refStaffTitleDriver.save(failOnError: true)
        }

        RefStaffTitle refStaffTitleOp = RefStaffTitle.findByName('Operasyoncu')
        if ( refStaffTitleOp == null ) {

            refStaffTitleOp = new RefStaffTitle()
            refStaffTitleOp.name = 'Operasyoncu'
            refStaffTitleOp.code = 'OPT'
            refStaffTitleOp.sysCompany = sysCompanyBumerang
            refStaffTitleOp.description = 'Operasyoncu unvanı'

            refStaffTitleOp.save(failOnError: true)
        }

        SysrefGender sysrefGenderMale = SysrefGender.findByName('Erkek')
        if(sysrefGenderMale == null) {

            sysrefGenderMale = new SysrefGender()
            sysrefGenderMale.name = 'Erkek'
            sysrefGenderMale.code = 'MAL'
            sysrefGenderMale.save(failOnError: true)
        }

        SysrefGender sysrefGenderFemale = SysrefGender.findByName('Kadın')
        if(sysrefGenderFemale == null) {

            sysrefGenderFemale = new SysrefGender()
            sysrefGenderFemale.name = 'Kadın'
            sysrefGenderFemale.code = 'FEM'
            sysrefGenderFemale.save(failOnError: true)
        }

    }

}
