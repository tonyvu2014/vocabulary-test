package com.wordcraft

import grails.transaction.Transactional

import com.wordcraft.CraftWord
import com.wordcraft.WordCraftsman

@Transactional
class CraftWordService {

    def serviceMethod() {

    }
	
	/***
	 * An user mark as word as known by him, needs to save this information into DB
	 * 
	 * @param wordCraftsman the user who initiates this action
	 * @param word the word to be marked as known by the user
	 * @return the word will be added and marked as known
	 */
	def markAsKnown(WordCraftsman wordCraftsman, String word) {
		if (!(word in wordCraftsman.craftWords)) {
			CraftWord craftWord = new CraftWord(word: word)
			craftWord.wordCraftsman = wordCraftsman
			craftWord.save()
			wordCraftsman.addToCraftWords(craftWord)
			wordCraftsman.save(flush:true, failOnError:true)
		}
	}
 	
}
