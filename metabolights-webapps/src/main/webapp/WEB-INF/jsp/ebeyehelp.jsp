
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 11/27/12 3:22 PM
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

<%--@author Tejasvi --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<h2>Welcome to Ebeye-client help/Search Page.</h2>

<div style='width: 300px;' class='iscell'>
	<spring:message code="ebeye.msg.query"></spring:message>
	<br /> <br />
	<form action="ebeyehelp">
		<input placeholder="wsdlurl" name="url" value="${url}"> 
		<input placeholder="Query" name="query" value="${query}"> 
		<select name="domain">
			<c:forEach var="DomainLists" items="${listDomains}">
				<c:choose>
					<c:when test="${domain eq DomainLists}">
						<option selected="selected">${DomainLists}</option>
					</c:when>
					<c:otherwise>
						<option>${DomainLists}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select> <br /> <br /> <input type="submit" value="Submit" />
	</form>
</div>

<div style='clear: both;'></div>

<c:if test="${not empty getEntries}">
	<div style="overflow: auto; width: 90%" class='iscell'>
		<table border="1">
			<c:forEach var="FieldsList" items="${listFields}">
				<c:if test="${not empty listFields}">
					<th>${FieldsList}</th>
				</c:if>
			</c:forEach>
			<c:forEach var="Entry" items="${getEntries}">
				<tr>
					<c:forEach var="fieldvalue" items="${Entry.string}">
						<td>${fieldvalue}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>

<%--
<c:if test="${not empty arrayOfarrayOfEntries}">

	<div style="overflow: auto; width: 100%" class='iscell'>
		<table border="1">
			<c:forEach var="AddFieldsList" items="${ListAddRefFields}">
				<c:if test="${not empty ListAddRefFields}">
					<th>${AddFieldsList}</th>
				</c:if>
			</c:forEach>
			<c:forEach var="AddEntry" items="${arrayOfarrayOfEntries}">
				<tr>
					<c:forEach var="Addfieldvalue" items="${AddEntry.string}">
						<td>${Addfieldvalue}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</div>
</c:if>
 --%>