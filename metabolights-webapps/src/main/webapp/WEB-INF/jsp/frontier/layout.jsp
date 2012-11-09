<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">

<head>
  	<meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta name="description" content="Metabolights metabolmics experiments" />
    <meta name="author" content="The Metabolights team" />
    <meta http-equiv="Content-Language" content="en-GB" />
    <meta http-equiv="Window-target" content="_top" />
    <meta name="no-email-collection" content="http://www.unspam.com/noemailcollection/" />
    <meta name="keywords" content="metabolite, metabolism, metabolic, metabonomics, metabolomics study, metabolomics experiment, metabolic pathway, metabolite database" />
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css">
	<link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css">
	<link rel="stylesheet" href="http://wwwdev.ebi.ac.uk/web_guidelines/css/compliance/develop/boilerplate-style.css">
	<link rel="stylesheet" href="http://wwwdev.ebi.ac.uk/web_guidelines/css/compliance/develop/ebi-global.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="http://wwwdev.ebi.ac.uk/web_guidelines/css/compliance/develop/ebi-visual.css" type="text/css" media="screen">
	<link rel="stylesheet" href="http://wwwdev.ebi.ac.uk/web_guidelines/css/compliance/develop/984-24-col-fluid.css" type="text/css" media="screen" />
	<link rel="stylesheet" href="http://wwwdev.ebi.ac.uk/web_guidelines/css/compliance/develop/embl-petrol-colours.css" type="text/css" media="screen">

	<!-- you can replace this with [projectname]-colours.css. See http://frontier.ebi.ac.uk/web/style/colour for details of how to do this -->
	<!-- link rel="stylesheet" type="text/css" href="cssrl/metabolights-reflayer-colours.css" media="screen"/> -->
	<link rel="stylesheet" type="text/css" href="cssrl/jquery-ui-1.9.0.custom.min.css" media="all" />
	<link rel="stylesheet" type="text/css" href="cssrl/iconfont/font_style.css" media="all" />
	<link rel="stylesheet" type="text/css" href="cssrl/reflayer.css" media="screen" />
   
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
	
</head>
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
			<tiles:insertAttribute name="body" />
	    </div>
		<tiles:insertAttribute name="footer" />

	</div>
</body>
</html>

