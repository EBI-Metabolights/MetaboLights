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
			<td height="100px" width="300px" class="button_cell_dark" onclick="window.location.href='biisubmit';">
				<spring:message code="label.submitNewStudy" /><br/><br/>
				<spring:message code="label.submitNewStudySub" /><br/>&nbsp;&nbsp;
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td height="100px" width="300px" class="button_cell_pale" onclick="window.location.href='mysubmissions?status=PRIVATE';">
				<spring:message	code="label.updateOldStudy" /><br/><br/>
				<spring:message code="label.updateOldStudySub" />
			</td>
		</tr>
	</table>
	<br/>
	<br/>
</div>