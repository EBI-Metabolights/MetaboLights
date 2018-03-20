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


<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-6 col-md-offset-3">
			<div class="ml--loginContainer">
				<div class="ml-loginpanelhead">
					<h4><spring:message code="msg.pwReset" /></h4>
				</div>
				<div class="ml-loginpanelbody">
					<div class="row">
						<form:form name="resetForm" action="resetPassword" method="post" commandName="emailAddress" >
							<div class="form-group">
								<label><spring:message code="label.email" /></label>

								<form:input class="form-control" path="emailAddress" maxlength="255" size="35" />
								<span class="error">
									<form:errors path="emailAddress" />
								</span>
							</div>
							<div class="form-group">
								<input name="submit" type="submit" class="submit" value="<spring:message code="label.resetPw"/>"/>
								<input name="cancel" type="button" class="submit cancel" value="<spring:message code="label.cancel"/>" onclick="location.href='index'"/>
							</div>
						</form:form>
						<c:if test="${not empty message}">
						  <span class="error">
							  <c:out value="${message}" />
						  </span>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" language="javascript">
   document.resetForm.email.focus();
</script>
