package tr.com.manerp.business.ref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class RefStaffTitleService extends BaseService
{

    ManePaginatedResult getRefStaffTitleList(ManePaginationProperties properties)
    {

        def closure = {
            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(RefStaffTitle, properties, closure, excludedFields)
    }

    RefStaffTitle getRefStaffTitle(String id)
    {
        RefStaffTitle refStaffTitle = RefStaffTitle.createCriteria().get {

            eq('id', id)

        } as RefStaffTitle

        return refStaffTitle
    }

    def save(RefStaffTitle refStaffTitle)
    {

        refStaffTitle.save(flush: true, failOnError: true)
    }

    def delete(RefStaffTitle refStaffTitle)
    {

        refStaffTitle.delete(flush: true, failOnError: true)
    }

}
