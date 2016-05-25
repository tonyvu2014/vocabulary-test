package com.wordcraft

class CraftSettings {

	Integer craftLoad //How many words per time
	Integer craftPace //How often do you want to learn new words (day period)
	Integer craftHour //Hour of the day that you want to receive notification (0-23)
	Integer craftMinute //Minute of the hour that you want to receive the notification (0, 15, 30, 45)
	Boolean craftNotification //Do you want to receive notification?

	static belongsTo = WordCraftsman

	static constraints = {
		craftLoad(nullable:false)
		craftPace(nullable:false)
		craftHour(nullable:true)
		craftMinute(nullable:true)
		craftNotification(nullable:false)
	}

	static mapping = {
		craftLoad defaultValue: 3
		craftPace defaultValue: 1
		craftNotification defaultValue: false
	}
}
