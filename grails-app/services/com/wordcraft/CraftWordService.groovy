package com.wordcraft

import grails.transaction.Transactional

@Transactional
class CraftWordService {

	/***
	 * An user mark as word as known by him, needs to save this information into DB
	 * 
	 * @param wordCraftsman the user who initiates this action
	 * @param word the word to be marked as known by the user
	 * @return the word will be added and marked as known
	 */
	def markAsKnown(WordCraftsman wordCraftsman, String word) {
		if (wordCraftsman.craftWords == null || !wordCraftsman.craftWords.collect{it.word}.contains(word)) {	
			log.info("Adding a new word")
			CraftWord craftWord = new CraftWord(word: word)
			craftWord.wordCraftsman = wordCraftsman
			craftWord.save()
			wordCraftsman.addToCraftWords(craftWord)
			wordCraftsman.save(flush:true, failOnError:true)
		} else {
			log.info("Word is already in the list");
		}
	}
}
