package com.wordcraft

import com.wordcraft.CraftWordService;
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CraftWordService)
@Mock([WordCraftsman, CraftWord])
class CraftWordServiceSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "test markWordAsKnown()"() {
		given:
		def wordCraftsman = new WordCraftsman(username:"testUsername", "password":"testPass", email: 'test@email.com')
		service.markAsKnown(wordCraftsman, 'the')
		def knownWords = CraftWord.findByWordCraftsman(wordCraftsman).collect{it.word}
		expect:
		'the' in knownWords
	}
}
