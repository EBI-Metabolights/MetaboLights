<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="text_header" style="background-color: white; padding-top: 35px">
	<spring:message code="label.metaboLightsEntry" />
	${document.accStudy}
</div>

<div id="content">
	<div style='width: 1000px; border: 1px solid #D5AE60; margin-bottom: 10px;'>
		<b>${document.title}</b>
		<br>
		<br>
		<b><spring:message code="label.organism" />:</b> ${document.organism}
		<br>
		<br>
		<b><spring:message code="label.description" />:</b> ${document.description}
		<br>
		<br>
	    <b><spring:message code="label.design" /></b>
            <ul id="resultList">
             	<c:forEach var="design" items="${document.design}">
                	<li>${design}</li>
                </c:forEach>
            </ul>
		<br>		
	    <b><spring:message code="label.expFact" /></b>
            <ul id="resultList">
             	<c:forEach var="factor" items="${document.factors}">
                	<li>${factor}</li>
                </c:forEach>
            </ul>
		<br>
		<b><spring:message code="label.assays"/></b>
        	<ul id="resultList">
            	<c:forEach var="assay" items="${document.assays}">
                	<li>${assay.technology} (${assay.count})</li>
                </c:forEach>
           </ul>
		<br>
		<b><spring:message code="label.sampleAttr"/></b>
        	<ul id="resultList">
            	<c:forEach var="property" items="${document.properties}">
                	<li>${property}</li>
                </c:forEach>
           </ul>
		<br>		
		<b><spring:message code="label.pubBy"/></b>: TODO, store in index ${searchResult.pubAuthors}
		<b><spring:message code="label.pubIn"/></b> <a href="http://www.ebi.ac.uk/citexplore/citationDetails.do?externalId=${searchResult.pubId}&dataSource=MED">Citexplore</a>
		<br>
		<br>
	    <b><spring:message code="label.subm" />:</b> ${document.userName}
		<br>
		<br>
	</div>
</div>	