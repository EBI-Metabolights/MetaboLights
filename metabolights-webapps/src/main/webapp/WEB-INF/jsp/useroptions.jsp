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

<sec:authorize access="hasRole('ROLE_SUBMITTER')">
    <sec:authentication var="token" property="principal.apiToken" />
        <br>
        <div class="panel panel-info">
            <div class="panel-heading">Hi <sec:authentication property="principal.firstName" />, <spring:message code="msg.useroptions" /></div>
            <div class="panel-body">
                <div class="clearfix">&nbsp;</div>
                <div class="col-md-12">
                    <div class="row">
                        <%--<div class="col-md-3">--%>
                            <%--<div class="form-group">--%>
                                <%--<a target="_blank" id="_labsLink" class="btn btn-default btn-md form-control ml--noborder">--%>
                                    <%--<i class="fa fa-tachometer" aria-hidden="true"></i>&nbsp;--%>
                                    <%--<spring:message code="menu.myWorkspaceCap" />&nbsp;<span class="label label-warning">Coming soon</span>--%>
                                <%--</a>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="col-md-2">
                            <div class="form-group">
                                <a id="redirectToMyStudiesPage" class="btn btn-default btn-md form-control ml--noborder">
                                    <i class="fa fa-list-alt" aria-hidden="true"></i>&nbsp;
                                    <spring:message code="menu.myStudiesCap" />
                                </a>
                            </div>
                        </div>
                        <%--<div class="col-md-3">--%>
                            <%--<div class="form-group">--%>
                                <%--<a id="claimStudies" href="#" target="_blank" class="btn btn-default btn-md form-control ml--noborder">--%>
                                    <%--<i class="thorOrcIdSpan">--%>
                                        <%--<img src="img/orcid_bw.png"></i>&nbsp;Batch Claim Studies--%>
                                <%--</a>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="col-md-2">
                            <div class="form-group">
                                <a href="<spring:url value="myAccount"/>" class="btn btn-default btn-md form-control ml--noborder">
                                    <i class="fa fa-cogs" aria-hidden="true"></i>&nbsp;
                                    <spring:message code="menu.myAccountCaps" />
                                </a>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <a id="userLoggingOut" href="#" class="btn btn-default btn-md form-control ml--noborder">
                                    <i class="fa fa-sign-out" aria-hidden="true"></i>&nbsp;
                                    <spring:message code="menu.logoutCaps" />
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <script>
        $('#userLoggingOut').click(function () {
            localStorage.removeItem("jwt");            
        })
    </script>
</sec:authorize>
<br>
<sec:authorize access="hasRole('ROLE_SUPER_USER')">
        <div class="panel panel-success">
            <div class="panel-heading"><spring:message code="msg.useroptionscurator" /></div>
            <div class="panel-body">
                <div class="clearfix">&nbsp;</div>
                <div class="col-md-12">
                    <div class="row">
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
                            <%--<div class="col-md-3">--%>
                            <%--<div class="form-group">--%>
                            <%--<a href="<spring:url value="tools"/>" class="btn btn-default btn-md form-control ml--noborder">--%>
                            <%--<i class="fa fa-pencil-square-o" aria-hidden="true"></i>&nbsp;--%>
                            <%--<spring:message code="menu.toolsCaps" />--%>
                            <%--</a>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="col-md-3">--%>
                            <%--<div class="form-group">--%>
                            <%--<a href="<spring:url value="compoundsDashboard"/>" class="btn btn-default btn-md form-control ml--noborder">--%>
                            <%--<i class="fa fa-dashboard" aria-hidden="true"></i>&nbsp;--%>
                            <%--<spring:message code="menu.compoundsDashboard" />--%>
                            <%--</a>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                    </div>
                </div>
            </div>
        </div>
</sec:authorize>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js"></script>
<script>
    const setCookie = (name, value, days = 7, path = '/') => {
        const expires = new Date(Date.now() + days * 864e5).toUTCString()
        document.cookie = name + '=' + encodeURIComponent(value) + '; expires=' + expires + '; path=' + path
      }
        const getCookie = (name) => {
        return document.cookie.split('; ').reduce((r, v) => {
          const parts = v.split('=')
          return parts[0] === name ? decodeURIComponent(parts[1]) : r
        }, '')
      }
      const deleteCookie = (name, path) => {
        setCookie(name, '', -1, path)
      }
      
      $(document).ready(function () {

        $('#redirectToMyStudiesPage').click(function(){
            metabolightsEditorUrl = "${metabolightsEditorUrl}";
            metabolightsPythonWsUrl = "${metabolightsPythonWsUrl}";
            jwt = getCookie("jwt");
            
            if (jwt == null){
                jwt = getCookie("jwt");
                
            }
            if(jwt != null && jwt != ''){
                localJwt = localStorage.getItem("jwt");
                if (jwt != localJwt){
                    localStorage.setItem('jwt', jwt);
                }
                
                axios.get(metabolightsPythonWsUrl + "/auth/create-onetime-token", { headers: { "Authorization" : "Bearer " + jwt}}).then(response => {
                    loginOneTimeToken = response.data.one_time_token;
                    if (loginOneTimeToken != null){
                        window.open(metabolightsEditorUrl + "?loginOneTimeToken="+loginOneTimeToken, 'toolbar=no, menubar=no,scrollbars=yes,resizable=yes');
                    } else {
                        window.open(metabolightsEditorUrl, 'toolbar=no, menubar=no,scrollbars=yes,resizable=yes');
                    }
                }).catch((error) => { 
                    if (error.response && error.response.status == 401) {
                        localStorage.removeItem("jwt");
                    } 
                    window.open(metabolightsEditorUrl, 'toolbar=no, menubar=no,scrollbars=yes,resizable=yes');
                  });
                ;
            } else {
                window.open(metabolightsEditorUrl, 'toolbar=no, menubar=no,scrollbars=yes,resizable=yes');
            }
            
        });
    });
    $('#userLoggingOut').click(function(){
        localStorage.removeItem('jwt');
        deleteCookie("jwt", "/metabolights");
        window.location.href = '/metabolights/logout';
    });
</script>