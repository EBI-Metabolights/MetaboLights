<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div style="border:1px solid #D5AE60;margin-top:10px;padding-left:20px">

	<c:if test="${not empty message}">
	  <span class="error">
	      <c:out value="${message}" />
	  </span>
      <br/>
	</c:if>
	<br/>
	<b><spring:message code="msg.pwreminder" /></b>
	<br/>
	<br/>
	
	<form:form name="reminderForm" action="sendPasswordReminder" method="post" commandName="emailReminder" >
	    <table cellpadding="3px">
	        <tr>
	            <td><spring:message code="label.email" />:</td>
	            <td><form:input path="emailAddress" /> </td>
	            <td class="error"><form:errors path="emailAddress" /></td>
	        </tr>
	        <tr>
                <td></td>
	            <td colspan='2'><input name="submit" type="submit"
	                value="<spring:message code="label.remind"/>">
	            </td>
	        </tr>
	    </table>
	</form:form>
</div>

<script type="text/javascript" language="javascript">
   document.reminderForm.email.focus();
</script>
