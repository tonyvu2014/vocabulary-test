package com.wordcraft

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class WebUserWebRole implements Serializable {

	private static final long serialVersionUID = 1

	WebUser webUser
	WebRole webRole

	WebUserWebRole(WebUser u, WebRole r) {
		this()
		webUser = u
		webRole = r
	}

	@Override
	boolean equals(other) {
		if (!(other instanceof WebUserWebRole)) {
			return false
		}

		other.webUser?.id == webUser?.id && other.webRole?.id == webRole?.id
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (webUser) builder.append(webUser.id)
		if (webRole) builder.append(webRole.id)
		builder.toHashCode()
	}

	static WebUserWebRole get(long webUserId, long webRoleId) {
		criteriaFor(webUserId, webRoleId).get()
	}

	static boolean exists(long webUserId, long webRoleId) {
		criteriaFor(webUserId, webRoleId).count()
	}

	private static DetachedCriteria criteriaFor(long webUserId, long webRoleId) {
		WebUserWebRole.where {
			webUser == WebUser.load(webUserId) &&
			webRole == WebRole.load(webRoleId)
		}
	}

	static WebUserWebRole create(WebUser webUser, WebRole webRole, boolean flush = false) {
		def instance = new WebUserWebRole(webUser: webUser, webRole: webRole)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(WebUser u, WebRole r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = WebUserWebRole.where { webUser == u && webRole == r }.deleteAll()

		if (flush) { WebUserWebRole.withSession { it.flush() } }

		rowCount
	}

	static void removeAll(WebUser u, boolean flush = false) {
		if (u == null) return

		WebUserWebRole.where { webUser == u }.deleteAll()

		if (flush) { WebUserWebRole.withSession { it.flush() } }
	}

	static void removeAll(WebRole r, boolean flush = false) {
		if (r == null) return

		WebUserWebRole.where { webRole == r }.deleteAll()

		if (flush) { WebUserWebRole.withSession { it.flush() } }
	}

	static constraints = {
		webRole validator: { WebRole r, WebUserWebRole ur ->
			if (ur.webUser == null || ur.webUser.id == null) return
			boolean existing = false
			WebUserWebRole.withNewSession {
				existing = WebUserWebRole.exists(ur.webUser.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['webUser', 'webRole']
		version false
	}
}
