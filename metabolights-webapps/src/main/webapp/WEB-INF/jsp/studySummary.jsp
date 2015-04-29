<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 7/11/14 11:26 AM
  ~ Modified by:   conesa
  ~
  ~
  ~ ©, EMBL, European Bioinformatics Institute, 2014.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>


<%--<div id="hourglass">
    <img src="img/wait.gif" alt="Please wait"/>&nbsp;<b><spring:message code="msg.fetchingData"/></b>
</div>--%>

<div class="grid_24 alpha omega box">
	<c:choose>
		<c:when test="${(!empty welcomemessage || !empty curator) && !(liteStudy.studyStatus == 'PUBLIC') && empty reviewer}">
			<div class="grid_20 alpha">
				<a href="${liteStudy.studyIdentifier}"><strong>${liteStudy.title}</strong></a>
			</div>
			<div class="grid_4 omega">
					<%--
                    <form name="update-form" action="updatestudyform" method="post" class="one-button-form">
                        <input type="hidden" name="study" value="${liteStudy.studyIdentifier}"/>
                        <input type="submit" id="update" class="multi-line-button main" value=" <spring:message code="label.updatestudy"/> ">
                    </form>
                     --%>
				<ul id="sddm">
					<li><a onmouseover="mopen('actions${liteStudy.studyIdentifier}')"onmouseout="mclosetime()">
						Actions<span class="smallArrow"></span></a>
						<div id="actions${liteStudy.studyIdentifier}" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
							<a href="updatestudyform?study=${liteStudy.studyIdentifier}"><spring:message code="label.updatestudy"/></a>
							<a href="updatepublicreleasedateform?study=${liteStudy.studyIdentifier}"><spring:message code="label.updateReleaseDate"/></a>
							<a class="confirmLink" href="deleteStudy?study=${liteStudy.studyIdentifier}"><spring:message code="label.deleteStudy"/></a>
							<jsp:useBean id="now" class="java.util.Date" scope="page" />
							<a href="updatepublicreleasedateform?study=${liteStudy.studyIdentifier}&date=<fmt:formatDate pattern="dd-MMM-yyyy" value="${now}" />"><spring:message code="label.makeStudyPublic"/></a>
						</div>
					</li>
				</ul>
				<div style="clear:both"></div>
			</div>
		</c:when>
		<c:otherwise>
			<c:if test="${!empty curator && (liteStudy.studyStatus == 'PUBLIC')}">
				<div class="grid_20 alpha">
					<a href="${liteStudy.studyIdentifier}"><strong>${liteStudy.title}</strong></a>
				</div>
				<div class="grid_4 omega">
					<ul id="sddm">
						<li><a onmouseover="mopen('actions${liteStudy.studyIdentifier}')"onmouseout="mclosetime()">
							Actions<span class="smallArrow"></span></a>
							<div id="actions${liteStudy.studyIdentifier}" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
								<a href="makestudyprivateform?study=${liteStudy.studyIdentifier}">Make it private</a>
							</div>
						</li>
					</ul>
					<div style="clear:both"></div>
				</div>
			</c:if>
			<c:if test="${empty curator}">
				<div class='grid_24 alpha omega'>
					<a href="${liteStudy.studyIdentifier}"><strong>${liteStudy.title}</strong></a>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>

	<div class='grid_20 alpha omega'>
		<strong><spring:message code="label.releaseDate"/>:</strong> <fmt:formatDate pattern="dd-MMM-yyyy" value="${liteStudy.studyPublicReleaseDate}"/>
		<c:if test="${!(liteStudy.studyStatus == 'PUBLIC')}">
			&nbsp;<div class="ebiicon key"></div><strong>&nbsp;<spring:message code="label.expPrivate"/></strong>
		</c:if>
	</div>

	<div class='grid_20 alpha omega'>
		<br/>
		<strong><spring:message code="label.organism" /></strong>
		<ul id="resultList">
			<c:forEach var="species" items="${liteStudy.organism}">
				<li>${species.organismName}</li>
			</c:forEach>
		</ul>
	</div>

	<div class='grid_18 alpha'>
		<strong><spring:message code="label.expFact" /></strong>
		<ul id="resultList">
			<c:forEach var="factor" items="${liteStudy.factors}">
				<li>${factor.name}</li>
			</c:forEach>
		</ul>
	</div>

	<div class='grid_6 omega'>
		<spring:message code="label.expId" />: <strong>${liteStudy.studyIdentifier}</strong><br/>
		<spring:message code="label.subm" />&nbsp;
		<ul id="resultList">
			<c:forEach var="owner" items="${liteStudy.users}">
				<li>
					<a href="mailto:${owner.email}?subject=<spring:message code="msg.emailStudyLinkSubject"/>&nbsp;${liteStudy.studyIdentifier}">${owner.fullName}</a>
				</li>
			</c:forEach>
		</ul>
	</div>


	<%--<div class='grid_24 alpha omega'>--%>
	<%--<strong><spring:message code="label.assays" /></strong>--%>
	<%--<ul id="resultList">--%>
	<%--<c:forEach var="assay" items="${searchResult.assays}">--%>
	<%--<li>${assay.technology} (${assay.count})</li>--%>
	<%--</c:forEach>--%>
	<%--</ul>--%>
	<%--</div>--%>

</div>
