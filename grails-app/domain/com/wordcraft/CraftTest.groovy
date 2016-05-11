package com.wordcraft

class CraftTest {

	Date testTime
	Integer resultSize //Vocabulary size result

	static belongsTo = [wordCraftsman:WordCraftsman]

	static constraints = {
		testTime(nullable:false)
		resultSize(nullable:false)
		wordCraftsman(nullable:false)
	}
}
