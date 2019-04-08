package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.commands.controller.common.ShowCommand

@Transactional
class SysrefCompanyTypeService extends BaseService
{

    def getSysrefCompanyTypeList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefCompanyType, properties, closure, excludedFields)
    }

    def getSysrefCompanyType(ShowCommand cmd)
    {
        SysrefCompanyType companyType = SysrefCompanyType.createCriteria().get {

            eq('id', cmd.id)

        } as SysrefCompanyType

        def _companyType = companyType
        if ( cmd.fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(cmd.fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _companyType = filterDomainInstance(companyType, fieldList, excludedFields)
        }
        return _companyType
    }
}
