
<%@ page import="com.wordcraft.WordCraftsman" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'wordCraftsman.label', default: 'WordCraftsman')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-wordCraftsman" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-wordCraftsman" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list wordCraftsman">
			
				<g:if test="${wordCraftsmanInstance?.username}">
				<li class="fieldcontain">
					<span id="username-label" class="property-label"><g:message code="wordCraftsman.username.label" default="Username" /></span>
					
						<span class="property-value" aria-labelledby="username-label"><g:fieldValue bean="${wordCraftsmanInstance}" field="username"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${wordCraftsmanInstance?.password}">
				<li class="fieldcontain">
					<span id="password-label" class="property-label"><g:message code="wordCraftsman.password.label" default="Password" /></span>
					
						<span class="property-value" aria-labelledby="password-label"><g:fieldValue bean="${wordCraftsmanInstance}" field="password"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${wordCraftsmanInstance?.craftLogs}">
				<li class="fieldcontain">
					<span id="craftLogs-label" class="property-label"><g:message code="wordCraftsman.craftLogs.label" default="Craft Logs" /></span>
					
						<g:each in="${wordCraftsmanInstance.craftLogs}" var="c">
						<span class="property-value" aria-labelledby="craftLogs-label"><g:link controller="craftLog" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${wordCraftsmanInstance?.craftSettings}">
				<li class="fieldcontain">
					<span id="craftSettings-label" class="property-label"><g:message code="wordCraftsman.craftSettings.label" default="Craft Settings" /></span>
					
						<span class="property-value" aria-labelledby="craftSettings-label"><g:link controller="craftSettings" action="show" id="${wordCraftsmanInstance?.craftSettings?.id}">${wordCraftsmanInstance?.craftSettings?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${wordCraftsmanInstance?.craftTests}">
				<li class="fieldcontain">
					<span id="craftTests-label" class="property-label"><g:message code="wordCraftsman.craftTests.label" default="Craft Tests" /></span>
					
						<g:each in="${wordCraftsmanInstance.craftTests}" var="c">
						<span class="property-value" aria-labelledby="craftTests-label"><g:link controller="craftTest" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${wordCraftsmanInstance?.craftWords}">
				<li class="fieldcontain">
					<span id="craftWords-label" class="property-label"><g:message code="wordCraftsman.craftWords.label" default="Craft Words" /></span>
					
						<g:each in="${wordCraftsmanInstance.craftWords}" var="c">
						<span class="property-value" aria-labelledby="craftWords-label"><g:link controller="craftWord" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${wordCraftsmanInstance?.estimatedSize}">
				<li class="fieldcontain">
					<span id="estimatedSize-label" class="property-label"><g:message code="wordCraftsman.estimatedSize.label" default="Estimated Size" /></span>
					
						<span class="property-value" aria-labelledby="estimatedSize-label"><g:fieldValue bean="${wordCraftsmanInstance}" field="estimatedSize"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${wordCraftsmanInstance?.level}">
				<li class="fieldcontain">
					<span id="level-label" class="property-label"><g:message code="wordCraftsman.level.label" default="Level" /></span>
					
						<span class="property-value" aria-labelledby="level-label"><g:fieldValue bean="${wordCraftsmanInstance}" field="level"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:wordCraftsmanInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${wordCraftsmanInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
