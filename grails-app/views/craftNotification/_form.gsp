<%@ page import="com.wordcraft.CraftNotification" %>



<div class="fieldcontain ${hasErrors(bean: craftNotificationInstance, field: 'date', 'error')} required">
	<label for="date">
		<g:message code="craftNotification.date.label" default="Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="date" required="" value="${craftNotificationInstance?.date}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftNotificationInstance, field: 'hour', 'error')} required">
	<label for="hour">
		<g:message code="craftNotification.hour.label" default="Hour" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="hour" type="number" value="${craftNotificationInstance.hour}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftNotificationInstance, field: 'minute', 'error')} required">
	<label for="minute">
		<g:message code="craftNotification.minute.label" default="Minute" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="minute" type="number" value="${craftNotificationInstance.minute}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftNotificationInstance, field: 'wordCraftsman', 'error')} required">
	<label for="wordCraftsman">
		<g:message code="craftNotification.wordCraftsman.label" default="Word Craftsman" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="wordCraftsman" name="wordCraftsman.id" from="${com.wordcraft.WordCraftsman.list()}" optionKey="id" required="" value="${craftNotificationInstance?.wordCraftsman?.id}" class="many-to-one"/>

</div>

