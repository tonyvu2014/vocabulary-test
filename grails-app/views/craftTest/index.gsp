
<%@ page import="com.wordcraft.CraftTest" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftTest.label', default: 'CraftTest')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-craftTest" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-craftTest" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="testTime" title="${message(code: 'craftTest.testTime.label', default: 'Test Time')}" />
					
						<g:sortableColumn property="resultSize" title="${message(code: 'craftTest.resultSize.label', default: 'Result Size')}" />
					
						<th><g:message code="craftTest.wordCraftsman.label" default="Word Craftsman" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${craftTestInstanceList}" status="i" var="craftTestInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${craftTestInstance.id}">${fieldValue(bean: craftTestInstance, field: "testTime")}</g:link></td>
					
						<td>${fieldValue(bean: craftTestInstance, field: "resultSize")}</td>
					
						<td>${fieldValue(bean: craftTestInstance, field: "wordCraftsman")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${craftTestInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
