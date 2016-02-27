package com.wordcraft

class AcquisitionWord {
	
	Date acquisitionTime
	String word
	
	static belongsTo = [wordCraftsman: WordCraftsman]

    static constraints = {
		acquisitionTime(nullable:false)
		word(nullable:false)
    }
}
