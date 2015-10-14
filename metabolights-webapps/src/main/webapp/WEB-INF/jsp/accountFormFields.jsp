<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>


	<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 1/3/13 3:14 PM
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
		<div class="grid_6 alpha"><spring:message code="label.orcid" />:</div>
		<div class="grid_18 omega">
			<form:input path="orcid"  maxlength="20" />
			<span class="error"><form:errors path="orcid" /></span>
		</div>
	</div>

	<div class="grid_24">
		<div class="grid_6 alpha"><spring:message code="label.firstName" />*:</div>
		<div class="grid_18 omega"><form:input path="firstName" maxlength="255" size="40" />
			<span class="error"><form:errors path="firstName" /></span>
        </div>
	</div>

	<div class="grid_24">
		<div class="grid_6 alpha"><spring:message code="label.lastName" />*:</div>
		<div class="grid_18 omega">
			<form:input path="lastName"  maxlength="255" size="40" />
			<span class="error"><form:errors path="lastName" /></span>
        </div>
	</div>

	<div class="grid_24">
		<div class="grid_6 alpha"><spring:message code="label.password" />*:</div>
		<div class="grid_18 omega">
			<form:input path="dbPassword"  maxlength="255" size="40" type="password" />
			<span class="error"><form:errors path="dbPassword" /></span>
		</div>
	</div>


	<div class="grid_24">
		<div class="grid_6 alpha"><spring:message code="label.passwordRep" />*:</div>
		<div class="grid_18 omega">
			<form:input path="userVerifyDbPassword"  maxlength="255" size="40" type="password" />
			<span class="error"><form:errors path="userVerifyDbPassword" /></span>
        </div>
	</div>

	<div class="grid_24">
		<div class="grid_6 alpha"><spring:message code="label.country" />*:</div>
		<div class="grid_18 omega">
			<form:select path="address" items="${metabolightsUser.listOfAllCountries}"/>
			<span class="error"><form:errors path="address" /></span>
		</div>
	</div>

	<div class="grid_24">
		<div class="grid_6 alpha"><spring:message code="label.affili" />*:</div>
		<div class="grid_18 omega">
			<form:input path="affiliation"  maxlength="255" size="40" />
			<span class="error"><form:errors path="affiliation" /></span>
		</div>
	</div>
	
	<div class="grid_24">
		<div class="grid_6 alpha"><spring:message code="label.affiliUrl" />*:</div>
		<div class="grid_18 omega">
			<form:input path="affiliationUrl"  maxlength="4000" size="66" />
			<span class="error"><form:errors path="affiliationUrl" /></span>
		</div>
	</div>

<sec:authorize ifAnyGranted="ROLE_SUPER_USER" >
	
	<div class="grid_24">
		<div class="grid_6 alpha"><spring:message code="label.userStatus" />*:</div>
		<div class="grid_18 omega">
			<form:select path="status" items="${metabolightsUser.listOfAllStatus}"/>
			<span class="error"><form:errors path="status" /></span>
        </div>
	</div>    
	        

</sec:authorize>