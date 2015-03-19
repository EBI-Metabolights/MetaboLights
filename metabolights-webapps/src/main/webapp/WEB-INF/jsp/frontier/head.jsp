<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2015-Mar-18
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2015 EMBL - European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

<head>
  	<meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
    <meta name="description" content="MetaboLights metabolomics experiments" />
    <meta name="author" content="The MetaboLights team" />
    <meta http-equiv="Content-Language" content="en-GB" />
    <meta http-equiv="Window-target" content="_top" />
    <meta name="no-email-collection" content="http://www.unspam.com/noemailcollection/" />
    <meta name="keywords" content="metabolite, metabolites, metabolism, metabolic, metabonomics, metabolomics, metabolomics study, metabolomics experiment, metabolic pathway, metabolite database, cosmos" />
    <link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css">
	<link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css" type="text/css">

	<link rel="stylesheet" href="//www.ebi.ac.uk/web_guidelines/css/compliance/mini/ebi-fluid-embl.css">
   	<!-- you can replace this with [projectname]-colours.css. See http://frontier.ebi.ac.uk/web/style/colour for details of how to do this -->
  	<!-- also inform ES so we can host your colour palette file -->
 	<%--<link rel="stylesheet" href="http://www.ebi.ac.uk/web_guidelines/css/compliance/develop/embl-petrol-colours.css" type="text/css" media="screen">--%>

	<!-- you can replace this with [projectname]-colours.css. See http://frontier.ebi.ac.uk/web/style/colour for details of how to do this -->
	<%--<link rel="stylesheet" type="text/css" href="cssrl/icons.css" media="screen" />--%>

    <%--Test--%>
    <link rel="stylesheet" href='<spring:url value="/cssrl/test-scheme.css"/>' type="text/css" media="screen">
    <link rel="stylesheet" type="text/css" href='<spring:url value="/cssrl/metabolights_test.css"/>' media="screen" />
    <link rel="stylesheet" type="text/css" href='<spring:url value="/cssrl/jquery-ui-1.9.2.custom.min.css"/>' media="all" />
    <link rel="stylesheet" type="text/css" href='<spring:url value="/cssrl/icons.css"/>' media="all" />
 	<link rel="stylesheet" type="text/css" href='<spring:url value="/cssrl/movingboxes.css"/>' media="screen" />

    <script type="text/javascript" src="//code.jquery.com/jquery-1.8.3.min.js" ></script>
    <script type="text/javascript" src="//code.jquery.com/ui/1.9.2/jquery-ui.min.js" charset="utf-8"></script>

	<script type="text/javascript" src='<spring:url value="/javascript/jquery.movingboxes.js"/>' charset="utf-8"></script>
	<script type="text/javascript" src='<spring:url value="/javascript/menu.js"/>'></script>
    <script type="text/javascript" src="//www.ebi.ac.uk/web_guidelines/js/libs/modernizr.custom.49274.js"></script>

    <%
        String servletPath = request.getAttribute("javax.servlet.forward.request_uri").toString();
        request.setAttribute("servletPath", servletPath);
    %>
    <c:if test="${pageContext.request.serverPort ne 80}">
        <c:set var="port" value=":${pageContext.request.serverPort}"/>
    </c:if>
    <c:set scope="request" var="fullContextPath" value="http://${pageContext.request.serverName}${port}${pageContext.request.contextPath}"/>
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

    <%-- Add Google Analytics to the prod instance only   --%>
    <c:if test="${pageContext.request.serverName=='www.ebi.ac.uk'}" >
        <script type="text/javascript">
            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-60886861-1', 'auto');
            ga('send', 'pageview');

        </script>
    </c:if>

</head>
