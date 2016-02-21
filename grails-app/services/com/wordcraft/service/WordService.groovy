package com.wordcraft.service

import grails.transaction.Transactional
import com.wordcraft.WordFrequency
import com.wordcraft.utility.Constants

@Transactional
class WordService {

    def getRandomWordFromLevel(int level) {
		assert level>=1
        fromRank = Constants.WORD_PER_LEVEL*(level-1) + 1
		toRank = fromRank + Constants.WORD_PER_LEVEL - 1
		assert toRank<=Constants.MAX_WORD
		
		return getRandomWord(fromRank, toRank)
    }
	
	def getRandomWord(int fromRank, int toRank) {
		assert fromRank<=toRank
		assert toRank<=Constants.MAX_WORD
		Random random = new Random()
		def randomRank = random.nextInt(toRank - fromRank) + fromRank
		
		return WordFrequency.findByRank(randomRank)
	}
}
