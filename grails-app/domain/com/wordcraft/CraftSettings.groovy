package com.wordcraft

class CraftSettings {
	
	Integer craftLoad //How many words per time
	Integer craftPace //How often do you want to learn new words (day period)
	
	static belongsTo = WordCraftsman

    static constraints = {
        craftLoad(nullable:false)
		craftPace(nullable:false)       
    }
	
	static mapping = {
		craftLoad defaultValue: 3
		craftPace defaultValue: 1
	}
}
