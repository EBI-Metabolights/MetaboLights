<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div style='margin-bottom: 10px;' class="formbox">
	
	<div style='width: 700px;' class='iscell'>
		<b><a href="${searchResult.accStudy}">${searchResult.title}</a></b>
	</div>
	
	<div style='clear: both;'></div>
	<!-- new row -->

	<div style='width: 700px;' class='iscell'>
		<b><spring:message code="label.releaseDate"/>:</b> <fmt:formatDate pattern="dd MMM yyyy" value="${searchResult.releaseDate}"/>
		<c:if test="${!searchResult.isPublic}">
			&nbsp;
			<img src="img/warning.png" height="24px" width="24px" style="vertical-align: middle"/>
			<b><spring:message code="label.expPrivate"/></b>
			&nbsp;
			<!-- TODO: place it in the same line as Private Study -->
			<form name="publish-form" action="publish" method="post">
				<input type="hidden" name="study" value="${searchResult.accStudy}"/>
				<input type="submit" value="" class="publish-button" />
			</form>
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
		<spring:message code="label.expId" />: <b>${searchResult.accStudy}</b><br>
		<spring:message code="label.subm" /> ${searchResult.submitter.name} ${searchResult.submitter.surname}<br>
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
	
	<div style='width: 400px;' class='iscell'>
		<c:if test="${not empty searchResult.publications}">
            <!-- spring:message code="label.pubIn"/-->
            <c:forEach var="pub" items="${searchResult.publications}">
	               <IMG src="img/book.png" height="18"> <a href="http://www.ebi.ac.uk/citexplore/citationDetails.do?externalId=${pub.pubmedId}&dataSource=MED">${pub.title}</a> 
	            <BR>
            </c:forEach>
            <br>
   		</c:if>
   	</div>	

	<div style='clear: both;'></div>
	<!-- new row -->
</div>
