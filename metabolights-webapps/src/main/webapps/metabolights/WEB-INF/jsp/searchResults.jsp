<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="javascript/jquery-1.6.1.min.js"></script>                                    
<script src="javascript/jquery-imtechPager.js"></script>            

<c:if test="${!empty searchResults}">
    <div class="example">


        <div id="pagingControlsTop"></div>
        <br>
        <br>

        <div id="content">
	        <c:forEach var="searchResult" items="${searchResults}">
	            <div class="z">
		            <div style='width: 950px; border:1px solid #D5AE60; margin-bottom:10px;'>
		                <div style='width: 900px;' class='iscell'><b><a href="http://wwwdev.ebi.ac.uk/mtbl/entry=${searchResult.accStudy}">${searchResult.title}</a></b></div>
		                <div style='clear: both;'></div><!-- new row -->
		
		                <div style='width: 300px; ' class='iscell' >
		                Experimental factors
		                    <ul>
		                      <c:forEach var="factor" items="${searchResult.factors}">
		                       <li>${factor}</li>
		                      </c:forEach>
		                    </ul>
		                 </div>
		                <div style='width: 600px;' class='iscell' >Experimental identifier : ${searchResult.accStudy}<br>Submitted by ${searchResult.userName}</div>
		                <div style='clear: both;'></div><!-- new row -->
		        
		        
		                <div style='width: 300px; ' class='iscell' >
		                Assays
		                    <ul>
		                      <c:forEach var="assay" items="${searchResult.assays}">
		                       <li>${assay.technology} (${assay.count})</li>
		                      </c:forEach>
		                    </ul>
		                 </div>
		                <div style='width: 600px;' class='iscell'>Publication by TODO ${searchResult.pubAuthors}, details in <a href="http://www.ebi.ac.uk/citexplore/citationDetails.do?externalId=${searchResult.pubId}&dataSource=MED">Citexplore</a></div>
		                <div style='clear: both;'></div><!-- new row -->
		            </div>
	            </div>
	        </c:forEach>
        </div>

        <br>
        <br>
        <div id="pagingControlsBot"></div>
   </div>
</c:if>
     
<c:if test="${empty searchResults}">
	<b>NOTHING FOUND</b>
	<br>
	<br>
	<br>
</c:if>
     
<script type="text/javascript">
var pager = new Imtech.Pager();
$(document).ready(function() {
    pager.paragraphsPerPage = 4; // set amount elements per page
    pager.pagingContainer = $('#content'); // set of main container
    pager.paragraphs = $('div.z', pager.pagingContainer); // set of required containers
    pager.showPage(1);
});
</script>
