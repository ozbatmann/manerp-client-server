package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
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

        ManePaginatedResult result = paginate(SysrefDistrict, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, SysrefDistrict)
        return result
    }

    def getSysrefDistrict(String id, String fields = null)
    {
        def district = SysrefDistrict.createCriteria().get {

            eq('id', id)

        } as SysrefDistrict

        district = formatResultForShow(district)
        if ( fields ) district = filterDataByFields(district, fields, SysrefDistrict)

        return district
    }

    List formatResultForList(List data)
    {
        List formattedData = data.collect {
            [
                id  : it.id,
                name: it.name,
                code: it?.code
            ]
        }

        formattedData
    }

    def formatResultForShow(def data)
    {
        return [
            id  : data.id,
            name: data.name,
            code: data?.code
        ]
    }

}
