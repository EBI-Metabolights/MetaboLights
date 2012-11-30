<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<br/><br/>
<div class="grid_6 prefix_1 alpha">
	<img src="img/error2.png"/>
</div>
<div class="grid_17 omega">
<br/><br/>
<span class="error">
  <spring:message code="msg.error.general" />
  <c:out value="${errorMainMessage}"/>
</span>
<br/>
<span class="bgcolor">
	<c:if test="${!empty errorStack}">
		<c:out value="${errorStack}" escapeXml="false"  />
	<br/>
	</c:if>
<%-- 	
	<c:if test="${!empty pageContext.exception}">
		<c:out value="${pageContext.exception.message}"/><br/><br/>
		<c:forEach var="element" items="${pageContext.exception.stackTrace}">
    		<c:out value="${element}"/><br/>
		</c:forEach>
	</c:if>
 --%>
 </span>


<br/>
</div>
