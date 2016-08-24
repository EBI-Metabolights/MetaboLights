<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 12/23/13 2:25 PM
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

<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

<div class="container-fluid">
    <div class="col-md-12">
        <div class="row">
            <h3><spring:message code="menu.downloadHelp" /></h3><br>
            <div class="well">
                <p>
                    <a class="icon icon-generic bigfont" data-icon="T" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/submissionTool/ISAcreatorMetaboLights.zip"></a>
                    <spring:message code="msg.metabolightsAbout12" />
                <p>
            </div>
            <div class="well">
                <p>
                    <a class="icon icon-functional bigfont" data-icon="A" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/"></a>
                    <spring:message code="msg.metabolightsAbout7" />
                </p>
            </div>

            <br>
            <p><spring:message code="msg.metabolightsAbout13" /></p>
            <p><spring:message code="msg.metaboLightsAbout16" />&nbsp;<spring:message code="msg.metabolightsAbout8"/></p>
            <p>MetaboLights XML exports can be downloaded using the following links:</p>
            <p><a class="btn btn-default" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/eb-eye/eb-eye_metabolights_complete.xml" target="_blank">MetaboLights - Complete </a>&emsp;|&emsp;<a class="btn btn-default" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/eb-eye/eb-eye_metabolights.xml" target="_blank">MetaboLights</a></p>

        </div>
    </div>
</div>
<hr>
<div class="container-fluid">
    <div class="col-md-12">
        <div class="row">
            <a class="noLine" href="<spring:message code="url.isatools"/>"><img src="img/softwaresuitelogo2.png" alt="ISAtools"/></a>
            <p><spring:message code="msg.metabolightsAbout6" /></p>
        </div>
    </div>
</div>