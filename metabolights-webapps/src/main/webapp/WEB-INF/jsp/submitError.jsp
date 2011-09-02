<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br/>
<br/>

<c:if test="${not empty error}">
	<br/><br/>
	<table>
		<tr>
			<td>
			   <!-- img src="img/warning.png" height ="30" width="30"-->
			   <img src="img/ebi-icons/32px/alert.png" class="img_alignment_yellow">
			</td>
			<td><spring:message code="msg.uploaded.wrong"/><br/>
				<b><c:out value="${error.message}"/></b>
			</td>
		</tr>
	</table>
</c:if>
  
<c:if test="${not empty cl}">
	<br/><br/>
	<table align="center" cellpadding="5px" cellspacing="0px">
	
	<tr>
		<td>
		  <!-- img src="img/info.png" height="30" width="30"-->
		  <img src="img/ebi-icons/32px/info.png" class="img_alignment_green">
		</td>
		<td><b><span style="text-decoration:underline"><spring:message code="msg.uploaded.checkListTitle"/></span></b></td>
	<c:forEach items="${cl}" var="mapEntry">
		<tr>
			<td>
				<c:choose>
		  			<c:when test="${mapEntry.value.isChecked}">
	      				<!-- img src="img/check.png" height ="20" width="20"-->
				    	<img src="img/ebi-icons/16px/check.png" class="img_alignment_green">

	      			</c:when>
	      			<c:otherwise>
						<!-- img src="img/uncheck.png" height="20" width="20"-->
				    	<img src="img/ebi-icons/16px/delete.png" class="img_alignment_yellow">

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

<a href="biisubmit"><spring:message code="msg.backToSubmitPage"/></a>
