package tr.com.manerp.business.main.resource

import grails.gorm.transactions.Transactional
import manerp.response.plugin.pagination.ManePaginatedResult
import manerp.response.plugin.pagination.ManePaginationProperties
import org.hibernate.sql.JoinType
import tr.com.manerp.auth.SysCompany
import tr.com.manerp.base.service.BaseService
import tr.com.manerp.business.main.voyage.Voyage
import tr.com.manerp.business.ref.RefStaffTitle
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefGender
import java.text.SimpleDateFormat

@Transactional
class StaffService extends BaseService {

    ManePaginatedResult getStaffList(ManePaginationProperties properties) {

        def closure = {

        }

        return paginate(Staff, properties)
    }

    def save(Staff staff) {

        staff.save(failOnError: true)
    }

    def delete(String id) {

        Staff.get(id).delete(flush: true, failOnError: true)
    }

}
