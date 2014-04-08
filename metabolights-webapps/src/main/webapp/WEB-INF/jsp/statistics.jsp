<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ Last modified: 4/8/14 9:55 AM
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  --%>

<h2><spring:message code="msg.statistics"/></h2>
</p>
</p>

    <c:choose>
        <c:when test="${not empty dataList}">
            <p>
            <H4><spring:message code="msg.statistics.data"/></H4>
            <c:forEach var="dataEntries" items="${dataList}">
                <div class="grid_8">
                    ${dataEntries.displayName}
                </div>
                <div class="grid_8">
                    <b>${dataEntries.displayValue}</b>
                </div>
                </br>
            </c:forEach>
            </p>
        </c:when>
    </c:choose>

    <c:choose>
        <c:when test="${not empty identifierList}">
            <p>
            <H4><spring:message code="msg.statistics.compounds"/></H4>
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
            <H4><spring:message code="msg.statistics.users"/></H4>
            <c:forEach var="submitters" items="${submittersList}">
                <div class="grid_8">
                    ${submitters.displayName}
                </div>
                <div class="grid_8">
                    <b>${submitters.displayValue}</b>
                </div>
                </br>
            </c:forEach>
            </p>
        </c:when>
    </c:choose>
