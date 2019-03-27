package tr.com.manerp.common

import at.favre.lib.crypto.bcrypt.BCrypt
import grails.gorm.transactions.Transactional
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.auth.User
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.main.vehicle.SemiTrailer
import tr.com.manerp.business.main.vehicle.Vehicle
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.ref.RefCompanySector
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.ref.RefWorkingArea
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCompanyType
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDeliveryStatus
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefGender
import tr.com.manerp.business.sysref.SysrefOrderState
import tr.com.manerp.business.sysref.SysrefRevenueType
import tr.com.manerp.business.sysref.SysrefStaffContractType
import tr.com.manerp.business.sysref.SysrefTransportationType
import tr.com.manerp.business.sysref.SysrefVehicleOwner
import tr.com.manerp.business.sysref.SysrefVehicleType
import tr.com.manerp.business.sysref.SysrefVoyageDirection
import tr.com.manerp.helper.SaltGenerator
import java.nio.charset.StandardCharsets

@Transactional
class DataService
{

    def initApplicationData()
    {
        List<User> userList = User.list()

        if ( userList.size() == 0 ) {

            User userBerat = new User()
            userBerat.username = 'instantcrush'
            String beratPassword = '1234'
            byte[] beratSalt = SaltGenerator.generateSalt()
            byte[] beratPasswordHash = BCrypt.withDefaults().hash(6, beratSalt, beratPassword.getBytes(StandardCharsets.UTF_8))
            userBerat.salt = beratSalt
            userBerat.passwordHash = beratPasswordHash
            userBerat.passwordExpired = false
            userBerat.accountExpired = false
            userBerat.accountLocked = false
            userBerat.isDeletable = false
            userBerat.firstName = 'Bilal'
            userBerat.middleName = 'Berat'
            userBerat.lastName = 'Postalcıoğlu'
            userBerat.email = 'beratpostalci@gmail.com'
            userBerat.tcIdNumber = 12212222222
            userBerat.gsmNo = '05343023283'
            userBerat.active = true
            userBerat.save(flush: true, failOnError: true)

            User userAli = new User()
            userAli.username = 'ozbatman'
            String aliPassword = '12345'
            byte[] aliSalt = SaltGenerator.generateSalt()
            byte[] aliPasswordHash = BCrypt.withDefaults().hash(6, aliSalt, aliPassword.getBytes(StandardCharsets.UTF_8))
            userAli.passwordHash = aliPasswordHash
            userAli.salt = aliSalt
            userAli.passwordExpired = false
            userAli.accountExpired = false
            userAli.accountLocked = false
            userAli.isDeletable = false
            userAli.firstName = 'Ali'
            userAli.middleName = 'İmran'
            userAli.lastName = 'Özbatman'
            userAli.email = 'aliimranozbatman@gmail.com'
            userAli.tcIdNumber = 12212222223
            userAli.gsmNo = '5051403166'
            userAli.active = true
            userAli.save(flush: true, failOnError: true)


            User userTuna = new User()
            userTuna.username = 'tuna'
            String tunaPassword = '123456'
            byte[] tunaSalt = SaltGenerator.generateSalt()
            byte[] tunaPasswordHash = BCrypt.withDefaults().hash(6, tunaSalt, tunaPassword.getBytes(StandardCharsets.UTF_8))
            userTuna.passwordHash = tunaPasswordHash
            userTuna.salt = tunaSalt
            userTuna.passwordExpired = false
            userTuna.accountExpired = false
            userTuna.accountLocked = false
            userTuna.isDeletable = false
            userTuna.firstName = 'Tunahan'
            userTuna.lastName = 'Bayındır'
            userTuna.email = 'tunahanbayindir@gmail.com'
            userTuna.tcIdNumber = 12212222224
            userTuna.gsmNo = '5051403166'
            userTuna.active = true
            userTuna.save(flush: true, failOnError: true)

            User userMuratcan = new User()
            userMuratcan.username = 'muratcan'
            String muratcanPassword = '123456'
            byte[] muratcanSalt = SaltGenerator.generateSalt()
            byte[] muratcanPasswordHash = BCrypt.withDefaults().hash(6, muratcanSalt, muratcanPassword.getBytes(StandardCharsets.UTF_8))
            userMuratcan.passwordHash = muratcanPasswordHash
            userMuratcan.salt = muratcanSalt
            userMuratcan.passwordExpired = false
            userMuratcan.accountExpired = false
            userMuratcan.accountLocked = false
            userMuratcan.isDeletable = false
            userMuratcan.firstName = 'Muratcan'
            userMuratcan.lastName = 'Balık'
            userMuratcan.email = 'muratcanbalikk@gmail.com'
            userMuratcan.tcIdNumber = 12212222225
            userMuratcan.gsmNo = '5051403166'
            userMuratcan.active = true
            userMuratcan.save(flush: true, failOnError: true)
        }

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

            sysCompanyBumerang.save(flush: true, failOnError: true)
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

        SysrefCountry sysrefCountryTr = SysrefCountry.findByName('Türkiye')
        if ( sysrefCountryTr == null ) {

            sysrefCountryTr = new SysrefCountry()
            sysrefCountryTr.name = 'Türkiye'
            sysrefCountryTr.code = 'TR'
            sysrefCountryTr.active = true

            sysrefCountryTr.save(failOnError: true)
        }

        SysrefCity sysrefCountryIzmir = SysrefCity.findByName('İzmir')
        if ( sysrefCountryIzmir == null ) {

            sysrefCountryIzmir = new SysrefCity()
            sysrefCountryIzmir.sysrefCountry = sysrefCountryTr
            sysrefCountryIzmir.name = 'İzmir'
            sysrefCountryIzmir.code = 'IZM'
            sysrefCountryIzmir.active = true

            sysrefCountryIzmir.save(failOnError: true)
        }

        SysrefDistrict sysrefDistrictOdemis = SysrefDistrict.findByName('Ödemiş')
        if ( sysrefDistrictOdemis == null ) {

            sysrefDistrictOdemis = new SysrefDistrict()
            sysrefDistrictOdemis.sysrefCity = sysrefCountryIzmir
            sysrefDistrictOdemis.name = 'Ödemiş'
            sysrefDistrictOdemis.code = 'ODM'
            sysrefDistrictOdemis.active = true

            sysrefDistrictOdemis.save(failOnError: true)
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

        SysrefStaffContractType sysrefStaffContractTypeSozlesmeli = SysrefStaffContractType.findByName('Sözleşmeli')
        if ( sysrefStaffContractTypeSozlesmeli == null ) {

            sysrefStaffContractTypeSozlesmeli = new SysrefStaffContractType()
            sysrefStaffContractTypeSozlesmeli.sysCompany = sysCompanyBumerang
            sysrefStaffContractTypeSozlesmeli.name = 'Sözleşmeli'
            sysrefStaffContractTypeSozlesmeli.active = true
            sysrefStaffContractTypeSozlesmeli.description = 'Sözleşmeli personeller'
            sysrefStaffContractTypeSozlesmeli.code = 'SOZ'

            sysrefStaffContractTypeSozlesmeli.save(failOnError: true)
        }

        SysrefStaffContractType sysrefStaffContractTypeKadrolu = SysrefStaffContractType.findByName('Kadrolu')
        if ( sysrefStaffContractTypeKadrolu == null ) {

            sysrefStaffContractTypeKadrolu = new SysrefStaffContractType()
            sysrefStaffContractTypeKadrolu.sysCompany = sysCompanyBumerang
            sysrefStaffContractTypeKadrolu.name = 'Kadrolu'
            sysrefStaffContractTypeKadrolu.active = true
            sysrefStaffContractTypeKadrolu.description = 'Kadrolu personeller'
            sysrefStaffContractTypeKadrolu.code = 'KAD'

            sysrefStaffContractTypeKadrolu.save(failOnError: true)
        }

        Staff staffBerat = Staff.findByFirstName('Berat')
        if ( staffBerat == null ) {

            staffBerat = new Staff()
            staffBerat.setRandomCode()
            staffBerat.sysCompany = sysCompanyBumerang
            staffBerat.firstName = 'Berat'
            staffBerat.lastName = 'Postalcıoğlu'
            staffBerat.birthDate = new Date('05/09/1995')
            staffBerat.active = true
            staffBerat.tcIdNumber = 21401769
            staffBerat.gsmNo = '05343023283'
            staffBerat.email = 'beratpostalci@gmail.com'
            staffBerat.drivingLicenseNumber = '314159'
            staffBerat.refStaffTitle = refStaffTitleDriver
            staffBerat.sysrefStaffContractType = sysrefStaffContractTypeKadrolu
            staffBerat.save(failOnError: true)
        }

        Staff staffMurat = Staff.findByFirstName('Muratcan')
        if ( staffMurat == null ) {

            staffMurat = new Staff()
            staffMurat.setRandomCode()
            staffMurat.sysCompany = sysCompanyBumerang
            staffMurat.firstName = 'Muratcan'
            staffMurat.lastName = 'Balık'
            staffMurat.birthDate = new Date('11/06/1995')
            staffMurat.active = true
            staffMurat.tcIdNumber = 21100176
            staffMurat.gsmNo = '05306400660'
            staffMurat.email = 'muratcanbalikk@gmail.com'
            staffMurat.drivingLicenseNumber = '987654'
            staffMurat.refStaffTitle = refStaffTitleDriver
            staffMurat.sysrefStaffContractType = sysrefStaffContractTypeSozlesmeli

            staffMurat.save(failOnError: true)
        }

        Staff staffTuna = Staff.findByFirstName('Tunahan')
        if ( staffTuna == null ) {

            staffTuna = new Staff()
            staffTuna.setRandomCode()
            staffTuna.sysCompany = sysCompanyBumerang
            staffTuna.firstName = 'Tunahan'
            staffTuna.lastName = 'Bayındır'
            staffTuna.birthDate = new Date('11/06/1995')
            staffTuna.active = true
            staffTuna.tcIdNumber = 20906646
            staffTuna.gsmNo = '05415984560'
            staffTuna.email = 'tunahanbayindir@gmail.com'
            staffTuna.refStaffTitle = refStaffTitleOp
            staffTuna.sysrefStaffContractType = sysrefStaffContractTypeSozlesmeli

            staffTuna.save(failOnError: true)
        }

        Staff staffAli = Staff.findByFirstName('Ali')
        if ( staffAli == null ) {

            staffAli = new Staff()
            staffAli.setRandomCode()
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
            staffAli.sysrefStaffContractType = sysrefStaffContractTypeKadrolu

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
            customerCompanyPinar.setRandomCode()
            customerCompanyPinar.name = 'Pınar'
            customerCompanyPinar.sysCompany = sysCompanyBumerang
            customerCompanyPinar.sysrefCompanyType = sysrefCompanyTypeCst
            customerCompanyPinar.refCompanySector = refCompanySectorGida
            customerCompanyPinar.title = 'Pınar Süt Mamülleri San. A.Ş.'
            customerCompanyPinar.address = 'Kemalpaşa Caddesi No:317 35060 Pınarbaşı/İzmir P.K. 904 35214 İzmir'
            customerCompanyPinar.phone = '(0232) 436 15 15'
            customerCompanyPinar.active = true
            customerCompanyPinar.email = 'pinargida@pinar.com'
            customerCompanyPinar.tradeRegistrationNo = '111222333'
            customerCompanyPinar.taxNumber = 'VERGI-333222111'
            customerCompanyPinar.taxOffice = 'İzmir Ödemiş Vergi Dairesi'
            customerCompanyPinar.customerRepresentative = 'Samet Aybaba'

            customerCompanyPinar.save(failOnError: true)
        }

        Vendor vendorPinarEsk = Vendor.findByTitle('Pınar Eskişehir Bayi')
        if ( vendorPinarEsk == null ) {

            vendorPinarEsk = new Vendor()
            vendorPinarEsk.setRandomCode()
            vendorPinarEsk.sysCompany = sysCompanyBumerang
            vendorPinarEsk.title = 'Pınar Eskişehir Bayi'
            vendorPinarEsk.active = true
            vendorPinarEsk.address = 'Eskişehir Fabrika: Org. San. Bölgesi Mümtaz Zeytinoğlu Bulvarı P.K. 55 26001'
            vendorPinarEsk.company = customerCompanyPinar
            vendorPinarEsk.phone = '0 (222) 236 08 89'

            vendorPinarEsk.save(failOnError: true)
        }

        Vendor vendorPinarKemal = Vendor.findByTitle('Pınar Kemalpaşa Bayi')
        if ( vendorPinarKemal == null ) {

            vendorPinarKemal = new Vendor()
            vendorPinarKemal.setRandomCode()
            vendorPinarKemal.sysCompany = sysCompanyBumerang
            vendorPinarKemal.title = 'Pınar Kemalpaşa Bayi'
            vendorPinarKemal.active = true
            vendorPinarKemal.address = 'Kemalpaşa Asfaltı No: 4 35060 Pınarbaşı / İZMİR'
            vendorPinarKemal.company = customerCompanyPinar
            vendorPinarKemal.phone = '0 (232) 436 52 50'

            vendorPinarKemal.save(failOnError: true)
        }

        Company customerCompanyKerevitas = Company.findByName('Kerevitaş')
        if ( customerCompanyKerevitas == null ) {

            customerCompanyKerevitas = new Company()
            customerCompanyKerevitas.setRandomCode()
            customerCompanyKerevitas.name = 'Kerevitaş'
            customerCompanyKerevitas.sysCompany = sysCompanyBumerang
            customerCompanyKerevitas.sysrefCompanyType = sysrefCompanyTypeCst
            customerCompanyKerevitas.refCompanySector = refCompanySectorGida
            customerCompanyKerevitas.title = 'Kerevitaş Unlu Mamülleri San. A.Ş.'
            customerCompanyKerevitas.address = 'Üniversite Mah. Bağlariçi cad. No:29 Avcılar İstanbul.'
            customerCompanyKerevitas.phone = '(0850) 209 16 16 '
            customerCompanyKerevitas.active = true
            customerCompanyKerevitas.email = 'info@kerevitas.com.tr'
            customerCompanyKerevitas.tradeRegistrationNo = '333222111'
            customerCompanyKerevitas.taxNumber = 'VERGI-KRV112233'
            customerCompanyKerevitas.taxOffice = 'İstanbul Bağlariçi Vergi Dairesi'

            customerCompanyKerevitas.save(failOnError: true)
        }

        Vendor vendorKerevitasAdana = Vendor.findByTitle('Kerevitaş Adana Bölge Müdürlüğü')
        if ( vendorKerevitasAdana == null ) {

            vendorKerevitasAdana = new Vendor()
            vendorKerevitasAdana.setRandomCode()
            vendorKerevitasAdana.sysCompany = sysCompanyBumerang
            vendorKerevitasAdana.title = 'Kerevitaş Adana Bölge Müdürlüğü'
            vendorKerevitasAdana.active = true
            vendorKerevitasAdana.address = 'Adana Tarsus Karayolu 14. Km. Büyük Dikili Kavşağı No: 800/A Kıvanç Tekstil Karşısı Seyhan / ADANA'
            vendorKerevitasAdana.company = customerCompanyKerevitas
            vendorKerevitasAdana.phone = '0 (322) 428 11 56 '

            vendorKerevitasAdana.save(failOnError: true)
        }

        Vendor vendorKerevitasAnkara = Vendor.findByTitle('Kerevitaş Ankara Bölge Müdürlüğü')
        if ( vendorKerevitasAnkara == null ) {

            vendorKerevitasAnkara = new Vendor()
            vendorKerevitasAnkara.setRandomCode()
            vendorKerevitasAnkara.sysCompany = sysCompanyBumerang
            vendorKerevitasAnkara.title = 'Kerevitaş Ankara Bölge Müdürlüğü'
            vendorKerevitasAnkara.active = true
            vendorKerevitasAnkara.address = 'Ostim Organize Sanayi Bölgesi Serhat Mah. 1271.Sok. No:36-38 Ostim - ANKARA'
            vendorKerevitasAnkara.company = customerCompanyKerevitas
            vendorKerevitasAnkara.phone = '0 (312) 387 08 01'

            vendorKerevitasAnkara.save(failOnError: true)
        }

        RefWorkingArea refWorkingAreaEge = RefWorkingArea.findByName('Ege Bölgesi')
        if ( refWorkingAreaEge == null ) {

            refWorkingAreaEge = new RefWorkingArea()
            refWorkingAreaEge.sysCompany = sysCompanyBumerang
            refWorkingAreaEge.name = 'Ege Bölgesi'
            refWorkingAreaEge.active = true
            refWorkingAreaEge.code = 'EGE'
            refWorkingAreaEge.description = 'Ege Bölgesinde çalışacak olan araçlar'

            refWorkingAreaEge.save(failOnError: true)
        }

        SysrefVehicleType sysrefVehicleTypeCekici = SysrefVehicleType.findByName('Çekici')
        if ( sysrefVehicleTypeCekici == null ) {

            sysrefVehicleTypeCekici = new SysrefVehicleType()
            sysrefVehicleTypeCekici.sysCompany = sysCompanyBumerang
            sysrefVehicleTypeCekici.name = 'Çekici'
            sysrefVehicleTypeCekici.active = true
            sysrefVehicleTypeCekici.code = 'SEM'
            sysrefVehicleTypeCekici.description = 'Çekici olan araç tipleri'

            sysrefVehicleTypeCekici.save(failOnError: true)
        }

        SysrefVehicleOwner sysrefVehicleOwnerOzmal = SysrefVehicleOwner.findByName('Özmal')
        if ( sysrefVehicleOwnerOzmal == null ) {

            sysrefVehicleOwnerOzmal = new SysrefVehicleOwner()
            sysrefVehicleOwnerOzmal.sysCompany = sysCompanyBumerang
            sysrefVehicleOwnerOzmal.name = 'Özmal'
            sysrefVehicleOwnerOzmal.active = true
            sysrefVehicleOwnerOzmal.code = 'OZM'
            sysrefVehicleOwnerOzmal.description = "$sysCompanyBumerang.title sahip olduğu araçlar"

            sysrefVehicleOwnerOzmal.save(failOnError: true)
        }

        SysrefVehicleOwner sysrefVehicleOwnerKiralik = SysrefVehicleOwner.findByName('Kiralık')
        if ( sysrefVehicleOwnerKiralik == null ) {

            sysrefVehicleOwnerKiralik = new SysrefVehicleOwner()
            sysrefVehicleOwnerKiralik.sysCompany = sysCompanyBumerang
            sysrefVehicleOwnerKiralik.name = 'Kiralık'
            sysrefVehicleOwnerKiralik.active = true
            sysrefVehicleOwnerKiralik.code = 'KRL'
            sysrefVehicleOwnerKiralik.description = "$sysCompanyBumerang.title kiraladığı araçlar"

            sysrefVehicleOwnerKiralik.save(failOnError: true)
        }

        Vehicle vehicle1 = Vehicle.findByPlateNumber('35 123 321')
        if ( vehicle1 == null ) {

            vehicle1 = new Vehicle()
            vehicle1.setRandomCode()
            vehicle1.sysCompany = sysCompanyBumerang
            vehicle1.active = true
            vehicle1.plateNumber = '35 123 321'
            vehicle1.brand = 'Volvo'
            vehicle1.fleetCardNumber = '111222333'
            vehicle1.hasLogo = true
            vehicle1.km = 12300
            vehicle1.isDualRegime = true
            vehicle1.refWorkingArea = refWorkingAreaEge
            vehicle1.sysrefVehicleType = sysrefVehicleTypeCekici
            vehicle1.sysrefVehicleOwner = sysrefVehicleOwnerOzmal
            vehicle1.vehicleOwnerFullName = 'Tunahan Bayındır'
            vehicle1.insuranceStartDate = new Date('05/06/2011')
            vehicle1.insuranceEndDate = new Date('05/06/2020')
            vehicle1.kgsNo = 'KGS-111222'
            vehicle1.ogsNo = 'OGS-111222'
            vehicle1.fuelKit = 'FUELKIT'
            vehicle1.description = 'Özmal Çekici'
            vehicle1.operationInsuranceNotification = true
            vehicle1.annualInsurance = true

            vehicle1.save(failOnError: true)
        }

        SemiTrailer trailer1 = SemiTrailer.findByPlateNumber('35 963 852')
        if ( trailer1 == null ) {

            trailer1 = new SemiTrailer()
            trailer1.setRandomCode()
            trailer1.sysCompany = sysCompanyBumerang
            trailer1.plateNumber = '35 963 852'
            trailer1.active = true
            trailer1.name = 'Çekici römorku'
            trailer1.brand = 'Volvo'

            trailer1.save(failOnError: true)
        }

        SysrefRevenueType sysrefRevenueTypeTicari = SysrefRevenueType.findByName('Ticari')
        if ( sysrefRevenueTypeTicari == null ) {

            sysrefRevenueTypeTicari = new SysrefRevenueType()
            sysrefRevenueTypeTicari.sysCompany = sysCompanyBumerang
            sysrefRevenueTypeTicari.name = 'Ticari'
            sysrefRevenueTypeTicari.active = true
            sysrefRevenueTypeTicari.description = 'Ticari Gelir Tipi'
            sysrefRevenueTypeTicari.code = 'TCR'

            sysrefRevenueTypeTicari.save(failOnError: true)
        }

        SysrefOrderState sysrefOrderStateComp = SysrefOrderState.findByCode('COMP')
        if ( sysrefOrderStateComp == null ) {

            sysrefOrderStateComp = new SysrefOrderState()
            sysrefOrderStateComp.sysCompany = sysCompanyBumerang
            sysrefOrderStateComp.name = 'Tamamlanmış'
            sysrefOrderStateComp.code = 'COMP'
            sysrefOrderStateComp.active = true
            sysrefOrderStateComp.description = 'Tamamlanmış siparişler'

            sysrefOrderStateComp.save(failOnError: true)
        }

        SysrefOrderState sysrefOrderStateWait = SysrefOrderState.findByCode('WAIT')
        if ( sysrefOrderStateWait == null ) {

            sysrefOrderStateWait = new SysrefOrderState()
            sysrefOrderStateWait.sysCompany = sysCompanyBumerang
            sysrefOrderStateWait.name = 'Bekleyen'
            sysrefOrderStateWait.code = 'WAIT'
            sysrefOrderStateWait.active = true
            sysrefOrderStateWait.description = 'Bekleyen siparişler'

            sysrefOrderStateWait.save(failOnError: true)
        }

        Order orderPinar = Order.findByName('Pınar Sipariş')
        if ( orderPinar == null ) {

            orderPinar = new Order()
            orderPinar.setRandomCode()
            orderPinar.sysCompany = sysCompanyBumerang
            orderPinar.name = 'Pınar Sipariş'
            orderPinar.active = true
            orderPinar.company = customerCompanyPinar
            orderPinar.orderDate = new Date()
            orderPinar.sysrefRevenueType = sysrefRevenueTypeTicari
            orderPinar.workOrderNo = 'SIPARIS-111222'
            orderPinar.billingNo = 'FATURA-111222'
            orderPinar.sysrefOrderState = sysrefOrderStateComp

            orderPinar.save(failOnError: true)
        }

        SysrefTransportationType sysrefTransportationTypeGidis = SysrefTransportationType.findByName('Komple')
        if ( sysrefTransportationTypeGidis == null ) {

            sysrefTransportationTypeGidis = new SysrefTransportationType()
            sysrefTransportationTypeGidis.sysCompany = sysCompanyBumerang
            sysrefTransportationTypeGidis.name = 'Komple'
            sysrefTransportationTypeGidis.active = true
            sysrefTransportationTypeGidis.description = 'Komple taşıma'
        }

        SysrefVoyageDirection sysrefVoyageDirectionGidis = SysrefVoyageDirection.findByName('Gidiş')
        if ( sysrefVoyageDirectionGidis == null ) {

            sysrefVoyageDirectionGidis = new SysrefVoyageDirection()
            sysrefVoyageDirectionGidis.sysCompany = sysCompanyBumerang
            sysrefVoyageDirectionGidis.name = 'Gidiş'
            sysrefVoyageDirectionGidis.active = true
            sysrefVoyageDirectionGidis.description = 'Gidiş sevkiyat yönü'
            sysrefVoyageDirectionGidis.code = 'GID'

            sysrefVoyageDirectionGidis.save(failOnError: true)
        }

        SysrefVoyageDirection sysrefVoyageDirectionGidisDonus = SysrefVoyageDirection.findByName('Gidiş-Dönüş')
        if ( sysrefVoyageDirectionGidisDonus == null ) {

            sysrefVoyageDirectionGidisDonus = new SysrefVoyageDirection()
            sysrefVoyageDirectionGidisDonus.sysCompany = sysCompanyBumerang
            sysrefVoyageDirectionGidisDonus.name = 'Gidiş-Dönüş'
            sysrefVoyageDirectionGidisDonus.active = true
            sysrefVoyageDirectionGidisDonus.description = 'Gidiş-Dönüş sevkiyat yönü'
            sysrefVoyageDirectionGidisDonus.code = 'GDN'

            sysrefVoyageDirectionGidisDonus.save(failOnError: true)
        }

        SysrefDeliveryStatus sysrefDeliveryStatusRezervasyon = SysrefDeliveryStatus.findByName('Rezervasyon')
        if ( sysrefDeliveryStatusRezervasyon == null ) {

            sysrefDeliveryStatusRezervasyon = new SysrefDeliveryStatus()
            sysrefDeliveryStatusRezervasyon.sysCompany = sysCompanyBumerang
            sysrefDeliveryStatusRezervasyon.name = 'Rezervasyon'
            sysrefDeliveryStatusRezervasyon.active = true
            sysrefDeliveryStatusRezervasyon.description = 'Rezervasyon taşıma durumu'
            sysrefDeliveryStatusRezervasyon.code = 'REZ'

            sysrefDeliveryStatusRezervasyon.save(failOnError: true)
        }

        SysrefDeliveryStatus sysrefDeliveryStatusYuklendi = SysrefDeliveryStatus.findByName('Yüklendi')
        if ( sysrefDeliveryStatusYuklendi == null ) {

            sysrefDeliveryStatusYuklendi = new SysrefDeliveryStatus()
            sysrefDeliveryStatusYuklendi.sysCompany = sysCompanyBumerang
            sysrefDeliveryStatusYuklendi.name = 'Yüklendi'
            sysrefDeliveryStatusYuklendi.active = true
            sysrefDeliveryStatusYuklendi.description = 'Yüklendi taşıma durumu'
            sysrefDeliveryStatusYuklendi.code = 'YUK'

            sysrefDeliveryStatusYuklendi.save(failOnError: true)
        }

        SysrefDeliveryStatus sysrefDeliveryStatusBosaltti = SysrefDeliveryStatus.findByName('Boşalttı')
        if ( sysrefDeliveryStatusBosaltti == null ) {

            sysrefDeliveryStatusBosaltti = new SysrefDeliveryStatus()
            sysrefDeliveryStatusBosaltti.sysCompany = sysCompanyBumerang
            sysrefDeliveryStatusBosaltti.name = 'Boşalttı'
            sysrefDeliveryStatusBosaltti.active = true
            sysrefDeliveryStatusBosaltti.description = 'Boşalttı taşıma durumu'
            sysrefDeliveryStatusBosaltti.code = 'BOS'

            sysrefDeliveryStatusBosaltti.save(failOnError: true)
        }

        Location loadingLocationPinar = Location.findByName('Karşıyaka')
        if ( loadingLocationPinar == null ) {

            loadingLocationPinar = new Location()
            loadingLocationPinar.sysCompany = sysCompanyBumerang
            loadingLocationPinar.name = 'Karşıyaka'

            loadingLocationPinar.save(failOnError: true)
        }

        Location dumpingLocationPinar = Location.findByName('Eskişehir')
        if ( dumpingLocationPinar == null ) {

            dumpingLocationPinar = new Location()
            dumpingLocationPinar.sysCompany = sysCompanyBumerang
            dumpingLocationPinar.name = 'Eskişehir'

            dumpingLocationPinar.save(failOnError: true)
        }

        Voyage voyagePinar = Voyage.findByOrder(orderPinar)
        if ( voyagePinar == null ) {

            voyagePinar = new Voyage()
            voyagePinar.setRandomCode()
            voyagePinar.sysCompany = sysCompanyBumerang
            voyagePinar.active = true
            voyagePinar.order = orderPinar
            voyagePinar.driver = staffBerat
            voyagePinar.company = customerCompanyPinar
            voyagePinar.vehicle = vehicle1
            voyagePinar.trailer = trailer1
            voyagePinar.sysrefTransportationType = sysrefTransportationTypeGidis
            voyagePinar.sysrefDeliveryStatus = sysrefDeliveryStatusYuklendi
            voyagePinar.sysrefVoyageDirection = sysrefVoyageDirectionGidisDonus
            voyagePinar.loadingLocation = loadingLocationPinar
            voyagePinar.dumpingLocation = dumpingLocationPinar
            voyagePinar.transportWaybillNo = 'IRSALIYE-111222333'
            voyagePinar.deliveryNoteNo = 'TESLIMAT-NOT-111222333'
            voyagePinar.sasNumber = 'SAS-111222333'

            voyagePinar.save(failOnError: true)
        }

    }
}