<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="formbox">
	<table cellpadding="15px" cellspacing="0px" width="90%">
	    <tr class="formheader">
	        <th class="tableheader" colspan="3">
	        	<c:if test="${not empty updated}"> <spring:message code="msg.updatedAccount" /></c:if>  <!--  only display on update account -->
            	<c:if test="${empty updated}">	 <spring:message code="msg.feedbackAccountRequestedHeader" /></c:if> <!--  only display on new account creation -->
	        : <c:out value="${user.email}"/> </th>
	    </tr>
	    
	    <tr>
            <td colspan='3'><b><spring:message code="msg.feedbackAccountRequestedDetails" /></b></td>
        </tr>
	    <tr>
	    	<td><spring:message code="label.userName" />:</td>
	    	<td colspan='2'>${user.email}</td>	    
        </tr>
         <tr>
	    	<td><spring:message code="label.firstName" />:</td>
	    	<td colspan='2'>${user.firstName}</td>	    
        </tr>
         <tr>
	    	<td><spring:message code="label.lastName" />:</td>
	    	<td colspan='2'>${user.lastName}</td>	    
        </tr>
        <tr>
	    	<td><spring:message code="label.affili" />:</td>
	    	<td colspan='2'>${user.affiliation}</td>	    
        </tr>   
        <tr>
	    	<td><spring:message code="label.affiliUrl" />:</td>
	    	<td colspan='2'>${user.affiliationUrl}</td>	    
        </tr>   
        <tr>
	    	<td><spring:message code="label.country" />:</td>
	    	<td colspan='2'>${user.address}</td>	    
        </tr>
        
        <tr>
            <td colspan='3'>
            	 <c:if test="${empty updated}">	 <spring:message code="msg.feedbackAccountRequestedBody" /></c:if> <!--  only display on new account creation -->
            </td>
        </tr>
        <tr>
            <td colspan='3'><a href="index"><spring:message code="msg.mainPage"/></a></td>
        </tr>
        
        <c:if test="${empty updated}">  <!--  only display on new account creation -->
        	<tr>
            	<td colspan='3'><img src="img/clock.png" ></img> </td>
        	</tr>
       </c:if>   
       	
	</table>
</div>

