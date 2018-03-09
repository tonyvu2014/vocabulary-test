package com.wordcraft

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import org.springframework.context.MessageSource

import com.wordcraft.utility.Constants

@Transactional(readOnly = false)
class CraftSettingsController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", secureGet: "GET", secureSet: "POST"]

	MessageSource messageSource
	CraftNotificationService notificationService

	@Secured(['ROLE_ADMIN'])
	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond CraftSettings.list(params), model:[craftSettingsInstanceCount: CraftSettings.count()]
	}

	@Secured(['ROLE_ADMIN'])
	def show(CraftSettings craftSettingsInstance) {
		respond craftSettingsInstance
	}
	
	@Secured(['ROLE_ADMIN'])
	def create() {
		respond new CraftSettings(params)
	}

	@Secured(['ROLE_ADMIN'])
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
				flash.message = message(code: 'default.created.message', args: [
					'CraftSettings',
					craftSettingsInstance.id
				])
				redirect craftSettingsInstance
			}
			'*' { respond craftSettingsInstance, [status: CREATED] }
		}
	}
	
	@Secured(['ROLE_ADMIN'])
	def edit(CraftSettings craftSettingsInstance) {
		respond craftSettingsInstance
	}

	@Secured(['ROLE_ADMIN'])
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
				flash.message = message(code: 'default.updated.message', args: [
					'CraftSettings',
					craftSettingsInstance.id
				])
				redirect craftSettingsInstance
			}
			'*'{ respond craftSettingsInstance, [status: OK] }
		}
	}
	
	@Secured(['ROLE_ADMIN'])
	@Transactional
	def delete(CraftSettings craftSettingsInstance) {

		if (craftSettingsInstance == null) {
			notFound()
			return
		}

		craftSettingsInstance.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					'CraftSettings',
					craftSettingsInstance.id
				])
				redirect action:"index", method:"GET"
			}
			'*'{ render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.not.found.message', args: [
					'CraftSettings',
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def secureGet() {
		def email = params.email
		def wordCraftsman = WordCraftsman.findByEmail(email)
		def settings = wordCraftsman.craftSettings
		if (settings) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS,
					'pace': settings.craftPace,
					'load': settings.craftLoad,
					'hour': settings.craftHour == null? "": settings.craftHour,
                    'minute': settings.craftMinute == null? "": settings.craftMinute,
					'notification': settings.craftNotification,
					'timezone': settings.craftTimezone,
					'notification_token': settings.craftNotificationToken == null? "": settings.craftNotificationToken
				]
			}
		} else {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('fail.to.get.settings', null, Locale.US)
				]
			}
		}
	}

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def secureSet() {
		def email = params.email
		def pace = params.int('pace')
		def load = params.int('load')
		def hour = params.int('hour')
		def minute = params.int('minute')
		def notification = params.boolean('notification')
		def notificationToken = params.notificationToken
		def timezone = params.timezone
		def time = hour==null || minute==null?"":hour + ":" + minute
		log.info("New settings: pace = ${pace}, load = ${load}, time = ${time}, notification=${notification}, notification_token=${notificationToken}, timezone=${timezone}")

		def wordCraftsman = WordCraftsman.findByEmail(email)
		def settings = wordCraftsman.craftSettings
		if (settings) {
			if (load != null) {
				settings.craftLoad = load
			}
			if (pace != null) {
				settings.craftPace = pace
			}
			if (hour != null && minute != null) {
				settings.craftHour = hour
				settings.craftMinute = minute
			}
			if (notification != null) {
				settings.craftNotification = notification
			}
			if (timezone != null && !timezone.isEmpty()) {
				settings.craftTimezone = timezone
			}
			if (notificationToken != null && !notificationToken.isEmpty()) {
				settings.craftNotificationToken = notificationToken
			}		
			settings.save(flush:true, failOnError:true)
			notificationService.updateNotifications(settings)
			log.info("Successfully updated settings to: pace = ${pace}, load = ${load}, time = ${time}, notification=${notification}, notification_token=${notificationToken},timezone=${timezone}")
		} else {
			def newSettings = new CraftSettings()
			if (load != null) {
				newSettings.craftLoad = load
			}
			if (pace != null) {
				newSettings.craftPace = pace
			}
			if (hour!=null && minute!=null) {
				newSettings.craftHour = hour
				newSettings.craftMinute = minute
			}
			if (notification!=null) {
				newSettings.craftNotification = notification
			}
			if (timezone != null && !timezone.isEmpty()) {
				newSettings.craftTimezone = timezone
			}
			if (notificationToken != null && !notificationToken.isEmpty()) {
				newSettings.craftNotificationToken = notificationToken
			}
			wordCraftsman.craftSettings = newSettings
			wordCraftsman.save(flush:true, failOnError: true)
			notificationService.updateNotifications(newSettings)
			log.info("Successfully created new settings: pace = ${pace}, load = ${load}, time=${time}, notification=${notification}, notification_token=${notificationToken},timezone=${timezone}")
		}

		render(contentType:'text/json') {
			[
				'status': Constants.STATUS_SUCCESS,
				'pace': pace,
				'load': load,
				'hour': hour,
				'minute': minute,
				'notification': notification,
				'notification_token': notificationToken,
				'timezone': timezone
			]
		}
	}
}
