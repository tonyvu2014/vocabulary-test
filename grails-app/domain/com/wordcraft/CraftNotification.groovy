package com.wordcraft

class CraftNotification {
	
	String date
	Integer hour
	Integer minute
	WordCraftsman wordCraftsman 
	String token

  static constraints = {
		date(nullable: false)
		hour(nullable: false)
		minute(nullable: false)
		wordCraftsman(nullable: false)
		token(nullable: false)
  }

}
