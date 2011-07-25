<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="formbox">
    <form:form name="emailForm" action="contactUsAlert" method="post" commandName="contactValidation">
        <table>
            <tr class="formheader">
                <th class="tableheader" colspan="3"><spring:message code="label.contact" /> </th>
            </tr>
            <tr>
                <td colspan='3'>&nbsp;</td>
            </tr>
            
	        <tr>
	            <td><spring:message code="label.firstName" />:</td>
	            <td><form:input path="firstName" maxlength="255" size="30" /> </td>
	            <td>(*)&nbsp;<span class="error"><form:errors path="firstName" /></span></td>
	        </tr>
	        
	        <tr>
	            <td><spring:message code="label.lastName" />:</td>
	            <td><form:input path="lastName"  maxlength="255" size="40" /> </td>
	            <td>(*)&nbsp;<span class="error"><form:errors path="lastName" /></span></td>
	        </tr>   
	            
	        <tr>
	            <td width="15%"><spring:message code="label.email" />:</td>
	            <td width="40%"><form:input path="emailAddress" maxlength="255" size="35" /> </td>
	            <td>(*)&nbsp;<span class="error"><form:errors path="emailAddress" /></span></td>
	        </tr>
	        <tr>
	            <td><spring:message code="label.affili" />:</td>
	            <td><form:input path="affiliation"  maxlength="255" size="40" /> </td>
	            <td>&nbsp;</td>
        	</tr>
        	
        	 <tr>
	            <td><spring:message code="label.affiliUrl" />:</td>
	            <td><form:input path="affiliationUrl"  maxlength="4000" size="50" /> </td>
	            <td>&nbsp;</td>
	        </tr>
        	
        	<tr>
	            <td><spring:message code="label.message" />:<br/><br/></td>
	            <td><form:textarea path="message" rows="5" cols="50" /></td>
	            <td>(*)&nbsp;<span class="error"><form:errors path="message" /></span></td>
        	</tr>
        	
	        <tr>
                <td>&nbsp;</td>
	            <td colspan='2'><input name="submit" type="submit" value="<spring:message code="label.submit"/>">
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
   document.emailForm.email.focus();
</script>
