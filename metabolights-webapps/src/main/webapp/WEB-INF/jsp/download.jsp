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
<div class="col-md-12">
    <p class="row">
    <h3><spring:message code="menu.downloadHelp" /></h3><br>
    <% String agent = request.getHeader ("user-agent");
        String userOS = "";
        try {
            if (agent.toLowerCase().indexOf("mac") >= 0) {
                userOS = "mac";
            } else {
                userOS = "non-mac";
            }
        }catch (Exception e){}
    %>
    <%
        String protocol = "http";
        if(userOS.equals("mac")){
            protocol = "ftp";
        }
    %>
    <div class="well nbr">
        <p>
            <a class="icon icon-functional bigfont" data-icon="A" href="<%=protocol%>://ftp.ebi.ac.uk/pub/databases/metabolights/"></a>
            <a href="<%=protocol%>://ftp.ebi.ac.uk/pub/databases/metabolights/"><b>Experiments</b></a>  <spring:message code="msg.metabolightsAbout7" />
        </p>
    </div>
    <p><spring:message code="msg.metabolightsAbout13" /></p>
    <br>
    <p>MetaboLights study metadata and compounds  can be downloaded as XML format. Please use the below links:</p><br>
    <p><a class="btn btn-default mt5" href="<%=protocol%>://ftp.ebi.ac.uk/pub/databases/metabolights/eb-eye/eb-eye_metabolights_complete.xml" target="_blank">MetaboLights - Complete </a>&emsp;|&emsp;<a class="mt5 btn btn-default" href="<%=protocol%>://ftp.ebi.ac.uk/pub/databases/metabolights/eb-eye/eb-eye_metabolights_studies.xml" target="_blank">MetaboLights</a></p>
    <div>
        <h4>ISA model support</h4>
        <div class="col-md-12">
            <p class="row">
                <br>
            <div class="well nbr">
                <a class="noLine" href="<spring:message code="url.isatools"/>"><img src="img/softwaresuitelogo2.png" alt="ISAtools"/></a>
                <p><spring:message code="msg.metabolightsAbout6" /></p>
            </div>
            </p>
        </div>
        <p>
            <a class="icon icon-generic bigfont" data-icon="T" href="<%=protocol%>://ftp.ebi.ac.uk/pub/databases/metabolights/submissionTool/ISAcreatorMetaboLights.zip"></a>
            <a href="<%=protocol%>://ftp.ebi.ac.uk/pub/databases/metabolights/submissionTool/ISAcreatorMetaboLights.zip"><spring:message code="msg.metabolightsMtblsIsaCreator" /> </a>  <spring:message code="msg.metabolightsAbout12" />
        </p>
        <br>
        <p><spring:message code="msg.metabolightsAbout8"/>&nbsp;<spring:message code="msg.metaboLightsAbout16" /></p>
    </div>
</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>