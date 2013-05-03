<%--
  Created by IntelliJ IDEA.
  User: tejasvi
  Date: 02/05/13
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h3><spring:message code="metaboLights.parameters"/></h3>


<div class="grid_23 title alpha">
    <div class="grid_4">
        <spring:message code="metaboLights.parameters.name"/>
    </div>
    <div class="grid_4">
        <spring:message code="metaboLights.parameters.value"/>
    </div>
</div>

<form name="parametersForm" action="parameters" method="post">

    <div class="grid_23 refLayerBox">
        <c:forEach var="parameters" items="${mtblparamteres}">

            <div class="grid_4">
                <a href="updateParameters?paramname=${parameters.parameterName}&paramvalue=${parameters.parameterValue}">${parameters.parameterName}</a>
            </div>

            <div class="grid_8">
                ${parameters.parameterValue}
            </div>
            <br/>

        </c:forEach>
        <br/>
        <a href="addParameters"><spring:message code="metaboLights.parameters.add"/></a>
    </div>
</form>