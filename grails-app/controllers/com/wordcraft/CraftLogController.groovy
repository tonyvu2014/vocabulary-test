package com.wordcraft

import java.text.DateFormat;
import java.text.SimpleDateFormat

import org.springframework.context.MessageSource

import com.wordcraft.utility.Constants
import com.wordcraft.utility.EventType

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional(readOnly = false)
class CraftLogController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE",
		secureCreateHistory: "POST", secureViewHistory:"GET"]

	def MessageSource messageSource

	@Secured(['ROLE_ADMIN'])
	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond CraftLog.list(params), model:[craftLogInstanceCount: CraftLog.count()]
	}

	@Secured(['ROLE_ADMIN'])
	def show(CraftLog craftLogInstance) {
		respond craftLogInstance
	}
	
	@Secured(['ROLE_ADMIN'])
	def create() {
		respond new CraftLog(params)
	}

	@Secured(['ROLE_ADMIN'])
	@Transactional
	def save(CraftLog craftLogInstance) {
		if (craftLogInstance == null) {
			notFound()
			return
		}

		if (craftLogInstance.hasErrors()) {
			respond craftLogInstance.errors, view:'create'
			return
		}

		craftLogInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [
					'CraftLog',
					craftLogInstance.id
				])
				redirect craftLogInstance
			}
			'*' { respond craftLogInstance, [status: CREATED] }
		}
	}

	@Secured(['ROLE_ADMIN'])
	def edit(CraftLog craftLogInstance) {
		respond craftLogInstance
	}

	@Secured(['ROLE_ADMIN'])
	@Transactional
	def update(CraftLog craftLogInstance) {
		if (craftLogInstance == null) {
			notFound()
			return
		}

		if (craftLogInstance.hasErrors()) {
			respond craftLogInstance.errors, view:'edit'
			return
		}

		craftLogInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [
					'CraftLog',
					craftLogInstance.id
				])
				redirect craftLogInstance
			}
			'*'{ respond craftLogInstance, [status: OK] }
		}
	}

	@Secured(['ROLE_ADMIN'])
	@Transactional
	def delete(CraftLog craftLogInstance) {

		if (craftLogInstance == null) {
			notFound()
			return
		}

		craftLogInstance.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					'CraftLog',
					craftLogInstance.id
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
					'CraftLog',
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def secureCreateHistory() {
		def email = params.email
		def wordCraftsman = WordCraftsman.findByEmail(email)

		def event = params.event
		assert event.toUpperCase() in EventType.values().collect{it.name()}

		def eventType = EventType.valueOf(event.toUpperCase())
		def desc = messageSource.getMessage('event.activity.desc', null, Locale.US)
		switch(eventType) {
			case EventType.TEST:
				def estimatedSize = params.int('estimatedSize')
				assert estimatedSize >= 0
				desc = messageSource.getMessage('event.test.desc', [estimatedSize] as Object[], Locale.US)
				break
			case EventType.LEARN:
				def words = params.int('words')
				assert words >= 1
				desc = messageSource.getMessage('event.learn.desc', [words] as Object[], Locale.US)
				break
			case EventType.ADVANCE:
				def level = params.int('level')
				assert level >= 1
				desc = messageSource.getMessage('event.advance.desc', [level] as Object[], Locale.US)
				break
			default:
				break
		}
		
		def eventTime = params.eventTime
		assert eventTime != null
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm")
		def craftLog = new CraftLog(eventTime: df.parse(eventTime), eventType: eventType, description: desc)
		wordCraftsman.addToCraftLogs(craftLog)
		wordCraftsman.save(flush:true, failOnError:true)
		render(contentType:'text/json') {
			[
				'status': Constants.STATUS_SUCCESS,
				'description': desc
			]
		}
	}

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def secureViewHistory() {
		def email = params.email
		def wordCraftsman = WordCraftsman.findByEmail(email)

		def craftLogs = wordCraftsman.craftLogs.sort{ it.eventTime }.reverse().take(Constants.MAX_LOG_ENTRIES)
		def history = []
		for (craftLog in craftLogs) {
			history += [
				"eventType": craftLog.eventType.toString(),
				"eventTime": craftLog.eventTime.format("dd/MM/yyyy HH:mm"),
				"eventDesc": craftLog.description
			]
		}

		render(contentType:'text/json') {
			[
				'status': Constants.STATUS_SUCCESS,
				'history': history
			]
		}
	}
}
