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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css" />

<br/>
<div class="container-fluid">
    <div class ="row">
        <div class="col-md-3 col">
            <h2><spring:message code="title.serviceName" /></h2>
            <p><spring:message code="msg.metabolightsAbout1" />&nbsp;<spring:message code="msg.metabolightsAbout" /></p>
        </div>
        <div class="col-md-5 col">
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

        <div class="col-md-4 col">
            <a class="twitter-timeline"  href="https://twitter.com/MetaboLights" data-widget-id="642268788906422272">Tweets by @MetaboLights</a>
            <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>
        </div>
    </div>
</div>
<br/>

<div class="container">
    <%--<h2><spring:message code="title.submit"/> </h2>--%>
    <br/><br/><br/>
    <div class='col-md-6'>
        <a href="submittoqueue">
            <div class="bigbutton maincolorI">
                <span class="bigfont"><spring:message code="label.submitNewStudy"/></span><br/>
                <span><spring:message code="label.submitNewStudySub"/></span>
            </div>
        </a>
    </div>
    <div class='col-md-6'>
        <a href="mysubmissions?status=PRIVATE">
            <div class="bigbutton seccolorI">

                <span class="bigfont"><spring:message	code="label.updateOldStudy"/></span></br>
                <span><spring:message code="label.updateOldStudySub"/></span>
                </br>
            </div>
        </a>
    </div>
</div>

<c:if test="${not empty param.message || not empty message}">
    <div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-labelledby="messageModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <p class="modal-title text-center" id="messageModalLabel">MetaboLights message</p>
                </div>
                <div class="modal-body">
                    <c:if test="${not empty param.message}">
                        <p><b><c:out value="${param.message}"/></b></p>
                    </c:if>
                    <c:if test="${not empty message}">
                        <p><b><c:out value="${message}"/></b></p>
                    </c:if>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">OK</button>
                </div>
            </div>
        </div>
    </div>
</c:if>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://raw.githubusercontent.com/flatlogic/bootstrap-tabcollapse/master/bootstrap-tabcollapse.js"></script>

<script>
    $(function() {
        $('#messageModal').modal('show');
    });
</script>
