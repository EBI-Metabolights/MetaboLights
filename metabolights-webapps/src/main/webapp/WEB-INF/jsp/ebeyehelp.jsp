
<%--@author Tejasvi --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<h1>Welcome to Ebeye-client help/Search Page.</h1>
<br />

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
	<div style="overflow: auto; width: 100%" class='iscell'>
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