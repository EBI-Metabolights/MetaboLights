<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

       
       <div id="top_bar">
		  <a href="index"><h1>foobar</h1></a>
          <div id="search-box">
 		   <form 	name="searchForm" 
		   			action="<c:choose>
		   						<c:when test="${empty action}">search</c:when>
		   						<c:otherwise>${action}</c:otherwise>
		   					</c:choose>"
		   			method="get"
		   			accept-charset="utf-8">
		      <input type="text" name="freeTextQuery" type="submit" id="query" value="${freeTextQuery}"/>
		      <input type="submit" value="" class="search-box-button"/>
		      <!--
		      <input type="text" name="freeTextQuery"  id="query" value="${freeTextQuery}"/>
		      <button type="submit" class="search-box-button"></button>
		      -->
		   </form>
         </div>
         <div class="loggedInAsBox">
          <sec:authorize ifAnyGranted="ROLE_SUBMITTER" >
            <span class="loggedInAs"><spring:message code="menu.loggedInAs"/>: <sec:authentication property="principal.userName"/>
            </span>
          </sec:authorize> 

         </div>
		  <ul id="navigation">
              <!--<li><a href="<spring:url value="index"/>"><spring:message code="menu.home"/></a></li>    -->
		      <li><a href="biisubmit"><spring:message code="menu.submit"/></a></li>
		      <li><a href="download"><spring:message code="menu.download"/></a></li>
              <!--<li><a href="about"><spring:message code="menu.about"/></a></li>              -->

              <sec:authorize ifNotGranted="ROLE_SUBMITTER" >
                <li><a href="login"><spring:message code="menu.login"/><br>&nbsp;</a></li>
              </sec:authorize> 
 
              <sec:authorize ifAnyGranted="ROLE_SUBMITTER" >
                <li><a href="myAccount"><spring:message code="menu.myAccount"/></a></li>
                <li><a href="<spring:url value="/j_spring_security_logout" htmlEscape="true" />"><spring:message code="menu.logout"/></a></li><br>
              </sec:authorize> 
		  </ul>  

       </div>
<script type="text/javascript" language="javascript">
   document.searchForm.query.focus();
</script>
		