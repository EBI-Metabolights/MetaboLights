<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

       
       <div id="top_bar">
		  <a href="index"><h1>foobar</h1></a>
          <div id="search-box">
		   <form name="searchForm" action="search" method="post" accept-charset="utf-8">
		      <input type="text" name="freeTextQuery"  id="query" value="${freeTextQuery}"/>
	
				<!-- Add FiterItems cheched 
				<c:forEach var="group" items="${filters}">
					<c:forEach var="filter" items="${group.value}">
						<c:if test='${filter.value.isChecked}'>
    						<input 	type="hidden"
								name="${filter.value.name}" 
								value="${filter.value.value}"/>
						</c:if>
							
					</c:forEach>
				</c:forEach>
				-->
		      <button type="submit" class="search-box-button"></button>
		   </form>
         </div>

		  <ul id="navigation">
              <li><a href="<spring:url value="index"/>"><spring:message code="menu.home"/></a></li>
		      <li><a href="biisubmit"><spring:message code="menu.submit"/></a></li>
              <li><a href="about"><spring:message code="menu.about"/></a></li>

              <sec:authorize ifNotGranted="ROLE_SUBMITTER" >
                 <!--  <li><a href="spring_security_login">login</a></li> -->
                 <li><a href="login"><spring:message code="menu.login"/><br>&nbsp;</a></li>
              </sec:authorize> 
 
              <sec:authorize ifAnyGranted="ROLE_SUBMITTER" >
                <li><a href="myAccount"><spring:message code="menu.myAccount"/></a></li>
                <li><a href="<spring:url value="/j_spring_security_logout" htmlEscape="true" />"><spring:message code="menu.logout"/></a><br>
                <span class="loggedInAs"><spring:message code="menu.loggedInAs"/> : <sec:authentication property="principal.userName"/></span></li>  
              </sec:authorize> 
		  </ul>  
       </div>
<script type="text/javascript" language="javascript">
   document.searchForm.query.focus();
</script>
		