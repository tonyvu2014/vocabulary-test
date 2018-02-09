package com.wordcraft

import static org.springframework.http.HttpStatus.*

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Transactional(readOnly = false)
class CraftTokenController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", secureGenerate: "POST", hasToken: "GET"]
	
	def TokenService tokenService

	@Secured(['ROLE_ADMIN'])
	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond CraftToken.list(params), model:[craftTokenInstanceCount: CraftToken.count()]
	}
	
	@Secured(['ROLE_ADMIN'])
	def show(CraftToken craftTokenInstance) {
		respond craftTokenInstance
	}

	@Secured(['ROLE_ADMIN'])
	def create() {
		respond new CraftToken(params)
	}

	@Secured(['ROLE_ADMIN'])
	@Transactional
	def save(CraftToken craftTokenInstance) {
		if (craftTokenInstance == null) {
			notFound()
			return
		}

		if (craftTokenInstance.hasErrors()) {
			respond craftTokenInstance.errors, view:'create'
			return
		}

		craftTokenInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [
					message(code: 'craftToken.label', default: 'CraftToken'),
					craftTokenInstance.id
				])
				redirect craftTokenInstance
			}
			'*' { respond craftTokenInstance, [status: CREATED] }
		}
	}

	
	@Secured(['ROLE_ADMIN'])
	def edit(CraftToken craftTokenInstance) {
		respond craftTokenInstance
	}

	@Secured(['ROLE_ADMIN'])
	@Transactional
	def update(CraftToken craftTokenInstance) {
		if (craftTokenInstance == null) {
			notFound()
			return
		}

		if (craftTokenInstance.hasErrors()) {
			respond craftTokenInstance.errors, view:'edit'
			return
		}

		craftTokenInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [
					message(code: 'CraftToken.label', default: 'CraftToken'),
					craftTokenInstance.id
				])
				redirect craftTokenInstance
			}
			'*'{ respond craftTokenInstance, [status: OK] }
		}
	}

	
	@Secured(['ROLE_ADMIN'])
	@Transactional
	def delete(CraftToken craftTokenInstance) {

		if (craftTokenInstance == null) {
			notFound()
			return
		}

		craftTokenInstance.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'CraftToken.label', default: 'CraftToken'),
					craftTokenInstance.id
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
					message(code: 'craftToken.label', default: 'CraftToken'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def hasToken() {
		def email = params.email
		def token = params.token
        log.info("Checking if user with email ${email} has token ${token}")		

		render(contentType:'text/json') {
			[
				'result': tokenService.hasToken(email, token)
			]
		}
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def secureGenerate() {
		def email = params.email
		log.info("Creating new token for user with ${email}")
		
		def token = tokenService.generateUUID(email)
		
		render(contentType:'text/json') {
			[
				'user_email': email,
				'token': token
			]
		}
	}
}
