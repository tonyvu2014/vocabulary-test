package com.wordcraft

//Tokens used for authentication
class CraftToken {

	String username
	String token
	
    static constraints = {
		username(blank:false)
		token(blank:false)
    }
}
