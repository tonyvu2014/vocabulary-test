package com.wordcraft

import grails.transaction.Transactional
import grails.validation.ValidationException
import org.springframework.context.MessageSource
import com.wordcraft.utility.Constants

@Transactional(readOnly = false)
class WordCraftsmanController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", login:"POST", secureLogout: "POST",
		register: "POST", secureChange: "POST"]

	def WordCraftsmanService wordCraftsmanService
	def MessageSource messageSource
	def TokenService tokenService

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond WordCraftsman.list(params), model:[wordCraftsmanInstanceCount: WordCraftsman.count()]
	}

	def show(WordCraftsman wordCraftsmanInstance) {
		respond wordCraftsmanInstance
	}

	def create() {
		respond new WordCraftsman(params)
	}

	@Transactional
	def save(WordCraftsman wordCraftsmanInstance) {
		if (wordCraftsmanInstance == null) {
			notFound()
			return
		}

		if (wordCraftsmanInstance.hasErrors()) {
			respond wordCraftsmanInstance.errors, view:'create'
			return
		}

		wordCraftsmanInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.created.message', args: [
					message(code: 'wordCraftsman.label', default: 'WordCraftsman'),
					wordCraftsmanInstance.id
				])
				redirect wordCraftsmanInstance
			}
			'*' { respond wordCraftsmanInstance, [status: CREATED] }
		}
	}

	def edit(WordCraftsman wordCraftsmanInstance) {
		respond wordCraftsmanInstance
	}

	@Transactional
	def update(WordCraftsman wordCraftsmanInstance) {
		if (wordCraftsmanInstance == null) {
			notFound()
			return
		}

		if (wordCraftsmanInstance.hasErrors()) {
			respond wordCraftsmanInstance.errors, view:'edit'
			return
		}

		wordCraftsmanInstance.save flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.updated.message', args: [
					message(code: 'WordCraftsman.label', default: 'WordCraftsman'),
					wordCraftsmanInstance.id
				])
				redirect wordCraftsmanInstance
			}
			'*'{ respond wordCraftsmanInstance, [status: OK] }
		}
	}

	@Transactional
	def delete(WordCraftsman wordCraftsmanInstance) {

		if (wordCraftsmanInstance == null) {
			notFound()
			return
		}

		wordCraftsmanInstance.delete flush:true

		request.withFormat {
			form multipartForm {
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'WordCraftsman.label', default: 'WordCraftsman'),
					wordCraftsmanInstance.id
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
					message(code: 'wordCraftsman.label', default: 'WordCraftsman'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}


	def login() {
		def username = params.username
		def password = params.password
		log.info("Logging in as user ${username}")


		def wordCraftsman = wordCraftsmanService.findPrincipal(username, password)
		if (wordCraftsman!=null) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS,
					'token': tokenService.generateUUID(username)
				]
			}
			log.info("Logged in successfully for user ${username}")
		} else {
			log.error("Login failed for user ${username}")
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('wrong.identity', null, Locale.ENGLISH)
				]
			}
		}
	}

	def secureLogout() {
		def username = params.username

		def result = tokenService.removeToken(username)
		if (result) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS
				]
			}
			log.info("Successfully logging out as user ${username}")
		} else {
			log.error("Error logging out as user ${username}")
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE
				]
			}
		}
	}


	def register() {
		def username = params.username
		def password = params.password
		assert password
		def email = params.email
		assert email

		def wordCraftsman = new WordCraftsman()
		wordCraftsman.username = username
		wordCraftsman.password = password
		wordCraftsman.email = email

		try {
			wordCraftsman.save(flush:true, failOnError: true)
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS,
					'username': username
				]
			}
			log.info("Successfully registered user ${username}")
		} catch (ValidationException e) {
			log.error("Error in saving the wordcraftsman")
			e.printStackTrace()
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('user.fail.to.register', null, Locale.US)
				]
			}
		}
	}

	def secureChange() {
		def username = params.username
		def wordCraftsman = WordCraftsman.findByUsername(username)

		def password = params.password
		def email = params.email
		def vocabularySize = params.int("vocabularySize")
		assert vocabularySize <= Constants.MAX_WORD

		if (password) {
			wordCraftsman.password = password
		}

		if (email) {
			wordCraftsman.email = email
		}

		if (vocabularySize) {
			wordCraftsman.estimatedSize = vocabularySize
			wordCraftsman.level = vocabularySize / Constants.WORD_PER_LEVEL + 1
		}
		try {
			wordCraftsman.save(flush:true, failOnError:true)
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS,
					'username': username,
				]
			}
			log.info("Successfully apply changes for user ${username}")
		} catch (ValidationException e) {
			log.error("Error in updating for the wordcraftsman ${username}")
			e.printStackTrace()
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('user.fail.to.update', null, Locale.US)
				]
			}
		}
	}
}
