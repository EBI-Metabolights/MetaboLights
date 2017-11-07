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

	<div class="row">
		<div class="col-md-3">
			&nbsp;
		</div>
		<div class="col-md-6">
			<div class="ml--loginContainer">
				<div class="ml-loginpanelhead">
					<h3>Create an account</h3>
					<p><small>
						<spring:message	code="msg.newAccount.submittersOnly"/>
					</small></p>
				</div>
				<div class="ml-loginpanelbody">
					<div class="row">
						<c:if test="${not empty fromsubmit}">
							<p><strong><spring:message code="msg.submHeader"/></strong></p>
						</c:if>

						<form:form name="accountForm" action="createNewAccount" method="post" commandName="metabolightsUser">
							<div class="form-group">
								<label><spring:message code="label.email" />*</label>
								<form:input class="form-control" path="email" maxlength="255" size="40" />
								<span class="error">
									<form:errors path="email" />
									<form:errors path="userName" />
									<c:if test="${not empty duplicateEmailAddress}"><c:out value="${duplicateEmailAddress}" /></c:if>
								</span>
							</div>
							<c:set var="orcidLinkUrl" value="${orcidLinkUrl}"/>
							<jsp:include page="accountFormFields.jsp" />

						<div class="form-group">
							<label class="checkbox-inline"><input type="checkbox" id="TandC"/><small><a href="http://www.ebi.ac.uk/Information/termsofuse.html"><spring:message code="msg.T&CAcceptance" /></a></small></label><br>
							<span class="error" id="TandCerror"/>
						</div>

						<div class="form-group">
								<input name="submit" type="submit" class="submit btn btn-primary form-control" value="<spring:message code="label.create"/>">
								<%--<input name="cancel" type="button" class="submit cancel" value="<spring:message code="label.cancel"/>" onclick="location.href='index'">--%>
						</div>
						</form:form>
					</div>
				</div>
			</div>
			<small><strong><spring:message code="msg.starRequired"/></strong></small>
		</div>
	</div>



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