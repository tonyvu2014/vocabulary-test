<%@ page import="com.wordcraft.CraftToken" %>



<div class="fieldcontain ${hasErrors(bean: craftTokenInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="craftToken.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${craftTokenInstance?.username}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftTokenInstance, field: 'token', 'error')} required">
	<label for="token">
		<g:message code="craftToken.token.label" default="Token" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="token" required="" value="${craftTokenInstance?.token}"/>

</div>

