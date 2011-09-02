<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br/>
<br/>
<c:if test="${not empty error}">
	<br/><br/>
	<table>
		<tr>
			<td><img src="img/ebi-icons/64px/stop.png" class="img_alignment_yellow"></td>
			<td><spring:message code="msg.uploaded.wrong"/><br/>
				<b><c:out value="${error.message}"/></b>
			</td>
		</tr>
	</table>
</c:if>
 
<c:if test="${not empty accessions}">
	<br/><br/>
	<table>
		<tr>
			<td><img src="img/ebi-icons/32px/check.png" class="img_alignment_green"></td>
			<td><spring:message code="msg.uploaded.ok1"/><br/>
				<spring:message code="msg.uploaded.ok2"/><br/>
				<b><spring:message code="msg.uploaded.ok3"/></b><br/>
				<c:forEach items="${accessions}" var="accessionEntry">
					  <b>${accessionEntry.key}</b> <spring:message code="msg.uploaded.ok4"/><a href="<c:out value="${accessionEntry.value}"/>">${accessionEntry.value}</a>.<br/>
				</c:forEach> 	
			</td>
		</tr>
	</table>
 </c:if>
 
 
 <c:if test="${not empty cl}">
	<br/><br/>
	<table >
	<tr>
		<td><img src="img/ebi-icons/64px/flag.png" class="img_alignment_green"></td>
		<td><b><spring:message code="msg.uploaded.checkListTitle"/></b></td>
	<c:forEach items="${cl}" var="mapEntry">
		<tr>
			<td>
				<c:choose>
		  			<c:when test="${mapEntry.value.isChecked}">
	      				<img src="img/ebi-icons/16px/check.png" class="img_alignment_green">
	      			</c:when>
	      			<c:otherwise>
						<img src="img/ebi-icons/16px/minus.png" class="img_alignment_yellow">
	      			</c:otherwise>
	    		</c:choose>
			</td>		
	    	<td>
	        	<b>${mapEntry.value.title}</b>
	        	<br>${mapEntry.value.notes}
	    	</td>
		</tr>
	</c:forEach> 
	</table>
</c:if>

<br/>
<br/>
<a href="index"><spring:message code="msg.backToMainPage"/></a>            


