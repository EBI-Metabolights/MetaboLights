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
<div class="grid_24 alpha omega box">

	<div class='grid_24 alpha omega'>
		<a href="${liteStudy.studyIdentifier}"><strong>${liteStudy.title}</strong></a>
	</div>

	<div class='grid_20 alpha omega'>
		<br/>
		<strong><spring:message code="label.validationsStatus"/>:&nbsp;</strong>
		<c:if test="${liteStudy.validations.status == 'RED'}">
			<span class="redTrafficL"/>
			<span class="offTrafficL"/>
			<span class="offTrafficL"/>
		</c:if>
		<c:if test="${liteStudy.validations.status == 'AMBER'}">
			<span class="offTrafficL"/>
			<span class="amberTrafficL"/>
			<span class="offTrafficL"/>
		</c:if>
		<c:if test="${liteStudy.validations.status == 'GREEN'}">
			<span class="offTrafficL"/>
			<span class="offTrafficL"/>
			<span class="greenTrafficL"/>
		</c:if>
		<br/>
		<br/>
	</div>

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
		<spring:message code="label.expId" />: <a href="${liteStudy.studyIdentifier}"><strong>${liteStudy.studyIdentifier}</strong></a><br/>
		<spring:message code="label.filesize" />: <strong>${liteStudy.studyHumanReadable}</strong><br/>
		<spring:message code="label.subm" />&nbsp;
		<ul id="resultList">
			<c:forEach var="owner" items="${liteStudy.users}">
				<li>
					<a href="mailto:${owner.userName}?subject=<spring:message code="msg.emailStudyLinkSubject"/>&nbsp;${liteStudy.studyIdentifier}">${owner.fullName}</a>
				</li>
			</c:forEach>
		</ul>
	</div>

</div>