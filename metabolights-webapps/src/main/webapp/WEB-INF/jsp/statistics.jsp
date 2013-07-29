<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<h2><spring:message code="msg.statistics" /></h2>
</p>
</p>

    <c:choose>
        <c:when test="${not empty dataList}">
            <p>
            <H4>Data in MetaboLights</H4>
            <c:forEach var="dataEntries" items="${dataList}">
                <div class="grid_8">
                    ${dataEntries.displayName}
                </div>
                <div class="grid_8">
                    <b>${dataEntries.displayValue} </b>
                </div>
                </br>
            </c:forEach>
            </p>
        </c:when>
    </c:choose>



    <c:choose>
        <c:when test="${not empty identifierList}">
            <p>
            <H4>MetaboLights compounds references from other databases</H4>
            <c:forEach var="idEntries" items="${identifierList}">
                <div class="grid_8">
                    ${idEntries.displayName}
                </div>
                <div class="grid_8">
                    <b>${idEntries.displayValue}</b>
                </div>
                </br>
            </c:forEach>
            </p>
        </c:when>
    </c:choose>

    <c:choose>
        <c:when test="${not empty submittersList}">
            <p>
            <H4>Submitters</H4>
            <c:forEach var="submitters" items="${submittersList}">
                <div class="grid_8">
                    ${submitters.displayName}
                </div>
                <div class="grid_8">
                    <b>${submitters.displayValue}</b>
                </div>
            </c:forEach>
            </p>
        </c:when>
    </c:choose>



