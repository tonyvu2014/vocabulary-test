
<%@ page import="com.wordcraft.CraftWord" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftWord.label', default: 'CraftWord')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-craftWord" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-craftWord" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="word" title="${message(code: 'craftWord.word.label', default: 'Word')}" />
					
						<th><g:message code="craftWord.wordCraftsman.label" default="Word Craftsman" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${craftWordInstanceList}" status="i" var="craftWordInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${craftWordInstance.id}">${fieldValue(bean: craftWordInstance, field: "word")}</g:link></td>
					
						<td>${fieldValue(bean: craftWordInstance, field: "wordCraftsman")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${craftWordInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
