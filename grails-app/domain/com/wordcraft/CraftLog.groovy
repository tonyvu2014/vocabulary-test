package com.wordcraft

import com.wordcraft.utility.EventType

class CraftLog {
	
	Date eventTime
	EventType eventType
	String description
	
	static belongsTo = [wordCraftsman: WordCraftsman]

    static constraints = {
		eventTime(nullable:false)
		eventType(nullable:false)
		description(blank:false, nullable:false)
		wordCraftsman(nullable:false)
    }
}
