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

<h2 class="strapline"><spring:message code="msg.pwReset" /></h2>

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
