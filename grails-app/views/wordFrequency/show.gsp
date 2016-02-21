
<%@ page import="com.wordcraft.WordFrequency" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'wordFrequency.label', default: 'WordFrequency')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-wordFrequency" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-wordFrequency" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list wordFrequency">
			
				<g:if test="${wordFrequencyInstance?.rank}">
				<li class="fieldcontain">
					<span id="rank-label" class="property-label"><g:message code="wordFrequency.rank.label" default="Rank" /></span>
					
						<span class="property-value" aria-labelledby="rank-label"><g:fieldValue bean="${wordFrequencyInstance}" field="rank"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${wordFrequencyInstance?.word}">
				<li class="fieldcontain">
					<span id="word-label" class="property-label"><g:message code="wordFrequency.word.label" default="Word" /></span>
					
						<span class="property-value" aria-labelledby="word-label"><g:fieldValue bean="${wordFrequencyInstance}" field="word"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${wordFrequencyInstance?.pos}">
				<li class="fieldcontain">
					<span id="pos-label" class="property-label"><g:message code="wordFrequency.pos.label" default="Pos" /></span>
					
						<span class="property-value" aria-labelledby="pos-label"><g:fieldValue bean="${wordFrequencyInstance}" field="pos"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${wordFrequencyInstance?.frequency}">
				<li class="fieldcontain">
					<span id="frequency-label" class="property-label"><g:message code="wordFrequency.frequency.label" default="Frequency" /></span>
					
						<span class="property-value" aria-labelledby="frequency-label"><g:fieldValue bean="${wordFrequencyInstance}" field="frequency"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${wordFrequencyInstance?.dispersion}">
				<li class="fieldcontain">
					<span id="dispersion-label" class="property-label"><g:message code="wordFrequency.dispersion.label" default="Dispersion" /></span>
					
						<span class="property-value" aria-labelledby="dispersion-label"><g:fieldValue bean="${wordFrequencyInstance}" field="dispersion"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:wordFrequencyInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${wordFrequencyInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
