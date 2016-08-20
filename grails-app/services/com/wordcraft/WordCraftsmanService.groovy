package com.wordcraft

import grails.transaction.Transactional

@Transactional
class WordCraftsmanService {

	def findPrincipal(String email, String password) {
		return WordCraftsman.findByEmailAndPassword(email, password)
	}
}
