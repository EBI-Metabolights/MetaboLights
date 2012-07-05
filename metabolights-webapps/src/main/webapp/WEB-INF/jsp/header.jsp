<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="javascript/menu.js"></script>

<div id="top_bar">
	<a href="index"> <!--h1>foobar</h1-->
	</a>

    <h1></h1>

	<div id="search-box">
		<form name="searchForm"	
		   action="<c:choose>
		      <c:when test="${empty action}">search</c:when>
		      <c:otherwise>search</c:otherwise>
		   </c:choose>"
		   method="get" accept-charset="utf-8">

		   <input type="text" name="freeTextQuery" id="query" value="${freeTextQuery}" />
           <input type="submit" value="" class="search-box-button" />
		</form>
	</div>

	<div class="loggedInAsBox">
			<!-- <sec:authorize ifAnyGranted="ROLE_SUBMITTER">		
			<ul id="sddm">	       		
			  <li><a href="<spring:url value="mysubmissions"/>" onmouseover="mopen('m2')"onmouseout="mclosetime()">
			  			<sec:authentication property="principal.firstName" /> <sec:authentication property="principal.lastName"/><span class="smallArrow"></span></a>
				 <div id="m2" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
					<a href="<spring:url value="mysubmissions"/>"><spring:message code="menu.myStudies"/></a> 
					<a href="<spring:url value="myAccount"/>"><spring:message code="menu.myAccount"/></a>  
					<a href="<spring:url value="/j_spring_security_logout"/>"><spring:message code="menu.logout"/></a> 
				 </div>  
			   </li>
			</ul>   
		</sec:authorize>	      	-->
        <a href="https://docs.google.com/document/d/1uL97eA5rozKHyXUbngQJNy-iwQXic7GMVK8bAQSWaCU/view">Help</a>
	</div>	 

 
	<ul id="sddm">
        <sec:authorize ifNotGranted="ROLE_SUBMITTER" >
          <li><a href="login"><spring:message code="menu.login"/><br/>&nbsp;</a></li>
        </sec:authorize>
        <sec:authorize ifAnyGranted="ROLE_SUBMITTER">
			<ul id="sddm">
			  <li><a href="<spring:url value="mysubmissions"/>" onmouseover="mopen('m2')"onmouseout="mclosetime()">
			  			<sec:authentication property="principal.firstName" /> <sec:authentication property="principal.lastName"/><span class="smallArrow"></span></a>
				 <div id="m2" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
					<a href="<spring:url value="mysubmissions"/>"><spring:message code="menu.myStudies"/></a>
					<a href="<spring:url value="myAccount"/>"><spring:message code="menu.myAccount"/></a>
					<a href="<spring:url value="/j_spring_security_logout"/>"><spring:message code="menu.logout"/></a>
					<sec:authorize ifAnyGranted="ROLE_SUPER_USER">
					<a href="<spring:url value="/config"/>"><spring:message code="menu.config"/></a>
					<a href="<spring:url value="/users"/>"><spring:message code="menu.users"/></a>
					</sec:authorize>
					
				 </div>
			   </li>
			</ul>
		</sec:authorize>


		<li><a href="presubmit"><spring:message code="menu.submit"/></a></li>
		<li><a href="download"><spring:message code="menu.download"/></a></li>
        <li><a href="browse"><spring:message code="menu.browse"/></a></li>
		<li><a href="index"><spring:message code="menu.home"/></a></li>
		<!--
		<li><a href="<spring:url value="index"/>" onmouseover="mopen('m1')"onmouseout="mclosetime()"><spring:message code="menu.home"/><span class="smallArrow"></span></a>
			<div id="m1" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
				<a href="<spring:url value="about"/>"><spring:message code="menu.about"/></a>
				<a href="<spring:url value="contact"/>"><spring:message code="menu.contact"/></a>
			</div>
		</li>
		 -->
	</ul>
	<div style="clear: both"></div>

</div>
<script type="text/javascript" language="javascript">
	document.searchForm.query.focus();
</script>
