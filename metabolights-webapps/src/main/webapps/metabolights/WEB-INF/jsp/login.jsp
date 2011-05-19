<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<div style="border:1px solid #D5AE60;margin-top:10px;padding-left:20px">
	<br/>
	<b><spring:message code="msg.credentials" /></b>
	<br/>
	
	<c:if test="${not empty param.login_error}">
	  <span class="error">
	    <br> 
	    Your login attempt was not successful, try again.<br/> 
	    Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />. 
	    <br>
	  </span>
	</c:if>
	
	<br>
	
	<table cellpadding="5px">
        <form name="loginForm" action="<c:url value='j_spring_security_check'/>" method="POST">
		<tr>
			<td><spring:message code="label.username" />:</td>
			<td colspan='2'><input type='text' name='j_username'/></td>
		</tr>
		<tr>
			<td><spring:message code="label.password" />:</td>
			<td><input type='password' name='j_password'></td>
            <td><a href="passwordReminder"><spring:message code="label.oopsForgot" /></a></td>
		</tr>

		<!-- tr>
			<td></td>
			<td><input type="checkbox"
				name="_spring_security_remember_me">&nbsp;<spring:message code="label.rememberme" />
			</td>
		</tr-->
		<tr>
            <td></td>
			<td colspan='2'><input name="submit" type="submit"
				value="<spring:message code="label.login"/>">
			</td>
		</tr>
        </form>

        <tr >
            <td valign="top" align="right" style="padding-top:30px">
                 <img src="img/newUser.png"/>
            </td>
            <td colspan='2' valign="top" style="padding-top:30px">
                 <a href="newAccount" ><spring:message code="label.needNewAccount" /></a>
            </td>
        </tr>       

	</table>
</div>


<script type="text/javascript" language="javascript">
   document.loginForm.j_username.focus();
</script>
