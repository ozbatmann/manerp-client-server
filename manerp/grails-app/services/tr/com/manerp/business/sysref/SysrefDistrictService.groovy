package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefDistrictService extends BaseService
{
    def getSysrefDistrictList(ManePaginationProperties properties, String cityId)
    {
        def closure = {

            eq('active', true)

            sysrefCity {
                eq('id', cityId)
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefDistrict, properties, closure, excludedFields)
    }

    def getSysrefDistrict(String id, String fields = null)
    {
        SysrefDistrict district = SysrefDistrict.createCriteria().get {

            eq('id', id)

        } as SysrefDistrict

        def _district = district
        if ( fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _district = filterDomainInstance(district, fieldList, excludedFields)
        }
        return _district
    }
}
