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

		return [
			randomRank,
			WORD_FREQUENCY_LIST.get(randomRank)
		]
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

	def static firstRound() {
		def currentLevel = Constants.DEFAULT_LEVEL
		def testedWord = []
		def levelKnownWord = [:]
		def level = currentLevel
		def reader = System.in.newReader()
		while ((level = isDone(currentLevel, testedWord, levelKnownWord)) < 0) {
			def w = ''
			while (true) {
				w = getRandomWordFromLevel(currentLevel)[1]
				if (!(w in testedWord)) {
					testedWord << w
					break
				}
			}
			print "Do you know this word (y/n)? ${w}:"
			while (true) {
				def ans = reader.readLine()
				if (ans && ans[0].toUpperCase() == 'Y') {
					levelKnownWord[currentLevel] = levelKnownWord.containsKey(currentLevel)?levelKnownWord[currentLevel]+1:1
					currentLevel = currentLevel < Constants.MAX_LEVEL?currentLevel+1:currentLevel
					println "OK. You answered YES."
					break
				}
				if (ans && ans[0].toUpperCase() == 'N') {
					currentLevel = currentLevel > Constants.MIN_LEVEL?currentLevel-1:currentLevel
					println "OK. You answered NO."
					break
				}

				print "Please enter the correct answer (y/n)?:"
			}
		}

		// If level is highest, ask 1 more question to determine the correct level
		if (level == Constants.MAX_LEVEL) {
			def w = ''
			while (true) {
				int lowerRank = Constants.MAX_WORD-Constants.WORD_PER_LEVEL/2+1
				int upperRank = Constants.MAX_WORD
				println "Asking extra question in the range ${lowerRank} to ${upperRank}"
				w = getRandomWord(lowerRank, upperRank)[1]
				if (!(w in testedWord)) {
					testedWord << w
					break
				}
			}
			print "Do you know this word (y/n)? ${w}:"
			while (true) {
				def ans = reader.readLine()
				if (ans && ans[0].toUpperCase() == 'Y') {
					println "OK. You answered YES."
					level += 1
					break
				}
				if (ans && ans[0].toUpperCase() == 'N') {
					println "OK. You answered NO."
					break
				}

				print "Please enter the correct answer (y/n)?:"
			}

		}

		return level
	}

	def static secondRound(int level) {
		assert level<=Constants.MAX_LEVEL
		println "Starting second round..."
		int lowerRank = Constants.WORD_PER_LEVEL*(level-1)+1
		int upperRank = Constants.WORD_PER_LEVEL*level
		println "Testing in the range from ${lowerRank} to ${upperRank}"
		int startRank = lowerRank
		int endRank = startRank + Constants.STEP_IN_LEVEL-1
		def reader = System.in.newReader()
		def knownRankList = []
		def firstRank = null
		while (endRank<=upperRank) {
			def (rank, word) = getRandomWord(startRank, endRank)
			print "Do you know this word (y/n)? ${word}:"
			if (!firstRank) firstRank = rank
			while (true) {
				def ans = reader.readLine()
				if (ans && ans[0].toUpperCase() == 'Y') {
					println "OK. You answered YES."
					knownRankList << rank
					break
				}
				if (ans && ans[0].toUpperCase() == 'N') {
					println "OK. You answered NO."
					break
				}

				print "Please enter the correct answer (y/n)?:"
			}

			startRank += Constants.STEP_IN_LEVEL
			endRank += Constants.STEP_IN_LEVEL
		}

		if (!knownRankList.size()) return firstRank
		return (getMedian(knownRankList) as int)

	}

	def static getMedian(def aList) {
		def listSize = aList.size()
		int midIndex = listSize/2
		def median = listSize % 2 == 0? (aList[midIndex] + aList[midIndex-1])/2:  aList[midIndex]
		return median
	}

	public static void main(String[] args) {
		int estimatedLevel = firstRound()
		println "Estimated level ${estimatedLevel}"
		if (estimatedLevel > Constants.MAX_LEVEL) {
			println "Your estimated vocabulary size is: More than ${Constants.MAX_WORD} words"
		} else {
			def estimatedSize = secondRound(estimatedLevel)
			println "Your estimated vocabulary size is: ${estimatedSize} words"
		}

	}
}
