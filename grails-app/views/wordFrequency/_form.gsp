<%@ page import="com.wordcraft.WordFrequency" %>



<div class="fieldcontain ${hasErrors(bean: wordFrequencyInstance, field: 'rank', 'error')} required">
	<label for="rank">
		<g:message code="wordFrequency.rank.label" default="Rank" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="rank" type="number" value="${wordFrequencyInstance.rank}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: wordFrequencyInstance, field: 'word', 'error')} required">
	<label for="word">
		<g:message code="wordFrequency.word.label" default="Word" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="word" required="" value="${wordFrequencyInstance?.word}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: wordFrequencyInstance, field: 'pos', 'error')} required">
	<label for="pos">
		<g:message code="wordFrequency.pos.label" default="Pos" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="pos" required="" value="${wordFrequencyInstance?.pos}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: wordFrequencyInstance, field: 'frequency', 'error')} required">
	<label for="frequency">
		<g:message code="wordFrequency.frequency.label" default="Frequency" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="frequency" type="number" value="${wordFrequencyInstance.frequency}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: wordFrequencyInstance, field: 'dispersion', 'error')} required">
	<label for="dispersion">
		<g:message code="wordFrequency.dispersion.label" default="Dispersion" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="dispersion" value="${fieldValue(bean: wordFrequencyInstance, field: 'dispersion')}" required=""/>

</div>

