package com.wordcraft

import com.wordcraft.WordFrequency
import com.wordcraft.WordFrequencyController;
import spock.lang.*
import org.codehaus.groovy.grails.web.json.JSONObject
import com.wordcraft.WordService

@TestFor(WordFrequencyController)
@Mock(WordFrequency)
class WordFrequencyControllerSpec extends Specification {

	def populateValidParams(params) {
		assert params != null
		// TODO: Populate valid properties like...
		//params["name"] = 'someValidName'
	}

	void "Test the index action returns the correct model"() {

		when:"The index action is executed"
		controller.index()

		then:"The model is correct"
		!model.wordFrequencyInstanceList
		model.wordFrequencyInstanceCount == 0
	}

	void "Test the create action returns the correct model"() {
		when:"The create action is executed"
		controller.create()

		then:"The model is correctly created"
		model.wordFrequencyInstance!= null
	}

	void "Test the save action correctly persists an instance"() {

		when:"The save action is executed with an invalid instance"
		request.contentType = FORM_CONTENT_TYPE
		request.method = 'POST'
		def wordFrequency = new WordFrequency()
		wordFrequency.validate()
		controller.save(wordFrequency)

		then:"The create view is rendered again with the correct model"
		model.wordFrequencyInstance!= null
		view == 'create'

		when:"The save action is executed with a valid instance"
		response.reset()
		populateValidParams(params)
		wordFrequency = new WordFrequency(params)

		controller.save(wordFrequency)

		then:"A redirect is issued to the show action"
		response.redirectedUrl == '/wordFrequency/show/1'
		controller.flash.message != null
		WordFrequency.count() == 1
	}

	void "Test that the show action returns the correct model"() {
		when:"The show action is executed with a null domain"
		controller.show(null)

		then:"A 404 error is returned"
		response.status == 404

		when:"A domain instance is passed to the show action"
		populateValidParams(params)
		def wordFrequency = new WordFrequency(params)
		controller.show(wordFrequency)

		then:"A model is populated containing the domain instance"
		model.wordFrequencyInstance == wordFrequency
	}

	void "Test that the edit action returns the correct model"() {
		when:"The edit action is executed with a null domain"
		controller.edit(null)

		then:"A 404 error is returned"
		response.status == 404

		when:"A domain instance is passed to the edit action"
		populateValidParams(params)
		def wordFrequency = new WordFrequency(params)
		controller.edit(wordFrequency)

		then:"A model is populated containing the domain instance"
		model.wordFrequencyInstance == wordFrequency
	}

	void "Test the update action performs an update on a valid domain instance"() {
		when:"Update is called for a domain instance that doesn't exist"
		request.contentType = FORM_CONTENT_TYPE
		request.method = 'PUT'
		controller.update(null)

		then:"A 404 error is returned"
		response.redirectedUrl == '/wordFrequency/index'
		flash.message != null


		when:"An invalid domain instance is passed to the update action"
		response.reset()
		def wordFrequency = new WordFrequency()
		wordFrequency.validate()
		controller.update(wordFrequency)

		then:"The edit view is rendered again with the invalid instance"
		view == 'edit'
		model.wordFrequencyInstance == wordFrequency

		when:"A valid domain instance is passed to the update action"
		response.reset()
		populateValidParams(params)
		wordFrequency = new WordFrequency(params).save(flush: true)
		controller.update(wordFrequency)

		then:"A redirect is issues to the show action"
		response.redirectedUrl == "/wordFrequency/show/$wordFrequency.id"
		flash.message != null
	}

	void "Test that the delete action deletes an instance if it exists"() {
		when:"The delete action is called for a null instance"
		request.contentType = FORM_CONTENT_TYPE
		request.method = 'DELETE'
		controller.delete(null)

		then:"A 404 is returned"
		response.redirectedUrl == '/wordFrequency/index'
		flash.message != null

		when:"A domain instance is created"
		response.reset()
		populateValidParams(params)
		def wordFrequency = new WordFrequency(params).save(flush: true)

		then:"It exists"
		WordFrequency.count() == 1

		when:"The domain instance is passed to the delete action"
		controller.delete(wordFrequency)

		then:"The instance is deleted"
		WordFrequency.count() == 0
		response.redirectedUrl == '/wordFrequency/index'
		flash.message != null
	}


	void "Test getWordFromLevel()"() {
		setup:
		def wordServiceMock = mockFor(WordService)
		wordServiceMock.demand.getRandomWordFromLevel() { int level -> return WordFrequency.findByRank(2658) }
		controller.wordService = wordServiceMock.createMock()

		when: "Calling action getWordFromLevel()"
		params.level = 3
		def res = controller.getWordFromLevel()

		then: "Response is correct type and correspond with returned value"
		res instanceof JSONObject
		res.getInt('rank') == 2658
		res.getWord('word') == WordFrequency.findByRank(2658).word
	}


	void "Test getWordFromRange()"() {
		setup:
		def wordServiceMock = mockFor(WordService)
		wordServiceMock.demand.getRandomWord() { int minRank, int maxRank -> return WordFrequency.findByRank(2327) }
		controller.wordService = wordServiceMock.createMock()

		when: "Calling action getWordFromRange()"
		params.minRank = 1017
		params.maxRank = 3520
		def res = controller.getWordFromRange()

		then: "Response is correct type and correspond with returned value"
		res instanceof JSONObject
		res.getInt('rank') == 2327
		res.getWord('word') == WordFrequency.findByRank(2327).word
	}
}
