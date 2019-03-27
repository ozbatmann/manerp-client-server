package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import tr.com.manerp.base.service.BaseService

@Transactional
class DriverService extends BaseService
{

    ManePaginatedResult getDriverList(ManePaginationProperties properties)
    {

        def closure = {

            eq('active', true)

            refStaffTitle {
                eq('active', true)
                eq('code', 'DRV')
            }

            if ( !properties.sortPairList ) {
                order('dateCreated', 'desc')
            }
        }

        return paginate(Staff, properties, closure)
    }

    Staff getDriver(String id)
    {
        Staff driver = Staff.createCriteria().get {

            refStaffTitle {
                eq('code', 'DRV')
            }

            eq('id', id)

        } as Staff

        return driver
    }

    def save(Staff driver)
    {

        driver.save(flush: true, failOnError: true)
    }

    def delete(Staff driver)
    {

        driver.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForList(def data)
    {
        List formattedData = data.collect {
            [
                id                     : it.id,
                code                   : it.code,
                firstName              : it.getFirstAndMiddleName(),
                lastName               : it.lastName,
                sysrefStaffContractType: [id: it.sysrefStaffContractType.id, name: it.sysrefStaffContractType.name],
                gsmNo                  : it.gsmNo,
                tcIdNumber             : it.tcIdNumber,
                drivingLicenseNumber   : it.drivingLicenseNumber,
                sysrefCountry          : it.sysrefCountry ? [id: it.sysrefCountry.id, name: it.sysrefCountry.name] : null,
                sysrefCity             : it.sysrefCity ? [id: it.sysrefCity.id, name: it.sysrefCity.name] : null,
                sysrefDistrict         : it.sysrefDistrict ? [id: it.sysrefDistrict.id, name: it.sysrefDistrict.name] : null
            ]
        }

        formattedData
    }

    List formatPaginatedResultForDropDown(def data)
    {

        List formattedData = data.collect {
            return [
                id  : it.id,
                name: it.getFullName()
            ]
        }

        return formattedData
    }
}
