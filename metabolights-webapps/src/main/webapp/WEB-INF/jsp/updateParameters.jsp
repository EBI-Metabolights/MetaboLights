<%--
  Created by IntelliJ IDEA.
  User: tejasvi
  Date: 02/05/13
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="grid_23 title alpha">
    <div class="grid_4">
        <spring:message code="metaboLights.parameters.name"/>
    </div>
    <div class="grid_4">
        <spring:message code="metaboLights.parameters.value"/>
    </div>
</div>

<c:if test="${not empty paramname}">
    <form name="parameters" action="updateParameters" method="post">

        <div class="grid_23 refLayerBox">
            <div class="grid_4">
                <input type="hidden" name="paramname" value="${paramname}" id="name"/>
                    ${paramname}
            </div>
            <div class="grid_4">
                <input type="textbox" name="paramvalue" value="${paramvalue}" id="value"/>
            </div>
            <a href="deleteParam?paramname=${paramname}&paramvalue=${paramvalue}" onclick="location.href='parameters'"><spring:message code="metaboLights.parameters.delete"/> </a>
            <br/>
            <br/>
            <input name="submit" type="submit" class="submit" value="Submit"/>
            <input name="cancel" type="button" class="submit cancel" value="Cancel" onclick="location.href='parameters'"/>
        </div>

    </form>
</c:if>

<c:if test="${empty paramname}">
    <form name="parameters" action="insertParameters" method="post">

        <div class="grid_23 refLayerBox">
            <input type="textbox" name="paramname" id="paramname"/>
            <input type="textbox" name="paramvalue" id="paramvalue"/>
            <br/>
            <br/>
            <input name="submit" type="submit" class="submit" value="Submit" />
            <input name="cancel" type="button" class="submit cancel" value="Cancel" onclick="location.href='parameters'"/>
        </div>


    </form>
</c:if>


