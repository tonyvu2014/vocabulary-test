package com.wordcraft

import grails.transaction.Transactional

import com.wordcraft.utility.Constants

@Transactional(readOnly = false)
class WordFrequencyController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", 
		                     getWordFromLevel: "GET", getWordFromRange: "GET", getRandomWord: "GET",
							 secureGetWordList: "GET", secureGetNextWord: "GET",
							 getTestWords: "GET"]

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
				flash.message = message(code: 'default.created.message', args: [
					message(code: 'wordFrequency.label', default: 'WordFrequency'),
					wordFrequencyInstance.id
				])
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
				flash.message = message(code: 'default.updated.message', args: [
					message(code: 'WordFrequency.label', default: 'WordFrequency'),
					wordFrequencyInstance.id
				])
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
				flash.message = message(code: 'default.deleted.message', args: [
					message(code: 'WordFrequency.label', default: 'WordFrequency'),
					wordFrequencyInstance.id
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
					message(code: 'wordFrequency.label', default: 'WordFrequency'),
					params.id
				])
				redirect action: "index", method: "GET"
			}
			'*'{ render status: NOT_FOUND }
		}
	}


	def getWordFromLevel() {
		def level = params.int('level')
		assert level > 0
		log.info("Get word from level: ${level}")

		def wordFrequency = wordService.getRandomWordFromLevel(level)
		render(contentType:'text/json') {
			[
				'word': wordFrequency.word,
				'rank': wordFrequency.rank
			]
		}
	}

	def getWordFromRange() {
		def minRank = params.int('minRank')
		def maxRank = params.int('maxRank')
		assert maxRank >= minRank
		log.info("Get word for rank from ${minRank} to ${maxRank}")

		def wordFrequency = wordService.getRandomWord(minRank, maxRank)
		render(contentType:'text/json') {
			[
				'status': Constants.STATUS_SUCCESS,
				'word': wordFrequency.word,
				'rank': wordFrequency.rank
			]
		}
	}
	
	def getRandomWord() {
		def wordFrequency = wordService.getRandomWord()
		
		render(contentType:'text/json') {
			[
				'status': Constants.STATUS_SUCCESS,
				'word': wordFrequency.word,
				'rank': wordFrequency.rank
			]
		}
	}

	def getTestWords() {
		def testSize = params.int('testSize')
		assert testSize > 0

		def wordRangeSize = Constants.MAX_WORD.intdiv(testSize)
		def remainder = Constants.MAX_WORD % testSize

		def wordList = []
		def startRank = 1
		for (int i = 0; i < testSize; i++) {
			def endRank = startRank + wordRangeSize
			if (remainder > 0) {
				endRank++;
				remainder--;
			}

			def wordFrequency = wordService.getRandomWord(startRank, endRank);
			wordList += wordFrequency.word
			startRank = endRank
		}

		render(contentType:'text/json') {
			[
			'status': Constants.STATUS_SUCCESS,
			'words': wordList
			]
		}
	}
	
	def secureGetNextWord() {
		def email = params.email
		assert email
		
		def wordCraftsman = WordCraftsman.findByEmail(email)
		if (!wordCraftsman) {
			log.error("Unable to find by email")
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('email.not.found', [email] as Object[], Locale.US)
				]
			}
			
			return;
		}
		
		def level = wordCraftsman.level? wordCraftsman.level:1
		
		while (true) {
			def wordFrequency = wordService.getRandomWordFromLevel(level)
			def word = wordFrequency.word
			
			if (!(word in wordCraftsman.craftWords)) {
				render(contentType:'text/json') {
					[
						'status': Constants.STATUS_SUCCESS,
						'word': word,
						'rank': wordFrequency.rank
					]
				}
				return;
			}
		}
		
	}
	
	
	def secureGetWordList() {
		def email = params.email
		assert email
		def wordCount = params.int('count')
		assert wordCount > 0 && wordCount <= Constants.MAX_WORDS_PER_TIME
		
		def wordCraftsman = WordCraftsman.findByEmail(email)	
		if (!wordCraftsman) {
			log.error("Unable to find by email")
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('email.not.found', [email] as Object[], Locale.US)
				]
			}
			
			return;
		}
		
		def level = wordCraftsman.level?wordCraftsman.level:1
		def wordList = []
		def numberOfWords = 0
		def attempts = 0
		while (numberOfWords < wordCount && attempts < Constants.MAX_LEVEL_ATTEMPTS) {
			def wordFrequency = wordService.getRandomWordFromLevel(level)
			attempts++
			def word = wordFrequency.word
			
			if (!(word in wordCraftsman.craftWords)) {
				wordList += word
				numberOfWords++
			}
		}
		
		if (attemtps == Constants.MAX_LEVEL_ATTEMPTS) {
			render(contentType:'text/json') {
				[
					'status': Constants.STATUS_FAILURE,
					'message': messageSource.getMessage('next.level.notification', Locale.US)
				]
			}
			
			return;
		}
		
	    render(contentType:'text/json') {
			[
			   'status': Constants.STATUS_SUCCESS,
				words: wordList
			]
		}
	}
}
