package tr.com.manerp.common

import grails.gorm.transactions.Transactional
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.company.Vendor
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.main.vehicle.SemiTruck
import tr.com.manerp.business.main.vehicle.SemiTrailer
import tr.com.manerp.business.main.vehicle.Vehicle
import tr.com.manerp.business.main.voyage.Location
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.ref.RefCompanySector
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.ref.RefWorkingArea
import tr.com.manerp.business.sysref.SysrefCompanyType
import tr.com.manerp.business.sysref.SysrefDeliveryStatus
import tr.com.manerp.business.sysref.SysrefGender
import tr.com.manerp.business.sysref.SysrefRevenueType
import tr.com.manerp.business.sysref.SysrefStaffContractType
import tr.com.manerp.business.sysref.SysrefTransportationType
import tr.com.manerp.business.sysref.SysrefVehicleOwner
import tr.com.manerp.business.sysref.SysrefVehicleType
import tr.com.manerp.business.sysref.SysrefVoyageDirection

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

        SysrefStaffContractType sysrefStaffContractTypeSozlesmeli = SysrefStaffContractType.findByName('Sözleşmeli')
        if ( sysrefStaffContractTypeSozlesmeli == null ) {

            sysrefStaffContractTypeSozlesmeli = new SysrefStaffContractType()
            sysrefStaffContractTypeSozlesmeli.sysCompany = sysCompanyBumerang
            sysrefStaffContractTypeSozlesmeli.name = 'Sözleşmeli'
            sysrefStaffContractTypeSozlesmeli.active = true
            sysrefStaffContractTypeSozlesmeli.description = 'Sözleşmeli personeller'
            sysrefStaffContractTypeSozlesmeli.code = UUID.randomUUID().toString()

            sysrefStaffContractTypeSozlesmeli.save(failOnError: true)
        }

        SysrefStaffContractType sysrefStaffContractTypeKadrolu = SysrefStaffContractType.findByName('Kadrolu')
        if ( sysrefStaffContractTypeKadrolu == null ) {

            sysrefStaffContractTypeKadrolu = new SysrefStaffContractType()
            sysrefStaffContractTypeKadrolu.sysCompany = sysCompanyBumerang
            sysrefStaffContractTypeKadrolu.name = 'Kadrolu'
            sysrefStaffContractTypeKadrolu.active = true
            sysrefStaffContractTypeKadrolu.description = 'Kadrolu personeller'
            sysrefStaffContractTypeKadrolu.code = UUID.randomUUID().toString()

            sysrefStaffContractTypeKadrolu.save(failOnError: true)
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
            staffBerat.sysrefStaffContractType = sysrefStaffContractTypeKadrolu

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
            staffMurat.sysrefStaffContractType = sysrefStaffContractTypeSozlesmeli

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
            staffTuna.sysrefStaffContractType = sysrefStaffContractTypeSozlesmeli

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
            trailer1.sysCompany = sysCompanyBumerang
            trailer1.plateNumber = '35 963 852'
            trailer1.active = true
            trailer1.code = UUID.randomUUID().toString()
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

        Order orderPinar = Order.findByName('Pınar Sipariş')
        if ( orderPinar == null ) {

            orderPinar = new Order()
            orderPinar.sysCompany = sysCompanyBumerang
            orderPinar.name = 'Pınar Sipariş'
            orderPinar.code = UUID.randomUUID().toString()
            orderPinar.active = true
            orderPinar.company = customerCompanyPinar
            orderPinar.orderDate = new Date()
            orderPinar.sysrefRevenueType = sysrefRevenueTypeTicari
            orderPinar.workOrderNo = 'SIPARIS-111222'
            orderPinar.billingNo = 'FATURA-111222'

            orderPinar.save(failOnError: true)
        }

        SysrefTransportationType sysrefTransportationTypeGidis = SysrefTransportationType.findByName('Komple')
        if ( sysrefTransportationTypeGidis == null ) {

            sysrefTransportationTypeGidis = new SysrefTransportationType()
            sysrefTransportationTypeGidis.sysCompany = sysCompanyBumerang
            sysrefTransportationTypeGidis.name = 'Komple'
            sysrefTransportationTypeGidis.code = UUID.randomUUID().toString()
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
            sysrefVoyageDirectionGidis.code = UUID.randomUUID().toString()

            sysrefVoyageDirectionGidis.save(failOnError: true)
        }

        SysrefVoyageDirection sysrefVoyageDirectionGidisDonus = SysrefVoyageDirection.findByName('Gidiş-Dönüş')
        if ( sysrefVoyageDirectionGidisDonus == null ) {

            sysrefVoyageDirectionGidisDonus = new SysrefVoyageDirection()
            sysrefVoyageDirectionGidisDonus.sysCompany = sysCompanyBumerang
            sysrefVoyageDirectionGidisDonus.name = 'Gidiş-Dönüş'
            sysrefVoyageDirectionGidisDonus.active = true
            sysrefVoyageDirectionGidisDonus.description = 'Gidiş-Dönüş sevkiyat yönü'
            sysrefVoyageDirectionGidisDonus.code = UUID.randomUUID().toString()

            sysrefVoyageDirectionGidisDonus.save(failOnError: true)
        }

        SysrefDeliveryStatus sysrefDeliveryStatusRezervasyon = SysrefDeliveryStatus.findByName('Rezervasyon')
        if ( sysrefDeliveryStatusRezervasyon == null ) {

            sysrefDeliveryStatusRezervasyon = new SysrefDeliveryStatus()
            sysrefDeliveryStatusRezervasyon.sysCompany = sysCompanyBumerang
            sysrefDeliveryStatusRezervasyon.name = 'Rezervasyon'
            sysrefDeliveryStatusRezervasyon.active = true
            sysrefDeliveryStatusRezervasyon.description = 'Rezervasyon taşıma durumu'
            sysrefDeliveryStatusRezervasyon.code = UUID.randomUUID().toString()

            sysrefDeliveryStatusRezervasyon.save(failOnError: true)
        }

        SysrefDeliveryStatus sysrefDeliveryStatusYuklendi = SysrefDeliveryStatus.findByName('Yüklendi')
        if ( sysrefDeliveryStatusYuklendi == null ) {

            sysrefDeliveryStatusYuklendi = new SysrefDeliveryStatus()
            sysrefDeliveryStatusYuklendi.sysCompany = sysCompanyBumerang
            sysrefDeliveryStatusYuklendi.name = 'Yüklendi'
            sysrefDeliveryStatusYuklendi.active = true
            sysrefDeliveryStatusYuklendi.description = 'Yüklendi taşıma durumu'
            sysrefDeliveryStatusYuklendi.code = UUID.randomUUID().toString()

            sysrefDeliveryStatusYuklendi.save(failOnError: true)
        }

        SysrefDeliveryStatus sysrefDeliveryStatusBosaltti = SysrefDeliveryStatus.findByName('Boşalttı')
        if ( sysrefDeliveryStatusBosaltti == null ) {

            sysrefDeliveryStatusBosaltti = new SysrefDeliveryStatus()
            sysrefDeliveryStatusBosaltti.sysCompany = sysCompanyBumerang
            sysrefDeliveryStatusBosaltti.name = 'Boşalttı'
            sysrefDeliveryStatusBosaltti.active = true
            sysrefDeliveryStatusBosaltti.description = 'Boşalttı taşıma durumu'
            sysrefDeliveryStatusBosaltti.code = UUID.randomUUID().toString()

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
