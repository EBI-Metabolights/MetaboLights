<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">
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
			<nav id="breadcrumb">
				<p>
					<a href="index">MetaboLights</a> &gt; <tiles:insertAttribute name="title" ignore="true" />
				</p>
			</nav>
			<tiles:insertAttribute name="body" />
	    </div>
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>

