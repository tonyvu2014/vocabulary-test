<%@ page import="com.wordcraft.CraftSettings" %>



<div class="fieldcontain ${hasErrors(bean: craftSettingsInstance, field: 'craftLoad', 'error')} required">
	<label for="craftLoad">
		<g:message code="craftSettings.craftLoad.label" default="Craft Load" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="craftLoad" type="number" value="${craftSettingsInstance.craftLoad}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftSettingsInstance, field: 'craftPace', 'error')} required">
	<label for="craftPace">
		<g:message code="craftSettings.craftPace.label" default="Craft Pace" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="craftPace" type="number" value="${craftSettingsInstance.craftPace}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftSettingsInstance, field: 'craftHour', 'error')} ">
	<label for="craftHour">
		<g:message code="craftSettings.craftHour.label" default="Craft Hour" />
		
	</label>
	<g:field name="craftHour" type="number" value="${craftSettingsInstance.craftHour}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftSettingsInstance, field: 'craftMinute', 'error')} ">
	<label for="craftMinute">
		<g:message code="craftSettings.craftMinute.label" default="Craft Minute" />
		
	</label>
	<g:field name="craftMinute" type="number" value="${craftSettingsInstance.craftMinute}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftSettingsInstance, field: 'craftNotification', 'error')} ">
	<label for="craftNotification">
		<g:message code="craftSettings.craftNotification.label" default="Craft Notification" />
		
	</label>
	<g:checkBox name="craftNotification" value="${craftSettingsInstance?.craftNotification}" />

</div>

