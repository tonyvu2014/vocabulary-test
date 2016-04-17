package com.wordcraft

class WordCraftsman {
	
	String username
	String password
	String email
	Integer level
	Integer estimatedSize //Estimated vocabulary size
	
	CraftSettings craftSettings
	
	static hasMany = [craftTests:CraftTest, craftLogs:CraftLog, craftWords: CraftWord]

    static constraints = {
		username(blank:false, nullable: false, unique:true)
		password(blank:false, nullable:false, minSize:6)
		email(blank:false, email:true)
		level(nullable:true)
		estimatedSize(nullable:true)
		craftSettings(nullable:true)
    }
	
	static mapping = {
		craftTests cascade: 'all'
		craftLogs cascade: 'all'
		craftWords cascade: 'all-delete-orphan'
		craftLogs sort: 'eventTime', order: 'desc'
	}
	
}
