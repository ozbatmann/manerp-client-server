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

        return paginate(Vendor, properties, closure, ['sysCompany'] as HashSet)
    }

    Vendor getVendor(String id)
    {
        Vendor vendor = Vendor.createCriteria().get {

            eq('id', id)

        } as Vendor

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

    List formatPaginatedResultForDropDown(def data)
    {

        List formattedData = data.collect {
            return [
                id   : it.id,
                title: it.title
            ]
        }

        return formattedData
    }

}
