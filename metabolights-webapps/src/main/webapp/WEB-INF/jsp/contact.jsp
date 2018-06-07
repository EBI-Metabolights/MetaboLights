<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

    <%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 4/3/13 1:37 PM
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
		<div class="col-md-6 col-md-offset-3">
			<div class="ml--loginContainer">
				<div class="ml-loginpanelhead">
					<h3>Feedback</h3>
					<p><spring:message code="label.contact" /></p>
				</div>
				<div class="ml-loginpanelbody">
					<form:form name="emailForm" action="contactUsAlert" method="post" commandName="contactValidation">
						<div class="form-group">
							<label><spring:message code="label.firstName" /> *</label>
							<form:input class="form-control" path="firstName" maxlength="255" size="40" />
							<span class="error"><form:errors path="firstName" /></span>
						</div>
						<div class="form-group">
							<label><spring:message code="label.lastName" /> *</label>
							<form:input class="form-control" path="lastName" maxlength="255" size="40" />
							<span class="error"><form:errors path="lastName" /></span>
						</div>
						<div class="form-group">
							<label><spring:message code="label.email" /> *</label>
							<form:input path="emailAddress" class="form-control"  maxlength="255" size="40" />
							<span class="error"><form:errors path="emailAddress" /></span>
						</div>
						<div class="form-group">
							<label><spring:message code="label.affili" /></label>
							<form:input path="affiliation" class="form-control"  maxlength="255" size="40" />
						</div>
						<div class="form-group">
							<label><spring:message code="label.affiliUrl" /></label>
							<form:input path="affiliationUrl" class="form-control"  maxlength="4000" size="66" />
						</div>
						<div class="form-group">
							<label><spring:message code="label.message" /> *</label>
							<form:textarea path="message" class="form-control" rows="5" cols="50" />
							<span class="error"><form:errors path="message" /></span>
						</div>
						<div class="form-group">
							<input name="submit" type="submit" class='submit btn btn-primary' value="<spring:message code="label.submit"/>" />
							<input name="cancel" type="button" class='submit cancel btn btn-default' value="<spring:message code="label.cancel"/>" onclick="location.href='index'"/>
						</div>
					</form:form>

				</div>
			</div>
			<p><strong><spring:message code="msg.starRequired"/></strong></p>
			<p><spring:message code="label.ebiContact"/></p>
			<p><spring:message code="label.sourceforge"/></p>
			<c:if test="${not empty message}">
				  <span class="error">
					  <c:out value="${message}" />
				  </span>
			</c:if>
		</div>
	</div>




<script type="text/javascript" language="javascript">
	document.emailForm.email.focus();
</script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

