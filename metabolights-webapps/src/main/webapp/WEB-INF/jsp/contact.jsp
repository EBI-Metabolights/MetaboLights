<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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

<form:form name="emailForm" action="contactUsAlert" method="post" commandName="contactValidation">
        
        <div class="grid_24">
        	<h2><spring:message code="label.contact" /></h2>
        </div>

        <div class="grid_24">
			<div class="grid_6 alpha">
				<p><spring:message code="label.firstName" />:</p>
			</div>
			<div class="grid_8">
				<form:input path="firstName" maxlength="255" size="40" />
			</div>
			<div class="grid_8">
				(*)&nbsp;<span class="error"><form:errors path="firstName" /></span>
			</div>
		</div>

        <div class="grid_24">
			<div class="grid_6 alpha">
				<p><spring:message code="label.lastName" />:</p>
			</div>
			<div class="grid_8">
				<form:input path="lastName"  maxlength="255" size="40" />
			</div>
			<div class="grid_8">
				(*)&nbsp;<span class="error"><form:errors path="lastName" /></span>
			</div>
		</div>

        <div class="grid_24">
			<div class="grid_6 alpha">
				<p><spring:message code="label.email" />:</p>
			</div>
			<div class="grid_8">
				<form:input path="emailAddress" maxlength="255" size="40" />
			</div>
			<div class="grid_8">
				(*)&nbsp;<span class="error"><form:errors path="emailAddress" /></span>
			</div>
		</div>

        <div class="grid_24">
			<div class="grid_6 alpha">
				<p><spring:message code="label.affili" />:</p>
			</div>
			<div class="grid_8">
				<form:input path="affiliation"  maxlength="255" size="40" />
			</div>
		</div>

        <div class="grid_24">
			<div class="grid_6 alpha">
				<p><spring:message code="label.affiliUrl" />:</p>
			</div>
			<div class="grid_8">
				<form:input path="affiliationUrl"  maxlength="4000" size="66" />
			</div>
		</div>

       <div class="grid_24">
			<div class="grid_6 alpha">
				<p><spring:message code="label.message" />:</p>
			</div>
			<div class="grid_8">
				<form:textarea path="message" rows="5" cols="50" />
			</div>
			<div class="grid_8">
				(*)&nbsp;<span class="error"><form:errors path="message" /></span>
			</div>
		</div>

		<div class="grid_24">
			<div class="grid_6 alpha">&nbsp;</div>
			<div class="grid_18">
				<input name="submit" type="submit" class='submit' value="<spring:message code="label.submit"/>" />
				<input name="cancel" type="button" class='submit cancel' value="<spring:message code="label.cancel"/>" onclick="location.href='index'"/>
			</div>
		</div>

		<div class="grid_24">
			<br/>
			<p><strong><spring:message code="msg.starRequired"/></strong></p>
		</div>

        </p>
        <div class="grid_24">
            <p><spring:message code="label.ebiContact"/></p>
        </div>

        </p>

		<div class="grid_24">
			<p><spring:message code="label.sourceforge"/></p>
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
   document.emailForm.email.focus();
</script>
