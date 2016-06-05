package com.wordcraft

class WordFrequency {

	Integer rank
	String word
	String pos
	Integer frequency
	Float dispersion

	static constraints = {
		rank(unique: true, nullable: false)
		word(blank: false, nullable: false)
		pos(blank:true, nullable: true)
		frequency(nullable:true)
		dispersion(nullable:true)
	}
}
