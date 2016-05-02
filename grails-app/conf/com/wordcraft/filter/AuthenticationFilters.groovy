package com.wordcraft.filter

import com.wordcraft.CraftToken
import com.wordcraft.utility.Constants
import org.springframework.context.MessageSource

class AuthenticationFilters {
	
	def MessageSource messageSource

    def filters = {
        secure(controller:'*', action:'secure*') {
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
