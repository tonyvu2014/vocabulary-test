package com.wordcraft

//Words which have been marked as KNOWN by a wordcraftsman
class CraftWord {
	
	String word
	
	static belongsTo = [wordCraftsman: WordCraftsman]

    static constraints = {
		word(nullable:false, blank:false)
    }
}
