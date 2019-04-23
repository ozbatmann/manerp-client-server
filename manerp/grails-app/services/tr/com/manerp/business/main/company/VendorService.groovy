package tr.com.manerp.business.main.company

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class VendorService extends BaseService
{

    ManePaginatedResult getVendorList(ManePaginationProperties properties, String companyId)
    {

        def closure = {

            eq('active', true)

            company {
                eq('active', true)
                eq('id', companyId)
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(Vendor, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, Vendor)

        return result
    }

    def getVendor(String id, String fields = null)
    {
        def vendor = Vendor.createCriteria().get { eq('id', id) } as Vendor
        vendor = formatResultForShow(vendor)
        if ( fields ) vendor = filterDataByFields(vendor, fields, Vendor)

        return vendor
    }

    def save(Vendor vendor)
    {
        vendor.save(flush: true, failOnError: true)
    }

    def delete(Vendor vendor)
    {
        vendor.delete(flush: true, failOnError: true)
    }

    List formatResultForList(List data)
    {
        List formattedData = data.collect {
            [
                id            : it.id,
                title         : it?.title,
                sysrefCity    : it.sysrefCity ? [id: it.sysrefCity.id, name: it.sysrefCity.name] : null,
                sysrefCountry : it.sysrefCountry ? [id: it.sysrefCountry.id, name: it.sysrefCountry.name] : null,
                sysrefDistrict: it.sysrefDistrict ? [id: it.sysrefDistrict.id, name: it.sysrefDistrict.name] : null,
                address       : it?.address,
                phone         : it?.phone,
                code          : it?.code,
                location      : it.location ? [it.location.latitude, it.location.longitude] : null
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id            : data.id,
            title         : data?.title,
            sysrefCity    : data.sysrefCity ? [id: data.sysrefCity.id, name: data.sysrefCity.name] : null,
            sysrefCountry : data.sysrefCountry ? [id: data.sysrefCountry.id, name: data.sysrefCountry.name] : null,
            sysrefDistrict: data.sysrefDistrict ? [id: data.sysrefDistrict.id, name: data.sysrefDistrict.name] : null,
            address       : data?.address,
            phone         : data?.phone,
            code          : data?.code,
            location      : data.location ? [data.location.latitude, data.location.longitude] : null
        ]
    }
}
