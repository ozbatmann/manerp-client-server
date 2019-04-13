package tr.com.manerp.business.main.company

import grails.gorm.transactions.Transactional
import grails.util.Holders
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

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(Vendor, properties, closure, excludedFields)
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

}
