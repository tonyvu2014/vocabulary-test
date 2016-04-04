package com.wordcraft.utility

@Singleton
class Constants {
	
	//Status
	public static final STATUS_SUCCESS = 1
	public static final STATUS_FAILURE = 0
	
	//Token length
	public static final TOKEN_LENGTH = 10
	
	//How many words per level
	public static final WORD_PER_LEVEL = 1000
	
	//Number of word in DB
	public static final MAX_WORD = 5000
	
	//Default level when users just join
	public static final DEFAULT_LEVEL = 3
	
	// Lowest possible level
	public static final MIN_LEVEL = 1
	
	//Highest possible level
	public static final MAX_LEVEL = 5
	
	//Maximum number of word to be used in test
	public static final MAX_TESTED_WORD = 20
	
	//Maximum number of words marked as known as tester before the test terminates
	public static final MAX_LEVEL_KNOWN_WORD = 3
	
	//How many words per step while testing in a level 
	public static final STEP_IN_LEVEL = 100
	
}
