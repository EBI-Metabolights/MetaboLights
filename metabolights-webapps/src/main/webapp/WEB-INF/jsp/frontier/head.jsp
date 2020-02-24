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
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="MetaboLights metabolomics experiments" />
    <meta name="author" content="The MetaboLights team" />
    <meta http-equiv="Content-Language" content="en-GB" />
    <meta http-equiv="Window-target" content="_top" />
    <meta name="no-email-collection" content="http://www.unspam.com/noemailcollection/" />
    <meta name="keywords" content="metabolite, metabolites, metabolism, metabolic, metabonomics, metabolomics, metabolomics study, metabolomics experiment, metabolic pathway, metabolite database, cosmos" />

    <link rel="stylesheet" href="https://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css">
	<link rel="stylesheet" href="https://www.ebi.ac.uk/inc/css/contents.css" type="text/css">
    <link rel="stylesheet" href="https://www.ebi.ac.uk/web_guidelines/EBI-Icon-fonts/v1.1/fonts.css">

    <link rel="stylesheet" type="text/css" href='<spring:url value="/cssrl/jquery-ui-1.9.2.custom.min.css"/>' media="all" />
    <link rel="stylesheet" type="text/css" href='<spring:url value="/cssrl/icons.css"/>' media="all" />
    <link rel="stylesheet" type="text/css" href='<spring:url value="/cssrl/iconfont/font_style.css"/>'/>

    <%--<link rel="stylesheet" type="text/css" href='<spring:url value="/cssrl/movingboxes.css"/>' media="screen" />--%>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="//cdn.datatables.net/1.10.10/css/dataTables.bootstrap.min.css"/>
    <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.4.2/css/buttons.dataTables.min.css">
    <link rel="stylesheet" href="/metabolights/css/ChEBICompound.css" type="text/css"/>
    <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-tour/0.11.0/css/bootstrap-tour.min.css">

    <script type="text/javascript" src="//code.jquery.com/jquery-1.11.3.js" ></script>
    <script type="text/javascript" src="//code.jquery.com/ui/1.9.2/jquery-ui.min.js" charset="utf-8"></script>

	<%--<script type="text/javascript" src='<spring:url value="/javascript/jquery.movingboxes.js"/>' charset="utf-8"></script>--%>
	<script type="text/javascript" src='<spring:url value="/javascript/menu.js"/>'></script>


    <script type="text/javascript" src="//www.ebi.ac.uk/web_guidelines/js/libs/modernizr.custom.49274.js"></script>

    <%
        String servletPath = request.getAttribute("javax.servlet.forward.request_uri").toString();
        request.setAttribute("servletPath", servletPath);
    %>
    <c:if test="${pageContext.request.serverPort ne 80}">
        <c:set var="port" value=":${pageContext.request.serverPort}"/>
    </c:if>
    <c:set scope="request" var="fullContextPath" value="https://${pageContext.request.serverName}${port}${pageContext.request.contextPath}"/>

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
    <link rel="stylesheet" type="text/css" href='<spring:url value="/css/ml.css"/>' media="screen" />
</head>