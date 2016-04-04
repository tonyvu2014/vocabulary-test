package com.wordcraft

import grails.transaction.Transactional

import com.wordcraft.utility.Constants
import com.wordcraft.utility.Utils

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
	
	/**
	 * Generate a random token with a certain length
	 * @param length length of the token
	 * @return a random token
	 */
	def generate() {
		return Utils.generateToken(Constants.TOKEN_LENGTH)
	}
}
