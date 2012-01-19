<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="formbox">
	<form:form name="accountForm" action="createNewAccount" method="post"
		commandName="metabolightsUser">
		<table cellpadding="5px" cellspacing="0px">
			<tr class="formheader">
				<th class="text_header plain" colspan="2"><spring:message
						code="msg.newAccount" />
				</th>
				<th><spring:message code="msg.starRequired"/></th>
			</tr>
			<tr>
				<td colspan='3'>&nbsp;</td>
			</tr>

	        <tr>
	            <td><spring:message code="label.email" />:</td>
	            <td><form:input path="email" maxlength="255" size="40" /> </td>
	            <td>(*)&nbsp;
	               <span class="error">
	                  <form:errors path="email" />
	                  <form:errors path="userName" />
                      <c:if test="${not empty duplicateEmailAddress}"><c:out value="${duplicateEmailAddress}" /></c:if>
	              </span>
	            </td>
	        </tr>

			<jsp:include page="accountFormFields.jsp" />

			<tr>
				<td>&nbsp;</td>
                <td colspan="2">
                    <div class='iscell left'>
				        <input type="submit" name="submit" class="multi-line-button main" value="<spring:message code="label.create"/> ">
                    </div>
                    <div class='iscell'>
                        <br/><a href="index"><spring:message code="label.cancel"/></a>
                    </div>
                </td>
			</tr>
		</table>
	</form:form>

	<script type="text/javascript" language="javascript">
	   document.reminderForm.email.focus();
	</script>

    <c:if test="${not empty message}">
        <span class="error"> <c:out value="${message}" /> </span>
        <br/>
    </c:if>

</div>