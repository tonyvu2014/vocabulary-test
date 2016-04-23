class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
		
		"/api/word/getWordFromLevel/$level"(controller: "wordFrequency", action: "getWordFromLevel")
		"/api/word/getWordFromRange/$minRank/$maxRank"(controller: "wordFrequency", action: "getWordFromRange")
		"/api/craftWord/markAsKnown"(controller:"craftWord", action: "markAsKnown")
		"/api/token/hasToken/$username/$token"(controller:"craftToken", action: "hasToken")
		"/api/token/generate/$username"(controller:"craftToken", action: "generate")
		"/api/wordCraftsman/login"(controller:"wordCraftsman", action: "login")
		"/api/wordCraftsman/logout"(controller: "wordCraftsman", action: "logout")
		"/api/settings/getSettings/$username"(controller: "craftSettings", action: "get")
		"/api/settings/changeSettings"(controller: "craftSettings", action: "set")
		"/api/log/createHistory"(controller:"craftLog", action:"createHistory")
		"/api/log/viewHistory/$username"(controller:"craftLog", action:"viewHistory")
		"/api/test/createTest"(controller: "craftTest", action: "createTest")
		"/api/wordCraftsman/register"(controller:"wordCraftsman", action: "register")
		"/api/wordCraftsman/change"(controller:"wordCraftsman", action: "change")
		
	}
}
