<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">

</script>	

<div class="formbox">
	

		<table border="0" cellpadding="5px" cellspacing="0px">

			<tr class="formheader">
				<th class="tableheader" colspan="3"><c:if test="${!empty user}">Hi ${user.firstName}. </c:if> <spring:message code="msg.submCredentialsShort" />
				</th>
			</tr>
			<tr>
				<td colspan='3'>&nbsp;</td>
			</tr>
			<tr>
				<td height="100px" width="300px" class="big_submit">
					<a href="biisubmit" style="color:white; font-size:20px"><spring:message code="label.submitNewStudy"/></a> 
					<br/><br/><spring:message code="label.submitNewStudySub"/><br/>&nbsp;&nbsp;
				</td>
				<td>&nbsp;</td>
				<td height="100px" width="300px" class="big_submit">
					<a href="biisubmit" style="color:white; font-size:20px"><spring:message code="label.updateOldStudy"/></a>	
					<br/><br/><spring:message code="label.updateOldStudySub"/>
				</td>
			</tr>
		</table>



</div>