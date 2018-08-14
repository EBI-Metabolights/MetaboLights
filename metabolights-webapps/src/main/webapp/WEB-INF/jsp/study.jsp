<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2014-Dec-18
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2015 EMBL - European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>
<%--<c:set var="readOnly" value="${!fn:contains(servletPath,study.studyIdentifier)}"/>--%>
<script type="text/javascript" src="/metabolights/javascript/jquery.linkify-1.0-min.js"
        charset="utf-8"></script>
<script type="text/javascript" src="/metabolights/javascript/chebicompoundpopup.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="/metabolights/javascript/MetExplore/metExploreViz/metexploreviz.js"
        charset="utf-8"></script>

<div class="study--wrapper">
    <div class="study--header">
        <ol class="progtrckr" data-progtrckr-steps="${(fn:length(studyStatuses))-1}">
            <c:set var="statusInitial" value="${studyStatuses[0]}"/>
            <li class="progtrckr-done node" data-content="${statusInitial.description}" data-toggle="popover" data-html="true" data-placement="bottom">${statusInitial.descriptiveName}</li>
            <c:choose>
            <c:when test="${study.studyStatus eq studyStatuses[4]}">
            <c:forEach begin="1" end="3" var="status" items="${studyStatuses}">
            <li class="progtrckr-todo node" data-content="${status.description}" data-toggle="popover" data-html="true" data-placement="bottom">${status.descriptiveName}</li></c:forEach>
        </ol>
        </c:when>
        <c:otherwise>
            <c:forEach begin="1" end="3" var="status" items="${studyStatuses}">
                <c:if test="${status gt study.studyStatus}">
            <li class="progtrckr-todo node" data-content="${status.description}" data-toggle="popover" data-html="true" data-placement="bottom">${status.descriptiveName}</li>
                </c:if>
                <c:if test="${status le study.studyStatus}">
            <li class="progtrckr-done node" data-content="${status.description}" data-toggle="popover" data-html="true" data-placement="bottom">${status.descriptiveName}</li>
                </c:if>
            </c:forEach>
            </ol>
        </c:otherwise>
        </c:choose>
        <h5 class="study--title">
            <span class="study--id" id="mStudyId">${study.studyIdentifier}:</span>
            ${study.title}
        </h5>
    </div>
    <div class="study-description-wrapper">
        <c:if test="${not empty study.description}">
            <p class="study--subtitle"><b>Abstract</b></p>
            <div class="description--wrapper">
                <div class="study--description--small">
                    <p class="study--description">${study.description}</p>
                </div>
                <a class="expand--description" href="#">Click to read more</a>
            </div>
            <script>
                if ($('.study--description').height() < 128) {
                    $('.study--description--small').toggleClass('study--description--small study--description--big');
                    $('.expand--description').addClass("hidden");
                }
                $('.description--wrapper').find('a[href="#"]').on('click', function (e) {
                    e.preventDefault();
                    $(this).closest('.description--wrapper').find('.study--description--small').toggleClass('study--description--small study--description--big');
                    $('.expand--description').addClass("hidden");
                });
            </script>
        </c:if>
    </div>
    <div class="study--infopanel col-md-12">
        <div class="row bb">
            <div class="col-md-8">
                <p><i class="fa fa-user"></i>&nbsp;<spring:message code="ref.msg.CitationAuthors"/>:
                    <c:forEach var="contact" items="${study.contacts}" varStatus="loopStatus"><span class="node" <c:if test="${not empty contact.affiliation}">data-content="<div style='min-width: 400px;'><b>Affiliation:</b><br>${contact.affiliation}</div>"</c:if> data-toggle="popover" data-html="true" data-placement="bottom"><strong><c:out value="${fn:trim(contact.firstName)}"></c:out>&nbsp;<c:out value="${fn:trim(contact.lastName)}"></c:out><c:if test="${loopStatus.index ne (fn:length(study.contacts) - 1)}">,</c:if></strong></span>
                    </c:forEach></p>
            </div>
            <div class="col-md-4">
                <p>
                    <span class="pull-right">
                        <c:forEach var="user" items="${liteStudy.users}" varStatus="loopStatus">
                            <c:if test="${loopStatus.index ne 0}">
                                <c:set var="submitters" value="${submitters},${user.userName}" />
                            </c:if>
                        </c:forEach>
                        <i class="fa fa-envelope">&nbsp;</i><a href="mailto:${liteStudy.users[0].userName}?subject=<spring:message code="msg.emailStudyLinkSubject"/>&nbsp;${liteStudy.studyIdentifier}&cc=${submitters}"> Contact Submitter</a>&emsp;
                        <c:if test="${userHasEditRights eq true}"> &nbsp;<i class="fa fa-pencil"></i><a id="redirectToEditorPage" style="cursor: pointer">&nbsp;Quick edit</a> </c:if>
                    </span>
                </p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-9 pt10">
                <%@include file="studyActions.jsp" %>
                <div class="btn-group" role="group" aria-label="...">
                    <div class="btn-group" role="group">
                        <c:if test="${fn:length(study.assays) eq 1}">
                            <button type="button" class="btn btn-default nbr mt10 dropdown-toggle quicklinks" data-assayid="1"
                                    data-destination="assay<c:if test="${fn:length(study.assays) gt 0}">${assay.assayNumber}</c:if>"
                                    <c:if test="${(empty study.assays[0].metaboliteAssignment) and ( empty study.assays[0].metaboliteAssignment.metaboliteAssignmentFileName) }">disabled</c:if> >
                                <i class="ml--icons fa fa-fire pull-left"></i> View Metabolites
                                <span class="icon icon-conceList of study filesptual" data-icon="b"></span><spring:message
                                    code="label.assays"/><c:if
                                    test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                            </button>
                        </c:if>
                        <c:if test="${fn:length(study.assays) gt 1}">
                            <ul class="dropdown-menu mt10">
                                <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                                    <li>
                                        <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                                            <c:set var="mafExist" value="true"/>
                                            <a class="quicklinks"
                                               data-assayid="<c:if test="${fn:length(study.assays) gt 0}">${assay.assayNumber}</c:if>"
                                               data-destination="assay<c:if test="${fn:length(study.assays) gt 0}">${assay.assayNumber}</c:if>">
                                            <span class="icon icon-conceList of study filesptual"
                                                  data-icon="b"></span><spring:message code="label.assays"/><c:if
                                                    test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                                            </a>
                                        </c:if>
                                    </li>
                                </c:forEach>
                            </ul>
                            <button type="button" class="btn btn-default nbr mt10 dropdown-toggle" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false"
                                    <c:if test="${mafExist ne true}">disabled</c:if>>
                                <i class="ml--icons fa fa-fire pull-left"></i> View Metabolites
                                <span class="caret"></span>
                            </button>
                        </c:if>
                    </div>
                    <button type="button" class="btn btn-default mt10 quicklinks files--tab" data-destination="files"><i
                            class="ml--icons fa fa-download pull-left"></i> Download files
                    </button>
                     <c:if test="${study.studyStatus.descriptiveName eq 'Public'}">
                         <button class="btn btn-default mt10 popover-toggle" type="button"
                                 id="orcidPopover"
                         >
                             <i class="thorOrcIdSpan">
                                 <img src="//www.ebi.ac.uk/europepmc/thor/resources/orcid-id.png" value="What is ORCID?"
                                      width="15" height="15" data-pin-nopin="true">
                             </i>
                             ORCID Claims
                         </button>
                     </c:if>
                        <div id="orcidPopoverDiv" style="display: none;">
                            <div class="thor_div_showIf_notSigned">
                                <div class="panel panel-info">
                                    <div class="panel-heading thor_div_showIf_datasetAlreadyClaimedList">
                                        You must <a href="#" class="thor_a_generate_signinLink"><strong>sign-in
                                        to
                                        ORCID</strong></a> to claim your data
                                    </div>
                                    <div class="panel-body">
                                        <div class="row existingClaimants" style="padding-left: 1em;">
                                        </div>
                                        <c:if test="${userApiToken ne 'MetaboLights-anonymous'}">
                                            <c:if test="${empty userOrcidID}">
                                                <br>
                                                <div class="row">
                                                    <%--<div class="panel panel-default">--%>
                                                        <div class="panel-body"> You can <a
                                                                href="${pageContext.request.contextPath}/myAccount"
                                                                target="_blank">Update your MTBLS account</a> with ORCID
                                                        <%--</div>--%>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:if>
                                    </div>
                                    <div class="panel-footer">
                                        <input type="checkbox" class="thor_checkbox_rememberMe_cookie">
                                        <a target="_blank" href="https://orcid.org/"><i>ORCID</i></a> <i> can Remember me on
                                        this
                                        computer </i>
                                    </div>
                                </div>
                            </div>
                            <div class="thor_div_showIf_signedIn">
                                <div class="panel panel-info">
                                    <div class="panel-heading thor_div_showIf_datasetAlreadyClaimedList">
                                        You have signed in as <label class="thor_label_show_userName"></label>
                                       <div class="thor_div_showIf_datasetNotClaimed">
                                            You can <a href="#"
                                                       class="thor_a_generate_claimLink"><strong>claim ${study.studyIdentifier}</strong></a>
                                            into your ORCID.
                                        </div>
                                        <div class="small thor_div_showIf_datasetAlreadyClaimed">
                                            You have claimed <strong>${study.studyIdentifier}</strong> into your ORCID.
                                        </div>

                                    </div>
                                    <div class="panel-body">
                                        <div class="row existingClaimants" style="padding-left: 1em;">

                                        </div>
                                        <c:if test="${userApiToken ne 'MetaboLights-anonymous'}">
                                            <c:if test="${empty userOrcidID}">
                                                <br>
                                                <div class="row">
                                                    <div class="panel panel-default">
                                                        <div class="panel-body"> You can <a
                                                                href="${pageContext.request.contextPath}/myAccount"
                                                                target="_blank">Update your MTBLS account</a> with ORCID
                                                        </div>
                                                    </div>

                                                </div>
                                            </c:if>
                                        </c:if>
                                    </div>
                                    <div class="panel-footer">
                                        <a href="#" class="thor_a_generate_logoutLink"><i>Logout from ORCID</i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    <button type="button" class="btn mt10 btn-primary" data-toggle="modal" data-target="#shareStudy">
                        <i class="fa fa-link"></i>&nbsp;
                        <spring:message code="label.study.share"/>
                    </button>
                </div>
            </div>

            <div class="col-md-3 pt5">
                <div class="col-md-12">
                    <p class="row">
                    <span class="pull-right mt10 node" data-toggle="popover" data-html="true" data-placement="bottom" data-content="<div style='width: 300px;'> Submission date: <b><fmt:formatDate value="${study.studySubmissionDate}" pattern="dd-MMM-yyyy"/></b> <br> Update date: <b><fmt:formatDate value="${study.updateDate}" pattern="dd-MMM-yyyy"/></b></div>">
                        <i class="fa fa-calendar"></i>&nbsp;
                    <%--<c:if test="${not empty study.studySubmissionDate}">--%>
                    <%--<spring:message code="label.subDate"/>: <strong--%>
                    <%--title="<fmt:formatDate pattern="dd-MMM-yyyy hh:mm" value="${study.studySubmissionDate}"/>"><fmt:formatDate--%>
                    <%--pattern="dd-MMM-yyyy" value="${study.studySubmissionDate}"/></strong>--%>
                    <%--</c:if>--%>
                    <c:if test="${not empty study.studyPublicReleaseDate}"><spring:message
                            code="label.releaseDate"/>: <strong><fmt:formatDate pattern="dd-MMM-yyyy"
                                                                                value="${study.studyPublicReleaseDate}"/></strong>
                    </c:if><c:if test="${not empty study.updateDate}">
                    <%--, <spring:message code="label.updateDate"/>:--%>
                    <%--<strong title="${study.updateDate}"><fmt:formatDate pattern="dd-MMM-yyyy"--%>
                    <%--value="${study.updateDate}"/></strong>--%>
                    </span>
                        </c:if>
                    </p>
                    <p class="row" id="mStudyStatus">
                        <span class="pull-right">
                            <c:choose>
                                <c:when test="${study.studyStatus.descriptiveName eq 'Dormant'}">
                                    <i class="fa fa-bookmark"></i>&nbsp;<spring:message
                                        code="ref.msg.status"/>:&nbsp;<span class="label label-xs label-pill label-danger">${study.studyStatus.descriptiveName}</span>
                                </c:when>
                                <c:otherwise>
                                    <i class="fa fa-bookmark"></i>&nbsp;<spring:message
                                        code="ref.msg.status"/>:&nbsp;<span class="label label-xs label-pill label-success">${study.studyStatus.descriptiveName}</span>
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </p>
                </div>
            </div>
        </div>
    </div>
    <div class="tabs--wrapper">
        <div>
            <!-- Nav tabs -->
            <ul class="nav nav-tabs responsive" id="study--tab" role="tablist">
                <c:if test="${study.factors != null}">
                    <li role="presentation" class="active ml-1"><a href="#description" aria-controls="description"
                                                              role="tab" data-toggle="tab"><spring:message
                            code="label.studyDesign"/></a></li>
                </c:if>
                <c:if test="${not empty study.protocols}">
                    <li role="presentation"><a href="#protocols" aria-controls="protocols" role="tab"
                                               data-toggle="tab"><spring:message code="label.protocols"/></a></li>
                </c:if>
                <c:if test="${not empty study.sampleTable}">
                    <li role="presentation"><a href="#sample" aria-controls="sample" role="tab"
                                               data-toggle="tab"><spring:message code="label.sample"/></a></li>
                </c:if>
                <c:if test="${not empty study.assays}">
                    <c:if test="${fn:length(study.assays) eq 1}">
                        <li role="presentation">
                            <a class="assay--tab" href="#assay" aria-controls="assay" data-assayid="1" role="tab"
                               data-toggle="tab">
                                <c:if test="${(not empty study.assays[0].metaboliteAssignment) and (not empty study.assays[0].metaboliteAssignment.metaboliteAssignmentFileName) }">
                                    <c:set var="metabolitesExist" value="true"/>
                                    <span id="study-metabolitesicon" class="icon icon-conceptual"
                                          data-icon="b"></span>
                                </c:if>
                                <spring:message code="label.assays"/>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${fn:length(study.assays) gt 1}">
                        <li class="dropdown">
                            <ul class="dropdown-menu">
                                <c:set var="metabolitesExist" value="false"/>
                                <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                                    <li>
                                        <a class="assay--tab" href="#assay${assay.assayNumber}"
                                           aria-controls="assay${assay.assayNumber}"
                                           data-assayid="${assay.assayNumber}" role="tab" data-toggle="tab">
                                            <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                                                <c:set var="metabolitesExist" value="true"/>
                                                <span class="icon icon-conceptual" data-icon="b"></span>
                                            </c:if>
                                            <spring:message code="label.assays"/> <c:if
                                                test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                <c:if test="${metabolitesExist eq true}">
                                        <span id="study-metabolitesicon" class="icon icon-conceptual"
                                              data-icon="b"></span>
                                </c:if>
                                <spring:message code="label.assays"/>&nbsp;<span class="caret"></span>
                            </a>
                        </li>
                    </c:if>
                </c:if>
                <c:if test="${not empty files}">
                    <li role="presentation"><a href="#files" class="files--tab" aria-controls="files" role="tab"
                                               data-toggle="tab"><spring:message code="label.Files"/></a></li>
                </c:if>

                <c:if test="${not empty study.validations}">
                    <li role="presentation">
                        <a id="valid-tab" href="#validations" aria-controls="validations" role="tab"
                           data-toggle="tab">
                            <spring:message code="label.studyvalidation"/>&nbsp;
                            <c:set var="validationstatus" value="${study.validations.status}"/>
                            <c:set var="validationoverridden" value="${study.validations.overriden}"/>
                            <%@include file="validations.jsp" %>
                        </a>
                    </li>
                </c:if>
                <c:if test="${not empty study.assays}">
                    <c:if test="${(not empty study.assays[0].metaboliteAssignment) and (not empty study.assays[0].metaboliteAssignment.metaboliteAssignmentFileName) }">
                        <c:set var="metabolitesExist" value="true"/>
                        <li role="presentation">
                            <a class="assay--tab" href="#metpathways" aria-controls="metpathways"
                               data-assayid="1" role="tab" data-toggle="tab">
                                Pathways
                            </a>
                        </li>
                    </c:if>
                </c:if>
            </ul>
            <div class="tab-content study--tabcontent responsive" id="mltab--content">
                <div role="tabpanel" class="tab-pane active" id="description">
                    <div class="row">
                        <!-- TAB1: INFO-->
                        <c:if test="${study.factors != null}">
                            <c:if test="${not empty study.organism}">
                                <div class="col-md-12">
                                    <div class="panel nbr panel-default">
                                        <div class="panel-heading">
                                            <spring:message code="label.organisms"/></div>
                                        <div class="panel-body">
                                            <c:forEach var="org" items="${study.organism}">
                                                <p>${org.organismName}</p>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${not empty study.descriptors}">
                                <div class="col-md-12">
                                    <div class="panel nbr panel-default">
                                        <div class="panel-heading"><span class="glyphicon glyphicon-list"
                                                                         aria-hidden="true"></span>&nbsp;
                                            <spring:message code="label.studyDesign"/></div>
                                        <div class="panel-body">
                                            <c:forEach var="design" items="${study.descriptors}">
                                                <p>${design.description}</p>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${not empty study.factors}">
                                <div class="col-md-12">
                                    <div class="panel nbr panel-default">
                                        <div class="panel-heading"><span class="glyphicon glyphicon-tags"
                                                                         aria-hidden="true"></span>&nbsp;
                                            <spring:message code="label.experimentalFactors"/></div>
                                        <div class="panel-body">
                                            <c:forEach var="fv" items="${study.factors}">
                                                <p class="capitalize">${fv.name}</p>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${not empty study.publications}">
                                <div class="col-md-12">
                                    <div class="panel nbr panel-default">
                                        <div class="panel-heading"><span class="glyphicon glyphicon-book"
                                                                         aria-hidden="true"></span>&nbsp;<spring:message
                                                code="label.publications"/></div>
                                        <div class="panel-body">
                                            <c:forEach var="pub" items="${study.publications}"
                                                       varStatus="loopPublications">
                                                        [${loopPublications.index+1}]&nbsp;
                                                        <c:set var="DOIValue" value="${pub.doi}"/>
                                                        <c:choose>
                                                            <c:when test="${not empty pub.pubmedId}">
                                                                <a href="//europepmc.org/abstract/MED/${pub.pubmedId}"
                                                                   target="_blank">${pub.title}</a>
                                                            </c:when>
                                                            <c:when test="${not empty pub.doi}">
                                                                <c:if test="${fn:contains(DOIValue, 'DOI')}">
                                                                    <c:set var="DOIValue"
                                                                           value="${fn:replace(DOIValue, 'DOI:','')}"/>
                                                                </c:if>
                                                                <c:if test="${fn:contains(DOIValue, 'dx.doi')}">
                                                                    <a target="_blank"
                                                                       href="${DOIValue}">${pub.title}</a>
                                                                </c:if>
                                                                <c:if test="${not fn:contains(DOIValue, 'dx.doi')}">
                                                                    <a target="_blank"
                                                                       href="//dx.doi.org/${DOIValue}">${pub.title}</a>
                                                                </c:if>
                                                            </c:when>
                                                            <c:otherwise>${pub.title}</c:otherwise>
                                                        </c:choose>
                                                <br>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="protocols">
                    <div class="row">
                        <div class="col-md-12">
                            <c:if test="${not empty study.protocols}">
                                <table class="table table-striped table-bordered protocols-table">
                                    <thead class='text_header'>
                                    <tr>
                                        <th>Protocol</th>
                                        <th>Description</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach var="protocol" items="${study.protocols}">
                                        <c:choose>
                                            <c:when test="${not empty protocol.description}">
                                                <tr>
                                                    <td class="tableitem">
                                                            <span class="node text-primary">
                                                                    ${protocol.name}
                                                            </span>
                                                    </td>
                                                    <td id="protocoldesc" class="tableitem">${protocol.description}</td>
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane ml--oh" id="sample">
                    <div class="row">
                        <div class="col-md-12">
                            <c:if test="${not empty study.sampleTable}">
                                <table class="dataTable scrollTable table table-striped table-bordered">
                                    <thead class='text_header'>
                                    <tr>
                                        <c:forEach var="fieldSet" items="${study.sampleTable.fields}">
                                            <c:set var="headerTitle" value="${fieldSet.value.description}"/>
                                            <c:if test="${not empty fieldSet.value.fieldType}">
                                                <c:set var="headerTitle"
                                                       value="${headerTitle} - Type: ${fieldSet.value.fieldType}"/>
                                            </c:if>
                                            <th title="${headerTitle}">${fieldSet.value.cleanHeader}</th>
                                        </c:forEach>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="row" items="${study.sampleTable.iterator}">
                                        <tr>
                                            <c:forEach var="cell" items="${row.iterator}">
                                                <c:set var="cellvalue" value="${cell.value}" scope="page"/>
                                                <c:if test="${cell.field.header eq 'Sample Name'}">
                                                    <c:if test="${fn:startsWith(cellvalue, 'SAMEA')}">
                                                        <c:set var="cellvalue"
                                                               value="<a href='//www.ebi.ac.uk/biosamples/sample/${cellvalue}'>${cellvalue}</a>"/>
                                                    </c:if>
                                                </c:if>
                                                <td>${cellvalue}</td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                        </div>
                    </div>
                </div>
                <c:if test="${fn:length(study.assays) eq 1}">
                    <c:if test="${not empty study.assays}">
                        <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                            <div role="tabpanel" class="tab-pane" id="assay">
                                <h3>Assay&nbsp;<c:if
                                        test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if></h3>
                                <div class="specs well nbr">
                                    <h5><spring:message code="label.assayName"/>:<b> <a
                                            href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/${assay.fileName}"><span
                                            class="icon icon-fileformats" data-icon="v">${assay.fileName}</span></a></b>
                                    </h5>
                                    <h5><spring:message code="label.measurement"/>: <b>${assay.measurement}</b></h5>
                                    <h5><spring:message code="label.technology"/>: <b>${assay.technology}
                                        </b>
                                        <%--<c:if test="${fn:contains(assay.technology,'NMR')}">--%>
                                            <%--<span aria-hidden="true" class="icon2-NMRLogo"></span>--%>
                                        <%--</c:if>--%>
                                        <%--<c:if test="${fn:contains(assay.technology,'mass')}">--%>
                                            <%--<span aria-hidden="true" class="icon2-MSLogo"></span>--%>
                                        <%--</c:if>--%>
                                    </h5>
                                    <h5><spring:message code="label.platform"/>:<b> ${assay.platform}</b></h5>
                                </div>
                                <br/>
                                <!-- Start of assay data: metabolites + data -->

                                <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                                    <div class="panel panel-primary">
                                        <div class="panel-heading">
                                            <h4 class="clickable"
                                                role="button"
                                                data-toggle="collapse"
                                                href="#collapseExample"
                                                aria-expanded="false"
                                                aria-controls="collapseExample"
                                            >
                                                    <span class="icon icon-conceptual"
                                                          data-icon="b"></span><spring:message
                                                    code="label.mafFileFound"/>
                                            </h4>
                                        </div>

                                        <div class="panel-body">

                                            <div id="mafTableWrapper${assay.assayNumber}"
                                                 data-obfuscationCode="${obfuscationCode}"
                                                 data-studyid="${study.studyIdentifier}"
                                                 data-assayid="${assay.assayNumber}">
                                                <p class="text-center"><span><img
                                                        src="${pageContext.request.contextPath}/img/beta_loading.gif"></span>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                                <!-- start of assay data -->

                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4><span class="icon icon-functional" data-icon="A"></span> Instrumentation</h4>
                                    </div>

                                    <div class="panel-body">
                                        <div>
                                            <c:if test="${not empty assay.assayTable}">
                                                <div class="custom--wrapper">
                                                    <table class="assayTable  table table-striped table-bordered"
                                                           cellpadding="0" cellspacing="0" border="0">
                                                        <thead class='text_header'>
                                                        <tr>
                                                            <c:forEach var="fieldSet"
                                                                       items="${assay.assayTable.fields}">
                                                                <th title="${fieldSet.value.description}">${fieldSet.value.cleanHeader}</th>
                                                            </c:forEach>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach var="row" items="${assay.assayTable.iterator}">
                                                            <tr>
                                                                <c:forEach var="cell" items="${row.iterator}">

                                                                    <c:set var="cellvalue" value="${cell.value}"
                                                                           scope="page"/>

                                                                    <c:if test="${cell.field.header eq 'Sample Name'}">
                                                                        <c:if test="${fn:startsWith(cellvalue, 'SAMEA')}">
                                                                            <c:set var="cellvalue"
                                                                                   value="<a href='//www.ebi.ac.uk/biosamples/sample/${cell.value}'>${cell.value}</a>"/>
                                                                        </c:if>
                                                                    </c:if>
                                                                    <td>${cellvalue}</td>
                                                                </c:forEach>
                                                            </tr>

                                                        </c:forEach>
                                                        </tbody>
                                                    </table>

                                                </div>
                                            </c:if>
                                        </div> <!-- end of an assay data -->
                                    </div>
                                </div>


                            </div>
                        </c:forEach>
                    </c:if>
                </c:if>

                <c:if test="${fn:length(study.assays) gt 1}">
                    <c:if test="${not empty study.assays}">
                        <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                            <div role="tabpanel" class="tab-pane" id="assay${assay.assayNumber}">
                                <h3>Assay&nbsp;<c:if
                                        test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if></h3>
                                <div class="specs well nbr">
                                    <h5><spring:message code="label.assayName"/>:<b> <a
                                            href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/${assay.fileName}"><span
                                            class="icon icon-fileformats" data-icon="v">${assay.fileName}</span></a></b>
                                    </h5>
                                    <h5><spring:message code="label.technology"/>: <b>${assay.technology}
                                        </b></h5>
                                    <%--<c:if test="${fn:contains(assay.technology,'NMR')}">--%>
                                        <%--<span aria-hidden="true" class="icon2-NMRLogo"></span>--%>
                                    <%--</c:if>--%>
                                    <%--<c:if test="${fn:contains(assay.technology,'mass')}">--%>
                                        <%--<span aria-hidden="true" class="icon2-MSLogo"></span>--%>
                                    <%--</c:if>--%>
                                    <h5><spring:message code="label.platform"/>:<b> ${assay.platform}</b></h5>
                                </div>
                                <br/>
                                <!-- Start of assay data: metabolites + data -->

                                <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                                    <div class="panel panel-primary">
                                        <div class="panel-heading">
                                            <h4 class="clickable"
                                                role="button"
                                                data-toggle="collapse"
                                                href="#collapseExample"
                                                aria-expanded="false"
                                                aria-controls="collapseExample"
                                            >
                                                    <span class="icon icon-conceptual"
                                                          data-icon="b"></span><spring:message
                                                    code="label.mafFileFound"/>
                                            </h4>
                                        </div>

                                        <div class="panel-body">
                                            <div id="mafTableWrapper${assay.assayNumber}"
                                                 data-studyid="${study.studyIdentifier}"
                                                 data-obfuscationCode="${obfuscationCode}"
                                                 data-assayid="${assay.assayNumber}">
                                                <p class="text-center"><span><img
                                                        src="/metabolights/img/beta_loading.gif"></span></p>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                                <!-- start of assay data -->

                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4><span class="icon icon-functional" data-icon="A"></span><spring:message
                                                code="label.data"/></h4>
                                    </div>

                                    <div class="panel-body">
                                        <div>
                                            <c:if test="${not empty assay.assayTable}">
                                                <div class="custom--wrapper">
                                                    <table class="assayTable  table table-striped table-bordered"
                                                           cellpadding="0" cellspacing="0" border="0">
                                                        <thead class='text_header'>
                                                        <tr>
                                                            <c:forEach var="fieldSet"
                                                                       items="${assay.assayTable.fields}">
                                                                <th title="${fieldSet.value.description}">${fieldSet.value.cleanHeader}</th>
                                                            </c:forEach>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <c:forEach var="row" items="${assay.assayTable.iterator}">
                                                            <tr>
                                                                <c:forEach var="cell" items="${row.iterator}">

                                                                    <c:set var="cellvalue" value="${cell.value}"
                                                                           scope="page"/>

                                                                    <c:if test="${cell.field.header eq 'Sample Name'}">
                                                                        <c:if test="${fn:startsWith(cellvalue, 'SAMEA')}">
                                                                            <c:set var="cellvalue"
                                                                                   value="<a href='//www.ebi.ac.uk/biosamples/sample/${cell.value}'>${cell.value}</a>"/>
                                                                        </c:if>
                                                                    </c:if>
                                                                    <td>${cellvalue}</td>
                                                                </c:forEach>
                                                            </tr>

                                                        </c:forEach>
                                                        </tbody>
                                                    </table>

                                                </div>
                                            </c:if>
                                        </div> <!-- end of an assay data -->
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                </c:if>

                <div role="tabpanel" class="tab-pane" id="files">
                    <c:if test="${not empty files}">
                        <%--Only show token if study es private--%>
                        <c:if test="${!study.publicStudy}">
                            <c:set var="token" value="?token=${study.obfuscationCode}"/>
                        </c:if>
                        <form id="selFilesForm"
                              action="${pageContext.request.contextPath}/${study.studyIdentifier}/files/downloadSelFiles"
                              method="post">
                            <h5 class="well nbr">
                                <!--  Request FTP folder -->
                                <c:if test="${(study.studyStatus == 'SUBMITTED') and !hasPrivateFtpFolder }">
                                    <sec:authorize access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                                        &nbsp;
                                        <a class="noLine" rel="nofollow"
                                           href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/requestFtpFolder"
                                           title="<spring:message code="label.requestFtpFolder"/>">
                                            <span class="icon icon-functional" data-icon="D"/>&nbsp;<spring:message
                                                code="label.requestFtpFolder"/>
                                        </a>
                                        &nbsp;|&nbsp;
                                    </sec:authorize>
                                </c:if>
                                &nbsp;
                                <!--  Request FTP folder -->
                                <c:if test="${study.studySize <= 1024000 }">
                                    <a class="noLine" rel="nofollow"
                                       href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/${study.studyIdentifier}${token}"
                                       title="<spring:message code="label.downloadstudy"/>">
                                        <span class="icon icon-functional" data-icon="="/>&nbsp;<spring:message
                                            code="label.downloadstudy"/>
                                    </a>
                                    &nbsp;|&nbsp;
                                </c:if>

                                <c:if test="${study.publicStudy}">
                                    <a class="noLine"
                                       href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/studies/public/${study.studyIdentifier}"
                                       title="<spring:message code="label.viewAllFiles"/>">
                                        <span class="icon icon-generic" data-icon="x"/>&nbsp;<spring:message
                                            code="label.viewAllFiles"/>
                                    </a>
                                    &nbsp;|&nbsp;
                                </c:if>
                                &nbsp;
                                <a class="noLine" rel="nofollow"
                                   href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/metadata${token}"
                                   title="<spring:message code="label.downloadstudyMetadata"/>">
                                        <span class="icon icon-functional" data-icon="=">&nbsp;<spring:message
                                                code="label.downloadstudyMetadata"/>
                                </a>
                                &nbsp;
                                <c:if test="${study.publicStudy}">
                                    <span id="asperaDownloadWrapper"></span>
                                </c:if>
                                &nbsp;
                                <c:if test="${(study.studyStatus == 'SUBMITTED') and hasPrivateFtpFolder }">
                                        <span class="pull-right"><i class="fa fa-angle-double-down"></i>&nbsp;<a
                                                href="#ftpFolderDetails">Upload folder details</a></span>
                                </c:if>
                            </h5>
                            <br/>
                            <div id="transferDiv" class="transferDiv well nbr">
                                <h5>Aspera Download Details:</h5>
                                <hr>
                            </div>
                            <div id="noAspera" class="noAspera"></div>
                            <div class="clearfix">
                                <h4><spring:message code="label.fileListTableExplanation"/>
                                    <span style="border-bottom: 1px solid #ccc; border-radius: 4px;"
                                          class="pull-right">
                                            <small><a class="btn btn-success btn-xs text-white" target="_blank"
                                                      href="/metabolights/parallelCoordinates?study=${study.studyIdentifier}"><i
                                                    class="fa fa-filter"
                                                    aria-hidden="true"></i>&nbsp; Subset</a></small>
                                        </span>
                                </h4>
                            </div>
                            <div class="input-group">
                                <input class="inputDiscrete form-control" id="fileSelector" type="text"
                                       placeholder="<spring:message code='label.fileList.Input.placeholder'/>">
                                <span class="input-group-btn">
                                              <button type="button" class="btn btn-primary ml--btngrpoup"
                                                      data-toggle="modal" data-target=".bs-example-modal-lg">?
                                              </button>
                                          </span>
                            </div><!-- /input-group -->
                            <!--
                                Help modal content
                            -->
                            <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
                                 aria-labelledby="myLargeModalLabel">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal"
                                                    aria-label="Close"><span aria-hidden="true">&times;</span>
                                            </button>
                                            <h4 class="modal-title">Help</h4>
                                        </div>
                                        <div class="modal-body">
                                            <p>
                                                <spring:message code='label.fileList.Input.placeholder'/><br>
                                                Example:
                                                <br>
                                                > Type <b>.txt</b> and then press <b>Enter or CMD</b> to select all
                                                files with .txt extension<br>
                                                > Type <b>!.txt</b> and then press <b>Enter or CMD</b> to deselect
                                                all files with .txt extension
                                            </p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">
                                                Close
                                            </button>
                                        </div>
                                    </div><!-- /.modal-content -->
                                </div><!-- /.modal-dialog -->
                            </div>
                            <c:if test="${!study.publicStudy}">
                                <input type="hidden" name="token" value="${study.obfuscationCode}">
                            </c:if>
                            <table id="files" class="filesTable table table-striped table-bordered"
                                   style="width: 100%;">
                                <thead>
                                <tr>
                                    <th>
                                        <label>
                                            <input type="checkbox" name="checkAll" id="checkAll">&emsp;Select all
                                        </label>
                                    </th>
                                    <th>File</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="file" items="${files}">
                                    <%--<c:if test="${!file.directory}">--%>
                                    <tr>
                                        <td><input type="checkbox" class="ml--file" name="file"
                                                   value="${file.name}"/></td>
                                        <td>
                                            <a rel="nofollow"
                                               href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/${file.name}${token}">${file.name}</a>
                                        </td>
                                    </tr>
                                    <%--</c:if>--%>
                                </c:forEach>
                                </tbody>
                            </table>
                            <script>
                                $("#checkAll").click(function () {
                                    if ($("#checkAll").is(':checked')) {
                                        $(".ml--file").prop("checked", true);
                                    } else {
                                        $(".ml--file").prop("checked", false);
                                    }
                                });
                            </script>
                            <div style="position: relative; width: 100%;">
                                <div style="float: left; padding: 10px;">
                                    <input name="btnSubmit" type="submit" class="submit btn btn-default"
                                           value="<spring:message code="label.downloadSelectedFiles"/>"/>
                                    <c:if test="${study.publicStudy}">
                                        <span id="aspDMF"></span>
                                    </c:if>
                                </div>
                                <sec:authorize ifAnyGranted="ROLE_SUPER_USER">
                                    <div style="float: right; padding: 10px;">
                                        <input data-delete="${pageContext.request.contextPath}/${study.studyIdentifier}/files/deleteSelFiles"
                                               data-download="${pageContext.request.contextPath}/${study.studyIdentifier}/files/downloadSelFiles"
                                               data-title="Confirmation"
                                               data-info="This will delete all selected files from the system, no way back!."
                                               data-toggle="modal" data-target="#confirm-delete-modal"
                                               id="deleteSelFiles" name="deleteSelFiles" type="button"
                                               class="submit cancel"
                                               value="<spring:message code="label.deleteSelectedFiles"/>"/>
                                    </div>
                                </sec:authorize>
                            </div><%--Show instructions--%>
                            <div class="alert alert-warning nmb">
                                <p><strong>Info:</strong><spring:message code="label.fileListTableInstructions"/>
                                </p>
                                <sec:authorize ifAnyGranted="ROLE_SUPER_USER">
                                    <p><spring:message code="label.fileListTableDelInstructions"/></p>
                                </sec:authorize>
                            </div>
                        </form>
                        <div class="modal fade" id="confirm-delete-modal" tabindex="-1" role="dialog"
                             aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <div id="modal-title"></div>
                                    </div>
                                    <div class="modal-body">
                                        <div id="modal-info"></div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel
                                        </button>
                                        <a class="btn btn-ok btn-danger" onclick="">Delete</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <script>
                            $('#confirm-delete-modal').on('show.bs.modal', function (e) {
                                $(this).find('#modal-title').html($(e.relatedTarget).data('title'));
                                $(this).find('#modal-info').html($(e.relatedTarget).data('info'));

                                $(this).find('.btn-ok').click(function () {
                                    var frm = document.getElementById('selFilesForm');
                                    frm.action = $(e.relatedTarget).data('delete');
                                    frm.method = "post";
                                    frm.submit();

                                    // change action back to download files (default)
                                    frm.reset();
                                    frm.action = $(e.relatedTarget).data('download');
                                    frm.method = "post";
                                });
                            });
                        </script>
                        <br/><br/>
                        <a name="ftpFolderDetails"></a>
                        <!-- private FTP files -->
                        <c:if test="${(study.studyStatus == 'SUBMITTED') and hasPrivateFtpFolder }">
                            <div class="accordion">
                                <h5 class="ftpFolder"><span class="icon icon-generic" data-icon="D"/></span>
                                    &nbsp;<spring:message code="label.privateFtpFolder"/></h5>

                                <div>
                                    <h5><spring:message code="label.ftpFileListTableExplanation"/></h5>
                                    <c:if test="${empty ftpFiles}">&emsp;&emsp;[EMPTY]</c:if>

                                    <c:if test="${!empty ftpFiles}">
                                        <form id="selFtpFilesForm"
                                              action="${pageContext.request.contextPath}/${study.studyIdentifier}/files/moveFilesfromFtpFolder"
                                              method="post">

                                            <!-- <p><input class="inputDiscrete resizable" id="ftpFileSelector" class="" type="text" placeholder="<spring:message code='label.ftpFileList.Input.placeholder'/>"></p> -->

                                            <table id="privFtpFiles"
                                                   class="ftpFiles table table-striped table-bordered">
                                                <tr>
                                                    <th><label>
                                                        <input type="checkbox" name="ftpCheckAll" id="ftpCheckAll">&emsp;Select
                                                        All
                                                    </label>
                                                    </th>
                                                    <th>File</th>
                                                </tr>
                                                <tbody>
                                                <c:forEach var="ftpFile" items="${ftpFiles}">
                                                    <tr>
                                                        <td><input type="checkbox" class="ml--ftpfile"
                                                                   name="ftpFile" value="${ftpFile.name}"/></td>
                                                        <td>
                                                                ${ftpFile.name}
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>

                                            <div style="position: relative; width: 100%;">
                                                <sec:authorize
                                                        access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                                                    <div style="float: left; padding: 10px;">
                                                        <input name="moveFtpSelFilesBtn" type="button"
                                                               class="submit"
                                                               value="<spring:message code="label.moveSelectedFiles"/>"
                                                               onclick="form.submit();"/>
                                                    </div>
                                                    <div style="float: right; padding: 10px;">
                                                        <input data-delete="${pageContext.request.contextPath}/${study.studyIdentifier}/files/deleteSelFtpFiles"
                                                               data-move="$${pageContext.request.contextPath}/{study.studyIdentifier}/files/moveFilesfromFtpFolder"
                                                               data-title="Confirmation"
                                                               data-info="This will delete all selected files from your private upload folder, no way back!."
                                                               data-toggle="modal"
                                                               data-target="#confirm-ftp-delete-modal"
                                                               id="deleteFtpSelFilesBtn" name="deleteFtpSelFilesBtn"
                                                               type="button" class="submit cancel"
                                                               value="<spring:message code="label.deleteFtpSelFiles"/>"/>
                                                    </div>
                                                </sec:authorize>
                                            </div>

                                                <%--Show instructions--%>
                                            <div class="alert alert-warning nmb">
                                                <p><strong>Info:</strong><spring:message
                                                        code="label.moveFileListTableInstructions"/></p>
                                            </div>
                                        </form>
                                    </c:if>
                                </div>
                                <div class="modal fade" id="confirm-ftp-delete-modal" tabindex="-1" role="dialog"
                                     aria-labelledby="myModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <div id="modal-title"></div>
                                            </div>
                                            <div class="modal-body">
                                                <div id="modal-info"></div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">
                                                    Cancel
                                                </button>
                                                <a class="btn btn-ok btn-danger" onclick="">Delete</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <script>
                                    $('#confirm-ftp-delete-modal').on('show.bs.modal', function (e) {
                                        $(this).find('#modal-title').html($(e.relatedTarget).data('title'));
                                        $(this).find('#modal-info').html($(e.relatedTarget).data('info'));

                                        $(this).find('.btn-ok').click(function () {
                                            var frm = document.getElementById('selFtpFilesForm');
                                            frm.action = $(e.relatedTarget).data('delete');
                                            frm.method = "post";
                                            frm.submit();

                                            // change action back to download files (default)
                                            frm.reset();
                                            frm.action = $(e.relatedTarget).data('move');
                                            frm.method = "post";
                                        });
                                    });
                                </script>
                            </div>
                        </c:if>
                        <!-- private FTP files -->
                    </c:if>
                    <script>
                        $("#ftpCheckAll").click(function () {
                            if ($("#ftpCheckAll").is(':checked')) {
                                $(".ml--ftpfile").prop("checked", true);
                            } else {
                                $(".ml--ftpfile").prop("checked", false);
                            }
                        });
                    </script>
                </div>

                <div role="tabpanel" class="tab-pane" id="validations">
                    <!-- TAB: Validations-->
                    <div id="tabs-validations" class="tab">
                        <c:if test="${not empty study.validations.entries}">
                            <!-- if Study fails any validations (status 1:RED, 2:AMBER, 3:GREEN)... -->
                            <c:if test="${study.validations.status.status ne 3 }">
                                <!-- ...allow to send report to submitter via email -->
                                <sec:authorize access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                                    <div class="specs well nbr">
                                        <a class="noLine" rel="nofollow"
                                           href="${pageContext.request.contextPath}/${study.studyIdentifier}/validations/statusReportByMail"
                                           title="<spring:message code="label.sendValidationsStatusReport"/>">
                                            <span class="icon icon-generic" data-icon="E"/>&nbsp;<spring:message
                                                code="label.sendValidationsStatusReport"/></a>
                                    </div>
                                </sec:authorize>
                            </c:if>

                            <sec:authorize access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                                <div class="specs well nbr">
                                    <spring:message code="label.experimentMsgPublic"/>
                                </div>
                            </sec:authorize>

                            <div class="specs well nbr">
                                <spring:message code="msg.curatorsOverride"/>
                                <br>
                                <spring:message code="msg.validationsDescription"/>
                            </div>
                            <c:choose>
                                <c:when test="${curator}">
                                    <%@include file="validationOverride.jsp" %>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="validations" value="${study.validations.entries}"/>
                                    <c:if test="${not empty study.validations.entries}">
                                        <table class="validationsTable table table-striped table-bordered">
                                            <thead class='text_header'>
                                            <tr>
                                                <th>Condition</th>
                                                <th>Status</th>
                                                <th>Description</th>
                                                <th>Requirement</th>
                                                <th>Group</th>
                                                <th>Message</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="validation" items="${study.validations.entries}">
                                                <tr>
                                                    <td>
                                                        <c:set var="validationType" value="${validation.type}"/>
                                                        <c:set var="validationOverridden"
                                                               value="${validation.overriden}"/>
                                                        <c:set var="validationPassedRequirement"
                                                               value="${validation.passedRequirement}"/>
                                                        <%@include file="validation.jsp" %>
                                                    </td>

                                                    <c:if test="${validation.status == 'GREEN'}">
                                                        <td>PASSES</td>
                                                    </c:if>
                                                    <c:if test="${validationPassedRequirement == 'false'}">
                                                        <c:if test="${validationType == 'MANDATORY'}">
                                                            <td>FAILS</td>
                                                        </c:if>
                                                        <c:if test="${validationType == 'OPTIONAL'}">
                                                            <td>INCOMPLETE</td>
                                                        </c:if>
                                                    </c:if>

                                                        <%--<td>${validation.status}</td>--%>
                                                    <td>${validation.description}</td>
                                                    <td>${validation.type}</td>
                                                    <td>${validation.group}</td>
                                                    <td>${validation.message}</td>
                                                </tr>

                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </div>
                </div>
                <c:if test="${not empty study.assays}">
                    <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                        <div role="tabpanel" class="tab-pane" id="metpathways">
                            <div class="row">
                                <div class="col-md-12" id="metexplore-wrapper">
                                    <h4 class="well nbr">Pathways - Assay&nbsp;<c:if
                                            test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                                    </h4>
                                    <div class="col-md-12">
                                        <div class="row">
                                                <label>&emsp;&emsp;Select Pathway(s)</label><br>
                                                <div class="form-group">
                                                    <select class="selectpicker form-control" id="metPathwaysSelect"
                                                            multiple data-live-search="true">
                                                    </select>
                                                </div>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div id="metExploreContainer"></div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <hr>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-12" id="metPathwaysMappingDataContainer">
                                    <div>
                                        <h4>MetExplore Pathways Mapping</h4>
                                        <div class="panel nbr panel-default">
                                            <div class="panel-body">
                                                <table class="table" id="metExploreTable">
                                                    <thead>
                                                    <tr>
                                                        <th>Name</th>
                                                        <th>DB Identifier</th>
                                                        <th>Mapped Metabolite(s)</th>
                                                        <!-- <th>p value</th> -->
                                                    </tr>
                                                    </thead>
                                                    <tbody id="metPathwaysMappingDataTable">
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-12" style="display: none;" id="noPathwaysFound">
                                    <h5 class="well nbr"><i class="fa fa fa-info-circle"></i>&nbsp;No pathways have been found for the metabolites in this study.</h5>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</div>

<a href="#" class="scrollToTop" style="display: inline;"><i class="fa fa-arrow-up"></i></a>

<%--<a id="tourButton" class="btn nbr btn-default" style="display: inline;" >--%>
    <%--<i class="fa fa-lg fa-binoculars"></i>--%>
<%--</a>--%>

<div id="chebiInfo"></div>

<div class="modal fade" id="shareStudy" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content nbr">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><spring:message code="label.study.share"/></h4>
            </div>
            <div class="modal-body">
                <c:if test="${(study.studyStatus == 'INREVIEW') || curator}">
                    <h5><spring:message code="title.study.private.link"/></h5>
                    <p><small><spring:message code="label.study.private.link"/></small></p>
                    <p>
                    <div class="input-group">
                        <input type="text" class="form-control" value="${fullContextPath}/reviewer${study.obfuscationCode}" readonly>
                        <span class="input-group-btn">
                        <button class="btn btn-default ml--clipboard" data-clipboard-text="${fullContextPath}/reviewer${study.obfuscationCode}" type="button">
                            <i class="fa fa-lg fa-clipboard"></i>
                        </button>
                      </span>
                    </div>
                    </p>
                    <br>
                </c:if>
                <c:if test="${(study.studyStatus == 'PUBLIC') || curator}">
                    <h5><spring:message code="title.study.paper.link"/></h5>
                    <p><small><spring:message code="label.study.paper.link"/></small></p>
                    <p>
                    <div class="input-group">
                        <input type="text" class="form-control" value="${fullContextPath}/${study.studyIdentifier}" readonly>
                        <span class="input-group-btn">
                            <button class="btn btn-default ml--clipboard" data-clipboard-text="${fullContextPath}/${study.studyIdentifier}" type="button">
                                <i class="fa fa-lg fa-clipboard"></i>
                            </button>
                          </span>
                    </div>
                    </p>
                </c:if>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $(document).on('click', '.dropdown-menu', function (e) {
            e.stopPropagation();
        });
    });
</script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/metabolights/javascript/aspera/asperaweb-4.js" charset="utf-8"></script>
<script type="text/javascript" src="/metabolights/javascript/aspera/connectinstaller-4.js" charset="utf-8"></script>
<script type="text/javascript" src="/metabolights/javascript/aspera/jquery-ui.js" charset="utf-8"></script>
<script type="text/javascript" src="/metabolights/javascript/aspera/jquery-namespace.js" charset="utf-8"></script>
<script type="text/javascript" src="/metabolights/javascript/aspera/ml-aspera-config.js" charset="utf-8"></script>
<script type="text/javascript" src="/metabolights/javascript/aspera/ml-aspera.js" charset="utf-8"></script>
<script type="text/javascript" src="/metabolights/javascript/aspera/install.js" charset="utf-8"></script>

<script type="text/javascript"
        src="//cdn.rawgit.com/flatlogic/bootstrap-tabcollapse/master/bootstrap-tabcollapse.js"></script>
<script type="text/javascript" charset="utf-8">
    $('#study--tab').tabCollapse();
</script>

<script type="text/javascript" src="//cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.10/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.4.2/js/dataTables.buttons.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.4.2/js/buttons.colVis.min.js"></script>


<script type="text/javascript"
        src="${pageContext.request.contextPath}/javascript/dataTables.conditionalPaging.js"></script>

<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/js/bootstrap-select.min.js"></script>

<script type="text/javascript" src="/metabolights/javascript/Biojs.js" charset="utf-8"></script>
<script type="text/javascript" src="/metabolights/javascript/Biojs.ChEBICompound.js"
        charset="utf-8"></script>

<script src="${orcidServiceUrl}"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-tour/0.11.0/js/bootstrap-tour.min.js"></script>
<script src="${pageContext.request.contextPath}/javascript/Notifier.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/clipboard.js/1.5.10/clipboard.min.js"></script>

<script>
    $(document).ready(function () {

        var clipboard = new Clipboard('.ml--clipboard');
        clipboard.on('success', function(e) {
            Notifier.success("Successful", e.trigger.id + " Copied to clipboard!");
        });

        clipboard.on('error', function(e) {
            console.error('Action:', e.action);
            console.error('Trigger:', e.trigger);
        });

        var tour = new Tour({
            steps: [
                {
                    element: "#mStudyId",
                    title: "Title of my step",
                    content: "Content of my step",
                    placement: "bottom"
                },
                {
                    element: "#mStudyStatus",
                    title: "Title of my step",
                    content: "Content of my step",
                    placement: "bottom"
                }
            ],
            storage: false
        });

        $('#tourButton').click(function (e) {
            tour.init(true);
            tour.start(true);
            tour.restart();
            // it's also good practice to preventDefault on the click event
            // to avoid the click triggering whatever is within href:
            e.preventDefault();
        });

        $('[data-toggle="popover"]').popover({ trigger: "hover click" });

        $("a#viewFiles").click(function (e) {
            e.preventDefault();

            // setter
            $("#tabs").tabs("option", "active", -2);
            $("#tabs-files").effect("highlight", {color: '#E2BD97'}, 1700);
        });

        $("input#fileSelector").bind("keypress keyup", function (e) {

                var code = e.keyCode || e.which;

                if (code == 13) {

                    var value = e.target.value;

                    var checked = true;

                    if (value[0] == "!") {
                        checked = false;
                        value = value.substr(1);
                    }

                    if (value != "") {
                        $("input[name='file'][value*='" + value + "']").attr("checked", checked)
                    }

                    e.target.select();

                    e.preventDefault();
                }
            }
        );

        var hash = $.trim(window.location.hash);
        if (hash) $('.nav-tabs a[href$="' + hash + '"]').trigger('click');

        //Check to see if the window is top if not then display button
        $(window).scroll(function () {
            if ($(this).scrollTop() > 100) {
                $('.scrollToTop').fadeIn();
            } else {
                $('.scrollToTop').fadeOut();
            }
        });

        $('.selectpicker').selectpicker({
            style: 'btn-info'
        });
        $('.selectpicker').on('change', function () {
            loadPathways($('.selectpicker').selectpicker('val'));
        });

        //Click event to scroll to top
        $('.scrollToTop').click(function () {
            $('html, body').animate({scrollTop: 0}, 800);
            return false;
        });


        function showPleaseWait() {
            var modalLoading = '<div class="modal" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false role="dialog">\
                <div class="modal-dialog waiting--dialog">\
                    <div class="modal-content">\
                        <div class="modal-header">\
                            <h4 class="modal-title">Loading pathways</h4>\
                        </div>\
                        <div class="modal-body">\
                            <div class="progress">\
                              <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar"\
                              aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width:100%; height: 40px">\
                              </div>\
                            </div>\
                        </div>\
                    </div>\
                </div>\
            </div>';
            $(document.body).append(modalLoading);
            $("#pleaseWaitDialog").modal("show");
        }

        /**
         * Hides "Please wait" overlay. See function showPleaseWait().
         */
        function hidePleaseWait() {
            $("#pleaseWaitDialog").modal("hide");
        }


        $('#transferDiv').toggle();

        jQuery(function ($) {
            $('.panel-heading .clickable').on("click", function (e) {
                if ($(this).hasClass('panel-collapsed')) {
                    // expand the panel
                    $(this).parents('.panel').find('.panel-body').slideDown();
                    $(this).removeClass('panel-collapsed');
                    $(this).find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
                }
                else {
                    // collapse the panel
                    $(this).parents('.panel').find('.panel-body').slideUp();
                    $(this).addClass('panel-collapsed');
                    $(this).find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
                }
            });
        });

        $('.assay--tab').on('click', function (e) {
            id = $(this).attr("data-assayid");
            getMAFFile(id);
        });


        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            var target = $(e.target).attr("href") // activated tab
            loadData(target);
        });

        function getMAFFile(id) {
            wrapperDiv = $('#mafTableWrapper' + id);
            assayid = id;
            studyid = wrapperDiv.attr("data-studyid");
            obfuscationCode = wrapperDiv.attr("data-obfuscationCode");
            var mafUrl = "";
            if (obfuscationCode != "null" && obfuscationCode) {
                mafUrl = "/metabolights/reviewer" + obfuscationCode + "/assay/" + assayid + "/maf";
            } else {
                mafUrl = "/metabolights/" + studyid + "/assay/" + assayid + "/maf";
            }


            $.ajax({
                url: mafUrl,
                dataType: "html",
            }).done(function (data) {
                wrapperDiv.html(data);
                $('.maf').addClass("table table-striped table-bordered")
                var table = $('.maf').DataTable();

                var chebiInfoDiv = new Biojs.ChEBICompound({
                    target: 'chebiInfo',
                    width: '400px',
                    height: '300px',
                    proxyUrl: undefined,
                    chebiDetailsUrl: '//www.ebi.ac.uk/webservices/chebi/2.0/test/getCompleteEntity?chebiId='
                });

                $('#chebiInfo').hide();


                $("a.showLink").click(function (event) {
                    var clickedId = event.target.id;
                    var idClickedSplit = clickedId.split("_");
                    /*id of the link is made up by 3 parts:
                     part 1: name of the div (eg.: syn) this is used to distinguish the show more
                     link of synonyms from the show more link in other divs
                     part 2: "link" to distinguish the link for show more link from other
                     ordinary links
                     part 3: the order of the result item to distinguish the show more button
                     in the result list is click. In case of filters of species or compounds
                     the order is always 0
                     */
                    var idPrefixClicked = idClickedSplit[0];
                    /*var itemClicked = idClickedSplit[1];*/
                    var orderOfItemClicked = idClickedSplit[2];
                    var idOfHiddenText = "#" + idPrefixClicked + "_" + orderOfItemClicked;
                    var jqClickedId = "#" + clickedId;
                    if ($(idOfHiddenText).is(":hidden")) {
                        $(jqClickedId).text("Show less");
                    } else {
                        $(jqClickedId).text("Show more");
                    }
                    $(idOfHiddenText).slideToggle();
                });

                var metLinkTimer = 0; // 0 is a safe "no timer" value


                function loadMetabolite(e) {
                    metLinkTimer = 0;
                    var metlink;
                    metlink = $(e.target);
                    var metaboliteId = metlink.attr('identifier');
                    // If its a chebi id
                    if (metaboliteId.indexOf("CHEBI:") == 0) {
                        var offset = metlink.offset();
                        var mouseX = offset.left + metlink.outerWidth() + 20;
                        var mouseY = offset.top - 200;
                        chebiId = metaboliteId;
                        $('#chebiInfo img:last-child').remove;
                        $('#chebiInfo').css({
                            'top': mouseY,
                            'left': mouseX,
                            'float': 'left',
                            'position': 'absolute',
                            'z-index': 10
                        });
                        $('#chebiInfo').fadeIn('slow');
                        chebiInfoDiv.setId(chebiId);
                    }
                }

                $(document).on('mouseenter', '.metLink', function (e) {
                    // I'm assuming you don't want to stomp on an existing timer
                    if (!metLinkTimer) {
                        metLinkTimer = setTimeout(function () {
                            loadMetabolite(e);
                        }, 500); // Or whatever value you want
                    }
                }).on('mouseleave', '.metLink', function () {
                    // Cancel the timer if it hasn't already fired
                    if (metLinkTimer) {
                        clearTimeout(metLinkTimer);
                        metLinkTimer = 0;
                    }
                    $('#chebiInfo').fadeOut('slow');
                });


            });
        }

        $('.assayTable').DataTable( {
            dom: 'Bfrtip',
            columnDefs: [
                {
                    targets: 1,
                    className: 'noVis'
                }
            ],
            buttons: [
                {
                    extend: 'colvis',
                    columns: ':not(.noVis)',
                    text: "Customise view"
                }
            ]
        });

        $('.validationsTable').DataTable({
            "bPaginate": false
        });

        $('.assayTable').wrap('<div class="scrollStyle row" />');
        $('.scrollTable').wrap('<div class="scrollStyle row" />');


        $('.dataTable').DataTable();

        $('.protocols-table').DataTable({
            "ordering": false,
            conditionalPaging: true
        });


        $('.quicklinks').click(function (e) {
            e.preventDefault();
            link = $(this).attr('data-destination');

            if ($(this).attr("data-assayid")) {
                id = $(this).attr("data-assayid");
                getMAFFile(id);
            }

            activaTab(link);
            var offset = $("#mltab--content").offset();
            $('html, body').animate({
                scrollTop: offset.top,
                scrollLeft: offset.left
            });
        })

        function activaTab(tab) {
            $('.nav-tabs a[href="#' + tab + '"]').tab('show');
        };

        var asperaLoaded = false;

        $(function () {
            $('.files--tab').on('click', function (e) {
                if (!asperaLoaded) {
                    loadAspera();
                }
            });

        });

        function loadAspera() {
            <c:if test="${study.publicStudy}">
            // If the new tab is NMR...

            // Adds an input element download button that uses Aspera
            var downloadButton = $('<a class="pull-left" id="downloadButton"><span class="icon icon-functional" data-icon="="/> Download whole study (FAST) &nbsp;|&nbsp;</a>');

            $('#asperaDownloadWrapper').prepend(downloadButton);

            function downloadButtonClick(e) {
                $('#transferDiv').show();
                var fc = new METABOLIGHTS.FileControl({
                    sessionId: 'metabolights-download',
                    transferContainer: '#transferDiv',
                    messageContainer: '#noAspera',
                    id: '0'
                });

                source = "studies/public/${study.studyIdentifier}";
                fc.asperaWeb.showSelectFolderDialog({
                    success: function (dataTransferObj) {
                        if (dataTransferObj.dataTransfer.files[0]) fc.download(source, dataTransferObj.dataTransfer.files[0].name);
                    }
                });
            };
            downloadButton.on("click", downloadButtonClick);

            var asperaMultipleFilesDownloadButton = $('<input type="button" value="Aspera: Download Selected Files" class="submit btn btn-default"/>')
            $('#aspDMF').append(asperaMultipleFilesDownloadButton)


            function asperaDownloadSelectedFiles(e) {
                var fc = new METABOLIGHTS.FileControl({
                    sessionId: 'metabolights-download',
                    transferContainer: '#transferDiv',
                    messageContainer: '#noAspera',
                    id: '0'
                });

                var selectedFilesArray = []
                $("input:checkbox[name=file]:checked").each(function () {
                    selectedFilesArray.push('studies/public/${study.studyIdentifier}/' + $(this).val());
                });
                if (selectedFilesArray.length == 0) {
                    alert('Please Select Files to download');
                } else {
                    source = selectedFilesArray;
                    fc.asperaWeb.showSelectFolderDialog({
                        success: function (dataTransferObj) {
                            if (dataTransferObj.dataTransfer.files[0]) fc.download(source, dataTransferObj.dataTransfer.files[0].name);
                        }
                    });
                }
                e.preventDefault();
            };
            asperaMultipleFilesDownloadButton.on("click", asperaDownloadSelectedFiles)
            asperaLoaded = true;
            </c:if>
        }


        function loadData(target) {
            if (target == '#metpathways') {
                loadPathwaysDropdown();
            }
        }


        function loadPathwaysDropdown() {
            MetExploreViz.initFrame("metExploreContainer");
            getMetExploreMappingData();
        }

        var metExploreDataJSONObj;

        function getMetExploreMappingData() {
            showPleaseWait();
            var url = "/metabolights/webservice/study/${study.studyIdentifier}/getMetExploreMappingData";
            $.ajax({
                url: url,
                type: 'GET',

                dataType: 'json',
                success: function (data) {
                    metExploreDataJSONObj = JSON.parse(data.content.replace("NaN", 0));
                    //console.log(metExploreDataJSONObj)
                    var selectedValue;
                    var select = document.getElementById("metPathwaysSelect");
                    var pathwayExists = false;
                    for (var key in metExploreDataJSONObj.pathwayList) {
                        var pathway = metExploreDataJSONObj.pathwayList[key]
                        if (pathway.mappedMetabolite > 0) {
                            if (pathway.name.search("Transport") == -1 && pathway.name.search("Exchange") == -1) {
                                //console.log(pathway);
                                var option = document.createElement("option");
                                option.text = pathway.name + "(" + pathway.mappedMetabolite + ")";
                                option.value = pathway.mysqlId;
                                if (!selectedValue) {
                                    selectedValue = pathway.mysqlId;
                                }
                                select.appendChild(option);

                                $('#metPathwaysMappingDataTable').append('<tr><td>' + pathway.name + '</td><td>' + pathway.dbIdentifier + ' (' + pathway.numberOfMetabolite + ')</td><td>' + pathway.mappedMetabolite + '</td></tr>');
                            }
                            pathwayExists = true;
                        }
                    }

                    $('#metExploreTable').DataTable({
                        "order": [[2, "desc"]]
                    });
                    $('.selectpicker').selectpicker('refresh');
                    $('.selectpicker').selectpicker('val', selectedValue);

                    if(pathwayExists){
                        loadPathways(selectedValue);
                    }else{
                        displayNoPathwaysFound();
                    }

                },
                error: function (request, error) {
                    alert("Request: " + JSON.stringify(request));
                }
            });
        }

        function displayNoPathwaysFound(){
            hidePleaseWait();
            $('#noPathwaysFound').toggle();
            $('#metPathwaysMappingDataContainer').hide();
            $('#metexplore-wrapper').hide();
        }


        $('#loadPathways').on('click', function () {
            //alert();
            hidePleaseWait();
        });

        function loadPathways(ids) {
            if (ids != undefined){
                MetExploreViz.onloadMetExploreViz(function () {
                    var url = "//metexplore.toulouse.inra.fr/metExploreWebService/mapping/graphresult/38285/filteredbypathway?pathwayidlist=(" + ids.toString() + ")";
                    $.ajax({
                        url: url,
                        type: 'GET',
                        async: false,
                        dataType: 'json',
                        success: function (data) {
                            loadPathwayData(JSON.stringify(data));
                            hidePleaseWait();
                        },
                        error: function (request, error) {
                            alert("Request: " + JSON.stringify(request));
                        }
                    });

                });
            }
        }


        function loadPathwayData(myJsonString) {
            MetExploreViz.onloadMetExploreViz(function () {
                metExploreViz.GraphPanel.refreshPanel(myJsonString,
                    function () {
                        metExploreViz.onloadSession(function () {

                            //Load mapping from the webservice
                            var mapJSON = metExploreViz.GraphUtils.parseWebServiceMapping(myJsonString);
                            metExploreViz.GraphMapping.loadDataFromJSON(mapJSON);

                            //Load mapping from Metabolight file
                            //metExploreViz.GraphMapping.loadDataTSV("../../files/mappingoninchi.tsv");

                            // //Color nodes
                            metExploreViz.GraphMapping.graphMappingContinuousData("mappingoninchi.tsv", "S-10", "red", "#8AB146"
                                ,
                                function () {
                                    var sideCompounds = ["M_adp_m", "M_adp_c", "M_amp_c", "M_amp_m", "M_amp_r", "M_amp_x", "M_atp_x", "M_atp_r", "M_atp_m", "M_atp_c", "M_hco3_c", "M_hco3_m", "M_co2_c", "M_co2_x", "M_co2_m", "M_ppi_c", "M_ppi_x", "M_ppi_r", "M_ppi_m", "M_fad_m", "M_fadh2_m", "M_gtp_c", "M_h2o2_x", "M_pi_m", "M_pi_c", "M_nad_x", "M_nad_c", "M_nad_m", "M_nadh_x", "M_nadh_m", "M_nadh_c", "M_nadp_m", "M_nadp_r", "M_nadp_x", "M_nadph_x", "M_nadph_r", "M_nadph_m", "M_o2_x", "M_o2_r", "M_o2_m", "M_h_r", "M_h_x", "M_h_l", "M_h_m", "M_h_c", "M_h_e", "M_so4_l", "M_h2o_x", "M_h2o_l", "M_h2o_m", "M_h2o_r", "M_h2o_e", "M_h2o_c"];
                                    metExploreViz.GraphNode.loadSideCompounds(sideCompounds);
                                    metExploreViz.GraphNetwork.duplicateSideCompounds();
                                }
                            );
                        });
                    });
            });

        }

    });
</script>
<script>
    $(document).ready(function () {
        $('#redirectToEditorPage').click(function(){
            var token = "${userApiToken}";
            localStorage.setItem("apiToken", token);
//                            window.location.href = ;
            window.open("${pageContext.request.contextPath}/studyeditor?studyId=${study.studyIdentifier}");
        })

        thorApplicationNamespace.loadClaimingInfo();

        $('#orcidPopover').popover({
            html : true,
            container: 'body',
            content: function() {
                return $('#orcidPopoverDiv').html();
            }
        });

        $('#orcidPopover').on('shown.bs.popover', function () {
            thorApplicationNamespace.loadClaimingInfo();
        })

        $('body').on('click', function (e) {
            $('#orcidPopover').each(function () {
                // hide any open popovers when the anywhere else in the body is clicked
                if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
                    $(this).popover('hide');
                }
            });
        });

        $('#orcidPopover').on('hidden.bs.popover', function (e) {
            $(e.target).data("bs.popover").inState = { click: false, hover: false, focus: false }
        });

        // Bind resize event
        $(window).bind('resize', function(){
            var isVisible = $('#orcidPopover').data('bs.popover').tip().hasClass('in');
            if(isVisible){
                $('#orcidPopover').popover('show');
            }
        });

        $(document).on('click', '.dropdown-menu', function (e) {
            e.stopPropagation();
        });
        var orcidMtblsClaims;
        $.ajax({
            cache: false,
            url: "//www.ebi.ac.uk/europepmc/thor/api/dataclaiming/findDatabaseClaims/METABOLIGHTS",
            dataType: "json",
            success: function (orchidRespData) {
                orcidMtblsClaims = orchidRespData['lstDatabaseClaims'];
            }
        });
        function getOrcidClaimList() {
            $.ajax({
                cache: false,
                url: "${orcidRetrieveClaimsServiceUrl}:${study.studyIdentifier}",
                dataType: "json",
                success: function (orchidRespData) {

                    var claimListText = '<p style="padding-left: 1em;">';
                    var resultsNumber = orchidRespData['orcid-search-results']['num-found'];
                    if (resultsNumber > 0) {
                        if (typeof thorApplicationNamespace != 'undefined') {
                            for (var uli = 0; uli < orchidRespData['orcid-search-results']['num-found']; uli++) {
                                if(resultsNumber === 1 ){
                                    var userOrcId = orchidRespData['orcid-search-results']['orcid-search-result']['orcid-profile']['orcid-identifier']['path'];
                                    var userOrcName = orchidRespData['orcid-search-results']['orcid-search-result']['orcid-profile']['orcid-bio']['personal-details']['given-names']['value'];
                                    userOrcName += " " + orchidRespData['orcid-search-results']['orcid-search-result']['orcid-profile']['orcid-bio']['personal-details']['family-name']['value'];
                                }else{
                                    var userOrcId = orchidRespData['orcid-search-results']['orcid-search-result'][uli]['orcid-profile']['orcid-identifier']['path'];
                                    var userOrcName = orchidRespData['orcid-search-results']['orcid-search-result'][uli]['orcid-profile']['orcid-bio']['personal-details']['given-names']['value'];
                                    userOrcName += " " + orchidRespData['orcid-search-results']['orcid-search-result'][uli]['orcid-profile']['orcid-bio']['personal-details']['family-name']['value'];
                                }
                                var claimedStudies = getMatchingMtblsOrcidClaims(userOrcId);
//                                claimListText  += '<a tabindex="0" role="button" data-toggle="popover" data-trigger="focus" title="Studies Claimed"\
//                                data-content=\'' + claimedStudies +  '\' ' +
//                                    'data-html="true" class="popup-ajax">'
//                                    + userOrcName + '</a><br>';

                                claimListText  += '<a target="_blank" href="//orcid.org/' + userOrcId +'">'
                                    + userOrcName + '<br>' + claimedStudies + '</a><br>';
                            }
//                            $(function (){
//                                $(".popup-ajax").popover({placement:'right', container:'body'});
//                            });
                        }
                        claimListText += '</p>';
                        $('.existingClaimants').html('<p style="padding-left: 1em;"><strong>Existing ORCID Claims</strong></p><br>' + claimListText);
                    }else {
                        $('.existingClaimants').html('<p style="padding-left: 1em;"><strong>Existing ORCID Claims</strong> <br> None so far</p>');
                    }

                }
            });
        }

        getOrcidClaimList();

        function  getMatchingMtblsOrcidClaims(orcidToMatch){
            var matchingIdsContent = "";
            for (var uli = 0; uli < orcidMtblsClaims.length; uli++) {
                var userOrcId = orcidMtblsClaims[uli]['orcId'];
                if(userOrcId === orcidToMatch){
                    var externalIdentifiers = orcidMtblsClaims[uli]['workExternalIdentifiers'];
                    if(externalIdentifiers.length > 0){
                        var mtblsid = externalIdentifiers[0]['workExternalIdentifierId'];
                        matchingIdsContent += '<a target="_blank" class="small" href="${pageContext.request.contextPath}/' + mtblsid + '">' + mtblsid + '</a><br>';
                    }
                }
            }
//            matchingIdsContent += '<br><a target="_blank" class="small" href="//orcid.org/' + orcidToMatch+'">View ORCID profile</a>';
            return matchingIdsContent;
        }
    });
</script>
<script>
    var title = "${study.title}";
    var description = "${study.description}";
    var editedTitle = title.replace(/[']/g, '');
    var editedDescription = description.replace(/[']/g, '');
    thorApplicationNamespace.createWorkOrcId(editedTitle, 'data-set', '${releaseYear}', '//www.ebi.ac.uk/metabolights/${study.studyIdentifier}', editedDescription, 'METABOLIGHTS');
    thorApplicationNamespace.addWorkIdentifier('other-id', '${study.studyIdentifier}');

    $(function () {
        $('[data-toggle="popover"]').popover();
    })

</script>
<c:if test="${(study.studyStatus == 'INCURATION')}">
    <feedback inline-template>
        <div>
            <div id="feedbackModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="feedbackModal">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Feedback</h4>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label>Overall experience *</label>
                                    <div class="cc-selector">
                                        <input id="happy" type="radio" v-model="experience" name="experience" value="happy" />
                                        <label class="cc happy" for="happy"></label>

                                        <input id="neutral" type="radio" name="experience" v-model="experience" value="neutral" />
                                        <label class="cc neutral" for="neutral"></label>

                                        <input id="sad" type="radio" name="experience" v-model="experience" value="sad" />
                                        <label class="cc sad" for="sad"></label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label>Comments</label>
                                    <textarea v-model="comments" checked="checked" class="form-control" rows="3"></textarea>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <span class="pull-left">
                                <button type="button" data-dismiss="modal" class="btn btn-sm btn-default">Ask me later</button>
                            </span>
                            <button :disabled="!isValid" @click="sendFeedback()" type="button" class="btn btn-sm btn-primary">Submit</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </feedback>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.18.0/axios.min.js"></script>
    <script>
        Vue.component('feedback', {
            data: function () {
                return {
                    experience: 'happy',
                    comments: ''
                }
            },
            created () {
                this.getFeedback();
            },
            methods: {
                getFeedback: function () {
                    axios.post('${pageContext.request.contextPath}/webservice/study/${study.studyIdentifier}/feedback', {
                    },{
                        headers: { 'user_token': '${userApiToken}' }
                    }).then(function (response) {
                        if(!response.data.content){
                            $('#feedbackModal').modal('show')
                        }
                    }).catch(function (error) {
                        console.log(error);
                    });
                },
                sendFeedback: function () {
                    axios.post('${pageContext.request.contextPath}/webservice/study/${study.studyIdentifier}/feedback', {
                        'experience' : this.experience,
                        'comments' : this.comments,
                    },{
                        headers: { 'user_token': '${userApiToken}' }
                    }).then(function (response) {
                        if(response.data.content){
                            alert("Thank you for your valuable feedback.")
                            $('#feedbackModal').modal('hide')
                        }else{
                            alert("Error saving the feedback. Please try again later")
                        }
                    }).catch(function (error) {
                        console.log(error);
                    });
                }
            },
            computed: {
                isValid: function () {
                    if(this.experience != null){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        })
        var feedbackVM = new Vue({
            el: 'body',
            data: {
            }
        })
    </script>
</c:if>