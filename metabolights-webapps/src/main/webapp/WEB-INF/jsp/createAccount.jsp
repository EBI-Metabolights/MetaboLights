<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 1/25/13 5:17 PM
  ~ Modified by:   conesa
  ~
  ~
  ~ ©, EMBL, European Bioinformatics Institute, 2014.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

<form:form name="accountForm" action="createNewAccount" method="post" commandName="metabolightsUser">

    <div class="grid_24">
       	<h2 class="strapline"><spring:message	code="msg.newAccount" /></h2>
		<div class="specs well">
			<h6><spring:message	code="msg.newAccount.submittersOnly"/> </h6>
		</div>

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