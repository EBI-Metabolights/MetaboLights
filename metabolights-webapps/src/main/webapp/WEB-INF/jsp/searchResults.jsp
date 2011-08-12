<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<script type="text/javascript" src="javascript/jquery-1.6.2.min.js"></script>
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
<script>
  var filterWidth=document.getElementById('leftFilterdiv');
  filterWidth.style.width="220px";
  var bodyWidth=document.getElementById('bodyDiv');
  bodyWidth.style.width="785px";
</script>
	<div class="topSpacer">
   		<c:if test="${!empty welcomemessage}">
			<div class="formheader">
				<span class="title">${welcomemessage}</span> 
			</div>
			<br/>
		</c:if>
		<div id="text_header" >
			    ${totalHits} <spring:message code="msg.searchResults" />
		    <c:if test="${totalHits gt 1}"> 
			    <spring:message code="msg.showing" /> ${1+((pageNumber-1)*pageSize)} <spring:message code="msg.to" /> 
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
	</div>
	
	<c:if test="${!empty searchResults}">
	<div id="highlight-plugin">
		<div id="content">
			<c:forEach var="searchResult" items="${searchResults}">
				
				<!--
				***********************************
				SINGLE ENTRY SUMMARY HERE (Include)
				***********************************
				-->
				<%@include file="entrySummary.jsp" %>				
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
	<c:if test="${!empty userQueryClean}">
		<script>
			$('#highlight-plugin').removeHighlight().highlight('${userQueryClean}');
		</script>
	</c:if>
	
	<br>
	</c:if>

	<c:if test="${empty searchResults}">
	<br />
	<br />
	<br />
	<h3>
		<div style="padding-left:100px"><spring:message code="msg.nothingFound" /></div>
	</h3>
	<br />
	</c:if>


