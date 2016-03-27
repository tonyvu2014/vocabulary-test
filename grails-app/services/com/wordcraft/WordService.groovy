package com.wordcraft

import grails.transaction.Transactional
import com.wordcraft.WordFrequency
import com.wordcraft.utility.Constants

@Transactional
class WordService {

	/***
	 * Get a random word from a level
	 * 
	 * @param level
	 * @return a random word belonging to this specific level
	 */
    def getRandomWordFromLevel(int level) {
		assert level>=1
        def fromRank = Constants.WORD_PER_LEVEL*(level-1) + 1
		def toRank = fromRank + Constants.WORD_PER_LEVEL
		assert toRank<=Constants.MAX_WORD+1
		
		return getRandomWord(fromRank, toRank)
    }
	
	/**
	 * Given a minimum rank and a maximum, get a random word from that range
	 * 
	 * @param fromRank - the given minimum rank
	 * @param toRank - the given maximum rank
	 * @return a random word between fromRank(inclusive) and toRank(exclusive)
	 */
	def getRandomWord(int fromRank, int toRank) {
		assert fromRank<toRank
		assert toRank<=Constants.MAX_WORD+1
		Random random = new Random()
		def randomRank = random.nextInt(toRank - fromRank) + fromRank
		
		return WordFrequency.findByRank(randomRank)
	}
}
