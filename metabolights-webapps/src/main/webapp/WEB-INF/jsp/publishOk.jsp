<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br/>
<br/>
<div class="formbox">
	<table cellpadding="15px" cellspacing="0px" width="90%">
	    <tr class="formheader">
	        <th class="tableheader" colspan="3">
	        	<c:if test="${not empty message}">
					<c:out value="${message}"/>
				</c:if>
	        </th>
	    </tr>

		<c:if test="${not empty error}">
			<br/><br/>
			<tr>
				<td><img src="img/warning.png" height ="30" width="30"></td>
				<td><spring:message code="msg.publish.wrong"/><br/>
					<b><c:out value="${error.message}"/></b>
				</td>
			</tr>
		</c:if>
	
		<c:if test="${not empty searchResult}">
			<tr>
				<td>
					<%@include file="entrySummary.jsp" %>
				</td>
			</tr>
			<tr>
				<td>
					<a href="mysubmissions"><spring:message code="msg.publish.again"/></a>            
				</td>
			</tr>
		</c:if>
	</table>
</div>
