<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


	<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 4/23/13 10:33 AM
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

    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="ml--loginContainer">
                <%--<div class="media text-center">
                    <div class="media-left">
                            <i class="fa fa-3x fa-sign-in"></i>
                    </div>
                    <div class="media-body">
                        <h4 class="media-heading">
                           Log In
                        </h4>
                    </div>
                </div>
                <br>--%>

                <div class="ml-loginpanelhead">
                    <h3 class="text-center"><img style="height: 50px;" src="${pageContext.request.contextPath}/img/MetaboLightsLogo.png">&nbsp;MetaboLights</h3>
                </div>

                    <div class="ml-loginpanelbody">
                <c:if test="${not empty fromsubmit}">
                    <p><strong><spring:message code="msg.submHeader"/></strong></p>
                </c:if>

                <form name="loginForm" action="<c:url value='j_spring_security_check'/>" method="post">

                    <c:if test="${not empty param.login_error}">
                        <p class="error">
                            <!-- Your login attempt was not successful, try again.<br/>-->
                            <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                        </p>
                    </c:if>

                    <div class="form-group">
                        <label><spring:message code="label.email" /></label>
                        <input class="form-control" type='text' name='j_username'/>
                    </div>

                    <div class="form-group">
                        <label><spring:message code="label.password" /></label>
                        <input class="form-control" type='password' name='j_password'/>
                    </div>

                    <div class="form-group">
                        <div class="form-group">
                            <input name="submit" type="submit" class="submit btn btn-primary form-control" value="<spring:message code="label.login"/>">
                        </div>
                        <%--<input name="cancel" type="button" class="submit cancel" value="<spring:message code="label.cancel"/>" onclick="location.href='index'">--%>
                    </div>
                    <small><a href="forgotPassword"><spring:message code="label.oopsForgot" /></a></small>
                    <br>
                </form>
                        </div>
            </div>
            <p class="text-center"><a href="newAccount" class="icon icon-functional" data-icon="7"></a>
                <a href="newAccount"><spring:message code="label.needNewAccount"/></a></p>
        </div>
    </div>

<script type="text/javascript" language="javascript">
    document.loginForm.j_username.focus();
</script>
