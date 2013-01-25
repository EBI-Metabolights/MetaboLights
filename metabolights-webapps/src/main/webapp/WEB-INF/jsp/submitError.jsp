<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br/>

<c:if test="${not empty error}">
	<h2><spring:message code="msg.uploaded.wrong"/></h2>
	<br/><br/>
	<div class="grid_23 alpha omega prefix_1">
		<div class="ebiicon alert_i"></div>&nbsp;<strong><c:out value="${error.message}"/></strong>
		<br/>
		<br/>
		<a href="submittoqueue"><spring:message code="msg.backToSubmitPage"/></a>
	</div>
</c:if>
