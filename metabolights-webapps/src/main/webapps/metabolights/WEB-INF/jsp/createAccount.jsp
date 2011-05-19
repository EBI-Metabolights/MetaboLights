<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="formbox">
	<c:if test="${not empty message}">
		<span class="error"> <c:out value="${message}" /> </span>
		<br>
	</c:if>

	<br /> <br />

	<form:form name="accountForm" action="createNewAccount" method="post"
		commandName="metabolightsUser">
		<table cellpadding="5px" cellspacing="0px">
			<tr class="formheader">
				<th class="tableheader" colspan="2"><spring:message
						code="msg.newAccount" />
				</th>
				<th>(*) indicates required field
				</th>
			</tr>

			<tr>
				<td colspan='3'>&nbsp;</td>
			</tr>

			<jsp:include page="accountFormFields.jsp" />

			<tr>
				<td></td>
				<td colspan='2'><input name="submit" type="submit"
					value="<spring:message code="label.create"/>"></td>
			</tr>
		</table>
	</form:form>

	<script type="text/javascript" language="javascript">
	   document.reminderForm.email.focus();
	</script>

</div>