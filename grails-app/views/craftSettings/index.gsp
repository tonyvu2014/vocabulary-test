
<%@ page import="com.wordcraft.CraftSettings" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'craftSettings.label', default: 'CraftSettings')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-craftSettings" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-craftSettings" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="craftLoad" title="${message(code: 'craftSettings.craftLoad.label', default: 'Craft Load')}" />
					
						<g:sortableColumn property="craftPace" title="${message(code: 'craftSettings.craftPace.label', default: 'Craft Pace')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${craftSettingsInstanceList}" status="i" var="craftSettingsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${craftSettingsInstance.id}">${fieldValue(bean: craftSettingsInstance, field: "craftLoad")}</g:link></td>
					
						<td>${fieldValue(bean: craftSettingsInstance, field: "craftPace")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${craftSettingsInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
