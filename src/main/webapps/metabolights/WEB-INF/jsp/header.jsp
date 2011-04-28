<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

       
       <div id="top_bar">
		  <h1><a href="index">Metabolights metabolomics data repository</a></h1>
		  <ul id="navigation">
		      <!-- li class="selected"><a href="index">home</a></li-->
              <li><a href="<spring:url value="index"/>">home</a></li>
		      <li><a href="submit">submit</a></li>
              <li><a href="about">about</a></li>

              <sec:authorize ifNotGranted="ROLE_SUBMITTER" >
                 <!--  <li><a href="spring_security_login">login</a></li> -->
                 <li><a href="login">login</a></li>
              </sec:authorize> 
 
              <sec:authorize ifAnyGranted="ROLE_SUBMITTER" >
                <!--  li><a href="j_spring_security_logout">logout</a> (<sec:authentication property="principal.userId"/>)  </li> -->
                <li><a href="<spring:url value="/j_spring_security_logout" htmlEscape="true" />">logout</a> (<sec:authentication property="principal.userName"/>) </li>  
              </sec:authorize> 

		  </ul>  
       </div>
 
		<div id="search-box">
		   <form name="searchForm" action="search" method="post" accept-charset="utf-8">
		   <div>
		      <label ><b><spring:message code="label.search"/></b></label>
		      <input type="text" name="query"  id="query" />
		      <button type="submit" class="search-box-button"></button>
		      <!-- a href="/advancedSearch">Advanced database search</a--> 
		   </div>
		   </form>
		</div> 

<script type="text/javascript" language="javascript">
   document.searchForm.query.focus();
</script>
		