
<%@ page import="com.wordcraft.CraftWord" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftWord.label', default: 'CraftWord')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-craftWord" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-craftWord" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list craftWord">
			
				<g:if test="${craftWordInstance?.word}">
				<li class="fieldcontain">
					<span id="word-label" class="property-label"><g:message code="craftWord.word.label" default="Word" /></span>
					
						<span class="property-value" aria-labelledby="word-label"><g:fieldValue bean="${craftWordInstance}" field="word"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftWordInstance?.wordCraftsman}">
				<li class="fieldcontain">
					<span id="wordCraftsman-label" class="property-label"><g:message code="craftWord.wordCraftsman.label" default="Word Craftsman" /></span>
					
						<span class="property-value" aria-labelledby="wordCraftsman-label"><g:link controller="wordCraftsman" action="show" id="${craftWordInstance?.wordCraftsman?.id}">${craftWordInstance?.wordCraftsman?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:craftWordInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${craftWordInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
