package com.wordcraft

import grails.plugin.springsecurity.annotation.Secured
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CraftNotificationController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	@Secured(['ROLE_ADMIN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond CraftNotification.list(params), model:[craftNotificationInstanceCount: CraftNotification.count()]
    }

	@Secured(['ROLE_ADMIN'])
    def show(CraftNotification craftNotificationInstance) {
        respond craftNotificationInstance
    }

	@Secured(['ROLE_ADMIN'])
    def create() {
        respond new CraftNotification(params)
    }

	@Secured(['ROLE_ADMIN'])
    @Transactional
    def save(CraftNotification craftNotificationInstance) {
        if (craftNotificationInstance == null) {
            notFound()
            return
        }

        if (craftNotificationInstance.hasErrors()) {
            respond craftNotificationInstance.errors, view:'create'
            return
        }

        craftNotificationInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: ['CraftNotification', craftNotificationInstance.id])
                redirect craftNotificationInstance
            }
            '*' { respond craftNotificationInstance, [status: CREATED] }
        }
    }

	@Secured(['ROLE_ADMIN'])
    def edit(CraftNotification craftNotificationInstance) {
        respond craftNotificationInstance
    }

	@Secured(['ROLE_ADMIN'])
    @Transactional
    def update(CraftNotification craftNotificationInstance) {
        if (craftNotificationInstance == null) {
            notFound()
            return
        }

        if (craftNotificationInstance.hasErrors()) {
            respond craftNotificationInstance.errors, view:'edit'
            return
        }

        craftNotificationInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: ['CraftNotification', craftNotificationInstance.id])
                redirect craftNotificationInstance
            }
            '*'{ respond craftNotificationInstance, [status: OK] }
        }
    }

	@Secured(['ROLE_ADMIN'])
    @Transactional
    def delete(CraftNotification craftNotificationInstance) {

        if (craftNotificationInstance == null) {
            notFound()
            return
        }

        craftNotificationInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: ['CraftNotification', craftNotificationInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: ['CraftNotification', params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
