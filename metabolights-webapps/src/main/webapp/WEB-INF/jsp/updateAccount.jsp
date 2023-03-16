<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

	<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 6/10/14 11:57 AM
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
    <div class="col-md-12">
        <h3 class="heading"><spring:message code="menu.myAccountCaps" /></h3>
        <div class="col-md-3">
            &nbsp;
        </div>
        <div class="col-md-6">
            <div class="ml--loginContainer">
                <div class="ml-loginpanelhead">
                    <h3>Update account details</h3>
                    <p><spring:message code="msg.updateAccount" /></p>
                </div>
                <div class="ml-loginpanelbody">
                    <div class="row">
                        <form:form name="accountForm" action="updateAccount" method="post" commandName="metabolightsUser">
                            <form:hidden path="userId" />
                            <form:hidden path="userName" />
                            <form:hidden path="email"/>
                            <form:hidden path="apiToken"/>
                            <form:hidden path="role"/>
                            <sec:authorize access="!hasRole('ROLE_SUPER_USER')" >
                                <form:hidden path="status" />
                            </sec:authorize>

                            <div class="form-group">
                                    <label><spring:message code="label.email" /></label>
                                    <input class="form-control" value="<c:out value="${metabolightsUser.email}" />" disabled/>
                            </div>
                            <c:set var="orcidLinkUrl" value="${orcidLinkUrl}"/>
                            <jsp:include page="accountFormFields.jsp" />

                            <div class="form-group">
                                <label><spring:message code="label.apiToken" /></label>
                                <div class="input-group">
                                    <input class="form-control" value="<c:out value="${metabolightsUser.apiToken}" />" disabled/>
                                    <span class="input-group-btn">
                                        <a data-clipboard-text="<c:out value="${metabolightsUser.apiToken}" />"  target="_blank" class="btn btn-default ml--clipboard" data-clipboard-text="{{ mtblc.structure }}"><i class="fa fa-clipboard" aria-hidden="true"></i></a>
                                        </button>
                                    </span>
                                </div>
                            </div>


                            <div class="form-group">
                                    <input name="submit" type="submit" class="btn btn-primary" value="<spring:message code="label.update"/>">
                                    <input name="cancel" type="button" class="btn btn-default" value="<spring:message code="label.cancel"/>" onclick="location.href='index'">
                            </div>
                        </form:form>
                    </div>
                </div>
                <div class="panel-footer">
                    <p><strong><spring:message code="msg.starRequired"/></strong></p>
                </div>
            </div>
        </div>
    <div>
</div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/1.5.10/clipboard.min.js"></script>
        <script src="${pageContext.request.contextPath}/javascript/Notifier.js"></script>

    <script type="text/javascript" language="javascript">
       document.accountForm.firstName.focus();
    </script>

    <c:if test="${not empty message}">
        <span class="error"> <c:out value="${message}" /> </span>
        <br/>
    </c:if>
<script>
        var clipboard = new Clipboard('.ml--clipboard');
        console.log(clipboard);
        clipboard.on('success', function(e) {
            Notifier.success("Successful", e.trigger.id + " Copied to clipboard!");
        });

        clipboard.on('error', function(e) {
            alert();
            console.error('Action:', e.action);
            console.error('Trigger:', e.trigger);
        });
</script>