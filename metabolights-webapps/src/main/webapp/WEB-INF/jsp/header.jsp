<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 4/23/13 10:33 AM
  ~ Modified by:   conesa
  ~
  ~
  ~ ©, EMBL, European Bioinformatics Institute, 2014.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

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

		   <input type="text" name="freeTextQuery" id="query" placeholder="Search MetaboLights" value="${freeTextQuery}" />
           <input type="submit" value="" class="search-box-button" />
		</form>

	</div>

	<div class="loggedInAsBox">
			<ul id="sddm">	       		
			  <li><a href="<spring:url value="mysubmissions"/>" onmouseover="mopen('m2')"onmouseout="mclosetime()">
			  			<sec:authentication property="principal.firstName" /><span class="smallArrow"></span></a>
				 <div id="m2" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
					<a href="<spring:url value="mysubmissions"/>"><spring:message code="menu.myStudies"/></a> 
					<a href="<spring:url value="myAccount"/>"><spring:message code="menu.myAccount"/></a>  
					<a href="<spring:url value="/logout"/>"><spring:message code="menu.logout"/></a> 
				 </div>  
			   </li>
			</ul>   
		</sec:authorize>	      	--%>
	</div>	 

 
	<ul id="sddm">
        <sec:authorize access="!hasRole('ROLE_SUBMITTER')" >
          <li><a href="login"><spring:message code="menu.login"/><br/>&nbsp;</a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_SUBMITTER')">
			<ul id="sddm">
			</ul>
		</sec:authorize>

        <li><a href="help"><spring:message code="menu.help"/></a></li>
		<li><a href="editor"><spring:message code="menu.submit"/></a></li>
		<li><a href="download"><spring:message code="menu.download"/></a></li>
        <li><a href="browse"><spring:message code="menu.browse"/></a></li>
		<li><a href="index"><spring:message code="menu.home"/></a></li>
		<%--
		<li><a href="<spring:url value="index"/>" onmouseover="mopen('m1')"onmouseout="mclosetime()"><spring:message code="menu.home"/><span class="smallArrow"></span></a>
			<div id="m1" onmouseover="mcancelclosetime()" onmouseout="mclosetime()">
				<a href="<spring:url value="about"/>"><spring:message code="menu.about"/></a>
				<a href="<spring:url value="contact"/>"><spring:message code="menu.contact"/></a>
			</div>
		</li>
		 --%>
	</ul>
	<div style="clear: both"></div>

</div>
<script type="text/javascript" language="javascript">
	document.searchForm.query.focus();
</script>
