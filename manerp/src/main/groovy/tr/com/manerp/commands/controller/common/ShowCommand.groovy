package tr.com.manerp.commands.controller.common

import grails.validation.Validateable

class ShowCommand implements Validateable
{
    String id
    String fields

    ShowCommand(def params)
    {
        this.id = params.id as String
        this.fields = params.fields as String ?: null
    }

    static constraints = {
        id nullable: false, blank: false
        fields nullable: true, blank: true
    }
}
