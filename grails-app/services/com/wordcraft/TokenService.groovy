package com.wordcraft

import grails.transaction.Transactional

import com.apple.laf.AquaBorder.Default
import com.sun.org.apache.bcel.internal.generic.NEW
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.wordcraft.utility.Constants
import com.wordcraft.utility.Utils

@Transactional
class TokenService {

	/**
	 * Check if a pair of email and token exist
	 * @param email the email
	 * @param token the token
	 * @return true if email+token pair exists in DB, false otherwise
	 */
	def hasToken(String email, String token) {

		return CraftToken.findByEmailAndToken(email, token) != null
	}

	/**
	 * Remove a craft token record for an email
	 * @param email - email provided
	 * @return true if record is deleted successfully, false otherwise
	 */
	def removeToken(String email) {
		def craftToken = CraftToken.findByEmail(email)
		if (!craftToken) {
			return false
		}
		craftToken.delete()
		return true
	}

	/**
	 * Generate a random token with a certain length
	 * @param length length of the token
	 * @return a random token
	 */
	def generate(def email) {
		def token = Utils.generateToken(Constants.TOKEN_LENGTH)

		def craftToken = CraftToken.findByEmail(email)
		if (craftToken) {
			craftToken.token = token
		} else {
			craftToken = new CraftToken(email: email, token: token)
		}
		craftToken.save(flush:true, failOnError:true)

		return token
	}


	/**
	 * Generate a new UUID
	 * @return a new UUID
	 */
	def generateUUID(def email) {
		def uuid =  Utils.generateUUID()

		return saveToken(email, uuid);
	}
	
	/**
	 * Save a token
	 * @param email
	 * @param token
	 * @return
	 */
	def saveToken(def email, def token) {
		def craftToken = CraftToken.findByEmail(email)		
		if (craftToken) {
			craftToken.token
		} else {
			craftToken = new CraftToken(email:email, token:token);
		}
		
		craftToken.save(flush:true, failOnError:true)
		
		return token; 
	}
}
