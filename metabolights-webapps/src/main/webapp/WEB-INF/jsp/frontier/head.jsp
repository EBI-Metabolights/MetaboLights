<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<head>
  	<meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta name="description" content="MetaboLights metabolomics experiments" />
    <meta name="author" content="The MetaboLights team" />
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
	<link rel="stylesheet" type="text/css" href="cssrl/icons.css" media="screen" />
	<link rel="stylesheet" type="text/css" href="cssrl/reflayer.css" media="screen" />
 	<script type="text/javascript" src="javascript/jquery-1.8.2.js" charset="utf-8"></script>
	<script type="text/javascript" src="javascript/jquery-ui-1.9.0.custom.min.js" charset="utf-8"></script>
	<script type="text/javascript" src="javascript/menu.js"></script>
	
   
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
	
	<c:if test="${pageContext.request.serverName!='www.ebi.ac.uk'}" >
    	<script type="text/javascript">var redline = {};redline.project_id = 196734042;</script>
    	<script id="redline_js" src="http://www.redline.cc/assets/button.js" type="text/javascript"> </script>
	</c:if>
	
</head>