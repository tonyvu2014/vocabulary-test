
<%@ page import="com.wordcraft.CraftNotification" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftNotification.label', default: 'CraftNotification')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-craftNotification" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-craftNotification" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="date" title="${message(code: 'craftNotification.date.label', default: 'Date')}" />
					
						<g:sortableColumn property="hour" title="${message(code: 'craftNotification.hour.label', default: 'Hour')}" />
					
						<g:sortableColumn property="minute" title="${message(code: 'craftNotification.minute.label', default: 'Minute')}" />
					
						<th><g:message code="craftNotification.wordCraftsman.label" default="Word Craftsman" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${craftNotificationInstanceList}" status="i" var="craftNotificationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${craftNotificationInstance.id}">${fieldValue(bean: craftNotificationInstance, field: "date")}</g:link></td>
					
						<td>${fieldValue(bean: craftNotificationInstance, field: "hour")}</td>
					
						<td>${fieldValue(bean: craftNotificationInstance, field: "minute")}</td>
					
						<td>${fieldValue(bean: craftNotificationInstance, field: "wordCraftsman")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${craftNotificationInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
