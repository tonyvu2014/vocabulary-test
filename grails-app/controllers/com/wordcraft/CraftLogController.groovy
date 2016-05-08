package com.wordcraft


import grails.transaction.Transactional

import org.springframework.context.MessageSource
import com.wordcraft.utility.Constants
import com.wordcraft.utility.EventType

@Transactional(readOnly = true)
class CraftLogController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", 
		                     secureCreateHistory: "POST", secureViewHistory:"GET"]
	
	def MessageSource messageSource
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond CraftLog.list(params), model:[craftLogInstanceCount: CraftLog.count()]
    }

    def show(CraftLog craftLogInstance) {
        respond craftLogInstance
    }

    def create() {
        respond new CraftLog(params)
    }

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
                flash.message = message(code: 'default.created.message', args: [message(code: 'craftLog.label', default: 'CraftLog'), craftLogInstance.id])
                redirect craftLogInstance
            }
            '*' { respond craftLogInstance, [status: CREATED] }
        }
    }

    def edit(CraftLog craftLogInstance) {
        respond craftLogInstance
    }

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
                flash.message = message(code: 'default.updated.message', args: [message(code: 'CraftLog.label', default: 'CraftLog'), craftLogInstance.id])
                redirect craftLogInstance
            }
            '*'{ respond craftLogInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(CraftLog craftLogInstance) {

        if (craftLogInstance == null) {
            notFound()
            return
        }

        craftLogInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'CraftLog.label', default: 'CraftLog'), craftLogInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'craftLog.label', default: 'CraftLog'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	def secureCreateHistory() {
		def username = params.username
		def wordCraftsman = WordCraftsman.findByUsername(username)
		log.info("Creating log event for user ${username}")
		
		def event = params.event
		assert event.toUpperCase() in EventType.values().collect{it.name()}
		
		def eventType = EventType.valueOf(event.toUpperCase())
		def eventTime = new Date()
		def desc = messageSource.getMessage('event.activity.desc', [eventTime.format("dd/MM/yyyy HH:mm")] as Object[] , Locale.US)
		switch(eventType) {
			case EventType.TEST:
			    log.info("Logging event: TEST")
			    desc = messageSource.getMessage('event.test.desc', [eventTime.format("dd/MM/yyyy HH:mm")] as Object[], Locale.US)
			    break
			case EventType.LEARN:
			    log.info("Logging event: LEARN")
			    def words = params.int('words')
				assert words >= 1
				desc = messageSource.getMessage('event.learn.desc', [words, eventTime.format("dd/MM/yyyy HH:mm")] as Object[], Locale.US)
			    break
			case EventType.ADVANCE:
			    log.info("Logging event: ADVANCE TO THE NEXT LEVEL")
			    def level = params.int('level')
				assert level >= 1
				desc = messageSource.getMessage('event.advance.desc', [level, eventTime.format("dd/MM/yyyy HH:mm")] as Object[], Locale.US)
				break
			default:
			    break
		}
		def craftLog = new CraftLog(eventTime:eventTime, eventType: eventType, description: desc)
        wordCraftsman.addToCraftLogs(craftLog)
		wordCraftsman.save(flush:true, failOnError:true)
		render(contentType:'text/json') {[
			'status': Constants.STATUS_SUCCESS,
			'description': desc
		]}
	}
	
	def secureViewHistory() {
		def username = params.username
		def wordCraftsman = WordCraftsman.findByUsername(username)
		log.info("Viewing history of user ${username}")
		
		def craftLogs = wordCraftsman.craftLogs
		def history = []
		for (craftLog in craftLogs) {
			history += [
				"eventTime": craftLog.eventTime.format("dd/MM/yyyy HH:mm"),
				"eventDesc": craftLog.description
			]
		}
	
		render(contentType:'text/json') {[
			'status': Constants.STATUS_SUCCESS,
			'history': history
		]}
		
	}
}
