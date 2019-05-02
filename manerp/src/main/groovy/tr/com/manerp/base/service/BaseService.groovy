package tr.com.manerp.base.service

import grails.util.Holders
import manerp.response.plugin.pagination.ManePaginationService
import manerp.response.plugin.util.FieldParser

class BaseService extends ManePaginationService
{
    // TODO: A class can be created like ManePaginationProperties
    def filterDataByFields(def data, String fields, Class clss, Set otherProperties = null)
    {
        FieldParser fieldParser = new FieldParser()
        List fieldList = fieldParser.parseFieldsToList(fields)
        fieldList = filterFieldList(fieldList, clss, otherProperties)
        return filterItem(data, fieldList)
    }

    List filterList(List fieldList, List paginatedList, Class clss, Set otherProperties = null)
    {
        // first filter fields
        List fields = filterFieldList(fieldList, clss, otherProperties)
        return filterPaginatedList(fields, paginatedList)
    }

    private List filterFieldList(List fieldList, Class clss, Set otherProperties)
    {
        HashSet clssPropertiesSet = clss.constrainedProperties.keySet()
        if ( otherProperties ) clssPropertiesSet.addAll(otherProperties)
        clssPropertiesSet.add('id')

        HashSet excludedFieldSet = Holders.config.manerp.domain.excludedFields
        if ( excludedFieldSet ) clssPropertiesSet.removeAll(excludedFieldSet)

        fieldList.retainAll { field ->

            if ( field instanceof String ) {

                field in clssPropertiesSet
            } else if ( field instanceof HashMap ) {

                field.keySet().getAt(0) in clssPropertiesSet
            }
        }

        fieldList
    }

}
