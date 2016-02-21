package com.wordcraft.test

import com.wordcraft.utility.Constants

class WordTest {
	
    def static WORD_FREQUENCY_LIST
	static {
		WORD_FREQUENCY_LIST = [:]
		def scriptDir = System.getProperty("user.dir")
		def filePath = scriptDir + File.separator + "word_frequency.txt"
		def fileText = new File(filePath)
		fileText.eachLine {
			String[] fields = it.split("\\s+")
			def word_rank = fields[0].toInteger()
			def word = fields[1]
			WORD_FREQUENCY_LIST.put(word_rank, word)
		}
		
		println "Loaded word: ${WORD_FREQUENCY_LIST.size()}"
	} 

	/**
	 * Get a word which has rank in a certain level
	 */
	def static getRandomWordFromLevel(int level) {
		assert level>=1
		def fromRank = Constants.WORD_PER_LEVEL*(level-1) + 1
		def toRank = fromRank + Constants.WORD_PER_LEVEL - 1
		assert toRank<=Constants.MAX_WORD
		
		return getRandomWord(fromRank, toRank)
	}
	
	/**
	 * Get a word which has rank in a range
	 */
	def static getRandomWord(int fromRank, int toRank) {
		assert fromRank<=toRank
		assert toRank<=Constants.MAX_WORD
		Random random = new Random()
		def randomRank = random.nextInt(toRank - fromRank) + fromRank
		
		return WORD_FREQUENCY_LIST.get(randomRank)
	}
	
	/**
	 * Test condition to stop the test
	 */
	def static isDone(def currentLevel, def testedWord, def levelKnownWord) {
		assert currentLevel>=1
		def max = levelKnownWord.max{it.value}
		if (max && max.value >= Constants.MAX_LEVEL_KNOWN_WORD) {
			return max.key
		}
		
		if (testedWord.size() >= Constants.MAX_TESTED_WORD) {
			return currentLevel
		}
		
		return -1
	}
	
	public static void main(String[] args) {
		def currentLevel = Constants.DEFAULT_LEVEL
		def testedWord = []
		def levelKnownWord = [:]
		def level = currentLevel
		while ((level = isDone(currentLevel, testedWord, levelKnownWord)) < 0) {
			def w = ''
			while (true) {
				w = getRandomWordFromLevel(currentLevel) 
				if (!(w in testedWord)) {
					testedWord << w
					break
				}
			}
			
			def reader = System.in.newReader()
			print "Do you know this word (y/n)? ${w}:"	
			while (true) {
				def ans = reader.readLine() 
				if (ans[0].toUpperCase() == 'Y') {
					levelKnownWord[currentLevel] = levelKnownWord.containsKey(currentLevel)?levelKnownWord[currentLevel]+1:1
					currentLevel = currentLevel < Constants.MAX_LEVEL?currentLevel+1:currentLevel
					println "OK. You answered YES."
					break
				}		
				if (ans[0].toUpperCase() == 'N') {
					currentLevel = currentLevel > Constants.MIN_LEVEL?currentLevel-1:currentLevel
					println "OK. You answered NO."
					break
				}			
				
				print "Please enter the correct answer (y/n)?:"
			}
		}
		
		def adjustLevel = (testedWord.size() < Constants.MAX_TESTED_WORD && level == 5)?6: level
		def estimatedSize = adjustLevel <=5? ((adjustLevel-1)*Constants.WORD_PER_LEVEL+1) + " to " + adjustLevel*Constants.WORD_PER_LEVEL  : "more than 5000 words"
		print "Your estimated vocabulary size: ${estimatedSize}" 
	}
}
