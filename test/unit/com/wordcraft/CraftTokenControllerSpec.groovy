package com.wordcraft

import grails.test.mixin.*
import spock.lang.*

@TestFor(CraftTokenController)
@Mock(CraftToken)
class CraftTokenControllerSpec extends Specification {

	def populateValidParams(params) {
		assert params != null
		// TODO: Populate valid properties like...
		//params["name"] = 'someValidName'
	}

	void "Test the index action returns the correct model"() {

		when:"The index action is executed"
		controller.index()

		then:"The model is correct"
		!model.craftTokenInstanceList
		model.craftTokenInstanceCount == 0
	}

	void "Test the create action returns the correct model"() {
		when:"The create action is executed"
		controller.create()

		then:"The model is correctly created"
		model.craftTokenInstance!= null
	}

	void "Test the save action correctly persists an instance"() {

		when:"The save action is executed with an invalid instance"
		request.contentType = FORM_CONTENT_TYPE
		request.method = 'POST'
		def craftToken = new CraftToken()
		craftToken.validate()
		controller.save(craftToken)

		then:"The create view is rendered again with the correct model"
		model.craftTokenInstance!= null
		view == 'create'

		when:"The save action is executed with a valid instance"
		response.reset()
		populateValidParams(params)
		craftToken = new CraftToken(params)

		controller.save(craftToken)

		then:"A redirect is issued to the show action"
		response.redirectedUrl == '/craftToken/show/1'
		controller.flash.message != null
		CraftToken.count() == 1
	}

	void "Test that the show action returns the correct model"() {
		when:"The show action is executed with a null domain"
		controller.show(null)

		then:"A 404 error is returned"
		response.status == 404

		when:"A domain instance is passed to the show action"
		populateValidParams(params)
		def craftToken = new CraftToken(params)
		controller.show(craftToken)

		then:"A model is populated containing the domain instance"
		model.craftTokenInstance == craftToken
	}

	void "Test that the edit action returns the correct model"() {
		when:"The edit action is executed with a null domain"
		controller.edit(null)

		then:"A 404 error is returned"
		response.status == 404

		when:"A domain instance is passed to the edit action"
		populateValidParams(params)
		def craftToken = new CraftToken(params)
		controller.edit(craftToken)

		then:"A model is populated containing the domain instance"
		model.craftTokenInstance == craftToken
	}

	void "Test the update action performs an update on a valid domain instance"() {
		when:"Update is called for a domain instance that doesn't exist"
		request.contentType = FORM_CONTENT_TYPE
		request.method = 'PUT'
		controller.update(null)

		then:"A 404 error is returned"
		response.redirectedUrl == '/craftToken/index'
		flash.message != null


		when:"An invalid domain instance is passed to the update action"
		response.reset()
		def craftToken = new CraftToken()
		craftToken.validate()
		controller.update(craftToken)

		then:"The edit view is rendered again with the invalid instance"
		view == 'edit'
		model.craftTokenInstance == craftToken

		when:"A valid domain instance is passed to the update action"
		response.reset()
		populateValidParams(params)
		craftToken = new CraftToken(params).save(flush: true)
		controller.update(craftToken)

		then:"A redirect is issues to the show action"
		response.redirectedUrl == "/craftToken/show/$craftToken.id"
		flash.message != null
	}

	void "Test that the delete action deletes an instance if it exists"() {
		when:"The delete action is called for a null instance"
		request.contentType = FORM_CONTENT_TYPE
		request.method = 'DELETE'
		controller.delete(null)

		then:"A 404 is returned"
		response.redirectedUrl == '/craftToken/index'
		flash.message != null

		when:"A domain instance is created"
		response.reset()
		populateValidParams(params)
		def craftToken = new CraftToken(params).save(flush: true)

		then:"It exists"
		CraftToken.count() == 1

		when:"The domain instance is passed to the delete action"
		controller.delete(craftToken)

		then:"The instance is deleted"
		CraftToken.count() == 0
		response.redirectedUrl == '/craftToken/index'
		flash.message != null
	}
}
