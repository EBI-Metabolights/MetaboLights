<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form name="accountForm" action="createNewAccount" method="post" commandName="metabolightsUser">

    <div class="grid_24">
       	<h3><spring:message	code="msg.newAccount" /></h3>
    </div>
	
	<div class="grid_24">
		<div class="grid_6 alpha"><spring:message code="label.email" />*:</div>
		<div class="grid_18 omega">
			<form:input path="email" maxlength="255" size="40" />
			<span class="error">
				<form:errors path="email" />
				<form:errors path="userName" />
				<c:if test="${not empty duplicateEmailAddress}"><c:out value="${duplicateEmailAddress}" /></c:if>
			</span>
        </div>
	</div>

	<jsp:include page="accountFormFields.jsp" />
	<p/>
	<div class="grid_24">
		<div class="grid_6 alpha"><a href="http://www.ebi.ac.uk/Information/termsofuse.html"><spring:message code="msg.T&CAcceptance" /></a></div>
		<div class="grid_18 omega">
			<input type="checkbox" id="TandC"/>
			<span class="error" id="TandCerror"/>
		</div>
	</div>
	
	<div class="grid_24">
		<div class="grid_18 prefix_6 alpha omega">
			<input name="submit" type="submit" class="submit" value="<spring:message code="label.create"/>">		
			<input name="cancel" type="button" class="submit cancel" value="<spring:message code="label.cancel"/>" onclick="location.href='index'">
         </div>
	</div>

	<div class="grid_24">
		<br/>
		<strong><spring:message code="msg.starRequired"/></strong>
		<br/>
</form:form>


<script>
	$("#metabolightsUser").submit(function() {
        	return validate();
    });
    
    function validate(){
    	$TandCerror = $("#TandCerror")[0];
    	$TandCerror.innerText = "";
    	result = $("#TandC").is(':checked');
    	
    	if (!result){
    		$TandCerror.innerText = '<spring:message code="msg.T&Cinvalid"/>';
    	}
    	return result;
    }
</script>

<script type="text/javascript" language="javascript">
	document.accountForm.email.focus();
</script>

<c:if test="${not empty message}">
	<span class="error"> <c:out value="${message}" /></span>
    <br/>
</c:if>