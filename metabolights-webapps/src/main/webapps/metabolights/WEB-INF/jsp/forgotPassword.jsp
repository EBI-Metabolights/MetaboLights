<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="formbox">
    <form:form name="resetForm" action="resetPassword" method="post" commandName="emailAddress" >
        <table cellpadding="5px" cellspacing="0px" width="90%">
            <tr class="formheader">
                <th class="tableheader" colspan="3"><spring:message code="msg.pwReset" /> </th>
            </tr>
            <tr>
                <td colspan='3'>&nbsp;</td>
            </tr>
	        <tr>
	            <td width="15%"><spring:message code="label.email" />:</td>
	            <td width="40%"><form:input path="emailAddress" maxlength="255" size="35" /> </td>
	            <td class="error">&nbsp;<form:errors path="emailAddress" /></td>
	        </tr>
	        <tr>
                <td></td>
	            <td colspan='2'><input name="submit" type="submit" value="<spring:message code="label.resetPw"/>">
                <a href="index"><input type="button" name="cancel" value="<spring:message code="label.cancel"/>" /></a>
	            </td>
	        </tr>
	    </table>
	</form:form>
</div>
<br/>
<br/>
<c:if test="${not empty message}">
  <span class="error">
      <c:out value="${message}" />
  </span>
  <br/>
</c:if>

<script type="text/javascript" language="javascript">
   document.resetForm.email.focus();
</script>
