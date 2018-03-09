package com.wordcraft

import static org.springframework.http.HttpStatus.*

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import org.springframework.context.MessageSource

import com.wordcraft.utility.Constants

@Transactional(readOnly = false)
class CraftWordController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", secureMarkAsKnown: "POST"]

    def CraftWordService craftWordService
    def MessageSource messageSource

    @Secured(['ROLE_ADMIN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond CraftWord.list(params), model: [craftWordInstanceCount: CraftWord.count()]
    }

    @Secured(['ROLE_ADMIN'])
    def show(CraftWord craftWordInstance) {
        respond craftWordInstance
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        respond new CraftWord(params)
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def save(CraftWord craftWordInstance) {
        if (craftWordInstance == null) {
            notFound()
            return
        }

        if (craftWordInstance.hasErrors()) {
            respond craftWordInstance.errors, view: 'create'
            return
        }

        craftWordInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [
                        'CraftWord',
                        craftWordInstance.id
                ])
                redirect craftWordInstance
            }
            '*' { respond craftWordInstance, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def edit(CraftWord craftWordInstance) {
        respond craftWordInstance
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def update(CraftWord craftWordInstance) {
        if (craftWordInstance == null) {
            notFound()
            return
        }

        if (craftWordInstance.hasErrors()) {
            respond craftWordInstance.errors, view: 'edit'
            return
        }

        craftWordInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [
                        'CraftWord',
                        craftWordInstance.id
                ])
                redirect craftWordInstance
            }
            '*' { respond craftWordInstance, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def delete(CraftWord craftWordInstance) {

        if (craftWordInstance == null) {
            notFound()
            return
        }

        craftWordInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [
                        'CraftWord',
                        craftWordInstance.id
                ])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [
                        'CraftWord',
                        params.id
                ])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    @Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
    def secureMarkAsKnown() {
        def email = params.email
        def wordCraftsman = WordCraftsman.findByEmail(email)
        def word = params.word
        log.info("Marking word ${word} as known by user with email ${email}")

        try {
            craftWordService.markAsKnown(wordCraftsman, word)
            render(contentType: 'text/json') {
                [
                        'status'     : Constants.STATUS_SUCCESS,
                        'word'       : word,
                        'wordsLearnt': wordCraftsman.craftWords == null ? 0 : wordCraftsman.craftWords.size()
                ]
            }
            log.info("Successfully marked word ${word} as known by user with email ${email}")
        } catch (Exception ex) {
            log.error("Error marking word ${word} as known by user with email ${email}")
            ex.printStackTrace()
            render(contentType: 'text/json') {
                [
                        'status' : Constants.STATUS_FAILURE,
                        'message': messageSource.getMessage('fail.to.mark', null, Locale.US)
                ]
            }
        }
    }
}
