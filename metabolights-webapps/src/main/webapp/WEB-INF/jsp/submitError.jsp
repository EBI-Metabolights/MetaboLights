<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2/12/13 11:43 AM
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

<br/>

<c:if test="${not empty error}">
	<h2><spring:message code="msg.uploaded.wrong"/></h2>
	<br/><br/>
	<div class="grid_23 alpha omega prefix_1">
		<div class="ebiicon alert_i"></div>&nbsp;<strong><c:out value="${error.message}"/></strong>
		<br/>
		<br/>
		<c:if test="${not empty studyId}">
            <a href="updatestudyform?study=${studyId}"><spring:message code="msg.backToSubmitPage"/></a>
		</c:if>
        <c:if test="${empty studyId}">
            <a href="submittoqueue"><spring:message code="msg.backToSubmitPage"/></a>
        </c:if>
	</div>
</c:if>
