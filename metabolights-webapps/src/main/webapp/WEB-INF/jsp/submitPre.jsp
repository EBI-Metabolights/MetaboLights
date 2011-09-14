<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="formbox">

	<table border="0" cellpadding="5px" cellspacing="0px">
		<tr class="formheader">
			<th class="tableheader" colspan="3"><c:if test="${!empty user}">Hi ${user.firstName}. </c:if>
				<spring:message code="msg.submCredentialsShort" />
			</th>
		</tr>
		<tr>
			<td colspan='3'>&nbsp;<br/><br/></td>
		</tr>
		<tr>
			<td>
			     <a class="multi-line-button main" href="biisubmit" style="width:350px; height:100px">
	       			 <span class="titlemlb"><spring:message code="label.submitNewStudy"/></span>
	        		 <span class="subtitlemlb"><spring:message code="label.submitNewStudySub"/></span>
      			 </a>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
			     <a class="multi-line-button highlight" href="mysubmissions?status=PRIVATE" style="width:350px; height:100px; color: black">
	       			 <span class="titlemlb"><spring:message	code="label.updateOldStudy"/></span>
	        		 <span class="subtitlemlb"><spring:message code="label.updateOldStudySub"/></span>
      			 </a>
			</td>
		</tr>

		
	</table>
	<br/>
	<br/>
</div>