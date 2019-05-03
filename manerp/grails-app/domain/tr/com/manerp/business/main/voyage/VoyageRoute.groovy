package tr.com.manerp.business.main.voyage

import tr.com.manerp.base.domain.BaseDomain

class VoyageRoute implements BaseDomain
{
    static auditable = true

    Location location
    Voyage voyage

    static constraints = {
        location nullable: false, unique: false
        voyage nullable: false, unique: false
    }

}
