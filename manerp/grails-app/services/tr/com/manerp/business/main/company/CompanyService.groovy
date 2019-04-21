package tr.com.manerp.business.main.company

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.resource.Staff

@Transactional
class CompanyService extends BaseService
{
    ManePaginatedResult getCompanyList(ManePaginationProperties properties, String sysrefCompanyTypeCode)
    {

        def closure = {

            eq('active', true)

            sysrefCompanyType {
                eq('code', sysrefCompanyTypeCode)
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }

        }

        ManePaginatedResult result = paginate(Company, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, Company)

        return result
    }

    def getCompany(String id, String fields)
    {
        def company = Company.createCriteria().get {
            eq('id', id)
        } as Company

        company = formatResultForShow(company)
        if ( fields ) company = filterDataByFields(company, fields, Company)

        return company
    }

    def save(Company company)
    {
        company.save(flush: true, failOnError: true)
    }

    def delete(Company company)
    {

        company.delete(flush: true, failOnError: true)
    }

    List formatResultForList(def data)
    {
        List formattedData = data.collect {
            return [
                id       : it.id,
                code     : it?.code,
                title    : it?.title,
                phone    : it?.phone,
                email    : it?.email,
                taxNumber: it?.taxNumber,
                taxOffice: it?.taxOffice
            ]
        }

        return formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id                    : data.id,
            code                  : data?.code,
            name                  : data?.name,
            title                 : data?.title,
            phone                 : data?.phone,
            email                 : data?.email,
            customerRepresentative: data?.customerRepresentative,
            employerRegistrationNo: data?.employerRegistrationNo,
            tradeRegistrationNo   : data?.tradeRegistrationNo,
            taxOffice             : data?.taxOffice,
            taxNumber             : data?.taxNumber,
            sysrefNaceCode        : data.sysrefNaceCode ? [id: data.sysrefNaceCode.id, name: data.sysrefNaceCode.name] : null,
            sysrefCountry         : data.sysrefCountry ? [id: data.sysrefCountry.id, name: data.sysrefCountry.name] : null,
            sysrefCity            : data.sysrefCity ? [id: data.sysrefCity.id, name: data.sysrefCity.name] : null,
            sysrefDistrict        : data.sysrefDistrict ? [id: data.sysrefDistrict.id, name: data.sysrefDistrict.name] : null,
            address               : data?.address
        ]
    }

}
