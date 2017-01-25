<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 7/11/14 11:26 AM
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
<div class="ml-studybox row">
    <div class="col-md-12 ml-study-heading">
        <div class="col-md-12">
            <a class="nb " href="${liteStudy.studyIdentifier}"><strong>${liteStudy.title}</strong></a>
                <span class="pull-right">
                    <c:if test="${!(liteStudy.studyStatus == 'PUBLIC')}">
                        <small data-toggle="tooltip"
                               data-placement="bottom"
                               title="${liteStudy.studyStatus.description}">
                            <span class="label label-danger">
                                <i class="fa fa-key"></i>
                                &nbsp;<spring:message code="label.expPrivate"/>
                            </span>
                        </small>
                    </c:if>
                    <c:if test="${(liteStudy.studyStatus == 'PUBLIC')}">
                        <small data-toggle="tooltip"
                               data-placement="bottom"
                               title="${liteStudy.studyStatus.description}">
                                <i class="fa fa-globe"></i>
                        </small>
                    </c:if>
                            <c:if test="${liteStudy.validations.status == 'RED'}">
                                <span class="btn btn-sm"
                                      data-toggle="tooltip"
                                      data-placement="bottom"
                                      title="Mandatory​ information is missing">
                                    <span class="redTrafficL"></span>&nbsp;
                                </span>
                            </c:if>
                            <c:if test="${liteStudy.validations.status == 'AMBER'}">
                                <span class="btn btn-sm"
                                      data-toggle="tooltip"
                                      data-placement="bottom"
                                      title="Optional fields are missing">
                                <span class="amberTrafficL"></span>&nbsp;</span>
                            </c:if>
                            <c:if test="${liteStudy.validations.status == 'GREEN'}">
                                <span class="btn btn-sm"
                                      data-toggle="tooltip"
                                      data-placement="bottom"
                                      title="All Mandatory and Optional fields are present">
                                    <span class="greenTrafficL"></span>&nbsp;
                                </span>
                            </c:if>
                    <c:if test="${(liteStudy.studyStatus == 'PUBLIC')}">
                    <small>
                        <span @click="studyQuickView('${liteStudy.studyIdentifier}')"><i class="fa fa-expand"></i></span>

                    </small>
                    </c:if>
                </span>
            <%--<span class="pull-right  btn-xs btn"--%>
            <%--data-toggle="modal"--%>
            <%--data-target=".bs-example-modal-lg"--%>
            <%--data-toggle="tooltip"--%>
            <%--data-placement="bottom"--%>
            <%--title="Quick&nbsp;view">--%>
            <%--<i class="fa fa-expand"></i>--%>
            <%--</span>--%>
        </div>
    </div>
    <div class="ml-study-content col-md-12 ">
        <div class="row">
            <div class="col-md-6">
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tbody>
                            <tr>
                                <td><i  class="fa fa-anchor" aria-hidden="true"></i>&nbsp;Study Identifier</td>
                                <td><a href="${liteStudy.studyIdentifier}">
                                    ${liteStudy.studyIdentifier}
                                </a></td>
                            </tr>
                            <c:if test="${!liteStudy.publicStudy}">
                            <tr>
                                <td><i data-toggle="tooltip"
                                       data-placement="bottom"
                                       title="Release date" class="fa fa-calendar"></i>&nbsp;Release Date</td>
                                <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${liteStudy.studyPublicReleaseDate}"/></td>
                            </tr>
                            </c:if>
                            <tr>
                                <td><i data-toggle="tooltip"
                                       data-placement="bottom"
                                       title="Study size" class="fa fa-hdd-o" aria-hidden="true"></i>&nbsp;Study Size</td>
                                <td>${liteStudy.studyHumanReadable}</td>
                            </tr>
                            <tr>
                                <td><i class="fa fa-user"></i>&nbsp;Submitted by</td>
                                <td><c:forEach var="owner" items="${liteStudy.users}">
                                    <a href="search?users.fullName=${owner.fullName}">${owner.fullName}</a>
                                    <a href="mailto:${owner.userName}?subject=<spring:message code="msg.emailStudyLinkSubject"/>&nbsp;${liteStudy.studyIdentifier}"><i
                                            class="fa fa-envelope-o" aria-hidden="true"></i>
                                    </a>
                                </c:forEach></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-md-6">
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <td><spring:message code="label.organism"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${empty liteStudy.organism}">
                                    -
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="species" items="${liteStudy.organism}"  varStatus="loop">
                                        ${species.organismName}
                                        ${!loop.last ? ', ' : ''}
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>

                        </td>
                    </tr>
                    <tr>
                        <td><spring:message code="label.expFact"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${empty liteStudy.factors}">
                                    -
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="factor" items="${liteStudy.factors}" varStatus="loop">
                                        ${factor.name}
                                        ${!loop.last ? ', ' : ''}
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
