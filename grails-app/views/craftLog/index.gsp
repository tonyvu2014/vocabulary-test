
<%@ page import="com.wordcraft.CraftLog" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftLog.label', default: 'CraftLog')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-craftLog" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-craftLog" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="eventTime" title="${message(code: 'craftLog.eventTime.label', default: 'Event Time')}" />
					
						<g:sortableColumn property="eventType" title="${message(code: 'craftLog.eventType.label', default: 'Event Type')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'craftLog.description.label', default: 'Description')}" />
					
						<th><g:message code="craftLog.wordCraftsman.label" default="Word Craftsman" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${craftLogInstanceList}" status="i" var="craftLogInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${craftLogInstance.id}">${fieldValue(bean: craftLogInstance, field: "eventTime")}</g:link></td>
					
						<td>${fieldValue(bean: craftLogInstance, field: "eventType")}</td>
					
						<td>${fieldValue(bean: craftLogInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: craftLogInstance, field: "wordCraftsman")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${craftLogInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
