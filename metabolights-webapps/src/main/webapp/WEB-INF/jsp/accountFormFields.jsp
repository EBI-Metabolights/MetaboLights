<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

        <tr>
            <td><spring:message code="label.firstName" />:</td>
            <td><form:input path="firstName" maxlength="255" size="30" /> </td>
            <td>(*)&nbsp;<span class="error"><form:errors path="firstName" /></span></td>
        </tr>
        <tr>
            <td><spring:message code="label.lastName" />:</td>
            <td><form:input path="lastName"  maxlength="255" size="40" /> </td>
            <td>(*)&nbsp;<span class="error"><form:errors path="lastName" /></span></td>
        </tr>
        <tr>
            <td><spring:message code="label.password" />:</td>
            <td><form:input path="dbPassword"  maxlength="255" size="40" type="password" /> </td>
            <td>(*)&nbsp;<span class="error"><form:errors path="dbPassword" /></span></td>
        </tr>
        <tr>
            <td><spring:message code="label.passwordRep" />:</td>
            <td><form:input path="userVerifyDbPassword"  maxlength="255" size="40" type="password" /> </td>
            <td>(*)&nbsp;<span class="error"><form:errors path="userVerifyDbPassword" /></span></td>
        </tr>

        <tr>
            <td><spring:message code="label.affili" />:</td>
            <td><form:input path="affiliation"  maxlength="255" size="40" /> </td>
            <td>(*)&nbsp;<span class="error"><form:errors path="affiliation" /></span></td>
        </tr>

        <tr>
            <td><spring:message code="label.affiliUrl" />:</td>
            <td><form:input path="affiliationUrl"  maxlength="4000" size="50" /> </td>
            <td>(*)&nbsp;<span class="error"><form:errors path="affiliationUrl" /></span></td>
        </tr>

        <tr>
            <td><spring:message code="label.country" />:</td>
            <td><form:select path="address" items="${metabolightsUser.listOfAllCountries}"/></td>
            <td>(*)&nbsp;<span class="error"><form:errors path="address" /></span></td>
        </tr>

        <sec:authorize ifAnyGranted="ROLE_SUPER_USER" >
	        <tr>
	            <td><spring:message code="label.userStatus" />:</td>
	            <td><form:select path="status" items="${metabolightsUser.listOfAllStatus}"/></td>
	            <td>(*)&nbsp;<span class="error"><form:errors path="status" /></span></td>
	        </tr>

        </sec:authorize>

