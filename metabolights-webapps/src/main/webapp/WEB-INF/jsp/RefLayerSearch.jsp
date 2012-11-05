
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<h3><b>Welcome to Reference Layer Search Page.</b></h3>

	<div class="grid_24" >
		<div class="grid_24">
			<div class="grid_6 alpha">
				<h6><b>Filter your results.</b></h6>
			</div>
			<div class="grid_12">
				<h6><b>Search results for ${query}</b></h6>
			</div>
			<div class="grid_6 omega">
				<h6><b>Other EBI results</b></h6>
			</div>
		</div>
		<div class="grid_6 alpha">
				<form name="Filters" id="filterForm" action="#" method="post">
					
					<div class="grid_24 genericDiv">
						<b><spring:message code="ref.msg.technology" ></spring:message></b>
							<c:forEach var="technology" items="${technologyList}">
								<ul style="max-height:400px; overflow:auto" id="technology" >
									<input type="checkbox" name="technology" value="${technology}" onclick="this.form.submit();">
										${technology}
								</ul>
							</c:forEach>
					</div>
					<br/>
					<div class="grid_24 genericDiv">
						<b><spring:message code="ref.msg.organism" ></spring:message></b>
						<c:forEach var="organisms" items="${organismList}">
							<ul style="max-height:400px; overflow:auto" id="organisms">
								<input type="checkbox" name="organisms" value="${organisms}" onclick="this.form.submit();">
									${organisms}
							</ul>
						</c:forEach>
					</div>
					<input type="hidden" name="query" value="<c:out value="${query}"/>"/>
	       			<input type="hidden" name="pageNumber" value="1"/>
				</form>
		</div>
		<c:if test="${not empty entries}">
		<div class="grid_12">
			<c:forEach var="entry" items="${entries}">
				<div style='clear: both;'></div>
				<div class="grid_24 genericDiv" >
					<div class="grid_8 alpha" >
						<img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${entry.chebiURL}"  width="100px" height="100px" />
					</div>
					<div  class="grid_16 omega">
						<div class="grid_24">
								<b>${entry.name}</b>
								(${entry.accession})
								<a href='<spring:message code="ref.msg.chebi.url"></spring:message>${entry.chebiURL}'>${entry.chebiId}</a>
						</div>
						<div class="grid_24">
							<c:forEach var="iupacNameList" items="${entry.iupac}" varStatus="loopStatus">
								<c:choose>
									<c:when test="${loopStatus.index eq 0}">
										<b><spring:message code="ref.msg.iupac"/></b>
									</c:when>
									<c:otherwise>
										,
									</c:otherwise>
								</c:choose>
								${iupacNameList}
							</c:forEach>
						</div>
						<br/>
						<br/>
						<br/>
						<div class="grid_24">
							<c:forEach var="MTBLStudiesList" items="${entry.MTBLStudies}" varStatus="loopStatus">
								<c:choose>
									<c:when test="${loopStatus.index eq 0}">
										<b><spring:message code="ref.msg.mtbl.studies"/></b>
									</c:when>
									<c:otherwise>
										,
									</c:otherwise>
								</c:choose>
								<a href="<spring:message code="ref.msg.mtbls.url"></spring:message><c:out value="${MTBLStudiesList}"></c:out>">${MTBLStudiesList}</a>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		</c:if>
		<div class="grid_6 omega genericDiv">
			<p><b>Space reserved for 'Other EBI results' facet</b></p>
		</div>
	</div>
	<div class="grid_24">
		<br/>
		<br/>
	</div>




















<%--

 http://frontier.ebi.ac.uk/interpro/relatedresults?q=${query}

<p><b>Space reserved for Facets.</b></p>
********Working Code below*******************

		
		<div class="grid_10 omega">
			<table border="1">
				<tr>
					<td rowspan="3">
						<img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ChebiNameImage}" alt="" width="100px" height="100px" class="logo" />
					</td>
					<td>
						<c:forEach var="nameFieldList" items="${Namefields}">
							<b>${nameFieldList}</b>
						</c:forEach>
						<c:forEach var="queryIDList" items="${queryIDfields}">(${queryIDList})</c:forEach>
						<c:forEach var="ChebiIdList" items="${ChebiNamefields}">
							<a href="<spring:message code="ref.msg.chebi.url"></spring:message><c:out value="${ChebiIdList}"></c:out>">${ChebiIdList}</a>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>
						<c:forEach var="iupacNameList" items="${IUPACNamefields}" varStatus="loopStatus">
							<c:choose>
								<c:when test="${loopStatus.index eq 0}">
									<b><spring:message code="ref.msg.iupac"/></b>
								</c:when>
								<c:otherwise>
									,
								</c:otherwise>
							</c:choose>
							${iupacNameList}
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>
						<c:forEach var="MTBLStudiesList" items="${MTBLStudiesfields}" varStatus="loopStatus">
							<c:choose>
								<c:when test="${loopStatus.index eq 0}">
									<b><spring:message code="ref.msg.mtbl.studies"/></b>
								</c:when>
								<c:otherwise>
									,
								</c:otherwise>
							</c:choose>
							<a href="<spring:message code="ref.msg.mtbls.url"></spring:message><c:out value="${MTBLStudiesList}"></c:out>">${MTBLStudiesList}</a>
						</c:forEach>
					</td>
				</tr>
			</table>
		</div>
	</div>


**********Working code above**********



<a href="<spring:message code="ref.msg.chebi.image.url"></spring:message><c:out value="${ChebiNameImage}"></c:out>">${ChebiNameImage}</a>

			</tr>
		</table>
	</div>
	<div class='grid_10'>
		<table border="1">
			<tr>

----------------------------------------------------------------------------------
		</table>
	</div>
	<div style="width: 400px;" class='iscell'>
		<table border="1">
MTBLStudiesfields

<spring:message code="ref.msg.iupac" />${iupacNameList}

<c:if test="${not empty queryIDfields}"></c:if>

			<c:forEach var="MTBLFieldsList" items="${MTBLReqFields}">
				<tr>

				</tr>
			</c:forEach>

<form action="RefLayerSearch">
</form>

	<input type="text" name="query" placeholder="search" value="${query}">
	<input type="submit" value="Search">

			<c:forEach var="MTBLFieldsList" items="${MTBLReqFields}">
				<tr>
					<c:forEach var="MTBLFields" items="${MTBLFieldsList.string}">
						<td>${MTBLFields}</td>
					</c:forEach>
				</tr>
			</c:forEach>

			<c:forEach var="MTBLFields" items="${MTBLFields}">
				<c:if test="${not empty MTBLFields}">
					<th>${MTBLFields}</th>
				</c:if>
			</c:forEach>
			<c:forEach var="MTBLEntries" items="${getMTBLEntries}">
				<tr>
					<c:forEach var="Entries" items="${MTBLEntries.string}">
							<td>${Entries}</td>
					</c:forEach>
				</tr>
			</c:forEach>
			
---------------------------------------------
<div>
	<table>
		<tr>
			<c:forEach value="${MTBLFields}">
			</c:out>
		</tr>
	</table>
</div>


<div>
	<table border="1" >
		<c:forEach var="MTBLResults" items="${MTBLResults}">
			<tr>
				<td>${MTBLResults}</td>
			</tr>
		</c:forEach>
	</table>
</div>
 --%>