package com.wordcraft

import org.springframework.context.MessageSource

import com.wordcraft.utility.Constants

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional(readOnly = false)
class CraftTestController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", secureCreateTest: "POST"]

	def MessageSource messageSource

	@Secured(['ROLE_ADMIN'])
	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond CraftTest.list(params), model:[craftTestInstanceCount: CraftTest.count()]
	}
	
	@Secured(['ROLE_ADMIN'])
	def show(CraftTest craftTestInstance) {
		respond craftTestInstance
	}
	
	@Secured(['ROLE_ADMIN'])
	def create() {
		respond new CraftTest(params)
	}
	
	@Secured(['ROLE_ADMIN'])
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
	
	@Secured(['ROLE_ADMIN'])
	def edit(CraftTest craftTestInstance) {
		respond craftTestInstance
	}

	@Secured(['ROLE_ADMIN'])
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

	@Secured(['ROLE_ADMIN'])
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

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def secureCreateTest() {
		def email = params.email
		def wordCraftsman = WordCraftsman.findByEmail(email)
		log.info("Creating test for user with email ${email}")

		def resultSize = params.int("resultSize")
		def testTime = new Date()
		def craftTest = new CraftTest(testTime: testTime, resultSize: resultSize)
		wordCraftsman.addToCraftTests(craftTest)
		wordCraftsman.save(flush:true, failOnError:true)
		render(contentType:'text/json') {
			[
				'status': Constants.STATUS_SUCCESS,
				'resultSize': resultSize,
				'testTime': testTime.format("dd/MM/yyyy HH:mm")
			]
		}
		log.info("Successfully created a new test for user with email ${email}")
	}
}
