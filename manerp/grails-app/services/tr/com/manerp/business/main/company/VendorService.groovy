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
                id: it.id
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id: data.id
        ]
    }
}
