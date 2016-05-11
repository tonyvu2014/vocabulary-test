package com.wordcraft

import grails.transaction.Transactional
import org.springframework.context.MessageSource
import com.wordcraft.utility.Constants

@Transactional(readOnly = false)
class CraftTestController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", secureCreateTest: "POST"]

	def MessageSource messageSource

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond CraftTest.list(params), model:[craftTestInstanceCount: CraftTest.count()]
	}

	def show(CraftTest craftTestInstance) {
		respond craftTestInstance
	}

	def create() {
		respond new CraftTest(params)
	}

	@Transactional
	def save(CraftTest craftTestInstance) {
		if (craftTestInstance == null) {
			notFound()
			return
		}

		if (craftTestInstance.hasErrors()) {
			respond craftTestInstance.errors, view:'create'
			return
		}

		craftTestInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [
					message(code: 'craftTest.label', default: 'CraftTest'),
					craftTestInstance.id
				])
				redirect craftTestInstance
			}
			'*' { respond craftTestInstance, [status: CREATED] }
		}
	}

	def edit(CraftTest craftTestInstance) {
		respond craftTestInstance
	}

	@Transactional
	def update(CraftTest craftTestInstance) {
		if (craftTestInstance == null) {
			notFound()
			return
		}

		if (craftTestInstance.hasErrors()) {
			respond craftTestInstance.errors, view:'edit'
			return
		}

		craftTestInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [
					message(code: 'CraftTest.label', default: 'CraftTest'),
					craftTestInstance.id
				])
				redirect craftTestInstance
			}
			'*'{ respond craftTestInstance, [status: OK] }
		}
	}

	@Transactional
	def delete(CraftTest craftTestInstance) {

		if (craftTestInstance == null) {
			notFound()
			return
		}

		craftTestInstance.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'CraftTest.label', default: 'CraftTest'),
					craftTestInstance.id
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
					message(code: 'craftTest.label', default: 'CraftTest'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}


	def secureCreateTest() {
		def username = params.username
		def wordCraftsman = WordCraftsman.findByUsername(username)
		log.info("Creating test for user ${username}")

		def resultSize = params.int("resultSize")
		def testTime = new Date()
		def craftTest = new CraftTest(testTime: testTime, resultSize: resultSize)
		wordCraftsman.addToCraftTests(craftTest)
		wordCraftsman.save(flush:true, failOnError:true)
		render(contentType:'text/json') {
			[
				'status': Constants.STATUS_SUCCESS,
				'result_size': resultSize,
				'test_time': testTime.format("dd/MM/yyyy HH:mm")
			]
		}
		log.info("Successfully created a new test for user ${username}")
	}
}
