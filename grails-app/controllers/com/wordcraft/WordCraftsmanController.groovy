package com.wordcraft

import grails.transaction.Transactional
import grails.validation.ValidationException

import org.springframework.context.MessageSource

import com.wordcraft.utility.Constants
import com.wordcraft.utility.Utils

@Transactional(readOnly = false)
class WordCraftsmanController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", login:"POST", secureLogout: "POST",
		register: "POST", secureChange: "POST", forgotPassword: "POST", hasUsername: "GET", hasEmail: "GET", 
		hasUsernameOrEmail: "GET", saveFacebookAccount: "POST"]

	def WordCraftsmanService wordCraftsmanService
	def MessageSource messageSource
	def TokenService tokenService 
	def groovyPageRenderer

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
	
	/**
	 * Check if an username already exists
	 * @return true if the username exists in the database, false otherwise
	 */
	def hasUsername() {
		def username = params.username
		log.info("Checking if username ${username} exists")
		
		def wordCraftsman = WordCraftsman.findByUsername(username);
		if (wordCraftsman) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS
				]
			}
			log.info("Username ${username} exists")
		} else {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE
				]
			}
			log.info("Username ${username} does not exist")
		}
		
	}
	
	/**
	 * Check if an email already exists
	 * @return true if the email exists in the database, false otherwise
	 */
	def hasEmail() {
		def email = params.email
		log.info("Checking if email ${email} exists")
		
		def wordCraftsman = WordCraftsman.findByEmail(email);
		if (wordCraftsman) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS
				]
			}
			log.info("Email ${email} exists")
		} else {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE
				]
			}
			log.info("Email ${email} does not exist")
		}
		
	}
	
	
	/**
	 * Check if an email or username already exists
	 * @return true if the username or email exists in the database, false otherwise
	 */
	def hasUsernameOrEmail() {
		def username = params.username
		def email = params.email
		log.info("Checking if username ${username} or email ${email} exists")
		
		def wordCraftsman = WordCraftsman.findByUsername(username);
		if (wordCraftsman) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS,
					'message':"Username ${username} is not available"
				]
			}
			log.info("Username ${username} exists")
			return;
		} 
		wordCraftsman = WordCraftsman.findByEmail(email)
		if (wordCraftsman) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS,
					'message':"Email ${email} is not available"
				]
			}
			log.info("Email ${email} exists")
			return;
		}
		
		log.info("Username ${username} and email ${email} do not exist")
		render(contentType:'text/json') {
			[
				'status': Constants.STATUS_FAILURE,
				'message': "Username ${username} and email ${email} are available"
			]
		}
		
	}



    /**
     * Login to the app
     * @return
     */
	def login() {
		def email = params.email
		def password = params.password
		log.info("Logging in with email ${email}")

		def wordCraftsman = wordCraftsmanService.findPrincipal(email, password)
		if (wordCraftsman!=null) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS,
					'token': tokenService.generateUUID(email)
				]
			}
			log.info("Logged in successfully for user with email ${email}")
		} else {
			log.error("Login failed for user with email ${email}")
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('wrong.identity', null, Locale.ENGLISH)
				]
			}
		}
	}

	
	/**
	 * Log out of the app
	 * @return
	 */
	def secureLogout() {
		def email = params.email

		def result = tokenService.removeToken(email)
		if (result) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS
				]
			}
			log.info("Successfully logging out as user with email ${email}")
		} else {
			log.error("Error logging out as user with ${email}")
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE
				]
			}
		}
	}
	
	/**
	 * Forgot password, resend new password to user's email
	 * @return
	 */
	def forgotPassword() {
		def email = params.email
		
		def wordCraftsman = WordCraftsman.findByEmail(email)
		if (!wordCraftsman) {
			log.error("Unable to find by email")
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('email.not.found', [email] as Object[], Locale.US)
				]
			}
		} else {
		    if (wordCraftsman.isFacebook) {
				log.info("User registers with Facebook account")
				render(contentType:'text/json') {
					[
						'status': Constants.STATUS_SUCCESS,
						'message': messageSource.getMessage('user.facebook.account', null, Locale.US)
					]
				}
				return
			}
		
		    def new_pass = Utils.generateToken(Constants.PASS_LENGTH)
			wordCraftsman.password=Utils.encryptData(new_pass)
			wordCraftsman.save(flush:true, failOnError:true)
			def content = groovyPageRenderer.render(view: '/mails/forgot_password', 
				                                    model:[username:wordCraftsman.username, password: new_pass])
		    sendMail {
				async true
			    to email
			    subject "Password Recovery"
			    html content
		    }
			log.info("Successfully sent user new password")
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS,
					'message': messageSource.getMessage('new.password.sent', null, Locale.US)
				]
			}
		}
		
	}

    /***
     * Register a new user
     * @return
     */
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
					'username': username,
					'email': email
				]
			}
			sendWelcomeEmail(email, username)
			log.info("Successfully registered user ${username} with email ${email}")
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
	
	
	/***
	 * Save Facebook account
	 * @return
	 */
	def saveFacebookAccount() {
		def username = params.username
		assert username
		def email = params.email
		assert email
		
		def wordCraftsman = WordCraftsman.findByEmail(email)
		
		if (wordCraftsman) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('user.email.exists', [email] as Object[], Locale.US)
				]
			}
			return;
		}
		wordCraftsman = new WordCraftsman()
		wordCraftsman.email = email
		wordCraftsman.username = username
		wordCraftsman.isFacebook = true

		try {
			wordCraftsman.save(flush:true, failOnError: true)
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS,
					'username': username,
					'email': email
				]
			}
		    sendWelcomeEmail(email, username)
			log.info("Successfully saved Facebook account for user ${username} with email ${email}")
		} catch (Exception e) {
			log.error("Error in saving Facebook account")
			log.error(e.message)
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('user.fail.to.save.facebook.account', null, Locale.US)
				]
			}
		}
	}
	
	def private sendWelcomeEmail(def email, def username) {
		def content = groovyPageRenderer.render(view: '/mails/welcome',
			model:[username:username])
		sendMail {
			async true
			to email
			subject "Welcome to Wordcraft"
			html content
		}
	}

	/***
	 * Change profile
	 * @return
	 */
	def secureChange() {
		def email = params.email
		assert email
		def wordCraftsman = WordCraftsman.findByEmail(email)

		def password = params.password
		def vocabularySize = params.int("vocabularySize")

		if (password) {
			wordCraftsman.password = password
		}

		if (vocabularySize) {
			if (vocabularySize > Constants.MAX_WORD) {
				render(contentType:'text/json') {
					[
						'status': Constants.STATUS_FAILURE,
						'message': messageSource.getMessage('vocabulary.size.exceeds.max', [Constants.MAX_WORD] as Object[], Locale.US)
					]
				}
			} else {
				wordCraftsman.estimatedSize = vocabularySize
				wordCraftsman.level = vocabularySize / Constants.WORD_PER_LEVEL + 1
			}
		}
		try {
			wordCraftsman.save(flush:true, failOnError:true)
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_SUCCESS,
					'email': email,
				]
			}
			log.info("Successfully apply changes for user with email ${email}")
		} catch (ValidationException e) {
			log.error("Error in updating for the user with email ${email}")
			log.error(e.message)
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('user.fail.to.update', null, Locale.US)
				]
			}
		}
	}
}
