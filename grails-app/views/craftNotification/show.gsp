
<%@ page import="com.wordcraft.CraftNotification" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftNotification.label', default: 'CraftNotification')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-craftNotification" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-craftNotification" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list craftNotification">
			
				<g:if test="${craftNotificationInstance?.date}">
				<li class="fieldcontain">
					<span id="date-label" class="property-label"><g:message code="craftNotification.date.label" default="Date" /></span>
					
						<span class="property-value" aria-labelledby="date-label"><g:fieldValue bean="${craftNotificationInstance}" field="date"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftNotificationInstance?.hour}">
				<li class="fieldcontain">
					<span id="hour-label" class="property-label"><g:message code="craftNotification.hour.label" default="Hour" /></span>
					
						<span class="property-value" aria-labelledby="hour-label"><g:fieldValue bean="${craftNotificationInstance}" field="hour"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftNotificationInstance?.minute}">
				<li class="fieldcontain">
					<span id="minute-label" class="property-label"><g:message code="craftNotification.minute.label" default="Minute" /></span>
					
						<span class="property-value" aria-labelledby="minute-label"><g:fieldValue bean="${craftNotificationInstance}" field="minute"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftNotificationInstance?.wordCraftsman}">
				<li class="fieldcontain">
					<span id="wordCraftsman-label" class="property-label"><g:message code="craftNotification.wordCraftsman.label" default="Word Craftsman" /></span>
					
						<span class="property-value" aria-labelledby="wordCraftsman-label"><g:link controller="wordCraftsman" action="show" id="${craftNotificationInstance?.wordCraftsman?.id}">${craftNotificationInstance?.wordCraftsman?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:craftNotificationInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${craftNotificationInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
