<%--
  Created by IntelliJ IDEA.
  User: kalai
  Date: 04/12/2015
  Time: 14:42
  To change this template use File | Settings | File Templates.
--%>

<%--<table class="display clean" order="[[ 1, 'asc' ],[ 0, 'desc' ]]" cellspacing="0" width="100%">--%>
<table id="validationTable" class="display clean" order="[[ 1, 'asc' ],[ 0, 'desc' ]]">
    <thead class='text_header'>
    <tr>
        <th>Condition</th>
        <th>Status</th>
        <th>Description</th>
        <th>Requirement</th>
        <th>Group</th>
        <th>Message</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="validation" items="${validations}">
        <tr>
            <td>
                <c:set var="validationType" value="${validation.type}"/>
                <c:set var="validationOverridden" value="${validation.overriden}"/>
                <c:set var="validationPassedRequirement" value="${validation.passedRequirement}"/>
                <%@include file="validation.jsp" %>
            </td>

            <c:if test="${validationPassedRequirement == 'true'}">
                <td id="${validation.id}">PASSES</td>
            </c:if>
            <c:if test="${validationPassedRequirement == 'false'}">
                <c:if test="${validationType == 'MANDATORY'}">
                    <td id="${validation.id}">FAILS</td>
                </c:if>
                <c:if test="${validationType == 'OPTIONAL'}">
                    <td id="${validation.id}">INCOMPLETE</td>
                </c:if>
            </c:if>
                <%--<td id="${validation.id}">--%>
                <%--<select size="1" id="statusOptions" name="statusValidation">--%>
                <%--<option value="PASSES" selected="selected">--%>
                <%--PASSES--%>
                <%--</option>--%>
                <%--<option value="FAILS">--%>
                <%--FAILS--%>
                <%--</option>--%>
                <%--<option value="INCOMPLETE">--%>
                <%--INCOMPLETE--%>
                <%--</option>--%>
                <%--</select>--%>
                <%--</td>--%>

                <%--<td>${validation.status}</td>--%>
            <td>${validation.description}</td>
            <td>${validation.type}</td>
            <td>${validation.group}</td>
            <td>${validation.message}</td>
        </tr>

    </c:forEach>
    </tbody>
</table>


