<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="formbox">
	<table cellpadding="15px" cellspacing="0px" border="0">
	    <tr class="text_header plain">
	        <th colspan="2">
	        	<c:if test="${not empty updated}"><spring:message code="msg.updatedAccount" /></c:if>  <!--  only display on update account -->
            	<c:if test="${empty updated}"><spring:message code="msg.feedbackAccountRequestedHeader" /></c:if> <!--  only display on new account creation -->
	        : <c:out value="${user.email}"/></th>
	    </tr>
	    
	    <tr>
            <td colspan='2'>
                    <b><spring:message code="msg.feedbackAccountRequestedDetails" /></b>
            </td>
        </tr>

        <tr>
	    	<td><spring:message code="label.userName" />:</td>
            <td>${user.email}</td>
        </tr>

        <tr>
           	<td><spring:message code="label.firstName" />:</td>
            <td>${user.firstName}</td>
        </tr>

        <tr>
	    	<td><spring:message code="label.lastName" />:</td>
	    	<td>${user.lastName}</td>
        </tr>

        <tr>
	    	<td><spring:message code="label.affili" />:</td>
	    	<td>${user.affiliation}</td>
        </tr>

        <tr>
	    	<td><spring:message code="label.affiliUrl" />:</td>
	    	<td>${user.affiliationUrl}</td>
        </tr>

        <tr>
	    	<td><spring:message code="label.country" />:</td>
	    	<td>
	    		<c:if test="${empty country}"> ${user.address}</c:if>  <!--  Short name UK -->
	    		<c:if test="${not empty country}"> ${country}</c:if>	<!--  Long name United Kingdom -->
	    	</td>	    
        </tr>
        
        <tr>
            <td colspan='2'>
            	 <c:if test="${empty updated}">	 <spring:message code="msg.feedbackAccountRequestedBody" /></c:if> <!--  only display on new account creation -->
            </td>
        </tr>
        <tr>
            <td colspan='2'><a href="index"><spring:message code="msg.mainPage"/></a></td>
        </tr>
        
        <c:if test="${empty updated}">  <!--  only display on new account creation -->
        	<tr>
            	<td colspan='2'><img src="img/clock.png" /> </td>
        	</tr>
       </c:if>   
       	
	</table>
</div>

