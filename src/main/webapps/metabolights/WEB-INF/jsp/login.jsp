<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<br/>
<br/>

<c:if test="${not empty param.login_error}">
	<font color="red"> Your login attempt was not successful, try
		again.<br /> Reason: <c:out
			value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />. </font>
</c:if>

<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
	<table cellpadding="3px">
		<tr>
			<td><spring:message code="label.username" />:</td>
			<td><input type='text' name='j_username'/>
			</td>
		</tr>
		<tr>
			<td><spring:message code="label.password" />:</td>
			<td><input type='password' name='j_password'>
			</td>
		</tr>
		<!-- tr>
			<td></td>
			<td><input type="checkbox"
				name="_spring_security_remember_me">&nbsp;<spring:message code="label.rememberme" />
			</td>
		</tr-->
		<tr>
			<td colspan='2'><input name="submit" type="submit"
				value="<spring:message code="label.login"/>">
			</td>
		</tr>
	</table>
</form>

