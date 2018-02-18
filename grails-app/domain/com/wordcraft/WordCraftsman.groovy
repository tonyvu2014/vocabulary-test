package com.wordcraft

import java.util.Date;

class WordCraftsman {

	String username
	String password
	String email
	Integer level = 6
	Integer estimatedSize = 5500//Estimated vocabulary size
	Boolean isFacebook = false //If this is a Facebook account
	Date joinedDate = new Date()

	CraftSettings craftSettings

	static hasMany = [craftTests:CraftTest, craftLogs:CraftLog, craftWords: CraftWord]

	static constraints = {
		username(blank:true, nullable: true)
		password(nullable: true)
		email(blank:false, email:true, unique: true)
		level(nullable:false)
		estimatedSize(nullable:false)
		craftSettings(nullable:true)
		isFacebook(nullable: false)
		joinedDate(nullable: true)
	}

	static mapping = {
		level defaultValue: 6
		isFacebook defaultValue: false
		estimatedSize defaultValue: 5500
		craftTests cascade: 'all'
		craftLogs cascade: 'all'
		craftWords cascade: 'all-delete-orphan'
		craftLogs sort: 'eventTime', order: 'desc'
	}

}
