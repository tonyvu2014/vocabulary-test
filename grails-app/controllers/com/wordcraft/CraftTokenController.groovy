package com.wordcraft

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CraftTokenController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	
	def TokenService tokenService

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond CraftToken.list(params), model:[craftTokenInstanceCount: CraftToken.count()]
	}

	def show(CraftToken craftTokenInstance) {
		respond craftTokenInstance
	}

	def create() {
		respond new CraftToken(params)
	}

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

	def edit(CraftToken craftTokenInstance) {
		respond craftTokenInstance
	}

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

	def hasToken() {
		def username = params.username
		def token = params.token

		render(contentType:'text/json') {
			[
				'result': tokenService.hasToken(username, token)
			]
		}
	}
	
	
	def generate() {
		def username = params.username
		def token = tokenService.generate(username)
		
		render(contentType:'text/json') {
			[
				'username': username,
				'token': token
			]
		}
	}
}
