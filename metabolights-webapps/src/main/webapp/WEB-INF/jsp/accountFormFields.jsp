<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>


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

	<div class="grid_24">
		<div class="grid_6 alpha"><spring:message code="label.country" />*:</div>
		<div class="grid_18 omega">
			<form:select path="address" items="${metabolightsUser.listOfAllCountries}"/>
			<span class="error"><form:errors path="address" /></span>
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