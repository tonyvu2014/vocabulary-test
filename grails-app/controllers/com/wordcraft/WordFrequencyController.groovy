package com.wordcraft


import grails.transaction.Transactional

import com.wordcraft.WordFrequency;
import com.wordcraft.WordService

@Transactional(readOnly = true)
class WordFrequencyController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", getWordFromLevel: "GET", getWordFromRange: "GET"]
	
	def WordService wordService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond WordFrequency.list(params), model:[wordFrequencyInstanceCount: WordFrequency.count()]
    }

    def show(WordFrequency wordFrequencyInstance) {
        respond wordFrequencyInstance
    }

    def create() {
        respond new WordFrequency(params)
    }

    @Transactional
    def save(WordFrequency wordFrequencyInstance) {
        if (wordFrequencyInstance == null) {
            notFound()
            return
        }

        if (wordFrequencyInstance.hasErrors()) {
            respond wordFrequencyInstance.errors, view:'create'
            return
        }

        wordFrequencyInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'wordFrequency.label', default: 'WordFrequency'), wordFrequencyInstance.id])
                redirect wordFrequencyInstance
            }
            '*' { respond wordFrequencyInstance, [status: CREATED] }
        }
    }

    def edit(WordFrequency wordFrequencyInstance) {
        respond wordFrequencyInstance
    }

    @Transactional
    def update(WordFrequency wordFrequencyInstance) {
        if (wordFrequencyInstance == null) {
            notFound()
            return
        }

        if (wordFrequencyInstance.hasErrors()) {
            respond wordFrequencyInstance.errors, view:'edit'
            return
        }

        wordFrequencyInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'WordFrequency.label', default: 'WordFrequency'), wordFrequencyInstance.id])
                redirect wordFrequencyInstance
            }
            '*'{ respond wordFrequencyInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(WordFrequency wordFrequencyInstance) {

        if (wordFrequencyInstance == null) {
            notFound()
            return
        }

        wordFrequencyInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'WordFrequency.label', default: 'WordFrequency'), wordFrequencyInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'wordFrequency.label', default: 'WordFrequency'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	
	def getWordFromLevel() {
		def level = params.int('level')
		assert level > 0
		def wordFrequency = wordService.getRandomWordFromLevel(level)
		render(contentType:'text/json') {[
			'word': wordFrequency.word,
			'rank': wordFrequency.rank
		]}
	}
	
	def getWordFromRange() {
		def minRank = params.int('minRank')
		def maxRank = params.int('maxRank')
		assert maxRank >= minRank
		def wordFrequency = wordService.getRandomWord(minRank, maxRank)
		render(contentType:'text/json') {[
			'word': wordFrequency.word,
			'rank': wordFrequency.rank
		]}
	}
	
}
