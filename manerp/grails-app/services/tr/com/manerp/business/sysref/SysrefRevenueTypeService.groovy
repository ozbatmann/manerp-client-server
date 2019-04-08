package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.commands.controller.common.ShowCommand

@Transactional
class SysrefRevenueTypeService {

    def getSysrefRevenueTypeList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefRevenueType, properties, closure, excludedFields)
    }

    def getSysrefRevenueType(ShowCommand cmd)
    {
        SysrefRevenueType revenueType = SysrefRevenueType.createCriteria().get {

            eq('id', cmd.id)

        } as SysrefRevenueType

        def _revenueType = revenueType
        if ( cmd.fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(cmd.fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _revenueType = filterDomainInstance(revenueType, fieldList, excludedFields)
        }
        return _revenueType
    }
}
