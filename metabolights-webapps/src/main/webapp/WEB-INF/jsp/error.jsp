<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2/4/13 2:55 PM
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

<br/><br/>
<div class="grid_6 prefix_1 alpha">
	<img src='<spring:url value="/img/error2.png"/>'/>
</div>
<div class="grid_17 omega">
<br/><br/>
<span class="error">
  <spring:message code="msg.error.general" />
  <c:out value="${errorMainMessage}"/>
</span>
<br/>
<span class="bgcolor">
	<c:if test="${!empty errorStack}">
		<c:out value="${errorStack}" escapeXml="false"  />
	<br/>
	</c:if>
<%-- 	
	<c:if test="${!empty pageContext.exception}">
		<c:out value="${pageContext.exception.message}"/><br/><br/>
		<c:forEach var="element" items="${pageContext.exception.stackTrace}">
    		<c:out value="${element}"/><br/>
		</c:forEach>
	</c:if>
 --%>
 </span>


<br/>
</div>
