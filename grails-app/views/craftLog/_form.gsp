<%@ page import="com.wordcraft.CraftLog" %>



<div class="fieldcontain ${hasErrors(bean: craftLogInstance, field: 'eventTime', 'error')} required">
	<label for="eventTime">
		<g:message code="craftLog.eventTime.label" default="Event Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="eventTime" precision="day"  value="${craftLogInstance?.eventTime}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: craftLogInstance, field: 'eventType', 'error')} required">
	<label for="eventType">
		<g:message code="craftLog.eventType.label" default="Event Type" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="eventType" from="${com.wordcraft.utility.EventType?.values()}" keys="${com.wordcraft.utility.EventType.values()*.name()}" required="" value="${craftLogInstance?.eventType?.name()}" />

</div>

<div class="fieldcontain ${hasErrors(bean: craftLogInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="craftLog.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="description" required="" value="${craftLogInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftLogInstance, field: 'wordCraftsman', 'error')} required">
	<label for="wordCraftsman">
		<g:message code="craftLog.wordCraftsman.label" default="Word Craftsman" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="wordCraftsman" name="wordCraftsman.id" from="${com.wordcraft.WordCraftsman.list()}" optionKey="id" required="" value="${craftLogInstance?.wordCraftsman?.id}" class="many-to-one"/>

</div>

