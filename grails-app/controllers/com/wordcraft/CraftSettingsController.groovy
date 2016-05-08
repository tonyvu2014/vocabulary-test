package com.wordcraft

import org.springframework.context.MessageSource;
import grails.transaction.Transactional
import com.wordcraft.utility.Constants

@Transactional(readOnly = true)
class CraftSettingsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", secureGet: "GET", secureSet: "POST"]
	
	def MessageSource messageSource

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
	
	def secureGet() {
		def username = params.username
        def wordCraftsman = WordCraftsman.findByUsername(username)
		def settings = wordCraftsman.craftSettings
		if (settings) {
			render(contentType:'text/json') {[
				'status': Constants.STATUS_SUCCESS,
				'pace': settings.craftPace,
				'load': settings.craftLoad 
			]}
		} else {
			render(contentType:'text/json') {[
				'status': Constants.STATUS_FAILURE,
				'message': messageSource.getMessage('fail.to.get.settings', null, Locale.US)
			]}
		}
	}
	
	def secureSet() {
		def username = params.username
		def pace = params.int('pace')
		def load = params.int('load')
		assert pace>=1
		assert load>=1
		log.info("New settings: pace = ${pace}, load = ${load}")
		
        def wordCraftsman = WordCraftsman.findByUsername(username)
		def settings = wordCraftsman.craftSettings	
		if (settings) {
			settings.craftLoad = load
			settings.craftPace = pace
			settings.save(flush:true, failOnError:true)
			render(contentType:'text/json') {[
				'status': Constants.STATUS_SUCCESS,
				'pace': pace,
				'load': load
			]}
			log.info("Successfully updated settings to: pace = ${pace}, load = ${load}")
		} else {
		    def newSettings = new CraftSettings(craftPace:pace, craftLoad:load)
			wordCraftsman.craftSettings = newSettings
			wordCraftsman.save(flush:true, failOnError: true)
			render(contentType:'text/json') {[
				'status': Constants.STATUS_SUCCESS,
				'pace': pace,
				'load': load
			]}
			log.info("Successfully created new settings: pace = ${pace}, load = ${load}")
		}
		
	}
}
