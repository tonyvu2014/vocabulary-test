
<%@ page import="com.wordcraft.WordCraftsman" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'wordCraftsman.label', default: 'WordCraftsman')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-wordCraftsman" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-wordCraftsman" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="username" title="${message(code: 'wordCraftsman.username.label', default: 'Username')}" />
					
						<g:sortableColumn property="password" title="${message(code: 'wordCraftsman.password.label', default: 'Password')}" />
					
						<th><g:message code="wordCraftsman.craftSettings.label" default="Craft Settings" /></th>
					
						<g:sortableColumn property="estimatedSize" title="${message(code: 'wordCraftsman.estimatedSize.label', default: 'Estimated Size')}" />
					
						<g:sortableColumn property="level" title="${message(code: 'wordCraftsman.level.label', default: 'Level')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${wordCraftsmanInstanceList}" status="i" var="wordCraftsmanInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${wordCraftsmanInstance.id}">${fieldValue(bean: wordCraftsmanInstance, field: "username")}</g:link></td>
					
						<td>${fieldValue(bean: wordCraftsmanInstance, field: "password")}</td>
					
						<td>${fieldValue(bean: wordCraftsmanInstance, field: "craftSettings")}</td>
					
						<td>${fieldValue(bean: wordCraftsmanInstance, field: "estimatedSize")}</td>
					
						<td>${fieldValue(bean: wordCraftsmanInstance, field: "level")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${wordCraftsmanInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
