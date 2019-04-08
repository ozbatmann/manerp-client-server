package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.commands.controller.common.ShowCommand

@Transactional
class SysrefDistrictService extends BaseService
{
    def getSysrefDistrictList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefDistrict, properties, closure, excludedFields)
    }

    def getSysrefDistrict(ShowCommand cmd)
    {
        SysrefDistrict district = SysrefDistrict.createCriteria().get {

            eq('id', cmd.id)

        } as SysrefDistrict

        def _district = district
        if ( cmd.fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(cmd.fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _district = filterDomainInstance(district, fieldList, excludedFields)
        }
        return _district
    }
}
