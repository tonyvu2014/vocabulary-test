package com.wordcraft

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

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
}
