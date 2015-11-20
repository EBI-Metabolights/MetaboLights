<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2/26/14 3:14 PM
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

<!doctype html>
<html class="no-js" lang="en">
    <title>
        <c:if test="${not empty pageTitle}">
            <c:out value="${pageTitle}"/>
        </c:if>
        <c:if test="${empty pageTitle}">
            <tiles:insertAttribute name="title" ignore="true" />
        </c:if>
    </title>
	<tiles:insertAttribute name="head" ignore="true" />
	<body class="level2">
		<div id="skip-to">
		  <ul>
	 	   <li><a href="#content" title="">Skip to main content</a></li>
		   <li><a href="#local-nav" title="">Skip to local navigation</a></li>
		   <li><a href="#global-nav" title="">Skip to EBI global navigation menu</a></li>
		   <li><a href="#global-nav-expanded" title="">Skip to expanded
		     EBI global navigation menu (includes all sub-sections)</a></li>
		  </ul>
		 </div>
	 	<div id="wrapper" class="container_24">
			<header>
				<tiles:insertAttribute name="header" ignore="true" />
				<tiles:insertAttribute name="localheader" />
			</header>
			<div id="content" role="main" class="grid_24 clearfix">
		 		<tiles:useAttribute name="noBreadCrumb" ignore="true"/>
				<c:if test="${empty noBreadCrumb}">
					<nav id="breadcrumb">
						<p>
							<a href="${pageContext.request.contextPath}/index">MetaboLights</a> &gt; <tiles:insertAttribute name="title" ignore="true" />
						</p>
					</nav>
				</c:if>
				<tiles:insertAttribute name="body" />
		    </div>
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>

