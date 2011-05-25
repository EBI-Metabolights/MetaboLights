<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="formbox">
	<table cellpadding="15px" cellspacing="0px" width="90%">
	    <tr class="formheader">
	        <th class="tableheader" colspan="3"><spring:message code="msg.feedbackAccountRequestedHeader"/>: <c:out value="${userName}"/> </th>
	    </tr>
        <tr>
            <td colspan='3'><spring:message code="msg.feedbackAccountRequestedBody" /></td>
        </tr>
        <tr>
            <td colspan='3'><a href="index"><spring:message code="msg.mainPage"/></a></td>
        </tr>
        <tr>
            <td colspan='3'><img src="img/clock.png" ></img> </td>
        </tr>
	</table>
</div>

