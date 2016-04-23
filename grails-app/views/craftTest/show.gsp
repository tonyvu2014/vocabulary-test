
<%@ page import="com.wordcraft.CraftTest" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftTest.label', default: 'CraftTest')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-craftTest" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-craftTest" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list craftTest">
			
				<g:if test="${craftTestInstance?.testTime}">
				<li class="fieldcontain">
					<span id="testTime-label" class="property-label"><g:message code="craftTest.testTime.label" default="Test Time" /></span>
					
						<span class="property-value" aria-labelledby="testTime-label"><g:formatDate date="${craftTestInstance?.testTime}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftTestInstance?.resultSize}">
				<li class="fieldcontain">
					<span id="resultSize-label" class="property-label"><g:message code="craftTest.resultSize.label" default="Result Size" /></span>
					
						<span class="property-value" aria-labelledby="resultSize-label"><g:fieldValue bean="${craftTestInstance}" field="resultSize"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${craftTestInstance?.wordCraftsman}">
				<li class="fieldcontain">
					<span id="wordCraftsman-label" class="property-label"><g:message code="craftTest.wordCraftsman.label" default="Word Craftsman" /></span>
					
						<span class="property-value" aria-labelledby="wordCraftsman-label"><g:link controller="wordCraftsman" action="show" id="${craftTestInstance?.wordCraftsman?.id}">${craftTestInstance?.wordCraftsman?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:craftTestInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${craftTestInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
