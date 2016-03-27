package com.wordcraft



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CraftSettingsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond CraftSettings.list(params), model:[craftSettingsInstanceCount: CraftSettings.count()]
    }

    def show(CraftSettings craftSettingsInstance) {
        respond craftSettingsInstance
    }

    def create() {
        respond new CraftSettings(params)
    }

    @Transactional
    def save(CraftSettings craftSettingsInstance) {
        if (craftSettingsInstance == null) {
            notFound()
            return
        }

        if (craftSettingsInstance.hasErrors()) {
            respond craftSettingsInstance.errors, view:'create'
            return
        }

        craftSettingsInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'craftSettings.label', default: 'CraftSettings'), craftSettingsInstance.id])
                redirect craftSettingsInstance
            }
            '*' { respond craftSettingsInstance, [status: CREATED] }
        }
    }

    def edit(CraftSettings craftSettingsInstance) {
        respond craftSettingsInstance
    }

    @Transactional
    def update(CraftSettings craftSettingsInstance) {
        if (craftSettingsInstance == null) {
            notFound()
            return
        }

        if (craftSettingsInstance.hasErrors()) {
            respond craftSettingsInstance.errors, view:'edit'
            return
        }

        craftSettingsInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'CraftSettings.label', default: 'CraftSettings'), craftSettingsInstance.id])
                redirect craftSettingsInstance
            }
            '*'{ respond craftSettingsInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(CraftSettings craftSettingsInstance) {

        if (craftSettingsInstance == null) {
            notFound()
            return
        }

        craftSettingsInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CraftSettings.label', default: 'CraftSettings'), craftSettingsInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'craftSettings.label', default: 'CraftSettings'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
