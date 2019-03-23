package tr.com.manerp.business.main.company

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties

@Transactional
class VendorService {

    ManePaginatedResult getVendorList(ManePaginationProperties properties, String companyId) {

        def closure = {

            eq('active', true)

            company {
                eq('id', companyId)
            }

        }

        return paginate(Vendor, properties, closure)
    }

    def save(Vendor vendor) {

        vendor.save(failOnError: true)
    }

    def delete(Vendor vendor) {

        vendor.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                    id   : it.id,
                    title: it.title
            ]
        }

        return formattedData
    }

}
