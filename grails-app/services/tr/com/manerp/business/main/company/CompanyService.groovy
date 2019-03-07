package tr.com.manerp.business.main.company

import grails.gorm.transactions.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.hibernate.sql.JoinType
import tr.com.manerp.auth.AwcCompany
import tr.com.manerp.business.main.order.Order
import tr.com.manerp.business.main.resource.Staff
import tr.com.manerp.business.ref.RefCompanySector
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefNaceCode

@Transactional
class CompanyService {

    def uniqueCodeGeneratorService
    def jsonArrayParserService

    def getAllCompanies(
            Integer orderColumn,
            String orderDirection,
            Integer length,
            Integer start,
            String code,
            String name,
            String title,
            String tradeRegistrationNo,
            String refCompanySectorName,
            Long awcCompanyId
    ) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        def companyList = []
        HashMap companyListMap = new HashMap()

        def companyTotalSize
        def companyFilteredSize
        def orderColumnString
        companyTotalSize = Company.count
        companyFilteredSize = companyTotalSize

        try {

            switch (orderColumn) {
                case 0: orderColumnString = "opDate"; break
                case 1: orderColumnString = "code"; break
                case 2: orderColumnString = "name"; break
                case 3: orderColumnString = "title"; break
                case 4: orderColumnString = "tradeRegistrationNo"; break
                case 5: orderColumnString = "_refCompanySector.id"; break
                case 6: orderColumnString = "active"; break
            }

            code = code.toLowerCase(new Locale("TR", "tr"))
            name = name.toLowerCase(new Locale("TR", "tr"))
            title = title.toLowerCase(new Locale("TR", "tr"))
            tradeRegistrationNo = tradeRegistrationNo.toLowerCase(new Locale("TR", "tr"))
            refCompanySectorName = refCompanySectorName.toLowerCase(new Locale("TR", "tr"))

            def companyCriteria = Company.createCriteria()
            companyList = companyCriteria.list(max: length, offset: start) {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("refCompanySector", "_refCompanySector", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompanyId.toLong())

                if (!code.equals("")) {
                    ilike('code', '%' + code + '%')
                }
                if (!name.equals("")) {
                    ilike('name', '%' + name + '%')
                }
                if (!title.equals("")) {
                    ilike('title', '%' + title + '%')
                }
                if (!tradeRegistrationNo.equals("")) {
                    ilike('tradeRegistrationNo', '%' + tradeRegistrationNo + '%')
                }
                if (!refCompanySectorName.equals("")) {
                    ilike('_refCompanySector.name', '%' + refCompanySectorName + '%')
                }

                order(orderColumnString, orderDirection)
            }

            def companyCountCriteria = Company.createCriteria()
            companyFilteredSize = companyCountCriteria.count {
                createAlias("awcCompany", "_awcCompany", JoinType.LEFT_OUTER_JOIN)
                createAlias("refCompanySector", "_refCompanySector", JoinType.LEFT_OUTER_JOIN)

                eq("_awcCompany.id", awcCompanyId.toLong())

                if (!code.equals("")) {
                    ilike('code', '%' + code + '%')
                }
                if (!name.equals("")) {
                    ilike('name', '%' + name + '%')
                }
                if (!title.equals("")) {
                    ilike('title', '%' + title + '%')
                }
                if (!tradeRegistrationNo.equals("")) {
                    ilike('tradeRegistrationNo', '%' + tradeRegistrationNo + '%')
                }
                if (!refCompanySectorName.equals("")) {
                    ilike('_refCompanySector.name', '%' + refCompanySectorName + '%')
                }

            }

            companyListMap.companyList = companyList.collect {
                return [
                        id                        : it.id,
                        code                      : it.code,
                        name                      : it.name,
                        title                     : it.title,
                        phone                     : it.phone,
                        email                     : it?.email,
                        tradeRegistrationNo       : it.tradeRegistrationNo,
                        customerRepresentativeName: it?.customerRepresentativeName
                ]
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        recordsTotal   : companyTotalSize,
                        recordsFiltered: companyFilteredSize,
                        companies      : companyListMap.companyList
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def getDetailOfCompany(Long companyId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        boolean status = true
        HashMap companyData = new HashMap()

        try {

            Company company = Company.get(companyId)

            companyData = [
                    id                        : company.id,
                    name                      : company.name,
                    title                     : company.title,
                    customerRepresentativeName: company?.customerRepresentative,
                    sysrefCountry             : company?.sysrefCountry?.id,
                    sysrefCity                : company?.sysrefCity?.id,
                    sysrefDistrict            : company?.sysrefDistrict?.id,
                    address                   : company.address,
                    phone                     : company.phone,
                    phone2                    : company?.phone2,
                    fax                       : company?.fax,
                    gsm                       : company?.gsm,
                    webAddress                : company?.webAddress,
                    email                     : company?.email,
                    refCompanySector          : company?.refCompanySector?.id,
                    customerRepresentative    : company.customerRepresentative == null ? null :
                            [
                                    id  : company.customerRepresentative.id,
                                    name: company.customerRepresentative.firstName + (company.customerRepresentative.middleName.equals(null) ? "" : " " + company.customerRepresentative.middleName) + " " + company.customerRepresentative.lastName
                            ],
                    numberOfStaff             : company?.numberOfStaff,
                    employerRegistrationNo    : company?.employerRegistrationNo,
                    tradeRegistrationNo       : company.tradeRegistrationNo,
                    taxOffice                 : company?.taxOffice,
                    taxNumber                 : company?.taxNumber,
                    sysrefNaceCode            : company?.sysrefNaceCode?.id

            ]


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        company: companyData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def addCompany(JSONObject companyJson) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true
        def id = null
        def code = null

        try {

            if (Company.findByTitle(companyJson.title) != null) {

                status = false
                errors = "Girdiğiniz İş Yeri Unvanı sistemde mevcut. Lütfen tekrar deneyin."

            } else if (Company.findByTradeRegistrationNo(companyJson.tradeRegistrationNo) != null) {

                status = false
                errors = "Girdiğiniz Ticari Sicil Numarası sistemde mevcut. Lütfen tekrar deneyin."

            } else if (Company.findByEmail(companyJson.email) != null) {

                status = false
                errors = "Girdiğiniz e-mail sistemde mevcut. Lütfen tekrar deneyin."

            } else {

                def uniqueEmployerRegistrationNo = companyJson.isNull("employerRegistrationNo") ? null : Company.findByEmployerRegistrationNo(companyJson.employerRegistrationNo)
                def uniqueTaxNumber = companyJson.isNull("taxNumber") ? null : Company.findByTaxNumber(companyJson.taxNumber)

                if (uniqueEmployerRegistrationNo != null) {

                    status = false
                    errors = "Girdiğiniz İş Yeri Sicil Numarası sistemde mevcut. Lütfen tekrar deneyin."


                } else if (uniqueTaxNumber != null) {

                    status = false
                    errors = "Girdiğiniz Vergi Numarası sistemde mevcut. Lütfen tekrar deneyin."

                } else {

                    Company company = new Company()
                    company.customerRepresentativeName = companyJson.isNull("customerRepresentativeName") ? null : companyJson.customerRepresentativeName
                    company.awcCompany = companyJson.isNull("awcCompanyId") ? AwcCompany.get(1) : AwcCompany.get(companyJson.awcCompanyId)
                    company.name = companyJson.name
                    company.title = companyJson.title
                    company.sysrefCountry = companyJson.isNull("sysrefCountry") ? null : SysrefCountry.get(companyJson.sysrefCountry)
                    company.sysrefCity = companyJson.isNull("sysrefCity") ? null : SysrefCity.get(companyJson.sysrefCity)
                    company.sysrefDistrict = companyJson.isNull("sysrefDistrict") ? null : SysrefDistrict.get(companyJson.sysrefDistrict)
                    company.address = companyJson.address
                    company.phone = companyJson.phone
                    company.phone2 = companyJson.isNull("phone2") ? null : companyJson.phone2
                    company.fax = companyJson.isNull("fax") ? null : companyJson.fax
                    company.gsm = companyJson.isNull("gsm") ? null : companyJson.gsm
                    company.webAddress = companyJson.isNull("webAddress") ? null : companyJson.webAddress
                    company.email = companyJson.email
                    company.refCompanySector = companyJson.isNull("refCompanySector") ? null : RefCompanySector.get(companyJson.refCompanySector)
                    company.customerRepresentative = companyJson.isNull("customerRepresentative") ? null : Staff.get(companyJson.customerRepresentative)
                    company.employerRegistrationNo = companyJson.isNull("employerRegistrationNo") ? null : companyJson.employerRegistrationNo
                    company.tradeRegistrationNo = companyJson.tradeRegistrationNo
                    company.taxOffice = companyJson.isNull("taxOffice") ? null : companyJson.taxOffice
                    company.taxNumber = companyJson.isNull("taxNumber") ? null : companyJson.taxNumber
                    company.sysrefNaceCode = companyJson.isNull("sysrefNaceCode") ? null : SysrefNaceCode.get(companyJson.sysrefNaceCode)

                    company.save(flush: true, failOnError: true)
                    company.code = uniqueCodeGeneratorService.generateUniqueCode("CMP", company.id)
                    company.save(flush: true, failOnError: true)

                    id = company.id
                    code = company.code

                }


            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        id  : id,
                        code: code
                ],
                status: status,
                errors: errors
        ]


        return jsonMap

    }

    def updateCompany(JSONObject companyJson) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            if (Company.findByTitle(companyJson.title) != null) {

                status = false
                errors = "Girdiğiniz İş Yeri Unvanı sistemde mevcut. Lütfen tekrar deneyin."

            } else if (Company.findByTradeRegistrationNo(companyJson.tradeRegistrationNo) != null) {

                status = false
                errors = "Girdiğiniz Ticari Sicil Numarası sistemde mevcut. Lütfen tekrar deneyin."

            } else if (Company.findByEmail(companyJson.email) != null) {

                status = false
                errors = "Girdiğiniz e-mail sistemde mevcut. Lütfen tekrar deneyin."

            } else {

                def uniqueEmployerRegistrationNo = companyJson.isNull("employerRegistrationNo") ? null : Company.findByEmployerRegistrationNo(companyJson.employerRegistrationNo)
                def uniqueTaxNumber = companyJson.isNull("taxNumber") ? null : Company.findByTaxNumber(companyJson.taxNumber)

                if (uniqueEmployerRegistrationNo != null) {

                    status = false
                    errors = "Girdiğiniz İş Yeri Sicil Numarası sistemde mevcut. Lütfen tekrar deneyin."


                } else if (uniqueTaxNumber != null) {

                    status = false
                    errors = "Girdiğiniz Vergi Numarası sistemde mevcut. Lütfen tekrar deneyin."

                } else {

                    Company company = Company.get(companyJson.id)
                    company.customerRepresentativeName = companyJson.isNull("customerRepresentativeName") ? null : companyJson.customerRepresentativeName
                    company.name = companyJson.name
                    company.title = companyJson.title
                    company.sysrefCountry = companyJson.isNull("sysrefCountry") ? null : SysrefCountry.get(companyJson.sysrefCountry)
                    company.sysrefCity = companyJson.isNull("sysrefCity") ? null : SysrefCity.get(companyJson.sysrefCity)
                    company.sysrefDistrict = companyJson.isNull("sysrefDistrict") ? null : SysrefDistrict.get(companyJson.sysrefDistrict)
                    company.address = companyJson.address
                    company.phone = companyJson.phone
                    company.phone2 = companyJson.isNull("phone2") ? null : companyJson.phone2
                    company.fax = companyJson.isNull("fax") ? null : companyJson.fax
                    company.gsm = companyJson.isNull("gsm") ? null : companyJson.gsm
                    company.webAddress = companyJson.isNull("webAddress") ? null : companyJson.webAddress
                    company.email = companyJson.email
                    company.refCompanySector = companyJson.isNull("refCompanySector") ? null : RefCompanySector.get(companyJson.refCompanySector)
                    company.customerRepresentative = companyJson.isNull("customerRepresentative") ? null : Staff.get(companyJson.customerRepresentative)
                    company.employerRegistrationNo = companyJson.isNull("employerRegistrationNo") ? null : companyJson.employerRegistrationNo
                    company.tradeRegistrationNo = companyJson.tradeRegistrationNo
                    company.taxOffice = companyJson.isNull("taxOffice") ? null : companyJson.taxOffice
                    company.taxNumber = companyJson.isNull("taxNumber") ? null : companyJson.taxNumber
                    company.sysrefNaceCode = companyJson.isNull("sysrefNaceCode") ? null : SysrefNaceCode.get(companyJson.sysrefNaceCode)

                    company.save(flush: true, failOnError: true)

                }

            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def deleteCompanies(JSONArray ids) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            long[] _ids = jsonArrayParserService.jsonArray2LongArray(ids)

            def companyList = Company.createCriteria().list {
                inList("id", _ids)
            }

            companyList.collect {
                it.delete(flush: true, failOnError: true)
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def deleteCompany(Long companyId) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true

        try {

            Company company = Company.get(companyId)

            if (Order.findAllByCompany(company).size() > 0) {

                status = false
                errors = "Silmek istediğiniz İş Yerinin sipariş kaydı bulunmaktadır."

            } else {

                company.delete(flush: true, failOnError: true)

            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def getDropDownSources() {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true
        def sysrefCountries = []
        def refCompanySectors = []
        def sysrefNaceCodes = []

        try {

            // TODO: Change cities and districts according to sysrefCountry
            jsonMap.sysrefCountries = SysrefCountry.findAllByActive(true).collect {
                return [id: it.id, name: it.name, code: it.code]
            }
            jsonMap.refCompanySectors = RefCompanySector.findAllByActive(true).collect {
                return [id: it.id, name: it.name, code: it.code]
            }
            /* jsonMap.sysrefNaceCodes = SysrefNaceCode.findAllByActive(true).collect {
                 return [id: it.id, name: it.name, code: it.code]
             }*/


        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        sysrefCountries  : sysrefCountries,
                        refCompanySectors: refCompanySectors
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

    def getCustomerRepresentativesQuery(String queryString) {

        HashMap jsonMap = new HashMap()
        def errors = null
        def status = true
        def customerRepresentativesData = []

        try {

            queryString = queryString.toLowerCase(new Locale("TR", "tr"))

            customerRepresentativesData = Company.createCriteria().list {
                or {
                    ilike("firstName", "%" + queryString + "%")
                    ilike("middleName", "%" + queryString + "%")
                    ilike("lastName", "%" + queryString + "%")
                }
            }.collect {
                return [
                        id  : it.id,
                        name: it.firstName + (it.middleName == null ? "" : " " + it.middleName) + " " + it.lastName
                ]
            }

        } catch (Exception ex) {

            status = false
            errors = ex.getMessage()
            ex.printStackTrace()

        }

        jsonMap.operationResult = [
                data  : [
                        customerRepresentatives: customerRepresentativesData
                ],
                status: status,
                errors: errors
        ]

        return jsonMap

    }

}
