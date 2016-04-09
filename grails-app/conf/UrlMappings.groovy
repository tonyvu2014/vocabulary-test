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
		"/api/craftWord/markAsKnown/$username/$word"(controller:"craftWord", action: "markAsKnown")
		"/api/token/hasToken/$username/$token"(controller:"craftToken", action: "hasToken")
		"/api/token/generate/$username"(controller:"craftToken", action: "generate")
		"/api/wordCraftsman/login/$username/$password"(controller:"wordCraftsman", action: "login")
		
	}
}
