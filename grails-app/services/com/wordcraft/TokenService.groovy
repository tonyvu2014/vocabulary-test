package com.wordcraft

import grails.transaction.Transactional

@Transactional
class TokenService {

	/**
	 * Check if a pair of username and token exist
	 * @param username the username
	 * @param token the token
	 * @return true if username+token pair exists in DB, false otherwise
	 */
    def hasToken(String username, String token) {
		
		return CraftToken.findByUsernameAndToken(username, token) != null

    }
}
