package com.wordcraft

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.DefaultValueLoaderDecorator;

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
		username(blank:true, nullable: true)
		password(nullable: true)
		email(blank:false, email:true, unique: true)
		level(nullable:false)
		estimatedSize(nullable:false)
		craftSettings(nullable:true)
		isFacebook(nullable: false)
	}

	static mapping = {
		level defaultValue: 1 
		isFacebook defaultValue: false
		estimatedSize defaultValue: 500
		craftTests cascade: 'all'
		craftLogs cascade: 'all'
		craftWords cascade: 'all-delete-orphan'
		craftLogs sort: 'eventTime', order: 'desc'
	}

}
