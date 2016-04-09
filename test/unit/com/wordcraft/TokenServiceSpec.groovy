package com.wordcraft

import grails.test.mixin.*
import spock.lang.Specification

import com.wordcraft.utility.Constants
import com.wordcraft.utility.Utils

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TokenService)
@Mock(CraftToken)
class TokenServiceSpec extends Specification {

    def setup() {
		def craftToken =  new CraftToken(username:'test', token:'testToken')
		craftToken.save()
    }

    def cleanup() {
    }

    void "test hasToken()"() {
		expect:
		    service.hasToken('test', 'testToken') == true
			service.hasToken('test', 'testtoken') == false
    }
	
	void "test generate()"() {
		given:
		    def token = service.generate('test')
		expect:
		    token.size() == Constants.TOKEN_LENGTH
			token.each{
				it in Utils.ALPHANUMERIC
			}
	}
	
	void "test generateUUID()"() {
		given:
		    def uuid = service.generateUUID('test')
		expect:
		    uuid.class == String
	}
	
	void "test removeToken()"() {
		expect:
		    service.removeToken('wonderwoman') == false
			service.removeToken('test') == true
	}
}
