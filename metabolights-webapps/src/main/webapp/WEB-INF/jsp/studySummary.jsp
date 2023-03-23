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
<div class="panel panel-default cpanel nbr">
    <div class="panel-heading nbr">
        <h4>
            <div class="row">
                <div class="col-md-10">
                    <a class="nb" href="${liteStudy.studyIdentifier}"><strong>${liteStudy.title}</strong></a>
                </div>
                <div class="col-md-2">
                    <div>
                        <span class="pull-right">
                            <div class="btn-group" role="group" aria-label="...">
                                <c:if test="${!(liteStudy.studyStatus == 'PUBLIC')}">
                                    <span data-toggle="tooltip"
                                          data-placement="bottom"
                                          title="${liteStudy.studyStatus.description}"
                                          class="btn btn-default btn-xs">
                                        <span class="label label-danger">
                                                <i class="fa fa-key"></i>
                                                &nbsp;<spring:message code="label.expPrivate"/>
                                        </span>
                                    </span>
                                </c:if>
                                <c:if test="${(liteStudy.studyStatus == 'PUBLIC')}">
                                    <span data-toggle="tooltip"
                                          data-placement="bottom"
                                          title="${liteStudy.studyStatus.description}"
                                          class="pointer btn btn-default btn-xs">
                                        <i class="fa fa-globe"></i>
                                    </span>
                                </c:if>
                                <%--<c:if test="${(liteStudy.studyStatus == 'PUBLIC')}">--%>
                                    <%--&nbsp;--%>
                                    <%--<span data-toggle="tooltip"--%>
                                          <%--data-placement="bottom"--%>
                                          <%--title="Quick view" @click="studyQuickView('${liteStudy.studyIdentifier}')"--%>
                                          <%--class="pointer btn btn-default btn-xs">--%>
                                        <%--<i class="fa fa-eye"></i>--%>
                                    <%--</span>--%>
                                <%--</c:if>--%>
                            </div>
                            <%--<c:if test="${liteStudy.validations.status == 'RED'}">--%>
                                 <%--<span class="btn btn-sm"--%>
                                       <%--data-toggle="tooltip"--%>
                                       <%--data-placement="bottom"--%>
                                       <%--title="Mandatory​ information is missing">--%>
                                     <%--<span class="redTrafficL"></span>&nbsp;--%>
                                 <%--</span>--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${liteStudy.validations.status == 'AMBER'}">--%>
                                 <%--<span class="btn btn-sm"--%>
                                       <%--data-toggle="tooltip"--%>
                                       <%--data-placement="bottom"--%>
                                       <%--title="Optional fields are missing">--%>
                                     <%--<span class="amberTrafficL"></span>&nbsp;--%>
                                 <%--</span>--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${liteStudy.validations.status == 'GREEN'}">--%>
                                <%--<span class="btn btn-sm"--%>
                                      <%--data-toggle="tooltip"--%>
                                      <%--data-placement="bottom"--%>
                                      <%--title="All Mandatory and Optional fields are present">--%>
                                    <%--<span class="greenTrafficL"></span>&nbsp;--%>
                                <%--</span>--%>
                            <%--</c:if>--%>
                        </span>
                    </div>
                </div>
            </div>
        </h4>
    </div>
    <div class="panel-body">
            <div class="col-md-6">
                <div class="row">
                <div class="table-responsive">
                    <table class="table table-bordered">
                        <tbody>
                        <c:if test="${!liteStudy.publicStudy}">
                          <tr>
                            <td><i  class="fa fa-anchor" aria-hidden="true"></i>&nbsp;Study Identifier</td>
                            <td><a href="editor/study/${liteStudy.studyIdentifier}">
                                ${liteStudy.studyIdentifier}
                            </a></td>
                          </tr>
                        </c:if>
                        <c:if test="${liteStudy.publicStudy}">
                          <tr>
                            <td><i  class="fa fa-anchor" aria-hidden="true"></i>&nbsp;Study Identifier</td>
                            <td><a href="${liteStudy.studyIdentifier}">
                                ${liteStudy.studyIdentifier}
                            </a></td>
                        </tr>
                        </c:if>
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
                                <a href="searchUserStudies?users.fullName=${owner.fullName}">${owner.fullName}</a>
                                <a href="mailto:${owner.userName}?subject=<spring:message code="msg.emailStudyLinkSubject"/>&nbsp;${liteStudy.studyIdentifier}"><i
                                        class="fa fa-envelope-o" aria-hidden="true"></i>
                                </a>
                            </c:forEach></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="row">
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
                                    <jsp:useBean id="organismNames" class="java.util.HashSet" />

                                    <c:forEach var="organism" items="${liteStudy.organism}"  varStatus="loop">
                                        <c:if test = '${organismNames.add(organism.organismName)}'>
                                        </c:if>
                                    </c:forEach>
                                    <c:forEach var="name" items="${organismNames}"  varStatus="loop">
                                        ${name}
                                        ${!loop.last ? ', ' : ''}
                                    </c:forEach>
                                    <c:if test = '${organismNames.clear()}'>
                                    </c:if>
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
                                    <jsp:useBean id="factorNames" class="java.util.HashSet" />

                                    <c:forEach var="factor" items="${liteStudy.factors}"  varStatus="loop">
                                        <c:if test = '${factorNames.add(factor.name)}'>
                                        </c:if>
                                    </c:forEach>
                                    <c:forEach var="name" items="${factorNames}" varStatus="loop">
                                        ${name}
                                        ${!loop.last ? ', ' : ''}
                                    </c:forEach>
                                    <c:if test = '${factorNames.clear()}'>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </tbody>
                </table>
                </div>
            </div>
            <%--<div v-if="hasGalaxyDetails != ''" class="col-md-12" v-cloak>--%>
                <%--<div class="row">--%>
                    <%--<div class="alert alert-success" role="alert">--%>
                        <%--<form :action="hasGalaxyDetails" method="POST">--%>
                            <%--<div class="checkbox">--%>
                                <%--<label>--%>
                                <%--<input type="checkbox" value="true" name="download_rawfiles"> Download raw files--%>
                                <%--</label><br>--%>
                            <%--</div>--%>
                            <%--<input type="HIDDEN" name="tool_id" :value="toolId">--%>
                            <%--<input type="HIDDEN" name="URL" :value="URL">--%>
                            <%--<input type="hidden"  value="${liteStudy.studyIdentifier}" name="study_id">--%>
                            <%--<input type="submit"  class="btn btn-default btn-xs" name="metabolights_doGalaxyQuery" id="metabolights_doGalaxyQuery" value="Export data to Galaxy">--%>
                        <%--</form>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
    </div>
</div>
