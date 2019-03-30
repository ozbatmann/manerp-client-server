package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class RefStaffTitleService extends BaseService {
    
    ManePaginatedResult getRefStaffTitleList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        return paginate(RefStaffTitle, properties, closure, ['sysCompany'] as HashSet)
    }
    
    RefStaffTitle getRefStaffTitle(String id)
    {
        RefStaffTitle refStaffTitle = RefStaffTitle.createCriteria().get {

            eq('id', id)

        } as RefStaffTitle

        return refStaffTitle
    }
    
    def save(RefStaffTitle refStaffTitle) {

        refStaffTitle.save(flush: true, failOnError: true)
    }

    def delete(RefStaffTitle refStaffTitle) {

        refStaffTitle.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                id  : it.id,
                name: it.name
            ]
        }

        return formattedData
    }


}
