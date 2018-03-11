package com.wordcraft

class CraftSettings {

	Integer craftLoad = 3 //How many words per time
	Integer craftPace = 1 //How often do you want to learn new words (day period)
	Integer craftHour //Hour of the day that you want to receive notification (0-23)
	Integer craftMinute //Minute of the hour that you want to receive the notification (0, 15, 30, 45)
	Boolean craftNotification = false //Do you want to receive notification?
	
	String craftTimezone
	String craftNotificationToken

	static belongsTo = [wordCraftsman:WordCraftsman]

	static constraints = {
		craftLoad(nullable:false)
		craftPace(nullable:false)
		craftHour(nullable:true)
		craftMinute(nullable:true)
		craftNotification(nullable:false)
		craftTimezone(nullable:true)
		craftNotificationToken(nullable:true)
	}

	static mapping = {
		craftLoad defaultValue: 3
		craftPace defaultValue: 1
		craftNotification defaultValue: false
	}
}
