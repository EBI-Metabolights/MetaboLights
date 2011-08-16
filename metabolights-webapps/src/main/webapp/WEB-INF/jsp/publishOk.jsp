<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br/>
<br/>
<c:if test="${not empty error}">
	<br/><br/>
	<table>
		<tr>
			<td><img src="img/warning.png" height ="30" width="30"></td>
			<td><spring:message code="msg.publish.wrong"/><br/>
				<b><c:out value="${error.message}"/></b>
			</td>
		</tr>
	</table>
</c:if>
 
<c:if test="${not empty message}">
	<br/><br/>
	<table>
		<tr>
			<td><img src="img/info.png" height ="30" width="30"></td>
			<td>
				<b><c:out value="${message}"/></b>
			</td>
		</tr>
	</table>
</c:if>

<c:if test="${not empty searchResult}">
	<%@include file="entrySummary.jsp" %>
</c:if>

<br/>
<br/>
<a href="index"><spring:message code="msg.backToMainPage"/></a>            


