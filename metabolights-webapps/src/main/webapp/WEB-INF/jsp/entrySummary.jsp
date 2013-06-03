<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<div class="grid_24 alpha omega box">
	<c:choose>
		<c:when test="${(!empty welcomemessage || !empty curator) && !searchResult.isPublic}">
			<div class="grid_20 alpha">
				<a href="${searchResult.accStudy}"><strong>${searchResult.title}</strong></a>
			</div>
			<div class="grid_4 omega">
				<%--
				<form name="update-form" action="updatestudyform" method="post" class="one-button-form">
					<input type="hidden" name="study" value="${searchResult.accStudy}"/>
                    <input type="submit" id="update" class="multi-line-button main" value=" <spring:message code="label.updatestudy"/> ">
				</form>
				 --%>
				<ul id="sddm">
				  <li><a onmouseover="mopen('actions${searchResult.accStudy}')"onmouseout="mclosetime()">
				  	Actions<span class="smallArrow"></span></a>
					<div id="actions${searchResult.accStudy}" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
						<a href="updatestudyform?study=${searchResult.accStudy}"><spring:message code="label.updatestudy"/></a>
						<a href="updatepublicreleasedateform?study=${searchResult.accStudy}"><spring:message code="label.makestudypublic"/></a>
						<a class="confirmLink" href="deleteStudy?study=${searchResult.accStudy}"><spring:message code="label.deleteStudy"/></a>
						<jsp:useBean id="now" class="java.util.Date" scope="page" />
						<a href="updatepublicreleasedateform?study=${searchResult.accStudy}&date=<fmt:formatDate pattern="dd-MMM-yyyy" value="${now}" />">Make it public</a>
					 </div>
				   </li>
				</ul>
				<div style="clear:both"></div>
			</div>
		</c:when>
		<c:otherwise>
            <c:if test="${!empty curator && searchResult.isPublic}">
                <div class="grid_20 alpha">
                    <a href="${searchResult.accStudy}"><strong>${searchResult.title}</strong></a>
                </div>
                <div class="grid_4 omega">
                    <ul id="sddm">
                        <li><a onmouseover="mopen('actions${searchResult.accStudy}')"onmouseout="mclosetime()">
                                Actions<span class="smallArrow"></span></a>
                            <div id="actions${searchResult.accStudy}" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
                                <a href="makestudyprivateform?study=${searchResult.accStudy}">Make it private</a>
                            </div>
                        </li>
                    </ul>
                    <div style="clear:both"></div>
                </div>
            </c:if>
			<c:if test="${empty curator}">
                <div class='grid_24 alpha omega'>
                    <a href="${searchResult.accStudy}"><strong>${searchResult.title}</strong></a>
                </div>
			</c:if>
		</c:otherwise>
	</c:choose>
	
	<div class='grid_20 alpha omega'>
		<strong><spring:message code="label.releaseDate"/>:</strong> <fmt:formatDate pattern="dd-MMM-yyyy" value="${searchResult.releaseDate}"/>
		<c:if test="${!searchResult.isPublic}">
			&nbsp;<div class="ebiicon key"></div><strong>&nbsp;<spring:message code="label.expPrivate"/></strong>
		</c:if>
	</div>
	
	<div class='grid_20 alpha omega'>
		<br/>
		<strong><spring:message code="label.organism" /></strong>
		<ul id="resultList">
			<c:forEach var="species" items="${searchResult.organism}">
				<li>${species}</li>
			</c:forEach>
		</ul>
	</div>
	
	<div class='grid_18 alpha'>
		<strong><spring:message code="label.expFact" /></strong>
		<ul id="resultList">
			<c:forEach var="factor" items="${searchResult.factors}">
				<li>${factor.key}: ${factor.value}</li>
			</c:forEach>
		</ul>
	</div>
	
	<div class='grid_6 omega'>
		<spring:message code="label.expId" />: <strong>${searchResult.accStudy}</strong><br/>
		<spring:message code="label.subm" /> ${searchResult.submitter.name} ${searchResult.submitter.surname}<br/>
	</div>
	

	<div class='grid_24 alpha omega'>
		<strong><spring:message code="label.assays" /></strong>
		<ul id="resultList">
			<c:forEach var="assay" items="${searchResult.assays}">
				<li>${assay.technology} (${assay.count})</li>
			</c:forEach>
		</ul>
	</div>

</div>
