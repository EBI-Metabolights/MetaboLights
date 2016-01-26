<%--
  Created by IntelliJ IDEA.
  User: jrmacias
  Date: 25/01/2016
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Validations Status Report</title>
</head>
<body>


<div role="tabpanel" class="tab-pane" id="validations">
    <!-- TAB: Validations-->
    <div id="tabs-validations" class="tab">
        <c:if test="${not empty study.validations.entries}">
            <div class="specs well">
                Validations marked with (*) are specially approved by the MetaboLights Curators
            </div>
                    <c:set var="validations" value="${study.validations.entries}"/>
                    <c:if test="${not empty study.validations.entries}">
                        <table class="dataTable table table-striped table-bordered">
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
                            <c:forEach var="validation" items="${study.validations.entries}">
                                <tr>
                                    <td>
                                        <c:set var="validationType" value="${validation.type}"/>
                                        <c:set var="validationOverridden" value="${validation.overriden}"/>
                                        <c:set var="validationPassedRequirement" value="${validation.passedRequirement}"/>
                                        <%@include file="validation.jsp" %>
                                    </td>

                                    <c:if test="${validation.status == 'GREEN'}">
                                        <td>PASSES</td>
                                    </c:if>
                                    <c:if test="${validationPassedRequirement == 'false'}">
                                        <c:if test="${validationType == 'MANDATORY'}">
                                            <td>FAILS</td>
                                        </c:if>
                                        <c:if test="${validationType == 'OPTIONAL'}">
                                            <td>INCOMPLETE</td>
                                        </c:if>
                                    </c:if>

                                        <%--<td>${validation.status}</td>--%>
                                    <td>${validation.description}</td>
                                    <td>${validation.type}</td>
                                    <td>${validation.group}</td>
                                    <td>${validation.message}</td>
                                </tr>

                            </c:forEach>
                            </tbody>
                        </table>
                    </c:if>


        </c:if>
    </div>
</div>


</body>
</html>
