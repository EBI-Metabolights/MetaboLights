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

<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.10/css/dataTables.bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/ChEBICompound.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-tour/0.10.3/css/bootstrap-tour.css"
      type="text/css"/>

<link rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/css/bootstrap-select.min.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/Biojs.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/Biojs.ChEBICompound.js"
        charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery.linkify-1.0-min.js"
        charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/chebicompoundpopup.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/javascript/MetExplore/metExploreViz/metexploreviz.js"
        charset="utf-8"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="//ves-ebi-37.ebi.ac.uk:8080/ThorWebTest/resources/js/dataclaiming/ebithor-claiming.0.2.js"></script>


<script language="javascript" type="text/javascript">
    $(document).ready(function () {

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

        var hash = $.trim( window.location.hash );

        if (hash) $('.nav-tabs a[href$="'+hash+'"]').trigger('click');

    });



</script>

<script>
    thorApplicationNamespace.createWorkOrcId('${study.title}', 'data-set', '${releaseYear}', 'http://www.ebi.ac.uk/metabolights/${study.studyIdentifier}', '${study.description}');
    thorApplicationNamespace.addWorkIdentifier('other-id', '${study.studyIdentifier}');

    //claim list
    function getOrcidClaimList(){
        $.ajax({
            cache: false,
            url: "http://ves-ebi-37.ebi.ac.uk:8080/ThorWebTest/api/orcid/find/other-identifier-type:${study.studyIdentifier}",
            dataType: "json",
            success: function(orchidRespData) {
                console.log( orchidRespData );
                var claimListText = "";
                if(orchidRespData['orcid-search-results']['num-found'] > 0){

                    if(typeof thorApplicationNamespace != 'undefined'){
                        for(var uli=0; uli < orchidRespData['orcid-search-results']['num-found']; uli++){
                            var userOrcId = orchidRespData['orcid-search-results']['orcid-search-result'][uli]['orcid-profile']['orcid-identifier']['path'];
                            claimListText += '<br><a href="http://europepmc.org/search?query=AUTHORID:\''+userOrcId+'\'&sortby=Date">'+userOrcId+'</a>';
                        }
                    }
                }
                if(claimListText != ""){
                    $('.existingClaimants').html(claimListText);
                    $('.thor_div_showIf_datasetAlreadyClaimedList').show();
                }
                else{
                    claimListText += '<br>No claims so far';
                    $('.existingClaimants').html(claimListText);
                }

            }
        });
    }
    //claim list
    function logInTheCurrentUser(element){

        // Fill dialog
        var targetUrl = $(element).attr("href");
        var text = $(element).attr("confirmationText");

        dialog.text(text);

        $(dialog).dialog({
//           autoOpen: false,
            modal: true,
            buttons : {
                "Confirm" : function() {
                    window.location.href = targetUrl;
                },
                "Cancel" : function() {
                    $(this).dialog("close");
                }
            }
        });
    }
</script>

<%--<c:set var="readOnly" value="${!fn:contains(servletPath,study.studyIdentifier)}"/>--%>

<ol class="progtrckr" data-progtrckr-steps="${(fn:length(studyStatuses))-1}">

        <c:set var="statusInitial" value="${studyStatuses[0]}"/>
<li class="progtrckr-done" title="${statusInitial.description}">${statusInitial.descriptiveName}</li><%--
         --%><c:choose>
        <c:when test="${study.studyStatus eq studyStatuses[4]}"><%--
        --%><c:forEach begin="1" end="3" var="status" items="${studyStatuses}"><%--
            --%><li class="progtrckr-todo" title="${status.description}">${status.descriptiveName}</li><%--
         --%></c:forEach><%--
        --%></ol>
        </c:when>
        <c:otherwise><%--
        --%><c:forEach begin="1" end="3" var="status" items="${studyStatuses}"><%--
        --%><c:if test="${status gt study.studyStatus}"><%--
            --%>
            <li class="progtrckr-todo" title="${status.description}">${status.descriptiveName}</li><%--
        --%></c:if><%--
        --%><c:if test="${status le study.studyStatus}"><%--
            --%>
            <li class="progtrckr-done" title="${status.description}">${status.descriptiveName}</li><%--
        --%></c:if><%--
        --%></c:forEach><%--
        --%></ol><%--
        --%></c:otherwise><%--
        --%></c:choose>

<div class="container-fluid">

    <div class="study--wrapper col-md-12">
        <h2 class="study--title col-md-9">
            <span class="study--id" id="mStudyId">${study.studyIdentifier}:</span>&nbsp;
            ${study.title}
        </h2>

        <div class="study--infopanel">

            <div class="col-md-5 no--padding">
                <p><i class="fa fa-user"></i>&nbsp;<spring:message code="ref.msg.CitationAuthors"/>:
                    <c:forEach var="contact" items="${study.contacts}" varStatus="loopStatus">
                        <c:if test="${loopStatus.index ne 0}">, </c:if>
                            <span id="aff"
                                  <c:if test="${not empty contact.affiliation}">title="${contact.affiliation}"</c:if>>
                                 <strong>${contact.firstName}&nbsp;${contact.lastName}</strong>
                            </span>
                    </c:forEach>
                </p>
                <div>
                    <%@include file="studyActions.jsp" %>
                    <a data-toggle="modal" data-target="#shareStudy"><i class="fa fa-link"></i>&nbsp;
                        <spring:message code="label.study.share"/>
                    </a>
                    <%-- &nbsp;|&nbsp;
                    <a href="${pageContext.request.contextPath}/beta/${study.studyIdentifier}" class="icon icon-generic" data-icon="&lt;">BETA</a> --%>
                </div>
            </div>

            <div class="col-md-7 no--padding">
                <p class="text-right">
                    <i class="fa fa-calendar"></i>&nbsp;
                    <c:if test="${not empty study.studySubmissionDate}">
                        <spring:message code="label.subDate"/>: <strong
                            title="<fmt:formatDate pattern="dd-MMM-yyyy hh:mm" value="${study.studySubmissionDate}"/>"><fmt:formatDate
                            pattern="dd-MMM-yyyy" value="${study.studySubmissionDate}"/></strong>
                    </c:if>
                    <c:if test="${not empty study.studyPublicReleaseDate}">, <spring:message
                            code="label.releaseDate"/>: <strong><fmt:formatDate pattern="dd-MMM-yyyy"
                                                                                value="${study.studyPublicReleaseDate}"/></strong>
                    </c:if><c:if test="${not empty study.updateDate}">, <spring:message code="label.updateDate"/>:
                    <strong title="${study.updateDate}"><fmt:formatDate pattern="dd-MMM-yyyy"
                                                                        value="${study.updateDate}"/></strong>
                </c:if>
                </p>
                <p class="text-right" id="mStudyStatus">
                    <i class="fa fa-user">&nbsp;</i><spring:message code="label.subm"/>:&nbsp;
                    <c:forEach var="owner" items="${liteStudy.users}">
                        <a href="mailto:${owner.userName}?subject=<spring:message code="msg.emailStudyLinkSubject"/>&nbsp;${liteStudy.studyIdentifier}">${owner.fullName}</a>
                    </c:forEach>
                    &nbsp;|&nbsp;

        <c:choose>
            <c:when test="${study.studyStatus.descriptiveName eq 'Dormant'}">
                <i class="fa fa-bookmark"></i>&nbsp;<spring:message
                    code="ref.msg.status"/>:&nbsp;<span class="label label-pill label-danger" style="font-family: sans-serif;font-size: 18px;font-weight: lighter">${study.studyStatus.descriptiveName}</span>
            </c:when>
            <c:otherwise>
                <i class="fa fa-bookmark"></i>&nbsp;<spring:message
                    code="ref.msg.status"/>:&nbsp;${study.studyStatus.descriptiveName}
            </c:otherwise>
        </c:choose>


    <%-- &emsp;
    <button type="button" class="btn btn-default btn-xs" data-toggle="tooltip" data-placement="bottom" id="tourButton" title="Study Tour"><i class="fa fa-compass"></i></button>--%>
    </p>
    </div>
    </div>

        <c:if test="${not empty study.description}">
            <div class="btn-group pull-right" role="group" aria-label="...">

                <div class="btn-group" role="group">
                    <c:if test="${fn:length(study.assays) eq 1}">
                        <button type="button" class="btn btn-default dropdown-toggle quicklinks" data-assayid="1"
                                data-destination="assay<c:if test="${fn:length(study.assays) gt 0}">${assay.assayNumber}</c:if>"
                                <c:if test="${(empty study.assays[0].metaboliteAssignment) and ( empty study.assays[0].metaboliteAssignment.metaboliteAssignmentFileName) }">disabled</c:if> >
                            <i class="ml--icons fa fa-fire pull-left"></i> View Metabolites
                            <span class="icon icon-conceList of study filesptual" data-icon="b"></span><spring:message
                                code="label.assays"/><c:if
                                test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                        </button>
                    </c:if>
                    <c:if test="${fn:length(study.assays) gt 1}">
                        <ul class="dropdown-menu">
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
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false"
                                <c:if test="${mafExist ne true}">disabled</c:if>>
                            <i class="ml--icons fa fa-fire pull-left"></i> View Metabolites
                            <span class="caret"></span>
                        </button>

                    </c:if>


                </div>
                <button type="button" class="btn btn-default quicklinks files--tab" data-destination="files"><i
                        class="ml--icons fa fa-download pull-left"></i> Download Study files
                </button>
                <c:choose>
                <c:when test="${userApiToken eq 'MetaboLights-anonymous'}">
                <button type="button" class="btn btn-default" title="To Claim this study login into MetaboLights and link your ORCID account" onclick="location.href ='login'"><i
                        class="thorOrcIdSpan">
                    <img src="//www.ebi.ac.uk/europepmc/thor/resources/orcid-id.png" value="What is ORCID?" width="15" height="15" data-pin-nopin="true">
                </i>  Claim this study to ORCID
                </button>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${not empty userOrcidID}">
                            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Claim this study to ORCID
                            </button>
                           <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <div class="thor_div_showIf_notSigned">
                                    <table>
                                        <tr>
                                            <td class="thor_div_showIf_datasetAlreadyClaimedList">
                                                <button type="button" class="btn btn-default" onclick="getOrcidClaimList()"
                                                        data-toggle="collapse" data-target="#claimants">${study.studyIdentifier} ORCID claims
                                                </button>
                                                <div id="claimants" class="collapse existingClaimants">
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>You can <a href="#" class="thor_a_generate_signinLink">sign-in to ORCID</a> to claim
                                                your
                                                data
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><input type="checkbox" class="thor_checkbox_rememberMe_cookie"> Remember
                                                me on this computer
                                            </td>
                                        </tr>
                                    </table>
                                </div>

                                <div class="thor_div_showIf_signedIn">
                                    <table>
                                        <tr>
                                            <td class="thor_div_showIf_datasetAlreadyClaimedList">
                                                <button type="button" class="btn btn-default" onclick="getOrcidClaimList()"
                                                        data-toggle="collapse" data-target="#claimants1">${study.studyIdentifier} ORCID claims
                                                </button>
                                                <div id="claimants1" class="collapse existingClaimants">
                                                </div>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td>You have signed in as <label class="thor_label_show_userName"></label></td>
                                        </tr>
                                        <tr style="display:none" class="thor_div_showIf_datasetNotClaimed">
                                            <td>You can <a href="#"
                                                           class="thor_a_generate_claimLink">claim ${study.studyIdentifier}</a>
                                                into your ORCID.
                                            </td>
                                        </tr>
                                        <tr style="display:none" class="thor_div_showIf_datasetAlreadyClaimed">
                                            <td>You have claimed ${study.studyIdentifier} into your ORCID.
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><a href="#" class="thor_a_generate_logoutLink"><i>logout</i></a>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </div>


                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton2" data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="false" onclick="location.href='myAccount'">
                                Update your ORCID ID
                            </button>

                        </c:otherwise></c:choose>
                </c:otherwise>
                </c:choose>


            </div>

            <p class="study--subtitle"><b>Study Description</b></p>

            <p class="study--description">${study.description}</p>

        </c:if>
        <div class="tabs--wrapper">
            <div>
                <!-- Nav tabs -->
                <ul class="nav nav-tabs responsive" id="study--tab" role="tablist">
                    <c:if test="${study.factors != null}">
                        <li role="presentation" class="active"><a href="#description" aria-controls="description"
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

                <!-- Tab panes -->
                <div class="tab-content study--tabcontent responsive col-sm-12" id="mltab--content">

                    <div role="tabpanel" class="tab-pane active" id="description">
                        <!-- TAB1: INFO-->
                        <c:if test="${study.factors != null}">
                            <c:if test="${not empty study.organism}">
                                <div class="col-md-12">
                                    <div class="panel panel-default">
                                        <div class="panel-heading"><span class="glyphicon glyphicon-globe"
                                                                         aria-hidden="true"></span>&nbsp;
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
                                    <div class="panel panel-default">
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

                            <c:if test="${not empty study.publications}">
                                <div class="col-md-12">
                                    <div class="panel panel-default">
                                        <div class="panel-heading"><span class="glyphicon glyphicon-book"
                                                                         aria-hidden="true"></span>&nbsp;<spring:message
                                                code="label.publications"/></div>
                                        <div class="panel-body">
                                            <c:forEach var="pub" items="${study.publications}"
                                                       varStatus="loopPublications">
                                                <div class="panel panel-default">
                                                    <div class="panel-body">
                                                        [${loopPublications.index+1}]&nbsp;
                                                        <c:set var="DOIValue" value="${pub.doi}"/>
                                                        <c:choose>
                                                            <c:when test="${not empty pub.pubmedId}">
                                                                <a href="http://europepmc.org/abstract/MED/${pub.pubmedId}"
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
                                                                       href="http://dx.doi.org/${DOIValue}">${pub.title}</a>
                                                                </c:if>
                                                            </c:when>
                                                            <c:otherwise>${pub.title}</c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${not empty study.factors}">
                                <div class="col-md-12">
                                    <div class="panel panel-default">
                                        <div class="panel-heading"><span class="glyphicon glyphicon-tags"
                                                                         aria-hidden="true"></span>&nbsp;
                                            <spring:message code="label.experimentalFactors"/></div>
                                        <div class="panel-body">
                                            <c:forEach var="fv" items="${study.factors}">
                                                <p>${fv.name}</p>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </div>

                    <div role="tabpanel" class="tab-pane" id="protocols">
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
                                                <td class="tableitem">${protocol.name}</td>
                                                <td id="protocoldesc" class="tableitem">${protocol.description}</td>
                                            </tr>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                    </div>

                    <div role="tabpanel" class="tab-pane ml--oh" id="sample">
                        <c:if test="${not empty study.sampleTable}">
                            <table class="dataTable table table-striped table-bordered">
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
                                                           value="<a href='http://www.ebi.ac.uk/biosamples/sample/${cellvalue}'>${cellvalue}</a>"/>
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

                    <div role="tabpanel" class="tab-pane" id="validations">
                        <!-- TAB: Validations-->
                        <div id="tabs-validations" class="tab">
                            <c:if test="${not empty study.validations.entries}">
                                <!-- if Study fails any validations (status 1:RED, 2:AMBER, 3:GREEN)... -->
                                <c:if test="${study.validations.status.status ne 3 }">
                                    <!-- ...allow to send report to submitter via email -->
                                    <sec:authorize access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                                        <div class="specs well">
                                            <a class="noLine" rel="nofollow"
                                               href="${pageContext.request.contextPath}/${study.studyIdentifier}/validations/statusReportByMail"
                                               title="<spring:message code="label.sendValidationsStatusReport"/>">
                                                <span class="icon icon-generic" data-icon="E"/><spring:message
                                                    code="label.sendValidationsStatusReport"/></a>
                                        </div>
                                    </sec:authorize>
                                </c:if>

                                <sec:authorize access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                                    <div class="specs well">
                                        <spring:message code="label.experimentMsgPublic"/>
                                    </div>
                                </sec:authorize>


                                <div class="specs well">
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

                    <c:if test="${fn:length(study.assays) eq 1}">
                        <c:if test="${not empty study.assays}">
                            <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                                <div role="tabpanel" class="tab-pane" id="assay">
                                    <h3>Assay&nbsp;<c:if
                                            test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if></h3>
                                    <div class="specs well">
                                        <h5><spring:message code="label.assayName"/>:<b> <a
                                                href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/${assay.fileName}"><span
                                                class="icon icon-fileformats" data-icon="v">${assay.fileName}</span></a></b>
                                        </h5>
                                        <h5><spring:message code="label.measurement"/>: <b>${assay.measurement}</b></h5>
                                        <h5><spring:message code="label.technology"/>: <b>${assay.technology}
                                            <c:if test="${fn:contains(assay.technology,'NMR')}">
                                                <span aria-hidden="true" class="icon2-NMRLogo"></span>
                                            </c:if>
                                            <c:if test="${fn:contains(assay.technology,'mass')}">
                                                <span aria-hidden="true" class="icon2-MSLogo"></span>
                                            </c:if></b></h5>
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
                                                                                       value="<a href='http://www.ebi.ac.uk/biosamples/sample/${cell.value}'>${cell.value}</a>"/>
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
                                    <div class="specs well">
                                        <h5><spring:message code="label.assayName"/>:<b> <a
                                                href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/${assay.fileName}"><span
                                                class="icon icon-fileformats" data-icon="v">${assay.fileName}</span></a></b>
                                        </h5>
                                        <h5><spring:message code="label.technology"/>: <b>${assay.technology}
                                            <c:if test="${fn:contains(assay.technology,'NMR')}">
                                                <span aria-hidden="true" class="icon2-NMRLogo"></span>
                                            </c:if>
                                            <c:if test="${fn:contains(assay.technology,'mass')}">
                                                <span aria-hidden="true" class="icon2-MSLogo"></span>
                                            </c:if></b></h5>
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
                                                                                       value="<a href='http://www.ebi.ac.uk/biosamples/sample/${cell.value}'>${cell.value}</a>"/>
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


                        <c:if test="${not empty study.assays}">
                            <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                                <div role="tabpanel" class="tab-pane" id="metpathways">

                                    <div class="col-md-12">
                                        <h3 class="well">Pathways - Assay&nbsp;<c:if
                                                test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if></h3>

                                        <div class="well col-md-12">
                                            <div class="">
                                                <label>&emsp;&emsp;Select Pathway(s)</label><br>
                                                <div class="col-xs-11">
                                                    <div class="form-group">
                                                        <select class="selectpicker form-control" id="metPathwaysSelect"
                                                                multiple data-live-search="true">
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-md-1">
                                                    <div class="form-group">
                                                        <a class="btn btn-success ml--button form-control"
                                                           id="loadPathways" role="button">Load</a>
                                                    </div>
                                                </div>
                                            </div>


                                            <div class="col-md-12">
                                                <div class="col-md-12">
                                                    <div id="metExploreContainer">
                                                    </div>
                                                </div>
                                            </div>


                                        </div>
                                    </div>


                                    <div class="col-md-12">
                                        <div id="metPathwaysMappingDataContainer">
                                            <div class="">
                                                <br>
                                                <div class="well">
                                                    <h4>MetExplore Pathways Mapping</h4>
                                                </div>
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
                            </c:forEach>
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
                                <h5 class="well">
                                    <!--  Request FTP folder -->
                                    <c:if test="${(study.studyStatus == 'SUBMITTED') and !hasPrivateFtpFolder }">
                                        <sec:authorize access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                                            &nbsp;
                                            <a class="noLine" rel="nofollow"
                                               href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/requestFtpFolder"
                                               title="<spring:message code="label.requestFtpFolder"/>">
                                                <span class="icon icon-functional" data-icon="D"/><spring:message
                                                    code="label.requestFtpFolder"/>
                                            </a>
                                        </sec:authorize>
                                    </c:if>
                                    <!--  Request FTP folder -->
                                    <c:if test="${study.studySize <= 1024000 }">
                                        <a class="noLine" rel="nofollow"
                                           href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/${study.studyIdentifier}${token}"
                                           title="<spring:message code="label.downloadstudy"/>">
                                            <span class="icon icon-functional" data-icon="="/><spring:message
                                                code="label.downloadstudy"/>
                                        </a>
                                        &nbsp;|&nbsp;
                                    </c:if>

                                    <c:if test="${study.publicStudy}">
                                        <a class="noLine"
                                           href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/studies/public/${study.studyIdentifier}"
                                           title="<spring:message code="label.viewAllFiles"/>">
                                            <span class="icon icon-generic" data-icon="x"/><spring:message
                                                code="label.viewAllFiles"/>
                                        </a>
                                        &nbsp;|&nbsp;
                                    </c:if>
                                    &nbsp;
                                    <a class="noLine" rel="nofollow"
                                       href="${pageContext.request.contextPath}/${study.studyIdentifier}/files/metadata${token}"
                                       title="<spring:message code="label.downloadstudyMetadata"/>">
                                        <span class="icon icon-functional" data-icon="="><spring:message
                                                code="label.downloadstudyMetadata"/>
                                    </a>
                                    &nbsp;
                                    <c:if test="${study.publicStudy}">
                                        <span id="asperaDownloadWrapper"></span>
                                    </c:if>
                                    &nbsp;
                                    <c:if test="${(study.studyStatus == 'SUBMITTED') and hasPrivateFtpFolder }">
                                        <span class="pull-right"><i class="fa fa-angle-double-down"></i>&nbsp;<a href="#ftpFolderDetails">FTP folder details</a></span>
                                    </c:if>

                                </h5>

                                <br/>

                                <div id="transferDiv" class="transferDiv well">
                                    <h5>Aspera Download Details:</h5>
                                    <hr>
                                </div>
                                <div id="noAspera" class="noAspera"></div>

                                <h4><spring:message code="label.fileListTableExplanation"/></h4>
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
                                        <input name="btnSubmit" type="submit" class="submit"
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
                                </div>

                                    <%--Show instructions--%>
                                <div class="ui-state-highlight ui-corner-all">
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
                                        &nbsp;<spring:message code="label.priavteFtpFolder"/></h5>

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
                                                            <input type="checkbox" name="ftpCheckAll" id="ftpCheckAll">&emsp;Select All
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
                                                                   data-info="This will delete all selected files from your private FTP folder, no way back!."
                                                                   data-toggle="modal"
                                                                   data-target="#confirm-ftp-delete-modal"
                                                                   id="deleteFtpSelFilesBtn" name="deleteFtpSelFilesBtn"
                                                                   type="button" class="submit cancel"
                                                                   value="<spring:message code="label.deleteFtpSelFiles"/>"/>
                                                        </div>
                                                    </sec:authorize>
                                                </div>

                                                    <%--Show instructions--%>
                                                <div class="ui-state-highlight ui-corner-all">
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

                </div>

            </div>
        </div>
    </div>
    <div id="chebiInfo"></div>
    <a href="#" class="scrollToTop"><i class="fa fa-arrow-up"></i></a>
</div>

<div class="modal fade" id="shareStudy" role="dialog">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title"><spring:message code="label.study.share"/></h4>
            </div>
            <div class="modal-body">
                <h5><spring:message code="title.study.paper.link"/></h5>
                <p><spring:message code="label.study.paper.link"/></p>
                <p><input class="form-control" type="text" value="${fullContextPath}/${study.studyIdentifier}"
                          readonly/></p>
                <c:if test="${(study.studyStatus == 'INREVIEW') || curator}">
                    <h5><spring:message code="title.study.private.link"/></h5>
                    <p><spring:message code="label.study.private.link"/></p>
                    <p><input class="inputDiscrete resizable" type="text"
                              value="${fullContextPath}/reviewer${study.obfuscationCode}" readonly/></p>
                </c:if>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://raw.githubusercontent.com/flatlogic/bootstrap-tabcollapse/master/bootstrap-tabcollapse.js"></script>
<script type="text/javascript" charset="utf-8">
    $('#study--tab').tabCollapse();
</script>

<script type="text/javascript" src="https://cdn.datatables.net/1.10.10/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.10/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/javascript/dataTables.conditionalPaging.js"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/js/bootstrap-select.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.9.4/js/i18n/defaults-*.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-tour/0.10.3/js/bootstrap-tour.js"></script>

<script type="text/javascript">
    // Instance the tour
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
        ]

    });

    $('#tourButton').click(function (e) {
        tour.init(true);
        tour.start(true);
        // it's also good practice to preventDefault on the click event
        // to avoid the click triggering whatever is within href:
        e.preventDefault();
    });


</script>

<script type="text/javascript" charset="utf-8">

    $(document).ready(function () {

        //Check to see if the window is top if not then display button
        $(window).scroll(function(){
            if ($(this).scrollTop() > 100) {
                $('.scrollToTop').fadeIn();
            } else {
                $('.scrollToTop').fadeOut();
            }
        });

        //Click event to scroll to top
        $('.scrollToTop').click(function(){
            $('html, body').animate({scrollTop : 0},800);
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

        $('.selectpicker').selectpicker({
            style: 'btn-info'
        });

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
                    metExploreDataJSONObj = JSON.parse(data.content);
                    //console.log(metExploreDataJSONObj)
                    var selectedValue;
                    var select = document.getElementById("metPathwaysSelect");
                    for (var key in metExploreDataJSONObj.pathwayList) {
                        var pathway = metExploreDataJSONObj.pathwayList[key]
                        if (pathway.mappedMetabolite > 0) {
                            if (pathway.name.search("Transport")==-1 && pathway.name.search("Exchange")==-1){
                                //console.log(pathway);
                                var option = document.createElement("option");
                                option.text = pathway.name + "(" + pathway.mappedMetabolite + ")";
                                option.value = pathway.mysqlId;
                                if (!selectedValue){
                                    selectedValue = pathway.mysqlId;
                                }
                                select.appendChild(option);

                                $('#metPathwaysMappingDataTable').append('<tr><td>' + pathway.name + '</td><td>' + pathway.dbIdentifier + ' (' + pathway.numberOfMetabolite + ')</td><td>' + pathway.mappedMetabolite + '</td></tr>');
                            }
                        }
                    }

                    $('#metExploreTable').DataTable({
                        "order": [[2, "desc"]]
                    });
                    $('.selectpicker').selectpicker('refresh');
                    $('.selectpicker').selectpicker('val', selectedValue);
                    loadPathways(selectedValue);
                    hidePleaseWait();

                },
                error: function (request, error) {
                    alert("Request: " + JSON.stringify(request));
                }
            });
        }


        $('#loadPathways').on('click', function () {
            //alert();
            showPleaseWait();
            //console.log($('.selectpicker').selectpicker('val'));
            loadPathways($('.selectpicker').selectpicker('val'));
            hidePleaseWait();
        });

        function loadPathways(ids) {
            MetExploreViz.onloadMetExploreViz(function () {
                var url = "http://metexplore.toulouse.inra.fr:8080/metExploreWebService/mapping/graphresult/38285/filteredbypathway?pathwayidlist=(" + ids.toString() + ")";
                $.ajax({
                    url: url,
                    type: 'GET',
                    async: false,
                    dataType: 'json',
                    success: function (data) {
                        loadPathwayData(JSON.stringify(data))
                    },
                    error: function (request, error) {
                        alert("Request: " + JSON.stringify(request));
                    }
                });

            });
        }


        function loadPathwayData(myJsonString) {


            MetExploreViz.onloadMetExploreViz(function(){

                metExploreViz.GraphPanel.refreshPanel(myJsonString,
                        function(){
                            metExploreViz.onloadSession(function(){

                                //Load mapping from the webservice
                                var mapJSON = metExploreViz.GraphUtils.parseWebServiceMapping(myJsonString);
                                metExploreViz.GraphMapping.loadDataFromJSON(mapJSON);

                                //Load mapping from Metabolight file
                                //metExploreViz.GraphMapping.loadDataTSV("../../files/mappingoninchi.tsv");

                                // //Color nodes
                                metExploreViz.GraphMapping.graphMappingContinuousData("mappingoninchi.tsv", "S-10", "red", "#8AB146"
                                        ,
                                        function(){
                                            var sideCompounds = ["M_adp_m", "M_adp_c", "M_amp_c", "M_amp_m", "M_amp_r", "M_amp_x", "M_atp_x", "M_atp_r", "M_atp_m", "M_atp_c", "M_hco3_c", "M_hco3_m", "M_co2_c", "M_co2_x", "M_co2_m", "M_ppi_c", "M_ppi_x", "M_ppi_r", "M_ppi_m", "M_fad_m", "M_fadh2_m", "M_gtp_c", "M_h2o2_x", "M_pi_m", "M_pi_c", "M_nad_x", "M_nad_c", "M_nad_m", "M_nadh_x", "M_nadh_m", "M_nadh_c", "M_nadp_m", "M_nadp_r", "M_nadp_x", "M_nadph_x", "M_nadph_r", "M_nadph_m", "M_o2_x", "M_o2_r", "M_o2_m", "M_h_r", "M_h_x", "M_h_l", "M_h_m", "M_h_c", "M_h_e", "M_so4_l", "M_h2o_x", "M_h2o_l", "M_h2o_m", "M_h2o_r", "M_h2o_e", "M_h2o_c"];
                                            metExploreViz.GraphNode.loadSideCompounds(sideCompounds);
                                            metExploreViz.GraphNetwork.duplicateSideCompounds();
                                        }
                                );
                            });
                        });
            });

        }


        function getMAFFile(id) {
            wrapperDiv = $('#mafTableWrapper' + id);
            assayid = id;
            studyid = wrapperDiv.attr("data-studyid");
            obfuscationCode = wrapperDiv.attr("data-obfuscationCode");
            var mafUrl = "";
            if (obfuscationCode != "null" && obfuscationCode){
                mafUrl  = "/metabolights/reviewer" + obfuscationCode + "/assay/" + assayid + "/maf";
            }else{
                mafUrl = "/metabolights/" + studyid + "/assay/" + assayid + "/maf";
            }
            $.ajax({
                url: mafUrl,
                dataType: "html",
            }).done(function (data) {
                wrapperDiv.html(data);
                $('.maf').addClass("table table-striped table-bordered")
                $('.maf').DataTable();

                var chebiInfoDiv = new Biojs.ChEBICompound({
                    target: 'chebiInfo',
                    width: '400px',
                    height: '300px',
                    proxyUrl: undefined,
                    chebiDetailsUrl: 'http://www.ebi.ac.uk/webservices/chebi/2.0/test/getCompleteEntity?chebiId='
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
                    // Clear this as flag there's no timer outstanding
                    metLinkTimer = 0;
                    var metlink;
                    metlink = $(e.target);
                    var metaboliteId = metlink.attr('identifier');
                    // If its a chebi id
                    if (metaboliteId.indexOf("CHEBI:") == 0) {
                        //var mouseX = metlink.left + metlink.offsetParent.offsetLeft + metlink.offsetWidth + 80;
                        //var mouseY = metlink.top + metlink.offsetParent.offsetTop + metlink.offsetParent.offsetParent.offsetTop;
                        var offset = metlink.offset();
                        var mouseX = offset.left + metlink.outerWidth() + 20;
                        var mouseY = offset.top;
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


                $('.metLink').on('mouseenter', function (e) {
                    // I'm assuming you don't want to stomp on an existing timer
                    if (!metLinkTimer) {
                        metLinkTimer = setTimeout(function () {
                            loadMetabolite(e);
                        }, 500); // Or whatever value you want
                    }
                }).on('mouseleave', function () {
                    // Cancel the timer if it hasn't already fired
                    if (metLinkTimer) {
                        clearTimeout(metLinkTimer);
                        metLinkTimer = 0;
                    }
                    $('#chebiInfo').fadeOut('slow');
                });


            });
        }

        $('.assayTable').DataTable();

        $('.validationsTable').DataTable({
            "bPaginate": false
        });

        $('.assayTable').wrap('<div class="scrollStyle" />');

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

    });

</script>


<script type="text/javascript" src="/metabolights/javascript/aspera/asperaweb-4.js" charset="utf-8"></script>
<script type="text/javascript" src="/metabolights/javascript/aspera/connectinstaller-4.js" charset="utf-8"></script>

<script type="text/javascript" src="/metabolights/javascript/aspera/jquery-ui.js" charset="utf-8"></script>

<script type="text/javascript" src="/metabolights/javascript/aspera/jquery-namespace.js" charset="utf-8"></script>
<script type="text/javascript" src="/metabolights/javascript/aspera/ml-aspera-config.js" charset="utf-8"></script>

<script type="text/javascript" src="/metabolights/javascript/aspera/ml-aspera.js" charset="utf-8"></script>

<script type="text/javascript" src="/metabolights/javascript/aspera/install.js" charset="utf-8"></script>


<script>
    $(document).ready(function () {

        thorApplicationNamespace.loadClaimingInfo();
        getOrcidClaimList();

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

            var asperaMultipleFilesDownloadButton = $('<input type="button" value="Aspera: Download Selected Files" class="submit"/>')
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

    });
</script>
