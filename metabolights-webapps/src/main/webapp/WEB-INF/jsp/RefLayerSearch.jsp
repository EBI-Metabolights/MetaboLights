
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


	<div class="grid_24" >
		<div class="grid_24">
			<div class="grid_6 alpha">
				<h6><b><spring:message code="ref.msg.filterResults"></spring:message></b></h6>
			</div>
			<div class="grid_12">
				<c:choose>
					<c:when test="${not empty query}">
						<h6><b>${queryResults} <spring:message code="ref.msg.searchResult">${query}</spring:message></b></h6>
					</c:when>
					<c:otherwise>
						<h6><b>${queryResults} <spring:message code="ref.msg.emptyBrowse" ></spring:message></b></h6>
					</c:otherwise>
				</c:choose>
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
									<input type="checkbox" name="technology" value="${technology.key}" <c:if test="${technology.value eq true}">CHECKED</c:if>
									onclick="this.form.submit();">
										${technology.key}
								</ul>
							</c:forEach>
					</div>
					<br/>
					<div class="grid_24 genericDiv">
						<b><spring:message code="ref.msg.organism" ></spring:message></b>
						<c:forEach var="RefLayerOrg" items="${RefLayer}">
							<c:forEach var="orghash" items="${RefLayerOrg.orgHash}">
									<ul style="max-height:400px; overflow:auto" id="organisms">
									<input type="checkbox" name="organisms" value="${orghash.key}" <c:if test="${orghash.value eq true}">CHECKED</c:if> 
									onclick="this.form.submit();">
										${orghash.key}
								</ul>
							</c:forEach>
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