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

