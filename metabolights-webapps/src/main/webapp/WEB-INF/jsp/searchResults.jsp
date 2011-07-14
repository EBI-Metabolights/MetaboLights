<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<script type="text/javascript" src="javascript/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="javascript/jquery-imtechPager.js"></script>
<script type="text/javascript" src="javascript/jquery-highlight.js"></script>


<script type="text/javascript">
function navigate(_pageNumber) {
	filterForm = document.forms['filterForm'];
	pageNumberField = filterForm.elements["pageNumber"];
	pageNumberField.value=_pageNumber;
    filterForm.submit();
}
</script>


<div id="text_header" >
    ${totalHits} <spring:message code="msg.searchResults" />
    <c:if test="${totalHits gt 1}"> 
	    showing ${1+((pageNumber-1)*pageSize)} to 
        <c:if test="${((pageNumber-1)*pageSize)+pageSize lt totalHits }">
	       ${((pageNumber-1)*pageSize)+pageSize}
	    </c:if>
	    <c:if test="${((pageNumber-1)*pageSize)+pageSize ge totalHits }">
	       ${totalHits}
	    </c:if>
    </c:if>     

</div>


<table width="100%">
 <tr>
   <td align="right" >
        <c:if test="${pageNumber ne 1}"> 
           <a href="#"><img ALIGN="texttop" src="img/prev.png" border=0 onClick="navigate(${pageNumber-1})" ></a>
        </c:if>
        <c:if test="${totalNumberOfPages > 1}">
        <c:forEach var="i" begin="${pagerLeft}" end="${pagerRight}" step="1" varStatus ="status">
            <c:if test="${pageNumber eq (i)}"> 
                <b><c:out value="${i}"/></b>&nbsp;
            </c:if>
            <c:if test="${pageNumber ne (i)}"> 
                <a href="#" style="text-decoration:none" > <span style="font-weight:normal" onClick="navigate(${i})"><c:out value="${i}"/></span></a>&nbsp;
            </c:if>
        </c:forEach>            
        </c:if>
        <c:if test="${(((pageNumber-1)*pageSize)+pageSize) lt totalHits}"> 
           <a href="#"><img ALIGN="texttop" src="img/next.png" border=0 onClick="navigate(${pageNumber+1})" ></a>
        </c:if>
   </td>
 </tr>
</table>


<c:if test="${!empty searchResults}">

	<div id="highlight-plugin">
		<div id="content">
			<c:forEach var="searchResult" items="${searchResults}">
				<div class="z">
					<div style='width: 800px; border: 1px solid #D5AE60; margin-bottom: 10px;' class="resultItem">
						
						<!-- div style='width: 950px;' class='iscell'><b><a href="http://wwwdev.ebi.ac.uk/mtbl/entry=${searchResult.accStudy}">${searchResult.title}</a></b></div-->
						<div style='width: 750px;' class='iscell'>
							<b><a href="${searchResult.accStudy}">${searchResult.title}</a></b>
						</div>
						
						<div style='clear: both;'></div>
						<!-- new row -->

						<div style='width: 550px;' class='iscell'>
							<spring:message code="label.expFact" />
							<ul id="resultList">
								<c:forEach var="factor" items="${searchResult.factors}">
									<li><b>${factor.key}:</b> ${factor.value}</li>
								</c:forEach>
							</ul>
						</div>

						<div style='width: 200px;' class='iscell'>
							<spring:message code="label.expId" />: ${searchResult.accStudy}<br>
							<spring:message code="label.subm" /> TODO ${searchResult.userName}<br>						
						</div>
						
						
						<div style='clear: both;'></div>
						<!-- new row -->

						<div style='width: 350px;' class='iscell'>
							<spring:message code="label.assays" />
							<ul id="resultList">
								<c:forEach var="assay" items="${searchResult.assays}">
									<li>${assay.technology} (${assay.count})</li>
								</c:forEach>
							</ul>
						</div>
						
						<div style='width: 400px;' class='iscell'>
							<c:if test="${not empty searchResult.publications}">
					            <spring:message code="label.pubIn"/>
					            <c:forEach var="pub" items="${searchResult.publications}">
						               <a href="http://www.ebi.ac.uk/citexplore/citationDetails.do?externalId=${pub.pubmedId}&dataSource=MED">${pub.title}</a> 
						            <BR>
					            </c:forEach>
					            <br>
					   		</c:if>
					   	</div>	

						<div style='clear: both;'></div>
						<!-- new row -->
						
					</div>
				</div>
			</c:forEach>
		</div>
	</div>

	<table width="100%">
	 <tr>
	   <td align="right" >
	        <c:if test="${pageNumber ne 1}"> 
	           <a href="#"><img ALIGN="texttop" src="img/prev.png" border=0 onClick="navigate(${pageNumber-1})" ></a>
	        </c:if>
	        <c:if test="${totalNumberOfPages > 1}">
	        <c:forEach var="i" begin="${pagerLeft}" end="${pagerRight}" step="1" varStatus ="status">
	            <c:if test="${pageNumber eq (i)}"> 
	                <b><c:out value="${i}"/></b>&nbsp;
	            </c:if>
	            <c:if test="${pageNumber ne (i)}"> 
	                <a href="#" style="text-decoration:none" > <span style="font-weight:normal" onClick="navigate(${i})"><c:out value="${i}"/></span></a>&nbsp;
	            </c:if>
	        </c:forEach>            
	        </c:if>
	        <c:if test="${(((pageNumber-1)*pageSize)+pageSize) lt totalHits}"> 
	           <a href="#"><img ALIGN="texttop" src="img/next.png" border=0 onClick="navigate(${pageNumber+1})" ></a>
	        </c:if>
	   </td>
	 </tr>
	</table>

	<c:if test="${!empty userQueryClean}"> <a href="javascript:void($('#highlight-plugin').removeHighlight().highlight('${userQueryClean}'));">Highlight Search Term</a></c:if>

	<br>

</c:if>

<c:if test="${empty searchResults}">
	<br />
	<br />
	<div class="messageBox">
		<br />
		<h3>
			<spring:message code="msg.nothingFound" />
		</h3>
		<br />
	</div>

</c:if>


</script-->
