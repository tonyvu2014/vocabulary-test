<%@ page import="com.wordcraft.CraftTest" %>



<div class="fieldcontain ${hasErrors(bean: craftTestInstance, field: 'testTime', 'error')} required">
	<label for="testTime">
		<g:message code="craftTest.testTime.label" default="Test Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="testTime" precision="day"  value="${craftTestInstance?.testTime}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: craftTestInstance, field: 'resultSize', 'error')} required">
	<label for="resultSize">
		<g:message code="craftTest.resultSize.label" default="Result Size" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="resultSize" type="number" value="${craftTestInstance.resultSize}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftTestInstance, field: 'wordCraftsman', 'error')} required">
	<label for="wordCraftsman">
		<g:message code="craftTest.wordCraftsman.label" default="Word Craftsman" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="wordCraftsman" name="wordCraftsman.id" from="${com.wordcraft.WordCraftsman.list()}" optionKey="id" required="" value="${craftTestInstance?.wordCraftsman?.id}" class="many-to-one"/>

</div>

