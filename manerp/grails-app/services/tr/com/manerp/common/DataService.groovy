package tr.com.manerp.common

import at.favre.lib.crypto.bcrypt.BCrypt
import grails.gorm.transactions.Transactional
import tr.com.manerp.auth.AuthenticationToken
import tr.com.manerp.auth.SaltGenerator
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.auth.User
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.company.CompanyService
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.company.VendorService
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.order.OrderService
import tr.com.manerp.business.main.order.OrderVendor
import tr.com.manerp.business.main.order.VoyageOrder
import tr.com.manerp.business.main.resource.DriverService
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.main.resource.StaffService
import tr.com.manerp.business.main.vehicle.SemiTrailer
import tr.com.manerp.business.main.vehicle.SemiTrailerService
import tr.com.manerp.business.main.vehicle.Vehicle
import tr.com.manerp.business.main.vehicle.VehicleService
import tr.com.manerp.business.main.vehicle.VehicleSpec
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.main.voyage.VoyageService
import tr.com.manerp.business.ref.RefCompanySector
import tr.com.manerp.business.ref.RefCompanySectorService
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.ref.RefWorkingArea
import tr.com.manerp.business.ref.RefWorkingAreaService
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCompanyType
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDeliveryStatus
import tr.com.manerp.business.sysref.SysrefDimensionUnit
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefDriverState
import tr.com.manerp.business.sysref.SysrefGender
import tr.com.manerp.business.sysref.SysrefOrderState
import tr.com.manerp.business.sysref.SysrefRevenueType
import tr.com.manerp.business.sysref.SysrefStaffContractType
import tr.com.manerp.business.sysref.SysrefTransportationType
import tr.com.manerp.business.sysref.SysrefVehicleOwner
import tr.com.manerp.business.sysref.SysrefVehicleState
import tr.com.manerp.business.sysref.SysrefVehicleType
import tr.com.manerp.business.sysref.SysrefVoyageDirection
import tr.com.manerp.business.sysref.SysrefWeightUnit

import java.nio.charset.StandardCharsets

@Transactional
class DataService
{
    VoyageService voyageService
    StaffService staffService
    RefCompanySectorService refCompanySectorService
    CompanyService companyService
    VendorService vendorService
    RefWorkingAreaService refWorkingAreaService
    VehicleService vehicleService
    SemiTrailerService semiTrailerService
    OrderService orderService
    DriverService driverService

    def initApplicationData()
    {
        AuthenticationToken.findAll().each {
            it.delete(flush: true, failOnError: true)
        }
        List<User> userList = User.list()

        if ( userList.size() == 0 ) {

            User userBerat = new User()
            userBerat.username = 'berat'
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
            userBerat.tcIdNumber = 25621561716
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
            userAli.tcIdNumber = 25513665796
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
            userTuna.tcIdNumber = 25463216556
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
            userMuratcan.tcIdNumber = 25365498731
            userMuratcan.gsmNo = '5051403166'
            userMuratcan.active = true
            userMuratcan.save(flush: true, failOnError: true)
        }

        SysCompany sysCompanyBumerang = SysCompany.findByName('Bumerang Lojistik')
        if ( sysCompanyBumerang == null ) {

            sysCompanyBumerang = new SysCompany()
            sysCompanyBumerang.name = 'Bumerang Lojistik'
            sysCompanyBumerang.title = 'Bumerang Lojistik LTD. ŞTİ.'
            sysCompanyBumerang.sysrefCountry = SysrefCountry.findByName('Türkiye')
            sysCompanyBumerang.sysrefCity = SysrefCity.findByName('İzmir')
            sysCompanyBumerang.sysrefDistrict = SysrefDistrict.findByName('Ödemiş')
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

            sysrefCompanyTypeCst.save(flush: true, failOnError: true)
        }

        SysrefCompanyType sysrefCompanyTypeSup = SysrefCompanyType.findByName('Tedarikçi')
        if ( sysrefCompanyTypeSup == null ) {

            sysrefCompanyTypeSup = new SysrefCompanyType()

            sysrefCompanyTypeSup.sysCompany = sysCompanyBumerang
            sysrefCompanyTypeSup.name = 'Tedarikçi'
            sysrefCompanyTypeSup.code = 'SPL'
            sysrefCompanyTypeSup.active = true
            sysrefCompanyTypeSup.description = 'Tedarikçi iş yerleri'

            sysrefCompanyTypeSup.save(flush: true, failOnError: true)
        }

        RefStaffTitle refStaffTitleDriver = RefStaffTitle.findByName('Şoför')
        if ( refStaffTitleDriver == null ) {

            refStaffTitleDriver = new RefStaffTitle()
            refStaffTitleDriver.name = 'Şoför'
            refStaffTitleDriver.code = 'DRV'
            refStaffTitleDriver.sysCompany = sysCompanyBumerang
            refStaffTitleDriver.description = 'Şoför unvanı'
            refStaffTitleDriver.active = true

            refStaffTitleDriver.save(flush: true, failOnError: true)
        }

        RefStaffTitle refStaffTitleOp = RefStaffTitle.findByName('Operasyoncu')
        if ( refStaffTitleOp == null ) {

            refStaffTitleOp = new RefStaffTitle()
            refStaffTitleOp.name = 'Operasyoncu'
            refStaffTitleOp.code = 'OPT'
            refStaffTitleOp.sysCompany = sysCompanyBumerang
            refStaffTitleOp.description = 'Operasyoncu unvanı'
            refStaffTitleOp.active = true

            refStaffTitleOp.save(flush: true, failOnError: true)
        }

        SysrefGender sysrefGenderMale = SysrefGender.findByName('Erkek')
        if ( sysrefGenderMale == null ) {

            sysrefGenderMale = new SysrefGender()
            sysrefGenderMale.name = 'Erkek'
            sysrefGenderMale.code = 'MAL'
            sysrefGenderMale.active = true
            sysrefGenderMale.save(flush: true, failOnError: true)
        }

        SysrefGender sysrefGenderFemale = SysrefGender.findByName('Kadın')
        if ( sysrefGenderFemale == null ) {

            sysrefGenderFemale = new SysrefGender()
            sysrefGenderFemale.name = 'Kadın'
            sysrefGenderFemale.code = 'FEM'
            sysrefGenderFemale.active = true
            sysrefGenderFemale.save(flush: true, failOnError: true)
        }

        SysrefStaffContractType sysrefStaffContractTypeSozlesmeli = SysrefStaffContractType.findByName('Sözleşmeli')
        if ( sysrefStaffContractTypeSozlesmeli == null ) {

            sysrefStaffContractTypeSozlesmeli = new SysrefStaffContractType()
            sysrefStaffContractTypeSozlesmeli.sysCompany = sysCompanyBumerang
            sysrefStaffContractTypeSozlesmeli.name = 'Sözleşmeli'
            sysrefStaffContractTypeSozlesmeli.active = true
            sysrefStaffContractTypeSozlesmeli.description = 'Sözleşmeli personeller'
            sysrefStaffContractTypeSozlesmeli.code = 'SOZ'

            sysrefStaffContractTypeSozlesmeli.save(flush: true, failOnError: true)
        }

        SysrefStaffContractType sysrefStaffContractTypeKadrolu = SysrefStaffContractType.findByName('Kadrolu')
        if ( sysrefStaffContractTypeKadrolu == null ) {

            sysrefStaffContractTypeKadrolu = new SysrefStaffContractType()
            sysrefStaffContractTypeKadrolu.sysCompany = sysCompanyBumerang
            sysrefStaffContractTypeKadrolu.name = 'Kadrolu'
            sysrefStaffContractTypeKadrolu.active = true
            sysrefStaffContractTypeKadrolu.description = 'Kadrolu personeller'
            sysrefStaffContractTypeKadrolu.code = 'KAD'

            sysrefStaffContractTypeKadrolu.save(flush: true, failOnError: true)
        }

        SysrefDriverState sysrefDriverStateIdle = SysrefDriverState.findByName('Boşta')
        if ( sysrefDriverStateIdle == null ) {

            sysrefDriverStateIdle = new SysrefDriverState()
            sysrefDriverStateIdle.sysCompany = sysCompanyBumerang
            sysrefDriverStateIdle.name = 'Boşta'
            sysrefDriverStateIdle.active = true
            sysrefDriverStateIdle.code = 'IDLE'
            sysrefDriverStateIdle.description = 'Boşta olan şoför durumları'

            sysrefDriverStateIdle.save(flush: true, failOnError: true)
        }

        SysrefDriverState sysrefDriverStateOnVoyage = SysrefDriverState.findByName('Sevkiyatta')
        if ( sysrefDriverStateOnVoyage == null ) {

            sysrefDriverStateOnVoyage = new SysrefDriverState()
            sysrefDriverStateOnVoyage.sysCompany = sysCompanyBumerang
            sysrefDriverStateOnVoyage.name = 'Sevkiyatta'
            sysrefDriverStateOnVoyage.active = true
            sysrefDriverStateOnVoyage.code = 'ONVOYAGE'
            sysrefDriverStateOnVoyage.description = 'Sevkiyatta olan şoför durumları'

            sysrefDriverStateOnVoyage.save(flush: true, failOnError: true)
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

            driverService.saveDriverWithState(staffBerat, SysrefDriverState.findByCode('IDLE'))
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

            driverService.saveDriverWithState(staffMurat, SysrefDriverState.findByCode('IDLE'))
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

            staffService.save(staffTuna)
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

            staffService.save(staffAli)
        }

        RefCompanySector refCompanySectorGida = RefCompanySector.findByName('Gıda')
        if ( refCompanySectorGida == null ) {

            refCompanySectorGida = new RefCompanySector()
            refCompanySectorGida.name = 'Gıda'
            refCompanySectorGida.code = 'FOD'
            refCompanySectorGida.description = 'Gıda sektöründeki firmalar'
            refCompanySectorGida.active = true
            refCompanySectorGida.sysCompany = sysCompanyBumerang

            refCompanySectorService.save(refCompanySectorGida)
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
            customerCompanyPinar.phone = '232 436 15 15'
            customerCompanyPinar.active = true
            customerCompanyPinar.email = 'pinargida@pinar.com'
            customerCompanyPinar.tradeRegistrationNo = '111222333'
            customerCompanyPinar.taxNumber = 'VERGI-333222111'
            customerCompanyPinar.taxOffice = 'İzmir Ödemiş Vergi Dairesi'
            customerCompanyPinar.customerRepresentative = 'Samet Aybaba'

            companyService.save(customerCompanyPinar)
        }

        Vendor vendorPinarEsk = Vendor.findByTitle('Pınar Eskişehir Bayi')
        if ( vendorPinarEsk == null ) {

            vendorPinarEsk = new Vendor()
            vendorPinarEsk.setRandomCode()

            Location location = new Location()
            location.active = true
            location.latitude = '39.776'
            location.longitude = '30.506'
            location.sysCompany = sysCompanyBumerang
            vendorPinarEsk.location = location

            vendorPinarEsk.sysCompany = sysCompanyBumerang
            vendorPinarEsk.title = 'Pınar Eskişehir Bayi'
            vendorPinarEsk.active = true
            vendorPinarEsk.address = 'Eskişehir Fabrika: Org. San. Bölgesi Mümtaz Zeytinoğlu Bulvarı P.K. 55 26001'
            vendorPinarEsk.company = customerCompanyPinar
            vendorPinarEsk.phone = '222 236 08 89'

            vendorService.save(vendorPinarEsk)
        }

        Vendor vendorPinarKemal = Vendor.findByTitle('Pınar Kemalpaşa Bayi')
        if ( vendorPinarKemal == null ) {

            vendorPinarKemal = new Vendor()
            vendorPinarKemal.setRandomCode()

            Location location = new Location()
            location.active = true
            location.latitude = '38.267'
            location.longitude = '28.219'
            location.sysCompany = sysCompanyBumerang
            vendorPinarKemal.location = location

            vendorPinarKemal.sysCompany = sysCompanyBumerang
            vendorPinarKemal.title = 'Pınar Kemalpaşa Bayi'
            vendorPinarKemal.active = true
            vendorPinarKemal.address = 'Kemalpaşa Asfaltı No: 4 35060 Pınarbaşı / İZMİR'
            vendorPinarKemal.company = customerCompanyPinar
            vendorPinarKemal.phone = '232 436 52 50'

            vendorService.save(vendorPinarKemal)
        }

        Vendor vendorPinarDuden = Vendor.findByTitle('Pınar Antalya Düden Bayi')
        if ( vendorPinarDuden == null ) {

            vendorPinarDuden = new Vendor()
            vendorPinarDuden.setRandomCode()

            Location location = new Location()
            location.active = true
            location.latitude = '36.855'
            location.longitude = '30.780'
            location.sysCompany = sysCompanyBumerang
            vendorPinarDuden.location = location

            vendorPinarDuden.sysCompany = sysCompanyBumerang
            vendorPinarDuden.title = 'Pınar Antalya Düden Bayi'
            vendorPinarDuden.active = true
            vendorPinarDuden.address = 'Çağlayan Mahallesi, Barınaklar Blv. No:146, 07230 Muratpaşa/Antalya'
            vendorPinarDuden.company = customerCompanyPinar
            vendorPinarDuden.phone = '232 436 99 99'

            vendorService.save(vendorPinarDuden)
        }

        Vendor vendorPinarAfyon = Vendor.findByTitle('Pınar Afyon Merkez Bayi')
        if ( vendorPinarAfyon == null ) {

            vendorPinarAfyon = new Vendor()
            vendorPinarAfyon.setRandomCode()

            Location location = new Location()
            location.active = true
            location.latitude = '38.756'
            location.longitude = '30.538'
            location.sysCompany = sysCompanyBumerang
            vendorPinarAfyon.location = location

            vendorPinarAfyon.sysCompany = sysCompanyBumerang
            vendorPinarAfyon.title = 'Pınar Afyon Merkez Bayi'
            vendorPinarAfyon.active = true
            vendorPinarAfyon.address = 'Dumlupınar Mahallesi, İzmir Karayolu 8. Km. Anemon Afyon, Anemon Afyon İçinde, Afyonkarahisar Merkez, Afyonkarahisar, 03100 Afyonkarahisar Merkez/Afyonkarahisar'
            vendorPinarAfyon.company = customerCompanyPinar
            vendorPinarAfyon.phone = '232 888 31 33'

            vendorService.save(vendorPinarAfyon)
        }

        Company customerCompanyKerevitas = Company.findByName('Kerevitaş')
        if ( customerCompanyKerevitas == null ) {

            customerCompanyKerevitas = new Company()
            customerCompanyKerevitas.setRandomCode()
            customerCompanyKerevitas.name = 'Kerevitaş'
            customerCompanyKerevitas.sysCompany = sysCompanyBumerang
            customerCompanyKerevitas.sysrefCompanyType = sysrefCompanyTypeCst
            customerCompanyKerevitas.refCompanySector = refCompanySectorGida
            customerCompanyKerevitas.customerRepresentative = 'Murat Ülker'
            customerCompanyKerevitas.title = 'Kerevitaş Unlu Mamülleri San. A.Ş.'
            customerCompanyKerevitas.address = 'Üniversite Mah. Bağlariçi cad. No:29 Avcılar İstanbul.'
            customerCompanyKerevitas.phone = '850 209 16 16 '
            customerCompanyKerevitas.active = true
            customerCompanyKerevitas.email = 'info@kerevitas.com.tr'
            customerCompanyKerevitas.tradeRegistrationNo = '333222111'
            customerCompanyKerevitas.taxNumber = 'VERGI-KRV112233'
            customerCompanyKerevitas.taxOffice = 'İstanbul Bağlariçi Vergi Dairesi'

            companyService.save(customerCompanyKerevitas)
        }

        Vendor vendorKerevitasAdana = Vendor.findByTitle('Kerevitaş Adana Bölge Müdürlüğü')
        if ( vendorKerevitasAdana == null ) {

            vendorKerevitasAdana = new Vendor()
            vendorKerevitasAdana.setRandomCode()

            Location location = new Location()
            location.active = true
            location.latitude = '37.002'
            location.longitude = '35.325'
            location.sysCompany = sysCompanyBumerang
            vendorKerevitasAdana.location = location

            vendorKerevitasAdana.sysCompany = sysCompanyBumerang
            vendorKerevitasAdana.title = 'Kerevitaş Adana Bölge Müdürlüğü'
            vendorKerevitasAdana.active = true
            vendorKerevitasAdana.address = 'Adana Tarsus Karayolu 14. Km. Büyük Dikili Kavşağı No: 800/A Kıvanç Tekstil Karşısı Seyhan / ADANA'
            vendorKerevitasAdana.company = customerCompanyKerevitas
            vendorKerevitasAdana.phone = '0 322 428 11 56 '

            vendorService.save(vendorKerevitasAdana)
        }

        Vendor vendorKerevitasAnkara = Vendor.findByTitle('Kerevitaş Ankara Bölge Müdürlüğü')
        if ( vendorKerevitasAnkara == null ) {

            vendorKerevitasAnkara = new Vendor()
            vendorKerevitasAnkara.setRandomCode()

            Location location = new Location()
            location.active = true
            location.latitude = '39.972'
            location.longitude = '32.745'
            location.sysCompany = sysCompanyBumerang
            vendorKerevitasAnkara.location = location

            vendorKerevitasAnkara.sysCompany = sysCompanyBumerang
            vendorKerevitasAnkara.title = 'Kerevitaş Ankara Bölge Müdürlüğü'
            vendorKerevitasAnkara.active = true
            vendorKerevitasAnkara.address = 'Ostim Organize Sanayi Bölgesi Serhat Mah. 1271.Sok. No:36-38 Ostim - ANKARA'
            vendorKerevitasAnkara.company = customerCompanyKerevitas
            vendorKerevitasAnkara.phone = '0 312 387 08 01'

            vendorService.save(vendorKerevitasAnkara)
        }

        RefWorkingArea refWorkingAreaEge = RefWorkingArea.findByName('Ege Bölgesi')
        if ( refWorkingAreaEge == null ) {

            refWorkingAreaEge = new RefWorkingArea()
            refWorkingAreaEge.sysCompany = sysCompanyBumerang
            refWorkingAreaEge.name = 'Ege Bölgesi'
            refWorkingAreaEge.active = true
            refWorkingAreaEge.code = 'EGE'
            refWorkingAreaEge.description = 'Ege Bölgesinde çalışacak olan araçlar'

            refWorkingAreaService.save(refWorkingAreaEge)
        }

        SysrefVehicleType sysrefVehicleTypeCekici = SysrefVehicleType.findByName('Çekici')
        if ( sysrefVehicleTypeCekici == null ) {

            sysrefVehicleTypeCekici = new SysrefVehicleType()
            sysrefVehicleTypeCekici.sysCompany = sysCompanyBumerang
            sysrefVehicleTypeCekici.name = 'Çekici'
            sysrefVehicleTypeCekici.active = true
            sysrefVehicleTypeCekici.code = 'SEM'
            sysrefVehicleTypeCekici.description = 'Çekici olan araç tipleri'

            sysrefVehicleTypeCekici.save(flush: true, failOnError: true)
        }

        SysrefVehicleState sysrefVehicleStateIdle = SysrefVehicleState.findByName('Boşta')
        if ( sysrefVehicleStateIdle == null ) {

            sysrefVehicleStateIdle = new SysrefVehicleState()
            sysrefVehicleStateIdle.sysCompany = sysCompanyBumerang
            sysrefVehicleStateIdle.name = 'Boşta'
            sysrefVehicleStateIdle.active = true
            sysrefVehicleStateIdle.code = 'IDLE'
            sysrefVehicleStateIdle.description = 'Boşta olan araç durumları'

            sysrefVehicleStateIdle.save(flush: true, failOnError: true)
        }

        SysrefVehicleState sysrefVehicleStateOnVoyage = SysrefVehicleState.findByName('Sevkiyatta')
        if ( sysrefVehicleStateOnVoyage == null ) {

            sysrefVehicleStateOnVoyage = new SysrefVehicleState()
            sysrefVehicleStateOnVoyage.sysCompany = sysCompanyBumerang
            sysrefVehicleStateOnVoyage.name = 'Sevkiyatta'
            sysrefVehicleStateOnVoyage.active = true
            sysrefVehicleStateOnVoyage.code = 'ONVOYAGE'
            sysrefVehicleStateOnVoyage.description = 'Sevkiyatta olan araç durumları'

            sysrefVehicleStateOnVoyage.save(flush: true, failOnError: true)
        }

        SysrefVehicleOwner sysrefVehicleOwnerOzmal = SysrefVehicleOwner.findByName('Özmal')
        if ( sysrefVehicleOwnerOzmal == null ) {

            sysrefVehicleOwnerOzmal = new SysrefVehicleOwner()
            sysrefVehicleOwnerOzmal.sysCompany = sysCompanyBumerang
            sysrefVehicleOwnerOzmal.name = 'Özmal'
            sysrefVehicleOwnerOzmal.active = true
            sysrefVehicleOwnerOzmal.code = 'OZM'
            sysrefVehicleOwnerOzmal.description = "$sysCompanyBumerang.title sahip olduğu araçlar"

            sysrefVehicleOwnerOzmal.save(flush: true, failOnError: true)
        }

        SysrefVehicleOwner sysrefVehicleOwnerKiralik = SysrefVehicleOwner.findByName('Kiralık')
        if ( sysrefVehicleOwnerKiralik == null ) {

            sysrefVehicleOwnerKiralik = new SysrefVehicleOwner()
            sysrefVehicleOwnerKiralik.sysCompany = sysCompanyBumerang
            sysrefVehicleOwnerKiralik.name = 'Kiralık'
            sysrefVehicleOwnerKiralik.active = true
            sysrefVehicleOwnerKiralik.code = 'KRL'
            sysrefVehicleOwnerKiralik.description = "$sysCompanyBumerang.title kiraladığı araçlar"

            sysrefVehicleOwnerKiralik.save(flush: true, failOnError: true)
        }

        SysrefDimensionUnit sysrefDimensionUnitMeter = SysrefDimensionUnit.findByName("meter")
        if ( sysrefDimensionUnitMeter == null ) {

            sysrefDimensionUnitMeter = new SysrefDimensionUnit()
            sysrefDimensionUnitMeter.name = "meter"
            sysrefDimensionUnitMeter.code = "m"
            sysrefDimensionUnitMeter.active = true

            sysrefDimensionUnitMeter.save(flush: true, failOnError: true)
        }

        SysrefDimensionUnit sysrefDimensionUnitFoot = SysrefDimensionUnit.findByName("foot")
        if ( sysrefDimensionUnitFoot == null ) {

            sysrefDimensionUnitFoot = new SysrefDimensionUnit()
            sysrefDimensionUnitFoot.name = "foot"
            sysrefDimensionUnitFoot.code = "ft"
            sysrefDimensionUnitFoot.active = true

            sysrefDimensionUnitFoot.save(flush: true, failOnError: true)
        }

        SysrefWeightUnit sysrefWeightUnitKg = SysrefWeightUnit.findByName("kilogram")
        if ( sysrefWeightUnitKg == null ) {

            sysrefWeightUnitKg = new SysrefWeightUnit()
            sysrefWeightUnitKg.name = "kilogram"
            sysrefWeightUnitKg.code = "kg"
            sysrefWeightUnitKg.active = true

            sysrefWeightUnitKg.save(flush: true, failOnError: true)
        }

        SysrefWeightUnit sysrefWeightUnitPound = SysrefWeightUnit.findByName("pound")
        if ( sysrefWeightUnitPound == null ) {

            sysrefWeightUnitPound = new SysrefWeightUnit()
            sysrefWeightUnitPound.name = "pound"
            sysrefWeightUnitPound.code = "lb"
            sysrefWeightUnitPound.active = true

            sysrefWeightUnitPound.save(flush: true, failOnError: true)
        }


        Vehicle vehicle1 = Vehicle.findByPlateNumber('35 MAS 321')
        if ( vehicle1 == null ) {

            vehicle1 = new Vehicle()
            vehicle1.setRandomCode()
            vehicle1.sysCompany = sysCompanyBumerang
            vehicle1.active = true
            vehicle1.plateNumber = '35 MAS 321'
            vehicle1.brand = 'Volvo'
            vehicle1.fleetCardNumber = '111222333'
            vehicle1.km = 12300
            vehicle1.isDualRegime = true
            vehicle1.refWorkingArea = refWorkingAreaEge
            vehicle1.sysrefVehicleState = sysrefVehicleStateIdle
            vehicle1.sysrefVehicleType = sysrefVehicleTypeCekici
            vehicle1.sysrefVehicleOwner = sysrefVehicleOwnerOzmal
            vehicle1.vehicleOwnerFullName = 'Muratcan Balık'
            vehicle1.insuranceStartDate = new Date('05/08/2009')
            vehicle1.insuranceEndDate = new Date('05/06/2025')
            vehicle1.kgsNo = 'KGS-222111'
            vehicle1.ogsNo = 'OGS-222111'
            vehicle1.fuelKit = 'FUELKIT'
            vehicle1.description = 'Özmal Mercedes Çekici'
            vehicle1.operationInsuranceNotification = true
            vehicle1.annualInsurance = true

            VehicleSpec spec = new VehicleSpec()
            spec.sysrefDimensionUnit = sysrefDimensionUnitMeter
            spec.sysrefWeightUnit = sysrefWeightUnitKg
            spec.vehicleHeight = 4
            spec.vehicleWeight = 25000
            spec.vehicleWidth = 3
            spec.vehicleLength = 8
            spec.vehicleAxles = 4
            spec.vehicleTrailers = 1

            vehicle1.vehicleSpec = spec

            vehicleService.saveVehicleWithState(vehicle1, SysrefVehicleState.findByCode('IDLE'))
        }

        Vehicle vehicle2 = Vehicle.findByPlateNumber('06 AS 8367')
        if ( vehicle2 == null ) {

            vehicle2 = new Vehicle()
            vehicle2.setRandomCode()
            vehicle2.sysCompany = sysCompanyBumerang
            vehicle2.active = true
            vehicle2.plateNumber = '06 AS 8367'
            vehicle2.brand = 'Mercedes'
            vehicle2.fleetCardNumber = '111222333'
            vehicle2.km = 90540
            vehicle2.isDualRegime = true
            vehicle2.refWorkingArea = refWorkingAreaEge
            vehicle2.sysrefVehicleType = sysrefVehicleTypeCekici
            vehicle2.sysrefVehicleOwner = sysrefVehicleOwnerOzmal
            vehicle2.vehicleOwnerFullName = 'Tunahan Bayındır'
            vehicle2.insuranceStartDate = new Date('05/06/2011')
            vehicle2.insuranceEndDate = new Date('05/06/2020')
            vehicle2.kgsNo = 'KGS-111222'
            vehicle2.ogsNo = 'OGS-111222'
            vehicle2.fuelKit = 'FUELKIT'
            vehicle2.description = 'Özmal Çekici'
            vehicle2.operationInsuranceNotification = true
            vehicle2.annualInsurance = true

            VehicleSpec spec = new VehicleSpec()
            spec.sysrefDimensionUnit = sysrefDimensionUnitMeter
            spec.sysrefWeightUnit = sysrefWeightUnitKg
            spec.vehicleHeight = 4
            spec.vehicleWeight = 30000
            spec.vehicleWidth = 3
            spec.vehicleLength = 8
            spec.vehicleAxles = 8
            spec.vehicleTrailers = 1

            vehicle2.vehicleSpec = spec

            vehicleService.saveVehicleWithState(vehicle2, SysrefVehicleState.findByCode('IDLE'))
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

            semiTrailerService.saveVehicleWithState(trailer1, SysrefVehicleState.findByCode('IDLE'))
        }

        SysrefRevenueType sysrefRevenueTypeTicari = SysrefRevenueType.findByName('Ticari')
        if ( sysrefRevenueTypeTicari == null ) {

            sysrefRevenueTypeTicari = new SysrefRevenueType()
            sysrefRevenueTypeTicari.sysCompany = sysCompanyBumerang
            sysrefRevenueTypeTicari.name = 'Ticari'
            sysrefRevenueTypeTicari.active = true
            sysrefRevenueTypeTicari.description = 'Ticari Gelir Tipi'
            sysrefRevenueTypeTicari.code = 'TCR'

            sysrefRevenueTypeTicari.save(flush: true, failOnError: true)
        }

        SysrefOrderState sysrefOrderStateComp = SysrefOrderState.findByCode('COMP')
        if ( sysrefOrderStateComp == null ) {

            sysrefOrderStateComp = new SysrefOrderState()
            sysrefOrderStateComp.sysCompany = sysCompanyBumerang
            sysrefOrderStateComp.name = 'Tamamlanmış'
            sysrefOrderStateComp.code = 'COMP'
            sysrefOrderStateComp.active = true
            sysrefOrderStateComp.description = 'Tamamlanmış siparişler'

            sysrefOrderStateComp.save(flush: true, failOnError: true)
        }

        SysrefOrderState sysrefOrderStateWait = SysrefOrderState.findByCode('WAIT')
        if ( sysrefOrderStateWait == null ) {

            sysrefOrderStateWait = new SysrefOrderState()
            sysrefOrderStateWait.sysCompany = sysCompanyBumerang
            sysrefOrderStateWait.name = 'Bekleyen'
            sysrefOrderStateWait.code = 'WAIT'
            sysrefOrderStateWait.active = true
            sysrefOrderStateWait.description = 'Bekleyen siparişler'

            sysrefOrderStateWait.save(flush: true, failOnError: true)
        }

//        Order orderPinar = Order.findByName('Pınar Sipariş')
//        if ( orderPinar == null ) {
//
//            orderPinar = new Order()
//            orderPinar.setRandomCode()
//            orderPinar.sysCompany = sysCompanyBumerang
//            orderPinar.name = 'Pınar Sipariş'
//            orderPinar.active = true
//            orderPinar.company = customerCompanyPinar
//            orderPinar.orderDate = new Date()
//            orderPinar.sysrefRevenueType = sysrefRevenueTypeTicari
//            orderPinar.workOrderNo = 'SIPARIS-111222'
//            orderPinar.billingNo = 'FATURA-111222'
//            orderPinar.sysrefOrderState = sysrefOrderStateWait
//
//            orderService.save(orderPinar)
//        }

//        OrderVendor orderVendorPinarEsk = OrderVendor.findByVendor(vendorPinarEsk)
//        if ( orderVendorPinarEsk == null ) {
//
//            orderVendorPinarEsk = new OrderVendor()
//            orderVendorPinarEsk._order = orderPinar
//            orderVendorPinarEsk.vendor = vendorPinarEsk
//            orderVendorPinarEsk.active = true
//            orderVendorPinarEsk.sysCompany = sysCompanyBumerang
//            orderVendorPinarEsk.setRandomCode()
//            orderVendorPinarEsk.save(flush: true, failOnError: true)
//        }
//
//        OrderVendor orderVendorPinarKemal = OrderVendor.findByVendor(vendorPinarKemal)
//        if ( orderVendorPinarKemal == null ) {
//
//            orderVendorPinarKemal = new OrderVendor()
//            orderVendorPinarKemal._order = orderPinar
//            orderVendorPinarKemal.vendor = vendorPinarKemal
//            orderVendorPinarKemal.active = true
//            orderVendorPinarKemal.sysCompany = sysCompanyBumerang
//            orderVendorPinarKemal.setRandomCode()
//            orderVendorPinarKemal.save(flush: true, failOnError: true)
//        }
//
//        OrderVendor orderVendorPinarDuden = OrderVendor.findByVendor(vendorPinarDuden)
//        if ( orderVendorPinarDuden == null ) {
//
//            orderVendorPinarDuden = new OrderVendor()
//            orderVendorPinarDuden._order = orderPinar
//            orderVendorPinarDuden.vendor = vendorPinarDuden
//            orderVendorPinarDuden.active = true
//            orderVendorPinarDuden.sysCompany = sysCompanyBumerang
//            orderVendorPinarDuden.setRandomCode()
//            orderVendorPinarDuden.save(flush: true, failOnError: true)
//        }
//
//        OrderVendor orderVendorPinarAfyon = OrderVendor.findByVendor(vendorPinarAfyon)
//        if ( orderVendorPinarAfyon == null ) {
//
//            orderVendorPinarAfyon = new OrderVendor()
//            orderVendorPinarAfyon._order = orderPinar
//            orderVendorPinarAfyon.vendor = vendorPinarAfyon
//            orderVendorPinarAfyon.active = true
//            orderVendorPinarAfyon.sysCompany = sysCompanyBumerang
//            orderVendorPinarAfyon.setRandomCode()
//            orderVendorPinarAfyon.save(flush: true, failOnError: true)
//        }

        SysrefTransportationType sysrefTransportationTypeGidis = SysrefTransportationType.findByName('Komple')
        if ( sysrefTransportationTypeGidis == null ) {

            sysrefTransportationTypeGidis = new SysrefTransportationType()
            sysrefTransportationTypeGidis.sysCompany = sysCompanyBumerang
            sysrefTransportationTypeGidis.name = 'Komple'
            sysrefTransportationTypeGidis.active = true
            sysrefTransportationTypeGidis.description = 'Komple taşıma'

            sysrefTransportationTypeGidis.save(flush: true, failOnError: true)
        }

        SysrefVoyageDirection sysrefVoyageDirectionGidis = SysrefVoyageDirection.findByName('Tek Yön')
        if ( sysrefVoyageDirectionGidis == null ) {

            sysrefVoyageDirectionGidis = new SysrefVoyageDirection()
            sysrefVoyageDirectionGidis.sysCompany = sysCompanyBumerang
            sysrefVoyageDirectionGidis.name = 'Tek Yön'
            sysrefVoyageDirectionGidis.active = true
            sysrefVoyageDirectionGidis.description = 'Tek Yön sevkiyat yönü'
            sysrefVoyageDirectionGidis.code = 'TEK'

            sysrefVoyageDirectionGidis.save(flush: true, failOnError: true)
        }

        SysrefVoyageDirection sysrefVoyageDirectionGidisDonus = SysrefVoyageDirection.findByName('Gidiş-Dönüş')
        if ( sysrefVoyageDirectionGidisDonus == null ) {

            sysrefVoyageDirectionGidisDonus = new SysrefVoyageDirection()
            sysrefVoyageDirectionGidisDonus.sysCompany = sysCompanyBumerang
            sysrefVoyageDirectionGidisDonus.name = 'Gidiş-Dönüş'
            sysrefVoyageDirectionGidisDonus.active = true
            sysrefVoyageDirectionGidisDonus.description = 'Gidiş-Dönüş sevkiyat yönü'
            sysrefVoyageDirectionGidisDonus.code = 'GDN'

            sysrefVoyageDirectionGidisDonus.save(flush: true, failOnError: true)
        }

        SysrefDeliveryStatus sysrefDeliveryStatusRezervasyon = SysrefDeliveryStatus.findByName('Rezervasyon')
        if ( sysrefDeliveryStatusRezervasyon == null ) {

            sysrefDeliveryStatusRezervasyon = new SysrefDeliveryStatus()
            sysrefDeliveryStatusRezervasyon.sysCompany = sysCompanyBumerang
            sysrefDeliveryStatusRezervasyon.name = 'Rezervasyon'
            sysrefDeliveryStatusRezervasyon.active = true
            sysrefDeliveryStatusRezervasyon.description = 'Rezervasyon taşıma durumu'
            sysrefDeliveryStatusRezervasyon.code = 'REZ'

            sysrefDeliveryStatusRezervasyon.save(flush: true, failOnError: true)
        }

        SysrefDeliveryStatus sysrefDeliveryStatusYuklendi = SysrefDeliveryStatus.findByName('İptal')
        if ( sysrefDeliveryStatusYuklendi == null ) {

            sysrefDeliveryStatusYuklendi = new SysrefDeliveryStatus()
            sysrefDeliveryStatusYuklendi.sysCompany = sysCompanyBumerang
            sysrefDeliveryStatusYuklendi.name = 'İptal'
            sysrefDeliveryStatusYuklendi.active = true
            sysrefDeliveryStatusYuklendi.description = 'İptal edilen sevkiyat'
            sysrefDeliveryStatusYuklendi.code = 'IPT'

            sysrefDeliveryStatusYuklendi.save(flush: true, failOnError: true)
        }

        SysrefDeliveryStatus sysrefDeliveryStatusBosaltti = SysrefDeliveryStatus.findByName('Boşalttı')
        if ( sysrefDeliveryStatusBosaltti == null ) {

            sysrefDeliveryStatusBosaltti = new SysrefDeliveryStatus()
            sysrefDeliveryStatusBosaltti.sysCompany = sysCompanyBumerang
            sysrefDeliveryStatusBosaltti.name = 'Boşalttı'
            sysrefDeliveryStatusBosaltti.active = true
            sysrefDeliveryStatusBosaltti.description = 'Boşalttı taşıma durumu'
            sysrefDeliveryStatusBosaltti.code = 'BOS'

            sysrefDeliveryStatusBosaltti.save(flush: true, failOnError: true)
        }

//        Location loadingLocationPinar = Location.findByName('Karşıyaka Yükleme Noktası')
//        if ( loadingLocationPinar == null ) {
//
//            loadingLocationPinar = new Location()
//            loadingLocationPinar.active = true
//            loadingLocationPinar.latitude = '38.492'
//            loadingLocationPinar.longitude = '27.112'
//            loadingLocationPinar.sysCompany = sysCompanyBumerang
//            loadingLocationPinar.name = 'Karşıyaka'
//
//            loadingLocationPinar.save(flush: true, failOnError: true)
//        }
//
//        Location dumpingLocationPinar = Location.findByName('Eskişehir Boşaltma Noktası')
//        if ( dumpingLocationPinar == null ) {
//
//            dumpingLocationPinar = new Location()
//            dumpingLocationPinar.active = true
//            dumpingLocationPinar.latitude = '39.766'
//            dumpingLocationPinar.longitude = '30.522'
//            dumpingLocationPinar.sysCompany = sysCompanyBumerang
//            dumpingLocationPinar.name = 'Eskişehir'
//
//            dumpingLocationPinar.save(flush: true, failOnError: true)
//        }
//
//        Voyage voyagePinar = Voyage.findBySasNumber('SAS-111222333')
//        if ( voyagePinar == null ) {
//
//
//            voyagePinar = new Voyage()
//            voyagePinar.startDate = new Date()
//            voyagePinar.setRandomCode()
//            voyagePinar.sysCompany = sysCompanyBumerang
//            voyagePinar.active = true
//            voyagePinar.driver = staffMurat
//            voyagePinar.vehicle = vehicle1
//            voyagePinar.trailer = trailer1
//            voyagePinar.sysrefTransportationType = sysrefTransportationTypeGidis
//            voyagePinar.sysrefDeliveryStatus = sysrefDeliveryStatusRezervasyon
//            voyagePinar.sysrefVoyageDirection = sysrefVoyageDirectionGidisDonus
//            voyagePinar.loadingLocation = loadingLocationPinar
//            voyagePinar.dumpingLocation = dumpingLocationPinar
//            voyagePinar.transportWaybillNo = 'IRSALIYE-111222333'
//            voyagePinar.deliveryNoteNo = 'TESLIMAT-NOT-111222333'
//            voyagePinar.sasNumber = 'SAS-111222333'
//
//            voyageService.save(voyagePinar)
//        }
//
//        VoyageOrder voyageOrder = VoyageOrder.findByVoyage(voyagePinar)
//        if ( voyageOrder == null ) {
//
//            voyageOrder = new VoyageOrder()
//            voyageOrder.sysCompany = sysCompanyBumerang
//            voyageOrder.setRandomCode()
//            voyageOrder.voyage = voyagePinar
//            voyageOrder.order = orderPinar
//            voyageOrder.active = true
//
//            voyageOrder.save(flush: true, failOnError: true)
//        }

    }
}