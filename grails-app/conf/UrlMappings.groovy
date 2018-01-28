class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?(.$format)?"{ constraints { // apply constraints here
			} }

		"/"(view:"/index")
		"500"(view:'/error')

		"/api/word/getWordFromLevel/$level"(controller: "wordFrequency", action: "getWordFromLevel")
		"/api/word/getWordFromRange/$minRank/$maxRank"(controller: "wordFrequency", action: "getWordFromRange")
		"/api/word/getRandomWord"(controller: "wordFrequency", action: "getRandomWord")
		"/api/word/getWordList/$count"(controller: "wordFrequency", action: "secureGetWordList")
		"/api/word/getNextWord/$email"(controller: "wordFrequency", action: "secureGetNextWord")
		"/api/word/getTestWords/$testSize"(controller: "wordFrequency", action: "getTestWords")
		"/api/word/getFinalTestWords"(controller: "wordFrequency", action: "getFinalTestWords")
		"/api/word/getTestEstimation"(controller: "wordFrequency", action: "getTestEstimation")
		"/api/word/getTestResult"(controller: "wordFrequency", action: "getTestResult")
		"/api/craftWord/markAsKnown"(controller:"craftWord", action: "secureMarkAsKnown")
		"/api/token/hasToken/$email/$token"(controller:"craftToken", action: "hasToken")
		"/api/token/generate"(controller:"craftToken", action: "secureGenerate")
		"/api/wordCraftsman/login"(controller:"wordCraftsman", action: "login")
		"/api/wordCraftsman/logout"(controller: "wordCraftsman", action: "secureLogout")
		"/api/settings/getSettings"(controller: "craftSettings", action: "secureGet")
		"/api/settings/changeSettings"(controller: "craftSettings", action: "secureSet")
		"/api/log/createHistory"(controller:"craftLog", action:"secureCreateHistory")
		"/api/log/viewHistory/$email"(controller:"craftLog", action:"secureViewHistory")
		"/api/test/createTest"(controller: "craftTest", action: "secureCreateTest")
		"/api/wordCraftsman/register"(controller:"wordCraftsman", action: "register")
		"/api/wordCraftsman/change"(controller:"wordCraftsman", action: "secureChange")
		"/api/wordCraftsman/forgotPassword"(controller:"wordCraftsman", action:"forgotPassword")
		"/api/wordCraftsman/hasUsername/$username"(controller:"wordCraftsman", action:"hasUsername")
		"/api/wordCraftsman/hasEmail/$email"(controller:"wordCraftsman", action:"hasEmail")
		"/api/wordCraftsman/hasUsernameOrEmail/$username/$email"(controller:"wordCraftsman", action:"hasUsernameOrEmail")
		"/api/wordCraftsman/saveFacebookAccount"(controller:"wordCraftsman", action:"saveFacebookAccount")
		"/api/wordCraftsman/getInfo/$email"(controller:"wordCraftsman", action: "secureGetInfo")
		
	}
}
