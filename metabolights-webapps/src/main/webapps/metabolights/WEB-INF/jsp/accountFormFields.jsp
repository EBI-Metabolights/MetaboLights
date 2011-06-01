<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


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
            <td><spring:message code="label.email" />:</td>
            <td><form:input path="email"  maxlength="255" size="40" /> </td>
            <td>(*)&nbsp;<span class="error"><form:errors path="email" />
                            <c:if test="${not empty duplicateEmailAddress}"><c:out value="${duplicateEmailAddress}" /></c:if>
                         </span>
            </td>
        </tr>

        <tr>
            <td><spring:message code="label.affili" />:</td>
            <td><form:input path="affiliation"  maxlength="255" size="40" /> </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td><spring:message code="label.country" />:</td>
            <td colspan="2"><form:select path="address" items="${metabolightsUser.listOfAllCountries}"/></td>
        </tr>


