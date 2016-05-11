class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?(.$format)?"{ constraints { // apply constraints here
			} }

		"/"(view:"/index")
		"500"(view:'/error')

		"/api/word/getWordFromLevel/$level"(controller: "wordFrequency", action: "getWordFromLevel")
		"/api/word/getWordFromRange/$minRank/$maxRank"(controller: "wordFrequency", action: "getWordFromRange")
		"/api/craftWord/markAsKnown"(controller:"craftWord", action: "secureMarkAsKnown")
		"/api/token/hasToken/$username/$token"(controller:"craftToken", action: "hasToken")
		"/api/token/generate"(controller:"craftToken", action: "secureGenerate")
		"/api/wordCraftsman/login"(controller:"wordCraftsman", action: "login")
		"/api/wordCraftsman/logout"(controller: "wordCraftsman", action: "secureLogout")
		"/api/settings/getSettings/$username"(controller: "craftSettings", action: "secureGet")
		"/api/settings/changeSettings"(controller: "craftSettings", action: "secureSet")
		"/api/log/createHistory"(controller:"craftLog", action:"secureCreateHistory")
		"/api/log/viewHistory/$username"(controller:"craftLog", action:"secureViewHistory")
		"/api/test/createTest"(controller: "craftTest", action: "secureCreateTest")
		"/api/wordCraftsman/register"(controller:"wordCraftsman", action: "register")
		"/api/wordCraftsman/change"(controller:"wordCraftsman", action: "secureChange")
	}
}
