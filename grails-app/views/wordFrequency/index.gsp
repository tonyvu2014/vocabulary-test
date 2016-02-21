
<%@ page import="com.wordcraft.WordFrequency" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'wordFrequency.label', default: 'WordFrequency')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-wordFrequency" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-wordFrequency" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="rank" title="${message(code: 'wordFrequency.rank.label', default: 'Rank')}" />
					
						<g:sortableColumn property="word" title="${message(code: 'wordFrequency.word.label', default: 'Word')}" />
					
						<g:sortableColumn property="pos" title="${message(code: 'wordFrequency.pos.label', default: 'Pos')}" />
					
						<g:sortableColumn property="frequency" title="${message(code: 'wordFrequency.frequency.label', default: 'Frequency')}" />
					
						<g:sortableColumn property="dispersion" title="${message(code: 'wordFrequency.dispersion.label', default: 'Dispersion')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${wordFrequencyInstanceList}" status="i" var="wordFrequencyInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${wordFrequencyInstance.id}">${fieldValue(bean: wordFrequencyInstance, field: "rank")}</g:link></td>
					
						<td>${fieldValue(bean: wordFrequencyInstance, field: "word")}</td>
					
						<td>${fieldValue(bean: wordFrequencyInstance, field: "pos")}</td>
					
						<td>${fieldValue(bean: wordFrequencyInstance, field: "frequency")}</td>
					
						<td>${fieldValue(bean: wordFrequencyInstance, field: "dispersion")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${wordFrequencyInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
