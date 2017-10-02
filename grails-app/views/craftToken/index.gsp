
<%@ page import="com.wordcraft.CraftToken" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftToken.label', default: 'CraftToken')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-craftToken" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-craftToken" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="email" title="${message(code: 'craftToken.email.label', default: 'Email')}" />
					
						<g:sortableColumn property="token" title="${message(code: 'craftToken.token.label', default: 'Token')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${craftTokenInstanceList}" status="i" var="craftTokenInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${craftTokenInstance.id}">${fieldValue(bean: craftTokenInstance, field: "email")}</g:link></td>
					
						<td>${fieldValue(bean: craftTokenInstance, field: "token")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${craftTokenInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
