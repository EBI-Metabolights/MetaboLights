
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
function navigate(_pageNumber) {
	filterForm = document.forms['filterForm'];
	pageNumberField = filterForm.elements["PageNumber"];
	pageNumberField.value=_pageNumber;
    filterForm.submit();
}
</script>
<c:if test="${not empty entries}">
<div class="grid_6 alpha">
	<h6>
		<b><spring:message code="ref.msg.filterResults"></spring:message></b>
	</h6>
</div>
<div class="grid_12">
	<div class="grid_24">
		<c:choose>
			<c:when test="${not empty query}">
				<h6>
					<b>${queryResults} <spring:message code="ref.msg.searchResult">${query}</spring:message></b>
				</h6>
			</c:when>
			<c:otherwise>
				<h6>
					<b>${queryResults} <spring:message code="ref.msg.emptyBrowse"></spring:message></b>
				</h6>
			</c:otherwise>
		</c:choose>
	</div>

	<div class="grid_24 title alpha">
		<div class="grid_4 aplha">
			<b>Page:&nbsp;${currrentPage}</b>
		</div>
		<div class="grid_20">
		<!-- <b><spring:message code="ref.msg.Navigate"/></b>-->
			<span id="pagination" class="right"> 
				<c:set var="RemainderValue"	value="${queryResults % 10}" /> 
				<c:set var="CrudeNumOfPages" value="${queryResults / 10}" /> 
				<c:set var="NumOfPages" value="${fn:split(CrudeNumOfPages, '.')}" /> 
				<c:set var="pageSize" value="10"/>
				<c:if test="${currentPage eq 1 }">
					<c:set var="pagerLeft" value="${currentPage}"/>
				</c:if>
				<c:set var="pagerRight" value="${currentPage +1}"/>
				<c:forEach var="loop" items="${NumOfPages}" varStatus="loopStatus">
						<c:if test="${loopStatus.index eq 0}">
							<c:set var="NumOfPages" value="${loop}" />
						</c:if>
						<c:if test="${loopStatus.index eq 1}">
							<c:set var="RemainderItems" value="${loop}" />
						</c:if>
				</c:forEach> 
				<c:if test="${RemainderItems ne 0 }">
					<c:set var="NumOfPages" value="${NumOfPages+1}" />
				</c:if> 
				<c:if test="${currrentPage lt 1}"> 
		           <a href="#"><img ALIGN="texttop" src="img/prev.png" border=0 onClick="navigate(${currrentPage})" ></a>
		        </c:if>
		        <c:if test="${currrentPage gt 1}"> 
		           <a href="#"><img ALIGN="texttop" src="img/prev.png" border=0 onClick="navigate(${currrentPage-1})" ></a>
		        </c:if>
			    <c:if test="${NumOfPages > 1}">
			    	<c:if test="${currrentPage ne 1 }">
			    		<a href="#" style="text-decoration:none" > <span style="font-weight:normal" onClick="navigate(1)"><c:out value="1"/></span></a>
			    	</c:if>
			        <c:if test="${currrentPage > 2 }">
			        	....<a href="#" style="text-decoration:none" > <span style="font-weight:normal" onClick="navigate(${currrentPage-1})"><c:out value="${currrentPage-1}"/></span></a>&nbsp;
			        </c:if>
			        ${currrentPage}&nbsp;
			        <c:choose>
			        	<c:when test="${currrentPage eq NumOfPages }">
			        		&nbsp;
			        	</c:when>
			        	<c:otherwise>
			        		<a href="#" style="text-decoration:none" > <span style="font-weight:normal" onClick="navigate(${currrentPage+1})"><c:out value="${currrentPage+1}"/></span></a>....
			        	</c:otherwise>
			        </c:choose>
			        <c:if test="${currrentPage ne NumOfPages }">
			        	<a href="#" style="text-decoration:none" > <span style="font-weight:normal" onClick="navigate(${NumOfPages})"><c:out value="${NumOfPages}"/></span></a>
			      	</c:if>        
		        </c:if>
		        <c:if test="${(((currrentPage-1)*pageSize)+pageSize) lt queryResults}"> 
		           <a href="#"><img ALIGN="texttop" src="img/next.png" border=0 onClick="navigate(${currrentPage+1})" ></a>
		        </c:if>
			</span>
		</div>
		<!-- Have to work on below Jump To part 
		<div class="grid_8 omega">
		<b><spring:message code="ref.msg.JumpTo"></spring:message></b>
			<form name="JumpTo" id="JumpToForm" action="#" method="post">
				<select id = JumpToPage">
			        <c:forEach var="i" begin="${pagerLeft+1}" end="${NumOfPages}" step="1" varStatus ="status">
							<option value="${i}">${i}</option>
					</c:forEach>
			    </select>
		    </form>
		</div>	-->
	</div>
</div>
<div class="grid_6 omega">
	<h6>
		<b>Other EBI results</b>
	</h6>
</div>

<div class="grid_24 clearfix" />
<div class="grid_24">
	<br />
</div>

<div class="grid_6 alpha">
	<form name="Filters" id="filterForm" action="#" method="post">
		<div class="grid_24 refLayerBox">
			<b><spring:message code="ref.msg.technology"></spring:message></b>
			<c:forEach var="technology" items="${technologyList}">
				<ul style="max-height: 400px; overflow: auto" id="technology">
					<input type="checkbox" name="technology" value="${technology.key}"
						<c:if test="${technology.value eq true}">CHECKED</c:if>
						onclick="this.form.submit();"> ${technology.key}
				</ul>
			</c:forEach>
		</div>
		<br />
		<div class="grid_24 refLayerBox">
			<b><spring:message code="ref.msg.organism"></spring:message></b>
			<c:forEach var="RefLayerOrg" items="${RefLayer}">
				<c:forEach var="orghash" items="${RefLayerOrg.orgHash}">
					<ul style="max-height: 400px; overflow: auto" id="organisms">
						<input type="checkbox" name="organisms" value="${orghash.key}"
							<c:if test="${orghash.value eq true}">CHECKED</c:if>
							onclick="this.form.submit();"> ${orghash.key}
					</ul>
				</c:forEach>
			</c:forEach>
		</div>

		<input type="hidden" name="query" value="<c:out value="${query}"/>" />
		<input type="hidden" name="PageNumber" value="1" />
	</form>
</div>

	<div class="grid_12">
		<c:forEach var="entry" items="${entries}">
			<div style='clear: both;'></div>
			<div class="grid_24 refLayerBox">
				<div class="grid_8 alpha">
					<a href="${entry.accession}"><img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${entry.chebiURL}" onerror="this.src='img/large_noImage.gif';"
						width="100px" height="100px" /></a>
				</div>
				<div class="grid_16 omega">
					<div class="grid_24">
						<b>${entry.name}</b> (<a href="${entry.accession}">${entry.accession}</a>)
						<a href='<spring:message code="ref.msg.chebi.url"></spring:message>${entry.chebiURL}'>${entry.chebiId}</a>
					</div>
					<div class="grid_24">
						<c:forEach var="iupacNameList" items="${entry.iupac}" varStatus="loopStatus">
							<c:if test="${iupacNameList ne 'null' }">
								<c:choose>
									<c:when test="${loopStatus.index eq 0}">
										<b><spring:message code="ref.msg.iupac" /></b>
									</c:when>
									<c:otherwise>
											,
									</c:otherwise>
								</c:choose>
								${iupacNameList}
							</c:if>
						</c:forEach>
					</div>
					<br /> <br /> <br />
					<div class="grid_24">
						<c:forEach var="MTBLStudiesList" items="${entry.MTBLStudies}"
							varStatus="loopStatus">
							<c:choose>
								<c:when test="${loopStatus.index eq 0}">
									<b><spring:message code="ref.msg.mtbl.studies" /></b>
								</c:when>
								<c:otherwise>
										,
									</c:otherwise>
							</c:choose>
							<a
								href="${MTBLStudiesList}">${MTBLStudiesList}</a>
						</c:forEach>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>

<div class="grid_6 omega refLayerBox">
	<p>
		<b>Space reserved for 'Other EBI results' facet</b>
	</p>
</div>
<div class="grid_24">
	<br />
</div>
<div class="grid_24">
		<div class="grid_6 alpha">
		<br/>
		</div>
		<div class="grid_12">
			<div id="paginationBottom" class="grid_24 title alpha" ></div>
				<script>$('#pagination').clone().appendTo('#paginationBottom');</script>
		</div>
		<div class="grid_6 omega">
		<br/>
		</div>
	</div>
</c:if>
<c:if test="${empty entries }">
	<div class="grid_6 alpha">
		<br/>
	</div>
	<div class="grid_12">
		<b><spring:message code="ref.msg.noResult"/><a href="MTBLC1358">Acetic acid</a>, <a href="MTBLC1402">Alanine</a>, <a href="MTBLC1547">Benzene</a> and so on...</b>
	</div>
	<div class="grid_6 alpha">
		<br/>
	</div>
</c:if>
<div class="grid_24">
	<br /> <br />
</div>