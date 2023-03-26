<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="${orcidLinkUrl}"></script>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">


<div class="form-group">
    <label><spring:message code="label.orcid"/>:</label>
        <%--<form:input path="orcId"  maxlength="20" />--%>
        <%--<span class="error"><form:errors path="orcId" /></span>--%>
            <c:choose>
                <c:when test="${empty metabolightsUser.orcId}">
                    <span class="thorOrcIdSpan"></span>
                    <form:input type="text" path="orcId" class="thorOrcIdIdentifier form-control"/>
                </c:when>
                <c:otherwise>
                    <form:input type="text" class="form-control" path="orcId"/>
                </c:otherwise>
            </c:choose>
            <span class="error"><form:errors path="orcId"/></span>
</div>

<div class="form-group">
    <label><spring:message code="label.firstName"/>*:</label>
    <form:input path="firstName" maxlength="255" size="40" class="thorGivenNameTxt form-control"/>
    <%--<div class="grid_18 omega"><form:input path="firstName" maxlength="255" size="40"/>--%>
        <span class="error"><form:errors path="firstName"/></span>

</div>

<div class="form-group">
    <label><spring:message code="label.lastName"/>*:</label>

        <form:input path="lastName" maxlength="255" size="40" class="thorFamilyNameTxt form-control"/>
            <%--<form:input path="lastName" maxlength="255" size="40"/>--%>
        <span class="error"><form:errors path="lastName"/></span>
</div>


<div class="form-group">
    <label><spring:message code="label.password"/>*:</label>
        <form:input class="form-control" path="dbPassword" maxlength="255" size="40" type="password"/>
        <span class="error"><form:errors path="dbPassword"/></span>
</div>

<div class="form-group">
    <label><spring:message code="label.passwordRep"/>*:</label>
        <form:input class="form-control" path="userVerifyDbPassword" maxlength="255" size="40" type="password"/>
        <span class="error"><form:errors path="userVerifyDbPassword"/></span>
</div>

<div class="form-group">
    <label><spring:message code="label.country"/>*:</label>
        <form:select class="form-control" path="address" items="${metabolightsUser.listOfAllCountries}"/>
        <span class="error"><form:errors path="address"/></span>
</div>

<div class="form-group">
    <label><spring:message code="label.affili"/>*:</label>
        <form:input class="form-control" path="affiliation" maxlength="255" size="40"/>
        <span class="error"><form:errors path="affiliation"/></span>
</div>

<div class="form-group">
    <label><spring:message code="label.affiliUrl"/>*:</label>
        <form:input class="form-control" path="affiliationUrl" maxlength="4000" size="66"/>
        <span class="error"><form:errors path="affiliationUrl"/></span>
</div>

<sec:authorize access="hasRole('ROLE_SUPER_USER')">
    <div class="form-group">
        <label><spring:message code="label.userStatus"/>*:</label>
            <form:select class="form-control" path="status" items="${metabolightsUser.listOfAllStatus}"/>
            <span class="error"><form:errors path="status"/></span>
    </div>
</sec:authorize>