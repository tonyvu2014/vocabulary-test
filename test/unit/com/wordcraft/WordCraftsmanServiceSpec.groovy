package com.wordcraft

import grails.test.GrailsMock
import grails.test.mixin.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(WordCraftsmanService)
class WordCraftsmanServiceSpec extends Specification {

    def setup() {
		GrailsMock mockWordCraftsman = mockFor(WordCraftsman)
		mockWordCraftsman.demand.static.findByUsernameAndPassword{
			String username, String password -> 
			if (username == 'testUser' && password == 'testPass') {
			    new WordCraftsman(username: 'testUser', password: 'testPass', email: 'abc@test.mail')
			} else {
			    null
			}
		}
    }

    def cleanup() {
    }

    void "test findPrincipal() success"() {
		given:
		    def wordCraftsman = service.findPrincipal('testUser', 'testPass')
		expect:
		    wordCraftsman.class == WordCraftsman
			wordCraftsman.username == 'testUser'
    }
	
	void "test findPrincipal() fail"() {
		given:
			def wordCraftsman = service.findPrincipal('testUser', 'secrectPass')
		expect:
			wordCraftsman == null
	}
}
