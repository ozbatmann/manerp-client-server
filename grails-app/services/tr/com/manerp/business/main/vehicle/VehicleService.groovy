package tr.com.manerp.business.main.vehicle

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.hibernate.sql.JoinType
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.company.Company
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.ref.RefWorkingArea
import tr.com.manerp.business.sysref.SysrefVehicleOwner
import tr.com.manerp.business.sysref.SysrefVehicleType
import java.text.SimpleDateFormat

@Transactional
class VehicleService extends BaseService {

    ManePaginatedResult getVehicleList(ManePaginationProperties properties) {

        def closure = {
            eq('active', true)
        }

        return paginate(Company, properties, closure)
    }

    def save(Vehicle vehicle) {

        vehicle.save(failOnError: true)
    }

    def delete(Vehicle vehicle) {

        vehicle.delete(flush: true, failOnError: true)
    }

    List formatPaginatedResultForDropDown(def data) {

        List formattedData = data.collect {
            return [
                id         : it.id,
                plateNumber: it.plateNumber
            ]
        }

        return formattedData
    }
}
