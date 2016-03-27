<%@ page import="com.wordcraft.CraftWord" %>



<div class="fieldcontain ${hasErrors(bean: craftWordInstance, field: 'word', 'error')} required">
	<label for="word">
		<g:message code="craftWord.word.label" default="Word" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="word" required="" value="${craftWordInstance?.word}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: craftWordInstance, field: 'wordCraftsman', 'error')} required">
	<label for="wordCraftsman">
		<g:message code="craftWord.wordCraftsman.label" default="Word Craftsman" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="wordCraftsman" name="wordCraftsman.id" from="${com.wordcraft.WordCraftsman.list()}" optionKey="id" required="" value="${craftWordInstance?.wordCraftsman?.id}" class="many-to-one"/>

</div>

