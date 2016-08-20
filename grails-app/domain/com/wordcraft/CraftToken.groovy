package com.wordcraft

//Tokens used for authentication
class CraftToken {

	String email
	String token

	static constraints = {
		email(blank:false, nullable:false)
		token(blank:false, nullable:false)
	}
}
