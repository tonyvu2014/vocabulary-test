
<%@ page import="com.wordcraft.CraftLog" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftLog.label', default: 'CraftLog')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-craftLog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-craftLog" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list craftLog">
			
				<g:if test="${craftLogInstance?.eventTime}">
				<li class="fieldcontain">
					<span id="eventTime-label" class="property-label"><g:message code="craftLog.eventTime.label" default="Event Time" /></span>
					
						<span class="property-value" aria-labelledby="eventTime-label"><g:formatDate date="${craftLogInstance?.eventTime}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftLogInstance?.eventType}">
				<li class="fieldcontain">
					<span id="eventType-label" class="property-label"><g:message code="craftLog.eventType.label" default="Event Type" /></span>
					
						<span class="property-value" aria-labelledby="eventType-label"><g:fieldValue bean="${craftLogInstance}" field="eventType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftLogInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="craftLog.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${craftLogInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftLogInstance?.wordCraftsman}">
				<li class="fieldcontain">
					<span id="wordCraftsman-label" class="property-label"><g:message code="craftLog.wordCraftsman.label" default="Word Craftsman" /></span>
					
						<span class="property-value" aria-labelledby="wordCraftsman-label"><g:link controller="wordCraftsman" action="show" id="${craftLogInstance?.wordCraftsman?.id}">${craftLogInstance?.wordCraftsman?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:craftLogInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${craftLogInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
