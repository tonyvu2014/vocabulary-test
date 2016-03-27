package com.wordcraft



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class WordCraftsmanController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

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
}
