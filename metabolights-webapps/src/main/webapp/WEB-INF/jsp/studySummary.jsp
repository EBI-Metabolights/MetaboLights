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
<div class="row ml-studybox">

	<div class="ml-study-heading">
		<a href="${liteStudy.studyIdentifier}"><strong>${liteStudy.title}</strong></a>
	</div>

	<div class="ml-study-content">
		<div class="row">
			<div class='col-md-3'>
				<strong><spring:message code="label.validationsStatus"/></strong>
			</div>
			<div class='col-md-9'>
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
			</div>
		</div>
		<div class='row'>
			<div class='col-md-3'>
				<strong><spring:message code="label.releaseDate"/>:</strong>
			</div>
			<div class='col-md-9'>
				<fmt:formatDate pattern="dd-MMM-yyyy" value="${liteStudy.studyPublicReleaseDate}"/> &emsp;
				<c:if test="${!(liteStudy.studyStatus == 'PUBLIC')}">
					&nbsp;<span class="label label-danger"><i class="fa fa-key"></i></i><strong>&nbsp;<spring:message code="label.expPrivate"/></strong></span>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<strong><spring:message code="label.organism" /></strong>
				<ul id="resultList">
					<c:forEach var="species" items="${liteStudy.organism}">
						<li>${species.organismName}</li>
					</c:forEach>
				</ul>
				<strong><spring:message code="label.expFact" /></strong>
				<ul id="resultList">
					<c:forEach var="factor" items="${liteStudy.factors}">
						<li>${factor.name}</li>
					</c:forEach>
				</ul>
			</div>
			<div class="col-md-6">
				<br><br>
				<div class="row">
					<div class='col-md-5'>
						<spring:message code="label.expId" />
					</div>
					<div class='col-md-7'>
						<a href="${liteStudy.studyIdentifier}"><strong>${liteStudy.studyIdentifier}</strong></a>
					</div>
				</div>
				<div class="row">
					<div class='col-md-5'>
						<spring:message code="label.filesize" />
					</div>
					<div class='col-md-7'>
						<strong>${liteStudy.studyHumanReadable}</strong>
					</div>
				</div>
				<div class="row">
					<div class='col-md-5'>
						<spring:message code="label.subm" />
					</div>
					<div class='col-md-7'>
						<ul class="no-bullets" id="resultList">
							<c:forEach var="owner" items="${liteStudy.users}">
								<li><a href="search?users.fullName=${owner.fullName}">${owner.fullName}</a>
									<a href="mailto:${owner.userName}?subject=<spring:message code="msg.emailStudyLinkSubject"/>&nbsp;${liteStudy.studyIdentifier}"><i class="fa fa-envelope-o" aria-hidden="true"></i>
									</a>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>