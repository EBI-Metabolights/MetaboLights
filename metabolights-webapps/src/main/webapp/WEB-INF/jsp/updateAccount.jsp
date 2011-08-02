<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="formbox">
    <form:form name="accountForm" action="updateAccount" method="post" commandName="metabolightsUser">
        
        <form:hidden path="userId" />
        <form:hidden path="userName" />
        <form:hidden path="status" />
        <form:hidden path="email" />
        
        <table cellpadding="5px" cellspacing="0px">
            <tr class="formheader">
                <th class="tableheader" colspan="2"><spring:message
                        code="msg.updateAccount" />
                </th>
                <th><spring:message code="msg.starRequired"/></th>
            </tr>
            <tr>
                <td colspan='3'>&nbsp;</td>
            </tr>
            <tr>
                <td><spring:message code="label.email" />:</td>
                <td><c:out value="${metabolightsUser.email}" /> </td>
                <td></td>
            </tr>

            <jsp:include page="accountFormFields.jsp" />
            <tr>
                <td></td>
                <td height="100px" colspan='2'><input name="submit" type="submit" value="<spring:message code="label.update"/>"> &nbsp;&nbsp;
                <a href="index"><input type="button" name="cancel" value="<spring:message code="label.cancel"/>" /></a>
                </td>
            </tr>
        </table>
    </form:form>

    <script type="text/javascript" language="javascript">
       document.reminderForm.email.focus();
    </script>

    <c:if test="${not empty message}">
        <span class="error"> <c:out value="${message}" /> </span>
        <br>
    </c:if>

</div>