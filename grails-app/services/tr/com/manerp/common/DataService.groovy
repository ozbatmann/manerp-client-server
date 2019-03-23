package tr.com.manerp.common

import grails.gorm.transactions.Transactional
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.ref.RefCompanySector
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefCompanyType
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
            sysCompanyBumerang.active = true
            sysCompanyBumerang.address = 'İzmir'
            sysCompanyBumerang.phone = '05343023283'

            sysCompanyBumerang.save(failOnError: true)
        }

        SysrefCompanyType sysrefCompanyTypeCst = SysrefCompanyType.findByName('Müşteri')
        if ( sysrefCompanyTypeCst == null ) {

            sysrefCompanyTypeCst = new SysrefCompanyType()

            sysrefCompanyTypeCst.sysCompany = sysCompanyBumerang
            sysrefCompanyTypeCst.name = 'Müşteri'
            sysrefCompanyTypeCst.code = 'CST'
            sysrefCompanyTypeCst.active = true
            sysrefCompanyTypeCst.description = 'Müşteri iş yerleri'

            sysrefCompanyTypeCst.save(failOnError: true)
        }

        SysrefCompanyType sysrefCompanyTypeSup = SysrefCompanyType.findByName('Tedarikçi')
        if ( sysrefCompanyTypeSup == null ) {

            sysrefCompanyTypeSup = new SysrefCompanyType()

            sysrefCompanyTypeSup.sysCompany = sysCompanyBumerang
            sysrefCompanyTypeSup.name = 'Tedarikçi'
            sysrefCompanyTypeSup.code = 'SPL'
            sysrefCompanyTypeSup.active = true
            sysrefCompanyTypeSup.description = 'Tedarikçi iş yerleri'

            sysrefCompanyTypeSup.save(failOnError: true)
        }

        RefStaffTitle refStaffTitleDriver = RefStaffTitle.findByName('Şoför')
        if ( refStaffTitleDriver == null ) {

            refStaffTitleDriver = new RefStaffTitle()
            refStaffTitleDriver.name = 'Şoför'
            refStaffTitleDriver.code = 'DRV'
            refStaffTitleDriver.sysCompany = sysCompanyBumerang
            refStaffTitleDriver.description = 'Şoför unvanı'
            refStaffTitleDriver.active = true

            refStaffTitleDriver.save(failOnError: true)
        }

        RefStaffTitle refStaffTitleOp = RefStaffTitle.findByName('Operasyoncu')
        if ( refStaffTitleOp == null ) {

            refStaffTitleOp = new RefStaffTitle()
            refStaffTitleOp.name = 'Operasyoncu'
            refStaffTitleOp.code = 'OPT'
            refStaffTitleOp.sysCompany = sysCompanyBumerang
            refStaffTitleOp.description = 'Operasyoncu unvanı'
            refStaffTitleOp.active = true

            refStaffTitleOp.save(failOnError: true)
        }

        SysrefGender sysrefGenderMale = SysrefGender.findByName('Erkek')
        if ( sysrefGenderMale == null ) {

            sysrefGenderMale = new SysrefGender()
            sysrefGenderMale.name = 'Erkek'
            sysrefGenderMale.code = 'MAL'
            sysrefGenderMale.active = true
            sysrefGenderMale.save(failOnError: true)
        }

        SysrefGender sysrefGenderFemale = SysrefGender.findByName('Kadın')
        if ( sysrefGenderFemale == null ) {

            sysrefGenderFemale = new SysrefGender()
            sysrefGenderFemale.name = 'Kadın'
            sysrefGenderFemale.code = 'FEM'
            sysrefGenderFemale.active = true
            sysrefGenderFemale.save(failOnError: true)
        }

        Staff staffBerat = Staff.findByFirstName('Berat')
        if ( staffBerat == null ) {

            staffBerat = new Staff()
            staffBerat.sysCompany = sysCompanyBumerang
            staffBerat.firstName = 'Berat'
            staffBerat.lastName = 'Postalcıoğlu'
            staffBerat.birthDate = new Date('05/09/1995')
            staffBerat.active = true
            staffBerat.tcIdNumber = 21401769
            staffBerat.gsmNo = '05343023283'
            staffBerat.email = 'beratpostalci@gmail.com'
            staffBerat.drivingLicenseNumber = '314159'
            staffBerat.hasFuelAdvance = true
            staffBerat.refStaffTitle = refStaffTitleDriver

            staffBerat.save(failOnError: true)
        }

        Staff staffMurat = Staff.findByFirstName('Muratcan')
        if ( staffMurat == null ) {

            staffMurat = new Staff()
            staffMurat.sysCompany = sysCompanyBumerang
            staffMurat.firstName = 'Muratcan'
            staffMurat.lastName = 'Balık'
            staffMurat.birthDate = new Date('11/06/1995')
            staffMurat.active = true
            staffMurat.tcIdNumber = 21100176
            staffMurat.gsmNo = '05306400660'
            staffMurat.email = 'muratcanbalikk@gmail.com'
            staffMurat.drivingLicenseNumber = '987654'
            staffMurat.hasFuelAdvance = true
            staffMurat.refStaffTitle = refStaffTitleDriver

            staffMurat.save(failOnError: true)
        }

        Staff staffTuna = Staff.findByFirstName('Tunahan')
        if ( staffTuna == null ) {

            staffTuna = new Staff()
            staffTuna.sysCompany = sysCompanyBumerang
            staffTuna.firstName = 'Tunahan'
            staffTuna.lastName = 'Bayındır'
            staffTuna.birthDate = new Date('11/06/1995')
            staffTuna.active = true
            staffTuna.tcIdNumber = 20906646
            staffTuna.gsmNo = '05415984560'
            staffTuna.email = 'tunahanbayindir@gmail.com'
            staffTuna.refStaffTitle = refStaffTitleOp

            staffTuna.save(failOnError: true)
        }

        Staff staffAli = Staff.findByFirstName('Ali')
        if ( staffAli == null ) {

            staffAli = new Staff()
            staffAli.sysCompany = sysCompanyBumerang
            staffAli.firstName = 'Ali'
            staffAli.middleName = 'İmran'
            staffAli.lastName = 'Özbatman'
            staffAli.birthDate = new Date('08/03/1995')
            staffAli.active = true
            staffAli.tcIdNumber = 21206646
            staffAli.gsmNo = '05051403166'
            staffAli.email = 'aliimranozbatman@gmail.com'
            staffAli.refStaffTitle = refStaffTitleOp

            staffAli.save(failOnError: true)
        }

        RefCompanySector refCompanySectorGida = RefCompanySector.findByName('Gıda')
        if ( refCompanySectorGida == null ) {

            refCompanySectorGida = new RefCompanySector()
            refCompanySectorGida.name = 'Gıda'
            refCompanySectorGida.code = 'FOD'
            refCompanySectorGida.description = 'Gıda sektöründeki firmalar'
            refCompanySectorGida.active = true
            refCompanySectorGida.sysCompany = sysCompanyBumerang

            refCompanySectorGida.save(failOnError: true)
        }

        Company customerCompanyPinar = Company.findByName('Pınar')
        if ( customerCompanyPinar == null ) {

            customerCompanyPinar = new Company()
            customerCompanyPinar.name = 'Pınar'
            customerCompanyPinar.sysCompany = sysCompanyBumerang
            customerCompanyPinar.sysrefCompanyType = sysrefCompanyTypeCst
            customerCompanyPinar.title = 'Pınar Süt Mamülleri San. A.Ş.'
            customerCompanyPinar.address = 'Kemalpaşa Caddesi No:317 35060 Pınarbaşı/İzmir P.K. 904 35214 İzmir'
            customerCompanyPinar.phone = '(0232) 436 15 15'
            customerCompanyPinar.active = true
            customerCompanyPinar.email = 'pinargida@pinar.com'
            customerCompanyPinar.tradeRegistrationNo = '111222333'

            customerCompanyPinar.save(failOnError: true)
        }

        Vendor vendorPinarEsk = Vendor.findByTitle('Pınar Eskişehir Bayi')
        if ( vendorPinarEsk == null ) {

            vendorPinarEsk = new Vendor()
            vendorPinarEsk.sysCompany = sysCompanyBumerang
            vendorPinarEsk.title = 'Pınar Eskişehir Bayi'
            vendorPinarEsk.active = true
            vendorPinarEsk.address = 'Eskişehir Fabrika: Org. San. Bölgesi Mümtaz Zeytinoğlu Bulvarı P.K. 55 26001'
            vendorPinarEsk.company = customerCompanyPinar
            vendorPinarEsk.phone = '(0222) 236 08 89'

            vendorPinarEsk.save(failOnError: true)
        }

        Vendor vendorPinarKemal = Vendor.findByTitle('Pınar Kemalpaşa Bayi')
        if ( vendorPinarKemal == null ) {

            vendorPinarKemal = new Vendor()
            vendorPinarKemal.sysCompany = sysCompanyBumerang
            vendorPinarKemal.title = 'Pınar Kemalpaşa Bayi'
            vendorPinarKemal.active = true
            vendorPinarKemal.address = 'Kemalpaşa Asfaltı No: 4 35060 Pınarbaşı / İZMİR'
            vendorPinarKemal.company = customerCompanyPinar
            vendorPinarKemal.phone = '(0232) 436 52 50'

            vendorPinarKemal.save(failOnError: true)
        }


    }

}
