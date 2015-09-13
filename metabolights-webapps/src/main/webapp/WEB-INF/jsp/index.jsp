<%@taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>


<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 3/21/14 2:54 PM
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

<%--<script>--%>

<%--// increase the default animation speed to exaggerate the effect--%>
<%--$.fx.speeds._default = 1500;--%>
<%--$(function() {--%>
<%--$( "#dialog" ).dialog({--%>
<%--autoOpen: true,--%>
<%--show: "slide",--%>
<%--position: ['center',200]--%>
<%--});--%>

<%--});--%>

<%--</script>--%>
<br/>
<div class="grid_24">
    <div class="grid_6 alpha lp20">
        <h2><spring:message code="title.serviceName" /></h2>
        <p><spring:message code="msg.metabolightsAbout1" />&nbsp;<spring:message code="msg.metabolightsAbout" /></p>
    </div>
<<<<<<< HEAD


    <div class="grid_10">
        <h2><spring:message code="title.download" /></h2>
        <p>
            <a class="icon icon-generic bigfont" data-icon="T" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/submissionTool/ISAcreatorMetaboLights.zip"></a>
            <spring:message code="msg.metabolightsAbout12" />
        </p>
        <br/>&nbsp;
        <p>
            <a class="icon icon-functional bigfont" data-icon="A" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/"></a>
            <spring:message code="msg.metabolightsAbout7" />
        </p>
    </div>

    <div class="grid_7">
        <a class="twitter-timeline"  href="https://twitter.com/MetaboLights" data-widget-id="642268788906422272">Tweets by @MetaboLights</a>
        <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
    </div>
</div>
<br/>

<%-- Gallery removed --%>
<%--<c:if test="${not empty gallery}">--%>
	<%--<br/>--%>
	<%--<div id="boxes" class="grid_14 alpha omega mb-wrapper">--%>
	 	<%--<c:forEach var="item" items="${gallery}">--%>

            <%--<c:set var="title" value="${item.title}"/>--%>
            <%--<c:set var="description" value="${item.description}"/>--%>

            <%--<div>--%>
			<%--<a href="${item.url}">--%>
			<%--<table>--%>
                <%--<tr>--%>
                    <%--<c:if test="${not empty item.imgUrl}">--%>
                        <%--<c:if test="${fn:length(title) gt 55}"><c:set var="title" value="${fn:substring(title, 0, 52)}..."/></c:if>--%>
                        <%--<c:if test="${fn:length(description) gt 90}"><c:set var="description" value="${fn:substring(description, 0, 87)}..."/></c:if>--%>
                        <%--<td style="width:150px;"><img src="${item.imgUrl}" onerror="this.src='img/large_noImage.gif';"/></td>--%>
                        <%--<td>--%>
                            <%--<a href="${item.url}">--%>
                            <%--<h6 title="${item.title}">${title}</h6>--%>
                            <%--<p>${description}</p>--%>
                            <%--</a>--%>
                        <%--</td>--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${empty item.imgUrl}">--%>
                        <%--<c:if test="${fn:length(title) gt 40}"><c:set var="title" value="${fn:substring(title, 0, 37)}..."/></c:if>--%>
                        <%--<c:if test="${fn:length(description) gt 120}"><c:set var="description" value="${fn:substring(description, 0, 117)}..."/></c:if>--%>
                        <%--<td>--%>
                            <%--<a href="${item.url}">--%>
                            <%--<h6>${title}</h6>--%>
                            <%--<p>${description}</p>--%>
                            <%--</a>--%>
                        <%--</td>--%>
                    <%--</c:if>--%>
                <%--</tr>--%>
			<%--</table>--%>

			<%--</a>--%>
			<%--</div>--%>
	 	<%--</c:forEach>--%>
	<%--</div>--%>

<%--</c:if>--%>

<br/><br/>



=======


    <div class="grid_10">
        <h2><spring:message code="title.download" /></h2>
        <p>
            <a class="icon icon-generic bigfont" data-icon="T" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/submissionTool/ISAcreatorMetaboLights.zip"></a>
            <spring:message code="msg.metabolightsAbout12" />
        </p>
        <br/>&nbsp;
        <p>
            <a class="icon icon-functional bigfont" data-icon="A" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/"></a>
            <spring:message code="msg.metabolightsAbout7" />
        </p>
    </div>

    <div class="grid_7">
        <a class="twitter-timeline"  href="https://twitter.com/MetaboLights" data-widget-id="642268788906422272">Tweets by @MetaboLights</a>
        <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
    </div>
</div>
<br/>

<%-- Gallery removed --%>
<%--<c:if test="${not empty gallery}">--%>
<%--<br/>--%>
<%--<div id="boxes" class="grid_14 alpha omega mb-wrapper">--%>
<%--<c:forEach var="item" items="${gallery}">--%>

<%--<c:set var="title" value="${item.title}"/>--%>
<%--<c:set var="description" value="${item.description}"/>--%>

<%--<div>--%>
<%--<a href="${item.url}">--%>
<%--<table>--%>
<%--<tr>--%>
<%--<c:if test="${not empty item.imgUrl}">--%>
<%--<c:if test="${fn:length(title) gt 55}"><c:set var="title" value="${fn:substring(title, 0, 52)}..."/></c:if>--%>
<%--<c:if test="${fn:length(description) gt 90}"><c:set var="description" value="${fn:substring(description, 0, 87)}..."/></c:if>--%>
<%--<td style="width:150px;"><img src="${item.imgUrl}" onerror="this.src='img/large_noImage.gif';"/></td>--%>
<%--<td>--%>
<%--<a href="${item.url}">--%>
<%--<h6 title="${item.title}">${title}</h6>--%>
<%--<p>${description}</p>--%>
<%--</a>--%>
<%--</td>--%>
<%--</c:if>--%>
<%--<c:if test="${empty item.imgUrl}">--%>
<%--<c:if test="${fn:length(title) gt 40}"><c:set var="title" value="${fn:substring(title, 0, 37)}..."/></c:if>--%>
<%--<c:if test="${fn:length(description) gt 120}"><c:set var="description" value="${fn:substring(description, 0, 117)}..."/></c:if>--%>
<%--<td>--%>
<%--<a href="${item.url}">--%>
<%--<h6>${title}</h6>--%>
<%--<p>${description}</p>--%>
<%--</a>--%>
<%--</td>--%>
<%--</c:if>--%>
<%--</tr>--%>
<%--</table>--%>

<%--</a>--%>
<%--</div>--%>
<%--</c:forEach>--%>
<%--</div>--%>

<%--</c:if>--%>

<br/><br/>



>>>>>>> feature/compound_exporter
<div class="grid_24">
    <%--<h2><spring:message code="title.submit"/> </h2>--%>
    <br/><br/><br/>
    <div class='grid_8 prefix_3 postfix_2'>
        <a href="submittoqueue">
            <div class="bigbutton maincolorI">
                <span class="bigfont"><spring:message code="label.submitNewStudy"/></span><br/>
                <span><spring:message code="label.submitNewStudySub"/></span>
            </div>
        </a>
    </div>
    <div class='grid_8 prefix_2 postfix_2'>
        <a href="mysubmissions?status=PRIVATE">
            <div class="bigbutton seccolorI">

                <span class="bigfont"><spring:message	code="label.updateOldStudy"/></span></br>
                <span><spring:message code="label.updateOldStudySub"/></span>
                </br>
            </div>
        </a>
    </div>
</div>



<%--<script>--%>
<%--/* $('.coolframe').shadow('lifted'); */--%>

<%--$(function(){--%>
<%--$('#boxes').movingBoxes({--%>
<%--startPanel: 1,--%>
<%--currentPanel: 'current',--%>
<%--speed: 0,--%>
<%--initialized: function(e, slider, tar){--%>
<%--slider.options.speed = 500;--%>
<%--},--%>
<%--reducedSize:1,--%>
<%--wrap: true,--%>
<%--fixedHeight: true--%>
<%--}); // add any non-default options inside here--%>
<%--});--%>

<%--</script>--%>


