<%@ page import="com.wordcraft.CraftToken" %>



<div class="fieldcontain ${hasErrors(bean: craftTokenInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="craftToken.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="email" required="" value="${craftTokenInstance?.email}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftTokenInstance, field: 'token', 'error')} required">
	<label for="token">
		<g:message code="craftToken.token.label" default="Token" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="token" required="" value="${craftTokenInstance?.token}"/>

</div>

