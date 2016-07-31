<%@ page import="com.wordcraft.WordCraftsman" %>



<div class="fieldcontain ${hasErrors(bean: wordCraftsmanInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="wordCraftsman.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${wordCraftsmanInstance?.username}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: wordCraftsmanInstance, field: 'password', 'error')} ">
	<label for="password">
		<g:message code="wordCraftsman.password.label" default="Password" />
		
	</label>
	<g:textField name="password" value="${wordCraftsmanInstance?.password}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: wordCraftsmanInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="wordCraftsman.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="email" required="" value="${wordCraftsmanInstance?.email}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: wordCraftsmanInstance, field: 'level', 'error')} ">
	<label for="level">
		<g:message code="wordCraftsman.level.label" default="Level" />
		
	</label>
	<g:field name="level" type="number" value="${wordCraftsmanInstance.level}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: wordCraftsmanInstance, field: 'estimatedSize', 'error')} ">
	<label for="estimatedSize">
		<g:message code="wordCraftsman.estimatedSize.label" default="Estimated Size" />
		
	</label>
	<g:field name="estimatedSize" type="number" value="${wordCraftsmanInstance.estimatedSize}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: wordCraftsmanInstance, field: 'craftSettings', 'error')} ">
	<label for="craftSettings">
		<g:message code="wordCraftsman.craftSettings.label" default="Craft Settings" />
		
	</label>
	<g:select id="craftSettings" name="craftSettings.id" from="${com.wordcraft.CraftSettings.list()}" optionKey="id" value="${wordCraftsmanInstance?.craftSettings?.id}" class="many-to-one" noSelection="['null': '']"/>

</div>

<div class="fieldcontain ${hasErrors(bean: wordCraftsmanInstance, field: 'isFacebook', 'error')} ">
	<label for="isFacebook">
		<g:message code="wordCraftsman.isFacebook.label" default="Is Facebook" />
		
	</label>
	<g:checkBox name="isFacebook" value="${wordCraftsmanInstance?.isFacebook}" />

</div>

<div class="fieldcontain ${hasErrors(bean: wordCraftsmanInstance, field: 'craftLogs', 'error')} ">
	<label for="craftLogs">
		<g:message code="wordCraftsman.craftLogs.label" default="Craft Logs" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${wordCraftsmanInstance?.craftLogs?}" var="c">
    <li><g:link controller="craftLog" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="craftLog" action="create" params="['wordCraftsman.id': wordCraftsmanInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'craftLog.label', default: 'CraftLog')])}</g:link>
</li>
</ul>


</div>

<div class="fieldcontain ${hasErrors(bean: wordCraftsmanInstance, field: 'craftTests', 'error')} ">
	<label for="craftTests">
		<g:message code="wordCraftsman.craftTests.label" default="Craft Tests" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${wordCraftsmanInstance?.craftTests?}" var="c">
    <li><g:link controller="craftTest" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="craftTest" action="create" params="['wordCraftsman.id': wordCraftsmanInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'craftTest.label', default: 'CraftTest')])}</g:link>
</li>
</ul>


</div>

<div class="fieldcontain ${hasErrors(bean: wordCraftsmanInstance, field: 'craftWords', 'error')} ">
	<label for="craftWords">
		<g:message code="wordCraftsman.craftWords.label" default="Craft Words" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${wordCraftsmanInstance?.craftWords?}" var="c">
    <li><g:link controller="craftWord" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="craftWord" action="create" params="['wordCraftsman.id': wordCraftsmanInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'craftWord.label', default: 'CraftWord')])}</g:link>
</li>
</ul>


</div>

