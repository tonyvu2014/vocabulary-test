package com.wordcraft

class WordCraftsman {
	
	String username
	String password
	Integer level
	Integer estimatedSize //Estimated vocabulary size
	
	CraftSettings craftSettings
	
	static hasMany = [craftTests:CraftTest, craftLogs:CraftLog, acquisitionWords: AcquisitionWord]

    static constraints = {
		username(blank:false, nullable: false)
		password(blank:false, nullable:false)
    }
}
