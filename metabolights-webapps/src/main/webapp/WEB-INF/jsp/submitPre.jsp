<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 1/25/13 4:50 PM
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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

<div class="container-fluid">
    <div class="row">
        &nbsp;
    </div>
    <div class="panel panel-default">
        <div class="panel-header">
            <h3 class="heading text-center"><c:if test="${!empty user}">Hi ${user.firstName}. </c:if><spring:message code="msg.submCredentialsShort" /></h3>
        </div>
        <div class="panel-body">
            <div class="col-md-12">
                &nbsp;
            </div>
            <div class="col-md-12">
                <div class="row">
                    <div class="col-md-4 col-md-offset-2 ">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <p>
                                <h4><span class="bigfont"><b><spring:message code="label.submitNewStudy"/></b></span></h4>
                                <span><small><spring:message code="label.submitNewStudySub"/></small></span>
                                </p>
                                <br>
                                <a href="submittoqueue" class="btn btn-success">
                                    Submit now
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <p>
                                <h4>
                                    <span class="bigfont"><b><spring:message	code="label.updateOldStudy"/></b></span>
                                </h4>
                                <span><small><spring:message code="label.updateOldStudySub"/></small></span>
                                </p>
                                <br>
                                <a href="mysubmissions?status=PRIVATE" class="btn btn-primary">
                                    Update now
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                &nbsp;
            </div>
        </div>
        <div class="panel-footer">
            <p class="text-center"><spring:message code="msg.metabolightsAbout11" /></p>
        </div>
    </div>
</div>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>