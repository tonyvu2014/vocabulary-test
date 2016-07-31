package com.wordcraft

class WordCraftsman {

	String username
	String password
	String email
	Integer level
	Integer estimatedSize //Estimated vocabulary size
	Boolean isFacebook //If this is a Facebook account

	CraftSettings craftSettings

	static hasMany = [craftTests:CraftTest, craftLogs:CraftLog, craftWords: CraftWord]

	static constraints = {
		username(blank:false, nullable: false)
		password(blank:false, nullable:true, minSize:6)
		email(blank:false, email:true, unique: true)
		level(nullable:true)
		estimatedSize(nullable:true)
		craftSettings(nullable:true)
		isFacebook(nullable: true)
	}

	static mapping = {
		craftTests cascade: 'all'
		craftLogs cascade: 'all'
		craftWords cascade: 'all-delete-orphan'
		craftLogs sort: 'eventTime', order: 'desc'
	}

}
