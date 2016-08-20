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
					log.error("Authentication: empty authorization token")
					render(contentType:'text/json') {
						[
							'status': Constants.STATUS_FAILURE,
							'message': messageSource.getMessage('authorization.token.not.found', null, Locale.US)
						]
					}
					return false
				}
				def email = params.email
				if (!email) {
					log.error("Authentication: empty email")
					render(contentType:'text/json') {
						[
							'status': Constants.STATUS_FAILURE,
							'message': messageSource.getMessage('authorization.email.not.found', null, Locale.US)
						]
					}
					return false
				}

				def wordCraftsman = WordCraftsman.findByEmail(email)
				if (!wordCraftsman) {
					log.error("Authentication: wordcraftsman with ${email} does not exist")
					render(contentType:'text/json') {
						[
							'status': Constants.STATUS_FAILURE,
							'message': messageSource.getMessage('fail.to.get.wordcraftsman', null, Locale.US)
						]
					}
					return false
				}

				def craftToken = CraftToken.findByEmailAndToken(email, authorizationToken)
				if (!craftToken) {
					log.error("Authentication: Failed to authenticate for user with ${email}")
					render(contentType:'text/json') {
						[
							'status': Constants.STATUS_FAILURE,
							'message': messageSource.getMessage('authorization.token.not.found', null, Locale.US)
						]
					}
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
