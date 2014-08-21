<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

<div class="grid_24">
	<h2>
		<c:if test="${not empty updated}"><spring:message code="msg.updatedAccount" /></c:if>  <!--  only display on update account -->
	    <strong><c:if test="${empty updated}"><spring:message code="msg.feedbackAccountRequestedHeader" /></c:if></strong><!--  only display on new account creation -->
		<%--: <c:out value="${user.email}"/>--%>
	</h2>
</div>

<div class="grid_24">
    <br/>
</div>

<c:if test="${empty updated}">
    <div class="grid_24">
        <p><spring:message code="msg.step1.accountDetails" /></p>
        <p><spring:message code="msg.step2.accountDetails" /></p>
        <p><spring:message code="msg.contactUsAndRegards" /></p>
    </div>
</c:if> <!--  only display on new account creation -->

<div class="grid_24">
    <br/>
</div>

<div class="grid_24">
	<p><strong><spring:message code="msg.feedbackAccountRequestedDetails" /></strong></p>
</div>
                    
<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.userName" />:</p>
	</div>
	<div class="grid_18 omega">
		${user.email}
	</div>
</div>

<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.firstName" />:</p>
	</div>
	<div class="grid_18 omega">
		${user.firstName}
	</div>
</div>

<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.lastName" />:</p>
	</div>
	<div class="grid_18 omega">
		${user.lastName}
	</div>
</div>

<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.affili" />:</p>
	</div>
	<div class="grid_18 omega">
		${user.affiliation}
	</div>
</div>

<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.affiliUrl" />:</p>
	</div>
	<div class="grid_18 omega">
		${user.affiliationUrl}
	</div>
</div>

<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.country" />:</p>
	</div>
	<div class="grid_18 omega">
 		<c:if test="${empty country}"> ${user.address}</c:if>  <!--  Short name UK -->
 		<c:if test="${not empty country}"> ${country}</c:if>	<!--  Long name United Kingdom -->
	</div>
</div>

<div class="grid_24">
	<p><a href="index"><spring:message code="msg.mainPage"/></a></p>
</div>
