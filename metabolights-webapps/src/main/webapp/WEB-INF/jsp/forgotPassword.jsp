<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<h3><spring:message code="msg.pwReset" /></h3>

    <form:form name="resetForm" action="resetPassword" method="post" commandName="emailAddress" >
		<div class="grid_4 alpha">
			<spring:message code="label.email" />:
		</div>
		<div class="grid_20 omega">
			<form:input path="emailAddress" maxlength="255" size="35" /> 
			<span class="error">
			&nbsp;<form:errors path="emailAddress" />
			</span>
		</div>
		<div class="grid_4 alpha">&nbsp;</div>
		<div class="grid_20 omega">
       		<input name="submit" type="submit" class="submit" value="<spring:message code="label.resetPw"/>"/>
          		<input name="cancel" type="button" class="submit cancel" value="<spring:message code="label.cancel"/>" onclick="location.href='index'"/>
  			</div>
	</form:form>
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
