package com.wordcraft

import grails.transaction.Transactional

import org.springframework.context.MessageSource

import com.wordcraft.utility.Constants

@Transactional(readOnly = true)
class WordCraftsmanController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", login:"POST", logout: "POST"]
	
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
                flash.message = message(code: 'default.created.message', args: [message(code: 'wordCraftsman.label', default: 'WordCraftsman'), wordCraftsmanInstance.id])
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
                flash.message = message(code: 'default.updated.message', args: [message(code: 'WordCraftsman.label', default: 'WordCraftsman'), wordCraftsmanInstance.id])
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
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'WordCraftsman.label', default: 'WordCraftsman'), wordCraftsmanInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'wordCraftsman.label', default: 'WordCraftsman'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	
	def login() {
		def username = params.username
		def password = params.password
		
		def wordCraftsman = wordCraftsmanService.findPrincipal(username, password)
		
		if (wordCraftsman!=null) {			
			render(contentType:'text/json') {[
			    'status': Constants.STATUS_SUCCESS,
		        'token': tokenService.generateUUID(username)			
			]}
		} else {
		    render(contentType:'text/json') {[
			    'status': Constants.STATUS_FAILURE,
			    'message': messageSource.getMessage('wrong.identity', null, Locale.ENGLISH)
		    ]}
		}
	} 
	
	def logout() {
		def username = params.username
		def result = tokenService.removeToken(username)
		if (result) {
			render(contentType:'text/json') {[
				'status': Constants.STATUS_SUCCESS
			]}
		} else {
			render(contentType:'text/json') {[
				'status': Constants.STATUS_FAILURE
			]}
		}
	}
	
	
	
}
