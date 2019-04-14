package tr.com.manerp.business.sysref

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class SysrefCityService extends BaseService
{
    def getSysrefCityList(ManePaginationProperties properties, String countryId)
    {
        def closure = {

            eq('active', true)

            sysrefCountry {
                eq('id', countryId)
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        ManePaginatedResult result = paginate(SysrefCity, properties, closure)

        result.data = formatResultForList(result.data as List)
        if ( properties.fieldList ) result.data = filterList(properties.fieldList, result.data as List, SysrefCity)
        return result
    }

    def getSysrefCity(String id, String fields = null)
    {
        def city = SysrefCity.createCriteria().get {

            eq('id', id)

        } as SysrefCity

        city = formatResultForShow(city)
        if ( fields ) city = filterDataByFields(city, fields, SysrefCity)

        return city
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
