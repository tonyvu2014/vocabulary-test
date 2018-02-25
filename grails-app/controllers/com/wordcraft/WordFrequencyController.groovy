package com.wordcraft

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

import com.wordcraft.utility.Constants

@Transactional(readOnly = false)
class WordFrequencyController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE", 
		                     getWordFromLevel: "GET", getWordFromRange: "GET", getRandomWord: "GET",
							 secureGetWordList: "GET", secureGetNextWord: "GET",
							 getTestWords: "GET", getTestEstimation: "POST",
							 getFinalTestWords: "GET", getTestResult: "POST"]

	def WordService wordService

	@Secured(['ROLE_ADMIN'])
	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond WordFrequency.list(params), model:[wordFrequencyInstanceCount: WordFrequency.count()]
	}

	@Secured(['ROLE_ADMIN'])
	def show(WordFrequency wordFrequencyInstance) {
		respond wordFrequencyInstance
	}

	@Secured(['ROLE_ADMIN'])
	def create() {
		respond new WordFrequency(params)
	}

	@Secured(['ROLE_ADMIN'])
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

	@Secured(['ROLE_ADMIN'])
	def edit(WordFrequency wordFrequencyInstance) {
		respond wordFrequencyInstance
	}

	@Secured(['ROLE_ADMIN'])
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

	@Secured(['ROLE_ADMIN'])
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

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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

	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def getTestWords() {
		def testSize = params.int('testSize')
		assert testSize > 0

		def wordRangeSize = Constants.MAX_WORD.intdiv(testSize)
		def remainder = Constants.MAX_WORD % testSize

		def wordList = []
		def rankList = []
		def wordFrequencyList = []
		def startRank = 1
		Random random = new Random()
		for (int i = 0; i < testSize; i++) {
			def endRank = startRank + wordRangeSize
			if (remainder > 0) {
				endRank++;
				remainder--;
			}

//			def wordFrequency = wordService.getRandomWord(startRank, endRank);
			def randomRank = random.nextInt(endRank - startRank) + startRank
			rankList += randomRank
			
			startRank = endRank
		}

		wordFrequencyList = wordService.getWordsByRanks(rankList)
		for (WordFrequency wordFrequency: wordFrequencyList) {
			wordList += wordFrequency.word
		}
		
		render(contentType:'text/json') {
			[
			'status': Constants.STATUS_SUCCESS,
			'words': wordList
			]
		}
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def getFinalTestWords() {
		def testSize = params.int('testSize')
		def minRank = params.int('minRank')
		def maxRank = params.int('maxRank')
		assert testSize > 0
		assert minRank >= 0
		assert maxRank > minRank
		
		def range = maxRank - minRank

		def wordRangeSize = range.intdiv(testSize)
		def remainder = range % testSize

		Random random = new Random()
		def rankList = []
		def wordList = []
		def startRank = minRank
		for (int i = 0; i < testSize; i++) {
			def endRank = startRank + wordRangeSize
			if (remainder > 0) {
				endRank++;
				remainder--;
			}
			
			def randomRank = random.nextInt(endRank - startRank) + startRank
			rankList += randomRank
			startRank = endRank
		}
		def wordFrequencyList = wordService.getWordsByRanks(rankList);
		for (WordFrequency wordFrequency : wordFrequencyList) {
			wordList += [
				word: wordFrequency.word,
				rank: wordFrequency.rank
			]
		}

		render(contentType:'text/json') {
			[
			'status': Constants.STATUS_SUCCESS,
			'words': wordList
			]
		}
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def getTestEstimation() {
		def data = request.JSON
		def result = []
		for (String key : data.keySet()) {
			result[Integer.valueOf(key)] = Boolean.valueOf(data.get(key))
		}
		
		def estimatedValue = wordService.getEstimatedValue(result)
		render(contentType:'text/json') {
			[
				'status': Constants.STATUS_SUCCESS,
				'range_number': estimatedValue,
			]
		}
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
	def getTestResult() {
		def data = request.JSON
		def result = []
		def rankMap = []
		def i = 0
		def keys = []
		for (String key : data.keySet()) {
			keys += Integer.valueOf(key)
		}
		def sortedKeys = keys.sort()
		for (int key : sortedKeys) {
			result[i] = Boolean.valueOf(data.get(String.valueOf(key)))
			rankMap[i] = Integer.valueOf(String.valueOf(key))
			i++;
		}
		
		def estimatedValue = wordService.getEstimatedValue(result)
		log.info("Estimated value: " + estimatedValue);
		def estimatedResult;
		if (estimatedValue in Integer) {
			estimatedResult = rankMap[estimatedValue]
		} else {
			def intEstimatedValue = (int) estimatedValue
			estimatedResult = (rankMap[intEstimatedValue] +  rankMap[intEstimatedValue+1]).intdiv(2)
		}
		render(contentType:'text/json') {
			[
				'status': Constants.STATUS_SUCCESS,
				'estimatedResult': estimatedResult,
			]
		}
	}
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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
		
		//TODO: To make this better by querying from a broader range
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
	
	@Secured(['IS_AUTHENTICATED_ANONYMOUSLY'])
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
