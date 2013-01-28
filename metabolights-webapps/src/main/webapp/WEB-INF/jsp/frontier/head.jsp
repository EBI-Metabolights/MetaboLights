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

	<link rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/compliance/mini/ebi-fluid-embl.css">
   	<!-- you can replace this with [projectname]-colours.css. See http://frontier.ebi.ac.uk/web/style/colour for details of how to do this -->
  	<!-- also inform ES so we can host your colour palette file -->
 	<%--<link rel="stylesheet" href="http://www.ebi.ac.uk/web_guidelines/css/compliance/develop/embl-petrol-colours.css" type="text/css" media="screen">--%>

	<!-- you can replace this with [projectname]-colours.css. See http://frontier.ebi.ac.uk/web/style/colour for details of how to do this -->
	<%--<link rel="stylesheet" type="text/css" href="cssrl/icons.css" media="screen" />--%>


    <%--Purple darker--%>
    <%--<link rel="stylesheet" href="cssrl/acaac5-scheme.css" type="text/css" media="screen">--%>
    <%--<link rel="stylesheet" type="text/css" href="cssrl/metabolights_acaac5.css" media="screen" />--%>

    <%--Purple even more darker--%>
    <%--<link rel="stylesheet" href="cssrl/645FAA-scheme.css" type="text/css" media="screen">--%>
    <%--<link rel="stylesheet" type="text/css" href="cssrl/metabolights_645FAA.css" media="screen" />--%>

    <%--Test--%>
    <link rel="stylesheet" href="cssrl/test-scheme.css" type="text/css" media="screen">
    <link rel="stylesheet" type="text/css" href="cssrl/metabolights_test.css" media="screen" />


    <link rel="stylesheet" type="text/css" href="cssrl/jquery-ui-1.9.2.custom.min.css" media="all" />
    <link rel="stylesheet" type="text/css" href="cssrl/icons.css" media="all" />

<%--<link rel="stylesheet" type="text/css" href="cssrl/jquery.shadow.css" media="screen" />--%>
 	<link rel="stylesheet" type="text/css" href="cssrl/movingboxes.css" media="screen" />
 	<script type="text/javascript" src="javascript/jquery-1.8.3.js" charset="utf-8"></script>
	<script type="text/javascript" src="javascript/jquery-ui-1.9.2.custom.min.js" charset="utf-8"></script>
	<%--<script type="text/javascript" src="javascript/jquery.shadow.js" charset="utf-8"></script>--%>
	<script type="text/javascript" src="javascript/jquery.movingboxes.js" charset="utf-8"></script>
	<script type="text/javascript" src="javascript/menu.js"></script>
	
   
    <title><tiles:insertAttribute name="title" ignore="true" /></title>

    <c:if test="${pageContext.request.serverName!='www.ebi.ac.uk'}" >
        <script type="text/javascript">var redline = {};redline.project_id = 196734042;</script>
        <script id="redline_js" src="http://www.redline.cc/assets/button.js" type="text/javascript"> </script>
        <script>
            $(document).ready(function() {
                setTimeout(function(){
                    // Handler for .ready() called.
                    $("#redline_side_car").css("background-image","url(img/redline_left_button.png)");
                    $("#redline_side_car").css("display", "block");
                    $("#redline_side_car").css("z-index", "-1");
                },1000);
            });
        </script>
    </c:if>
</head>