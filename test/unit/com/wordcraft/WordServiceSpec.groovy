package com.wordcraft

import grails.test.GrailsMock
import grails.test.mixin.TestFor
import spock.lang.Specification

import com.wordcraft.utility.Constants


/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(WordService)
class WordServiceSpec extends Specification {

	def setup() {
		GrailsMock mockWordFrequency = mockFor(WordFrequency)
		mockWordFrequency.demand.static.findByRank{Integer rank -> new WordFrequency(rank: rank, word: 'mock')}
	}

	def cleanup() {
	}

	void "test for getWordFromLevel()"(){
		given:
		    def wf = service.getRandomWordFromLevel(2)
		expect:
		    wf.rank>=Constants.WORD_PER_LEVEL+1
		    wf.rank<Constants.WORD_PER_LEVEL*2
	}

	void "test for getRandomWord()"() {
		given:
		   def wf = service.getRandomWord(2645, 4297)
		expect:
			wf.rank>=2645
			wf.rank<4297	
	}
	
}
