package com.wordcraft.filter

import org.springframework.context.MessageSource

import com.wordcraft.CraftToken
import com.wordcraft.WordCraftsman
import com.wordcraft.utility.Constants

class AuthenticationFilters {
	
	def MessageSource messageSource

    def filters = {
        secureFilter(controller:'*', action:'secure*') {
            before = {
				def authorizationToken = request.getHeader('Authorization')
				if (!authorizationToken) {
					render(contentType:'text/json') {[
						'status': Constants.STATUS_FAILURE,
						'message': messageSource.getMessage('authorization.token.not.found', null, Locale.US)
					]}
				    return false	
				}
				def username = params.username
				if (!username) {
					render(contentType:'text/json') {[
						'status': Constants.STATUS_FAILURE,
						'message': messageSource.getMessage('authorization.username.not.found', null, Locale.US)
					]}
					return false
				}
				
				def wordCraftsman = WordCraftsman.findByUsername(username)
				if (!wordCraftsman) {
					render(contentType:'text/json') {[
						'status': Constants.STATUS_FAILURE,
						'message': messageSource.getMessage('fail.to.get.wordcraftsman', null, Locale.US)
					]}
					return false
				}
				
				def craftToken = CraftToken.findByUsernameAndToken(username, authorizationToken)
				if (!craftToken) {
					render(contentType:'text/json') {[
						'status': Constants.STATUS_FAILURE,
						'message': messageSource.getMessage('authorization.token.not.found', null, Locale.US)
					]}
					return false
				}
				
				return true
				
            }
            after = { Map model ->

            }
            afterView = { Exception e ->

            }
        }
    }
}
