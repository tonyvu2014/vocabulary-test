package com.wordcraft

class CraftSettings {
	
	Integer craftLoad //How many words per time
	Integer craftPace //How often do you want to learn new words
	
	static belongsTo = WordCraftsman

    static constraints = {
    }
}
