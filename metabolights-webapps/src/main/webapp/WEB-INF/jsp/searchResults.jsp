<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="javascript/jquery-1.6.1.min.js"></script>                                    
<script src="javascript/jquery-imtechPager.js"></script>            

<br/>

<div id="text_header">
    <spring:message code="msg.searchResults"/> <c:if test="${!empty userQuery}">"${userQuery}"</c:if>
</div>

<br/>

<c:if test="${!empty searchResults}">
     <div id="pagingControlsTop"></div>
     <br>
     <br>

     <div id="content">
      <c:forEach var="searchResult" items="${searchResults}">
          <div class="z">
           <div style='width: 1000px; border:1px solid #D5AE60; margin-bottom:10px;'>
               <div style='width: 950px;' class='iscell'><b><a href="http://wwwdev.ebi.ac.uk/mtbl/entry=${searchResult.accStudy}">${searchResult.title}</a></b></div>
               <div style='clear: both;'></div><!-- new row -->

               <div style='width: 350px; ' class='iscell' >
               <spring:message code="label.expFact" />
                   <ul id="resultList">
                     <c:forEach var="factor" items="${searchResult.factors}">
                      <li>${factor}</li>
                     </c:forEach>
                   </ul>
                </div>

               <div style='width: 600px;' class='iscell' >
                  <spring:message code="label.expId"/> : ${searchResult.accStudy}<br>
                  <spring:message code="label.subm" /> ${searchResult.userName}</div>
               <div style='clear: both;'></div><!-- new row -->
       
       
               <div style='width: 350px; ' class='iscell' >
               <spring:message code="label.assays"/>
                   <ul id="resultList">
                     <c:forEach var="assay" items="${searchResult.assays}">
                      <li>${assay.technology} (${assay.count})</li>
                     </c:forEach>
                   </ul>
                </div>
               <div style='width: 600px;' class='iscell'><spring:message code="label.pubBy"/> TODO ${searchResult.pubAuthors}, <spring:message code="label.pubIn"/> <a href="http://www.ebi.ac.uk/citexplore/citationDetails.do?externalId=${searchResult.pubId}&dataSource=MED">Citexplore</a></div>
               <div style='clear: both;'></div><!-- new row -->
           </div>
          </div>
      </c:forEach>
     </div>

     <br>
     <br>
     <div id="pagingControlsBot"></div>
     <br>
     <br>
     
</c:if>
     
<c:if test="${empty searchResults}">
   <br/><br/>
   <div class="messageBox">
      <br/>
      <h3><spring:message code="msg.nothingFound"/></h3>
      <br/>
   </div>
	
</c:if>

     
<script type="text/javascript">
var pager = new Imtech.Pager();
$(document).ready(function() {
    pager.paragraphsPerPage = 10; // set amount elements per page
    pager.pagingContainer = $('#content'); // set of main container
    pager.paragraphs = $('div.z', pager.pagingContainer); // set of required containers
    pager.showPage(1);
});
</script>
