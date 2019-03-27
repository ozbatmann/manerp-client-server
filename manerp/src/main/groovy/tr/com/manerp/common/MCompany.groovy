package tr.com.manerp.common

import tr.com.manerp.business.ref.RefCompanySector
import tr.com.manerp.business.sysref.SysrefCity
import tr.com.manerp.business.sysref.SysrefCountry
import tr.com.manerp.business.sysref.SysrefDistrict
import tr.com.manerp.business.sysref.SysrefNaceCode

trait MCompany
{
    String name
    String title
    SysrefCountry sysrefCountry
    SysrefCity sysrefCity
    SysrefDistrict sysrefDistrict
    String address
    String phone
    String phone2
    String fax
    String gsm
    String webAddress
    String email
    RefCompanySector refCompanySector
//    Staff customerRepresentative
    String customerRepresentative
    Integer numberOfStaff
    String employerRegistrationNo
    String tradeRegistrationNo
    String taxOffice
    String taxNumber
    SysrefNaceCode sysrefNaceCode
}