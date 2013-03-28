<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="grid_24">
	<h2>
		<c:if test="${not empty updated}"><spring:message code="msg.updatedAccount" /></c:if>  <!--  only display on update account -->
	    <strong><c:if test="${empty updated}"><spring:message code="msg.feedbackAccountRequestedHeader" /></c:if></strong><!--  only display on new account creation -->
		<%--: <c:out value="${user.email}"/>--%>
	</h2>
</div>

<div class="grid_24">
    <br/>
</div>

<c:if test="${empty updated}">
    <div class="grid_24">
        <p><spring:message code="msg.step1.accountDetails" /></p>
        <p><spring:message code="msg.step2.accountDetails" /></p>
        <p><spring:message code="msg.contactUsAndRegards" /></p>
    </div>
</c:if> <!--  only display on new account creation -->

<div class="grid_24">
    <br/>
</div>

<div class="grid_24">
	<p><strong><spring:message code="msg.feedbackAccountRequestedDetails" /></strong></p>
</div>
                    
<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.userName" />:</p>
	</div>
	<div class="grid_18 omega">
		${user.email}
	</div>
</div>

<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.firstName" />:</p>
	</div>
	<div class="grid_18 omega">
		${user.firstName}
	</div>
</div>

<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.lastName" />:</p>
	</div>
	<div class="grid_18 omega">
		${user.lastName}
	</div>
</div>

<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.affili" />:</p>
	</div>
	<div class="grid_18 omega">
		${user.affiliation}
	</div>
</div>

<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.affiliUrl" />:</p>
	</div>
	<div class="grid_18 omega">
		${user.affiliationUrl}
	</div>
</div>

<div class="grid_24">
	<div class="grid_6 alpha">
		<p><spring:message code="label.country" />:</p>
	</div>
	<div class="grid_18 omega">
 		<c:if test="${empty country}"> ${user.address}</c:if>  <!--  Short name UK -->
 		<c:if test="${not empty country}"> ${country}</c:if>	<!--  Long name United Kingdom -->
	</div>
</div>

<div class="grid_24">
	<p><a href="index"><spring:message code="msg.mainPage"/></a></p>
</div>
