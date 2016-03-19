package com.wordcraft

class CraftWord {
	
	String word
	
	static belongsTo = [wordCraftsman: WordCraftsman]

    static constraints = {
		word(nullable:false, blank:false)
    }
}
