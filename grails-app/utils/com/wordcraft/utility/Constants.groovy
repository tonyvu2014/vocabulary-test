package com.wordcraft.utility

@Singleton
class Constants {

	//Status
	public static final STATUS_SUCCESS = 1
	public static final STATUS_FAILURE = 0

	//Token length
	public static final TOKEN_LENGTH = 20

	//Password length
	public static final PASS_LENGTH = 6

	//How many words per level
	public static final WORD_PER_LEVEL = 1000

	//Number of word in DB
	public static final MAX_WORD = 60000

	//Default level when users just join
	public static final DEFAULT_LEVEL = 5

	// Lowest possible level
	public static final MIN_LEVEL = 1

	//Highest possible level
	public static final MAX_LEVEL = 60

	//Maximum number of word to be used in test
	public static final MAX_TESTED_WORD = 20

	//Maximum number of words marked as known by testers before the test terminates
	public static final MAX_LEVEL_KNOWN_WORD = 7

	//How many words per step while testing in a level
	public static final STEP_IN_LEVEL = 100

	//Maximum number of words can be fetched each time
	public static final MAX_WORDS_PER_TIME = 10

	//Maximum attempts to get a word factor
	public static final MAX_LEVEL_ATTEMPTS = 50

	//Maximum number of history entries to show
	public static final MAX_LOG_ENTRIES = 50

	//Notification date format
	public static final NOTIFICATION_DATE_FORMAT = "yyyy-MM-dd"

	//Boundary for selecting notification to run (in minutes)
	public static final BOUNDARY_VALUE = 3

	//Firebase Cloud Messaging Server Key
	public static final ClOUD_MESSAGE_SERVER_KEY = "AAAA-YOH7RI:APA91bECLxVhHKKqCVgFe1tYUftG_vNf8YT9wcRLhFedzfbufWcSzuOdQj2be2vOex81VLNfAicenCJaYhzxAkq6_rmI_VWa_ckBOigRl6m4BYZzyeQBaYlcAIJPrWf_lvmeUICbLQA9"

	//Wordcraft uri
	public static final WORDCRAFT_URI = 'https://www.wordcraft.info'

	//Firebase Push Notification URL
	public static final FIREBASE_NOTIFICATION_URL = 'https://fcm.googleapis.com/fcm/send'
}
