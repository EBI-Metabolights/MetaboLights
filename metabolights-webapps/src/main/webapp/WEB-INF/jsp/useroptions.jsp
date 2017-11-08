<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 12/17/13 12:38 PM
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


<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">

<sec:authorize ifAnyGranted="ROLE_SUBMITTER">
    <sec:authentication var="token" property="principal.apiToken" />
    <div class="container">
        <br><br>
        <div class="panel panel-info">
            <div class="panel-heading">Hi <sec:authentication property="principal.firstName" />, <spring:message code="msg.useroptions" /></div>
            <div class="panel-body">
                <div class="clearfix">&nbsp;</div>
                <div class="col-md-12">
                    <div class="col-md-3">
                        <div class="form-group">
                            <a target="_blank" id="labsLink" class="btn btn-default btn-md form-control ml--noborder">
                                <i class="fa fa-tachometer" aria-hidden="true"></i>&nbsp;
                                <spring:message code="menu.myWorkspaceCap" />
                            </a>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <a href="<spring:url value="mysubmissions"/>" class="btn btn-default btn-md form-control ml--noborder">
                                <i class="fa fa-list-alt" aria-hidden="true"></i>&nbsp;
                                <spring:message code="menu.myStudiesCap" />
                            </a>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <a id="claimStudies" href="#" target="_blank" class="btn btn-default btn-md form-control ml--noborder">
                                <i class="thorOrcIdSpan">
                                    <img src="img/orcid_bw.png"></i>&nbsp;
                                Batch Claim studies to ORCID
                            </a>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <a href="<spring:url value="myAccount"/>" class="btn btn-default btn-md form-control ml--noborder">
                                <i class="fa fa-cogs" aria-hidden="true"></i>&nbsp;
                                <spring:message code="menu.myAccountCaps" />
                            </a>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <a href="<spring:url value="/j_spring_security_logout"/>" class="btn btn-default btn-md form-control ml--noborder">
                                <i class="fa fa-sign-out" aria-hidden="true"></i>&nbsp;
                                <spring:message code="menu.logoutCaps" />
                            </a>
                        </div>
                    </div>
                </div>
                <div class="clearfix">&nbsp;</div>
            </div>
        </div>
    </div>
    <script>
        $(function () {
            var token = {
                token : "<c:out value="${token}"/>"
            }
            $('#labsLink').on('click', function () {
                $.ajax({
                    url: '/metabolights/webservice/labs/authenticateToken',
                    type: 'post',
                    contentType: "application/json",
                    data: JSON.stringify(token),
                    success: function (data, textStatus, response) {
                        var jwt = response.getResponseHeader('jwt');
                        var user = response.getResponseHeader('user');
                        localStorage.setItem('jwt', jwt);
                        localStorage.setItem('user', user);
                        window.location.href = "/metabolights/labs";
                    },
                    error: function (request, status, error) {
                        alert(request.responseText);
                    }
                });
            });
        });

    </script>
    <script>
        function getStudyIdsForClaimPrioritization() {
            var token = {
                token : "<c:out value="${token}"/>"
            }
            console.log("json  token = " + token['token']);
            console.log(JSON.stringify(token));
            $.ajax({
                cache: false,
                url: "/metabolights/webservice/studyids",
                type: 'post',
                contentType: "application/json",
                data: JSON.stringify(token),
                success: function (studyIdList) {
                    var ebiClaimAllMetaboLightsUrl = "https://www.ebi.ac.uk/ebisearch/search.ebi?db=metabolights&query=domain_source:metabolights";
                    if(studyIdList['content'].length > 0){
                        var idsQueryPart = "&id:/MTBLS("
                        for (var i = 0; i < studyIdList['content'].length; i++) {
                            var studyId =    studyIdList['content'][i];
                            var ids = studyId.split("S");
                            idsQueryPart += ids[1]+ "|"
                        }
                        idsQueryPart += ")";
                        document.getElementById("claimStudies").href= ebiClaimAllMetaboLightsUrl + idsQueryPart;
                       }
                    else{
                        document.getElementById("claimStudies").href= ebiClaimAllMetaboLightsUrl;
                    }
                }
            });
        }
        getStudyIdsForClaimPrioritization();
 </script>
</sec:authorize>
<br><br>
<sec:authorize ifAnyGranted="ROLE_SUPER_USER">
    <div class="container">
        <div class="panel panel-success">
            <div class="panel-heading"><spring:message code="msg.useroptionscurator" /></div>
            <div class="panel-body">
                <div class="clearfix">&nbsp;</div>
                <div class="col-md-12">
                    <div class="col-md-3">
                        <div class="form-group">
                            <a href="<spring:url value="config"/>" class="btn btn-default btn-md form-control ml--noborder">
                                <i class="fa fa-cog" aria-hidden="true"></i>&nbsp;
                                <spring:message code="menu.configCaps" />
                            </a>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <a href="<spring:url value="users"/>" class="btn btn-default btn-md form-control ml--noborder">
                                <i class="fa fa-users" aria-hidden="true"></i>&nbsp;
                                <spring:message code="menu.usersCaps" />
                            </a>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <a href="<spring:url value="tools"/>" class="btn btn-default btn-md form-control ml--noborder">
                                <i class="fa fa-pencil-square-o" aria-hidden="true"></i>&nbsp;
                                <spring:message code="menu.toolsCaps" />
                            </a>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <a href="<spring:url value="compoundsDashboard"/>" class="btn btn-default btn-md form-control ml--noborder">
                                <i class="fa fa-dashboard" aria-hidden="true"></i>&nbsp;
                                <spring:message code="menu.compoundsDashboard" />
                            </a>
                        </div>
                    </div>
                </div>
                <div class="clearfix">&nbsp;</div>
            </div>
        </div>
    </div>
</sec:authorize>

