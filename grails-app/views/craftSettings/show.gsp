
<%@ page import="com.wordcraft.CraftSettings" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftSettings.label', default: 'CraftSettings')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-craftSettings" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-craftSettings" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list craftSettings">
			
				<g:if test="${craftSettingsInstance?.craftLoad}">
				<li class="fieldcontain">
					<span id="craftLoad-label" class="property-label"><g:message code="craftSettings.craftLoad.label" default="Craft Load" /></span>
					
						<span class="property-value" aria-labelledby="craftLoad-label"><g:fieldValue bean="${craftSettingsInstance}" field="craftLoad"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftSettingsInstance?.craftPace}">
				<li class="fieldcontain">
					<span id="craftPace-label" class="property-label"><g:message code="craftSettings.craftPace.label" default="Craft Pace" /></span>
					
						<span class="property-value" aria-labelledby="craftPace-label"><g:fieldValue bean="${craftSettingsInstance}" field="craftPace"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftSettingsInstance?.craftHour}">
				<li class="fieldcontain">
					<span id="craftHour-label" class="property-label"><g:message code="craftSettings.craftHour.label" default="Craft Hour" /></span>
					
						<span class="property-value" aria-labelledby="craftHour-label"><g:fieldValue bean="${craftSettingsInstance}" field="craftHour"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftSettingsInstance?.craftMinute}">
				<li class="fieldcontain">
					<span id="craftMinute-label" class="property-label"><g:message code="craftSettings.craftMinute.label" default="Craft Minute" /></span>
					
						<span class="property-value" aria-labelledby="craftMinute-label"><g:fieldValue bean="${craftSettingsInstance}" field="craftMinute"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftSettingsInstance?.craftNotification}">
				<li class="fieldcontain">
					<span id="craftNotification-label" class="property-label"><g:message code="craftSettings.craftNotification.label" default="Craft Notification" /></span>
					
						<span class="property-value" aria-labelledby="craftNotification-label"><g:formatBoolean boolean="${craftSettingsInstance?.craftNotification}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftSettingsInstance?.craftTimezone}">
				<li class="fieldcontain">
					<span id="craftTimezone-label" class="property-label"><g:message code="craftSettings.craftTimezone.label" default="Craft Timezone" /></span>
					
						<span class="property-value" aria-labelledby="craftTimezone-label"><g:fieldValue bean="${craftSettingsInstance}" field="craftTimezone"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftSettingsInstance?.craftNotificationToken}">
				<li class="fieldcontain">
					<span id="craftNotificationToken-label" class="property-label"><g:message code="craftSettings.craftNotificationToken.label" default="Craft Notification Token" /></span>
					
						<span class="property-value" aria-labelledby="craftNotificationToken-label"><g:fieldValue bean="${craftSettingsInstance}" field="craftNotificationToken"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:craftSettingsInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${craftSettingsInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
