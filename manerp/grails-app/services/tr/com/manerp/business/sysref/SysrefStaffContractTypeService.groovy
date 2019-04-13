package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefStaffContractTypeService extends BaseService
{

    def getSysrefStaffContractTypeList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefStaffContractType, properties, closure, excludedFields)
    }

    def getSysrefStaffContractType(String id, String fields = null)
    {
        SysrefStaffContractType contractType = SysrefStaffContractType.createCriteria().get {

            eq('id', id)

        } as SysrefStaffContractType

        def _contractType = contractType
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _contractType = filterDomainInstance(contractType, fieldList, excludedFields)
        }
        return _contractType
    }
}
