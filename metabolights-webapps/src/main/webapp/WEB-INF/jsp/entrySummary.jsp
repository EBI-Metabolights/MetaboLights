<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<div class="grid_24 box alpha omega">
	
	<c:choose>
		<c:when test="${!empty welcomemessage && !searchResult.isPublic}">
			<div class="grid_20 alpha">
				<a href="${searchResult.accStudy}"><strong>${searchResult.title}</strong></a>
			</div>
			<div class="grid_4 omega">
				<!--
				<form name="update-form" action="updatestudyform" method="post" class="one-button-form">
					<input type="hidden" name="study" value="${searchResult.accStudy}"/>
                    <input type="submit" id="update" class="multi-line-button main" value=" <spring:message code="label.updatestudy"/> ">
				</form>
				 -->
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
			</div>
		</c:when>
		<c:otherwise>
			<div class='grid_24 alpha omega'>
				<a href="${searchResult.accStudy}"><strong>${searchResult.title}</strong></a>
			</div>
		</c:otherwise>
	</c:choose>
	
	<div style='clear: both;'></div>
	<!-- new row -->

	<div style='width: 700px;' class='iscell'>
		<b><spring:message code="label.releaseDate"/>:</b> <fmt:formatDate pattern="dd-MMM-yyyy" value="${searchResult.releaseDate}"/>
		<c:if test="${!searchResult.isPublic}">
			&nbsp;<img src="img/ebi-icons/32px/key.png" class="img_alignment_yellow"/><b>&nbsp;<spring:message code="label.expPrivate"/></b>
			<!--
			<c:if test="${!empty welcomemessage}">&nbsp;
			<div style="float: right; margin-right:90px;">
            <form name="publish-form" action="updatepublicreleasedateform" method="post">
				<input type="hidden" name="study" value="${searchResult.accStudy}"/>
                <input type="submit" id="publish" class="multi-line-button main" value=" <spring:message code="label.makestudypublic"/> ">
			</form>
			</div>
			</c:if>
			 -->
		</c:if>
	</div>
	
	<div style='clear: both;'></div>
	<!-- new row -->
	
	<div style='width: 500px;' class='iscell'>
		<b><spring:message code="label.organism" /></b>
		<ul id="resultList">
			<c:forEach var="species" items="${searchResult.organism}">
				<li>${species}</li>
			</c:forEach>
		</ul>
	</div>
	
	<div style='clear: both;'></div>
	<!-- new row -->
	
	<div style='width: 500px;' class='iscell'>
		<b><spring:message code="label.expFact" /></b>
		<ul id="resultList">
			<c:forEach var="factor" items="${searchResult.factors}">
				<li>${factor.key}: ${factor.value}</li>
			</c:forEach>
		</ul>
	</div>
	
	<div style='width: 200px;' class='iscell'>
		<spring:message code="label.expId" />: <b>${searchResult.accStudy}</b><br/>
		<spring:message code="label.subm" /> ${searchResult.submitter.name} ${searchResult.submitter.surname}<br/>
	</div>
	

	<div style='clear: both;'></div>
	<!-- new row -->

	<div style='width: 300px;' class='iscell'>
		<b><spring:message code="label.assays" /></b>
		<ul id="resultList">
			<c:forEach var="assay" items="${searchResult.assays}">
				<li>${assay.technology} (${assay.count})</li>
			</c:forEach>
		</ul>
	</div>
	
	<!--
	<div style='width: 400px;' class='iscell'>
		<c:if test="${not empty searchResult.publications}">
            <c:forEach var="pub" items="${searchResult.publications}">
	               <IMG src="img/book.png" height="18"> <a href="http://www.ebi.ac.uk/citexplore/citationDetails.do?externalId=${pub.pubmedId}&dataSource=MED">${pub.title}</a> 
	            <br/>
            </c:forEach>
            <br/>
   		</c:if>
   	</div>
   	-->	

	<div style='clear: both;'></div>
	<!-- new row -->
</div>
