package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationProperties
import manerp.response.plugin.util.FieldParser
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.commands.controller.common.ShowCommand

@Transactional
class SysrefGenderService extends BaseService
{

    def getSysrefGenderList(ManePaginationProperties properties)
    {
        def closure = {

            eq('active', true)

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        HashSet excludedFields = Holders.config.manerp.domain.excludedFields
        return paginate(SysrefGender, properties, closure, excludedFields)
    }

    def getSysrefGender(ShowCommand cmd)
    {
        SysrefGender gender = SysrefGender.createCriteria().get {

            eq('id', cmd.id)

        } as SysrefGender

        def _gender = gender
        if ( cmd.fields ) {

            FieldParser fieldParser = new FieldParser()
            List fieldList = fieldParser.parseFieldsToList(cmd.fields)
            HashSet excludedFields = Holders.config.manerp.domain.excludedFields
            _gender = filterDomainInstance(gender, fieldList, excludedFields)
        }
        return _gender
    }
}
