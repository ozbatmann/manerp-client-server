package tr.com.manerp.commands

import grails.validation.Validateable

class PaginationCommand implements Validateable {

    Short max
    Integer offset
    String sort

    PaginationCommand(def params) {
        this.max = params.max as Short
        this.offset = params.offset as Integer
        this.sort = params.sort as String
    }

    static constraints = {
        max nullable: false, blank: false, max: 1000 as short
        offset nullable: false, blank: false, min: 0
        sort nullable: true, blank: true
    }

}
