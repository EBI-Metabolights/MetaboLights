<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ Last modified: 7/4/14 9:17 AM
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  --%>


<%--<div id="hourglass">
    <img src="img/wait.gif" alt="Please wait"/>&nbsp;<b><spring:message code="msg.fetchingData"/></b>
</div>--%>

<div class="grid_24 alpha omega box">
	<c:choose>
		<c:when test="${(!empty welcomemessage || !empty curator) && !searchResult.isPublic && empty reviewer}">
			<div class="grid_20 alpha">
                <a href="alt${searchResult.accStudy}" class="icon icon-generic" data-icon="&gt;"></a>|
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
						<a href="updatepublicreleasedateform?study=${searchResult.accStudy}"><spring:message code="label.updateReleaseDate"/></a>
						<a class="confirmLink" href="deleteStudy?study=${searchResult.accStudy}"><spring:message code="label.deleteStudy"/></a>
						<jsp:useBean id="now" class="java.util.Date" scope="page" />
						<a href="updatepublicreleasedateform?study=${searchResult.accStudy}&date=<fmt:formatDate pattern="dd-MMM-yyyy" value="${now}" />"><spring:message code="label.makeStudyPublic"/></a>
					 </div>
				   </li>
				</ul>
				<div style="clear:both"></div>
			</div>
		</c:when>
		<c:otherwise>
            <c:if test="${!empty curator && searchResult.isPublic}">
                <div class="grid_20 alpha">
                    <a href="alt${searchResult.accStudy}" class="icon icon-generic" data-icon="&gt;"></a> |
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
                    <a href="alt${searchResult.accStudy}" class="icon icon-generic" data-icon="&gt;"></a> |
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
			<c:forEach var="species" items="${searchResult.organisms}">
				<li>${species}</li>
			</c:forEach>
		</ul>
	</div>

	<div class='grid_18 alpha'>
		<strong><spring:message code="label.expFact" /></strong>
		<ul id="resultList">
			<c:forEach var="factor" items="${searchResult.factors}">
				<li>${factor.key}:&nbsp;${factor.value}</li>
			</c:forEach>
		</ul>
	</div>

	<div class='grid_6 omega'>
		<spring:message code="label.expId" />: <strong>${searchResult.accStudy}</strong><br/>
        <spring:message code="label.subm" />&nbsp;<a href="mailto:${searchResult.submitter.email}?subject=<spring:message code="msg.emailStudyLinkSubject"/>&nbsp;${searchResult.accStudy}">${searchResult.submitter.name}&nbsp;${searchResult.submitter.surname}</a>
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
