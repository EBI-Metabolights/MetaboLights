<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

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

<link rel="stylesheet" href="cssrl/iconfont/font_style.css" type="text/css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" />
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.10/css/dataTables.bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"/>
<link rel="stylesheet" href="css/ChEBICompound.css" type="text/css" />
<link rel="stylesheet" href="css/metabolights.css"  type="text/css" />


<script type="text/javascript" src="javascript/Biojs.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/Biojs.ChEBICompound.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/jquery.linkify-1.0-min.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/chebicompoundpopup.js" charset="utf-8"></script>



<script language="javascript" type="text/javascript">
    $(document).ready(function() {

        $("a#viewFiles").click(function(e) {
            e.preventDefault();

            // setter
            $("#tabs").tabs( "option", "active", -2 );
            $("#tabs-files").effect("highlight", {color: '#E2BD97'}, 1700);
        });

        $("input#fileSelector").bind("keypress keyup", function(e) {

            var code = e.keyCode || e.which;

            if (code == 13){

                var value = e.target.value;

                var checked = true;

                if(value[0] == "!"){
                    checked = false;
                    value = value.substr(1);
                }

                if (value != ""){
                    $("input[name='file'][value*='" + value + "']").attr("checked", checked)
                }

                e.target.select();

                e.preventDefault();


            }

        });

    });

</script>


<%--<c:set var="readOnly" value="${!fn:contains(servletPath,study.studyIdentifier)}"/>--%>

<ol class="progtrckr" data-progtrckr-steps="${fn:length(studyStatuses)}">
    <c:forEach var="status" items="${studyStatuses}"><%--
        --%><c:if test="${status gt study.studyStatus}"><%--
            --%><li class="progtrckr-todo" title="${status.description}">${status.descriptiveName}</li><%--
        --%></c:if><%--
        --%><c:if test="${status le study.studyStatus}"><%--
            --%><li class="progtrckr-done" title="${status.description}">${status.descriptiveName}</li><%--
        --%></c:if><%--
    --%></c:forEach>
</ol>


<div class="container-fluid">

    <div class="study--wrapper col-md-12">

        <h2 class="study--title">
            <span class="study--id">${study.studyIdentifier}:</span>&nbsp;
            ${study.title}
        </h2>

        <div class="study--infopanel">

            <div class="col-md-5 no--padding">
                <p><i class="fa fa-user"></i>&nbsp;
                    <c:forEach var="contact" items="${study.contacts}" varStatus="loopStatus">
                        <c:if test="${loopStatus.index ne 0}">, </c:if>
                            <span id="aff" <c:if test="${not empty contact.affiliation}">title="${contact.affiliation}"</c:if>>
                                ${contact.firstName}&nbsp;${contact.lastName}
                            </span>
                    </c:forEach>
                </p>
                <p>
                    <a data-toggle="modal" data-target="#shareStudy"><i class="fa fa-link"></i>&nbsp;
                        <spring:message code="label.study.share"/>
                    </a>
                </p>
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
                <p class="text-right"><i class="fa fa-bookmark"></i>&nbsp;<spring:message
                        code="ref.msg.status"/>:&nbsp;${study.studyStatus.descriptiveName}</p>
            </div>
        </div>

        <c:if test="${not empty study.description}">
            <div class="btn-group pull-right" role="group" aria-label="...">

                <div class="btn-group" role="group">
                    <c:if test="${fn:length(study.assays) eq 1}">
                        <button type="button" class="btn btn-default dropdown-toggle quicklinks" data-assayid="1" data-destination="assay<c:if test="${fn:length(study.assays) gt 0}">${assay.assayNumber}</c:if>">
                            <i class="ml--icons fa fa-fire pull-left"></i> View Metabolites
                            <span class="icon icon-conceList of study filesptual" data-icon="b"></span><spring:message code="label.assays"/><c:if test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                        </button>
                    </c:if>
                    <c:if test="${fn:length(study.assays) gt 1}">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="ml--icons fa fa-fire pull-left"></i> View Metabolites
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu">
                            <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                                <li>
                                    <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                                        <a class="quicklinks" data-assayid="<c:if test="${fn:length(study.assays) gt 0}">${assay.assayNumber}</c:if>" data-destination="assay<c:if test="${fn:length(study.assays) gt 0}">${assay.assayNumber}</c:if>" >
                                            <span class="icon icon-conceList of study filesptual" data-icon="b"></span><spring:message code="label.assays"/><c:if test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                                        </a>
                                    </c:if>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>


                </div>
                <button type="button" class="btn btn-default quicklinks files--tab" data-destination="files"><i class="ml--icons fa fa-download pull-left"></i> Download Study files</button>

            </div>

            <p class="study--subtitle"><b>Study Description</b></p>

            <p class="study--description">${study.description}</p>

        </c:if>
        <div class="tabs--wrapper">
            <div>
                <!-- Nav tabs -->
                <ul class="nav nav-tabs responsive" id="study--tab" role="tablist">
                    <c:if test="${study.factors != null}">
                        <li role="presentation" class="active"><a href="#description" aria-controls="description" role="tab" data-toggle="tab"><spring:message code="label.studyDesign"/></a></li>
                    </c:if>
                    <c:if test="${not empty study.protocols}">
                        <li role="presentation"><a href="#protocols" aria-controls="protocols" role="tab" data-toggle="tab"><spring:message code="label.protocols"/></a></li>
                    </c:if>
                    <c:if test="${not empty study.sampleTable}">
                        <li role="presentation"><a href="#sample" aria-controls="sample" role="tab" data-toggle="tab"><spring:message code="label.sample"/></a></li>
                    </c:if>
                    <c:if test="${not empty study.assays}">
                        <c:if test="${fn:length(study.assays) eq 1}">
                            <li role="presentation">
                                <a class="assay--tab" href="#assay" aria-controls="assay${assay.assayNumber}" data-assayid="1" role="tab" data-toggle="tab">
                                    <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                                        <c:set var="metabolitesExist" value="true"/>
                                        <span class="icon icon-conceptual" data-icon="b"></span>
                                    </c:if>
                                    <spring:message code="label.assays"/> <c:if test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                                </a>
                            </li>
                        </c:if>
                        <c:if test="${fn:length(study.assays) gt 1}">
                            <li class="dropdown">
                                <ul class="dropdown-menu">
                                    <c:set var="metabolitesExist" value="false"/>
                                    <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                                        <li>
                                            <a class="assay--tab" href="#assay${assay.assayNumber}" aria-controls="assay${assay.assayNumber}" data-assayid="${assay.assayNumber}" role="tab" data-toggle="tab">
                                                <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                                                    <c:set var="metabolitesExist" value="true"/>
                                                    <span class="icon icon-conceptual" data-icon="b"></span>
                                                </c:if>
                                                <spring:message code="label.assays"/> <c:if test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                    <c:if test="${metabolitesExist eq true}">
                                        <span id="study-metabolitesicon" class="icon icon-conceptual" data-icon="b"></span>
                                    </c:if>
                                    <spring:message code="label.assays"/>&nbsp;<span class="caret"></span>
                                </a>
                            </li>
                        </c:if>
                    </c:if>
                    <c:if test="${not empty files}">
                        <li role="presentation"><a href="#files" class="files--tab" aria-controls="files" role="tab" data-toggle="tab"><spring:message code="label.Files"/></a></li>
                    </c:if>

                    <c:if test="${not empty study.validations}">
                        <li role="presentation">
                            <a id="valid-tab" href="#validations" aria-controls="validations" role="tab" data-toggle="tab">
                                <spring:message code="label.studyvalidation"/>&nbsp;
                                <c:set var="validationstatus" value="${study.validations.status}"/>
                                <c:set var="validationoverridden" value="${study.validations.overriden}"/>
                                <%@include file="validations.jsp" %>
                            </a>
                        </li>
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
                                            <div class="panel-heading"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span>&nbsp; <spring:message code="label.organisms"/></div>
                                            <div class="panel-body">
                                                <c:forEach var="org" items="${study.organism}" >
                                                    <p>${org.organismName}</p>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>



                                </c:if>
                                <c:if test="${not empty study.descriptors}">
                                    <div class="col-md-12">
                                        <div class="panel panel-default">
                                            <div class="panel-heading"><span class="glyphicon glyphicon-list" aria-hidden="true"></span>&nbsp; <spring:message code="label.studyDesign"/></div>
                                            <div class="panel-body">
                                                <c:forEach var="design" items="${study.descriptors}" >
                                                    <p>${design.description}</p>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${not empty study.publications}">
                                   <div class="col-md-12">
                                       <div class="panel panel-default">
                                           <div class="panel-heading"><span class="glyphicon glyphicon-book" aria-hidden="true"></span>&nbsp;<spring:message code="label.publications"/></div>
                                           <div class="panel-body">
                                               <c:forEach var="pub" items="${study.publications}" varStatus="loopPublications">
                                               <div class="panel panel-default">
                                                   <div class="panel-body">
                                                       [${loopPublications.index+1}]&nbsp;
                                                       <c:set var="DOIValue" value="${pub.doi}"/>
                                                       <c:choose>
                                                           <c:when test="${not empty pub.pubmedId}">
                                                               <a href="http://europepmc.org/abstract/MED/${pub.pubmedId}" target="_blank">${pub.title}</a>
                                                           </c:when>
                                                           <c:when test="${not empty pub.doi}">
                                                               <c:if test="${fn:contains(DOIValue, 'DOI')}">
                                                                   <c:set var="DOIValue" value="${fn:replace(DOIValue, 'DOI:','')}"/>
                                                               </c:if>
                                                               <c:if test="${fn:contains(DOIValue, 'dx.doi')}">
                                                                   <a href="${DOIValue}">${pub.title}</a>
                                                               </c:if>
                                                               <c:if test="${not fn:contains(DOIValue, 'dx.doi')}">
                                                                   <a href="http://dx.doi.org/${DOIValue}">${pub.title}</a>
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
                                           <div class="panel-heading"><span class="glyphicon glyphicon-tags" aria-hidden="true"></span>&nbsp; <spring:message code="label.experimentalFactors"/></div>
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
                                <table class="dataTable table table-striped table-bordered">
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

                    <div role="tabpanel" class="tab-pane" id="sample">
                        <c:if test="${not empty study.sampleTable}">
                                <table class="dataTable table table-striped table-bordered">
                                    <thead class='text_header'>
                                    <tr>
                                        <c:forEach var="fieldSet" items="${study.sampleTable.fields}">
                                            <c:set var="headerTitle" value="${fieldSet.value.description}"/>
                                            <c:if test="${not empty fieldSet.value.fieldType}">
                                                <c:set var="headerTitle" value="${headerTitle} - Type: ${fieldSet.value.fieldType}"/>
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
                                                        <c:set var="cellvalue" value="<A href='http://www.ebi.ac.uk/biosamples/sample/${cellvalue}'>${cellvalue}</A>"/>
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
                        <c:if test="${not empty study.validations.entries}">
                            <div class="specs well">
                                Validations marked with (*) are specially approved by the MetaboLights Curators
                            </div>
                            <table class="dataTable table table-striped table-bordered">
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
                                            <c:set var="validationOverridden" value="${validation.overriden}"/>
                                            <c:set var="validationPassedRequirement" value="${validation.passedRequirement}"/>
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
                    </div>

                    <script type="text/javascript">
                        function confirmDeleteFiles(element){

                            var dialog = $("#confirmaction");

                            // Fill dialog
                            ///var targetUrl = $(element).attr("href");
                            var text = $(element).attr("confirmationText");

                            dialog.text(text);

                            $(dialog).dialog({
                            //           autoOpen: false,
                                modal: true,
                                buttons : {
                                    "Confirm" : function() {
                                        ///window.location.href = targetUrl;

                                        var frm = element.form;
                                        // change action for deleting files
                                        frm.action = "${study.studyIdentifier}/files/deleteSelFiles";
                                        frm.method ="post";
                                        frm.submit();

                                        // change action back to download files (default)
                                        frm.reset();
                                        frm.action = "${study.studyIdentifier}/files/downloadSelFiles";
                                        frm.method ="post";

                                    },
                                    "Cancel" : function() {
                                        $(this).dialog("close");
                                    }
                                }
                            });

                        //       $(dialog).dialog("open");

                            return false;
                        }

                        function confirmDeleteFtpFiles(element){
                            var dialog = $("#confirmaction");

                            // Fill dialog
                            var text = $(element).attr("confirmationText");

                            dialog.text(text);

                            $(dialog).dialog({
                                modal: true,
                                buttons : {
                                    "Confirm" : function() {

                                        var frm = element.form;
                                        // change action for deleting files
                                        frm.action = "${study.studyIdentifier}/files/deleteSelFtpFiles";
                                        frm.method ="post";
                                        frm.submit();

                                        // change action back to move files (default)
                                        frm.reset();
                                        frm.action = "${study.studyIdentifier}/files/moveFilesfromFtpFolder";
                                        frm.method ="post";
                                    },
                                    "Cancel" : function() {
                                        $(this).dialog("close");
                                    }
                                }
                            });

                            return false;
                        }
                    </script>

                    <c:if test="${fn:length(study.assays) eq 1}">
                        <c:if test="${not empty study.assays}">
                            <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                                <div role="tabpanel" class="tab-pane" id="assay">
                                    <h3>Assay&nbsp;<c:if test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if></h3>
                                    <div class="specs well">
                                        <h5><spring:message code="label.assayName"/>:<b> <a href="/metabolights/${study.studyIdentifier}/files/${assay.fileName}"><span class="icon icon-fileformats" data-icon="v">${assay.fileName}</span></a></b></h5>
                                        <h5><spring:message code="label.measurement"/>: <b>${assay.measurement}</b></h5>
                                        <h5><spring:message code="label.technology"/>:  <b>${assay.technology}
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
                                                    <span class="icon icon-conceptual" data-icon="b"></span><spring:message code="label.mafFileFound"/>
                                                </h4>
                                            </div>

                                            <div class="panel-body">
                                                <div id="mafTableWrapper${assay.assayNumber}" data-studyid="${study.studyIdentifier}" data-assayid="${assay.assayNumber}">
                                                    <p class="text-center"><span><img src="/metabolights/img/beta_loading.gif"></span></p>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>

                                    <!-- start of assay data -->

                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4><span class="icon icon-functional" data-icon="A"></span><spring:message code="label.data"/></h4>
                                        </div>

                                        <div class="panel-body">
                                            <div>
                                                <c:if test="${not empty assay.assayTable}">
                                                    <div class="custom--wrapper">
                                                        <table class="assayTable  table table-striped table-bordered" cellpadding="0" cellspacing="0" border="0">
                                                            <thead class='text_header'>
                                                            <tr>
                                                                <c:forEach var="fieldSet" items="${assay.assayTable.fields}">
                                                                    <th title="${fieldSet.value.description}">${fieldSet.value.cleanHeader}</th>
                                                                </c:forEach>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="row" items="${assay.assayTable.iterator}">
                                                                <tr>
                                                                    <c:forEach var="cell" items="${row.iterator}">

                                                                        <c:set var="cellvalue" value="${cell.value}" scope="page"/>

                                                                        <c:if test="${cell.field.header eq 'Sample Name'}">
                                                                            <c:if test="${fn:startsWith(cellvalue, 'SAMEA')}">
                                                                                <c:set var="cellvalue" value="<A href='http://www.ebi.ac.uk/biosamples/sample/${cell.value}'>${cell.value}</A>"/>
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
                                    <h3>Assay&nbsp;<c:if test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if></h3>
                                    <div class="specs well">
                                        <h5><spring:message code="label.assayName"/>:<b> <a href="/metabolights/${study.studyIdentifier}/files/${assay.fileName}"><span class="icon icon-fileformats" data-icon="v">${assay.fileName}</span></a></b></h5>
                                        <h5><spring:message code="label.measurement"/>: <b>${assay.measurement}</b></h5>
                                        <h5><spring:message code="label.technology"/>:  <b>${assay.technology}
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
                                                    <span class="icon icon-conceptual" data-icon="b"></span><spring:message code="label.mafFileFound"/>
                                                </h4>
                                            </div>

                                            <div class="panel-body">
                                                <div id="mafTableWrapper${assay.assayNumber}" data-studyid="${study.studyIdentifier}" data-assayid="${assay.assayNumber}">
                                                    <p class="text-center"><span><img src="/metabolights/img/beta_loading.gif"></span></p>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>

                                    <!-- start of assay data -->

                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4><span class="icon icon-functional" data-icon="A"></span><spring:message code="label.data"/></h4>
                                        </div>

                                        <div class="panel-body">
                                            <div>
                                                <c:if test="${not empty assay.assayTable}">
                                                    <div class="custom--wrapper">
                                                        <table class="assayTable  table table-striped table-bordered" cellpadding="0" cellspacing="0" border="0">
                                                            <thead class='text_header'>
                                                            <tr>
                                                                <c:forEach var="fieldSet" items="${assay.assayTable.fields}">
                                                                    <th title="${fieldSet.value.description}">${fieldSet.value.cleanHeader}</th>
                                                                </c:forEach>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="row" items="${assay.assayTable.iterator}">
                                                                <tr>
                                                                    <c:forEach var="cell" items="${row.iterator}">

                                                                        <c:set var="cellvalue" value="${cell.value}" scope="page"/>

                                                                        <c:if test="${cell.field.header eq 'Sample Name'}">
                                                                            <c:if test="${fn:startsWith(cellvalue, 'SAMEA')}">
                                                                                <c:set var="cellvalue" value="<A href='http://www.ebi.ac.uk/biosamples/sample/${cell.value}'>${cell.value}</A>"/>
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

                            <form id="selFilesForm" action="/metabolights/${study.studyIdentifier}/files/downloadSelFiles" method="post">
                                <h5 class="well">
                                    <!--  Request FTP folder -->
                                    <c:if test="${(study.studyStatus == 'SUBMITTED') and !hasPrivateFtpFolder }">
                                        <sec:authorize access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                                            &nbsp;
                                            <a class="noLine" rel="nofollow" href="/metabolights/${study.studyIdentifier}/files/requestFtpFolder" title="<spring:message code="label.requestFtpFolder"/>">
                                                <span class="icon icon-functional" data-icon="D"/><spring:message code="label.requestFtpFolder"/>
                                            </a>
                                        </sec:authorize>
                                        &nbsp;|&nbsp;
                                    </c:if>
                                    <!--  Request FTP folder -->
                                    <a class="noLine" rel="nofollow" href="/metabolights/${study.studyIdentifier}/files/${study.studyIdentifier}${token}" title="<spring:message code="label.downloadstudy"/>">
                                        <span class="icon icon-functional" data-icon="="/><spring:message code="label.downloadstudy"/>
                                    </a>
                                    &nbsp;|&nbsp;
                                    <a class="noLine" rel="nofollow" href="/metabolights/${study.studyIdentifier}/files/metadata${token}" title="<spring:message code="label.downloadstudyMetadata"/>">
                                        <span class="icon icon-functional" data-icon="="><spring:message code="label.downloadstudyMetadata"/>
                                    </a>
                                    &nbsp;
                                    <c:if test="${study.publicStudy}">
                                        |&nbsp;
                                        <a class="noLine" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/studies/public/${study.studyIdentifier}" title="<spring:message code="label.viewAllFiles"/>">
                                            <span class="icon icon-generic" data-icon="x"/><spring:message code="label.viewAllFiles"/>
                                        </a>
                                    </c:if>
                                    &nbsp;
                                    <c:if test="${study.publicStudy}">
                                        |&nbsp;
                                        <span id="asperaDownloadWrapper"></span>
                                    </c:if>

                                </h5>

                                <br/>

                                <div id="transferDiv" class="transferDiv well">
                                    <h5>Aspera Download Details:</h5><hr>
                                </div>
                                <div id="noAspera" class="noAspera"></div>

                                <h4><spring:message code="label.fileListTableExplanation"/></h4>
                                <div class="">
                                    <div class="input-group">
                                        <input class="inputDiscrete form-control" id="fileSelector" type="text" placeholder="<spring:message code='label.fileList.Input.placeholder'/>">
                                          <span class="input-group-btn">
                                            <button class="btn btn-default" type="button">?</button>
                                          </span>
                                    </div><!-- /input-group -->
                                </div>
                                <c:if test="${!study.publicStudy}">
                                    <input type="hidden" name="token" value="${study.obfuscationCode}">
                                </c:if>
                                <table id="files" class="filesTable table table-striped table-bordered" style="width: 100%;">
                                    <thead>
                                    <tr>
                                        <th>Select</th>
                                        <th>File</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="file" items="${files}">
                                        <%--<c:if test="${!file.directory}">--%>
                                        <tr>
                                            <td><input type="checkbox" name="file" value="${file.name}"/></td>
                                            <td>
                                                <a rel="nofollow" href="/metabolights/${study.studyIdentifier}/files/${file.name}${token}">${file.name}</a>
                                            </td>
                                        </tr>
                                        <%--</c:if>--%>
                                    </c:forEach>
                                    </tbody>
                                </table>


                                <div style="position: relative; width: 100%;">
                                    <div style="float: left; padding: 10px;">
                                        <input name="btnSubmit" type="submit" class="submit" value="<spring:message code="label.downloadSelectedFiles"/>"/>
                                        <c:if test="${study.publicStudy}">
                                            <span id="aspDMF"></span>
                                        </c:if>
                                    </div>
                                    <sec:authorize ifAnyGranted="ROLE_SUPER_USER">
                                        <div style="float: right; padding: 10px;">
                                            <input id="deleteSelFiles" name="deleteSelFiles" type="button" class="submit cancel" value="<spring:message code="label.deleteSelectedFiles"/>"
                                                   confirmationText="This will delete all selected files from the system, no way back!." onclick="return confirmDeleteFiles(this);"/>
                                        </div>
                                    </sec:authorize>
                                </div>

                                    <%--Show instructions--%>
                                <div class="ui-state-highlight ui-corner-all">
                                    <p><strong>Info:</strong><spring:message code="label.fileListTableInstructions"/></p>
                                    <sec:authorize ifAnyGranted="ROLE_SUPER_USER">
                                        <p><spring:message code="label.fileListTableDelInstructions"/></p>
                                    </sec:authorize>
                                </div>
                            </form>


                            <br/><br/>
                            <!-- private FTP files -->
                            <c:if test="${(study.studyStatus == 'SUBMITTED') and hasPrivateFtpFolder }">
                                <div class="accordion">
                                    <h5 class="ftpFolder"><span class="icon icon-generic" data-icon="D"/></span>&nbsp;<spring:message code="label.priavteFtpFolder"/></h5>
                                    <div>
                                        <h5><spring:message code="label.ftpFileListTableExplanation"/></h5>
                                        <c:if test="${empty ftpFiles}">&emsp;&emsp;[EMPTY]</c:if>

                                        <c:if test="${!empty ftpFiles}">
                                            <form id="selFtpFilesForm" action="${study.studyIdentifier}/files/moveFilesfromFtpFolder" method="post">

                                                <!-- <p><input class="inputDiscrete resizable" id="ftpFileSelector" class="" type="text" placeholder="<spring:message code='label.ftpFileList.Input.placeholder'/>"></p> -->

                                                <table id="privFtpFiles" class="ftpFiles">
                                                    <tr>
                                                        <th>Select</th>
                                                        <th>File</th>
                                                    </tr>
                                                    <tbody>
                                                    <c:forEach var="ftpFile" items="${ftpFiles}">
                                                        <tr>
                                                            <td><input type="checkbox" class="asperaMultipleFiles" name="ftpFile" value="${ftpFile.name}"/></td>
                                                            <td>
                                                                    ${ftpFile.name}
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>

                                                <div style="position: relative; width: 100%;">
                                                    <sec:authorize access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                                                        <div style="float: left; padding: 10px;">
                                                            <input name="moveFtpSelFilesBtn" type="button" class="submit" value="<spring:message code="label.moveSelectedFiles"/>"
                                                                   onclick="form.submit();" />
                                                        </div>
                                                        <div style="float: right; padding: 10px;">
                                                            <input id="deleteFtpSelFilesBtn" name="deleteFtpSelFilesBtn" type="button" class="submit cancel" value="<spring:message code="label.deleteFtpSelFiles"/>"
                                                                   confirmationText="This will delete all selected files from the system, no way back!." onclick="return confirmDeleteFtpFiles(this);"/>
                                                        </div>
                                                    </sec:authorize>
                                                </div>

                                                    <%--Show instructions--%>
                                                <div class="ui-state-highlight ui-corner-all">
                                                    <p><strong>Info:</strong><spring:message code="label.moveFileListTableInstructions"/></p>
                                                </div>
                                            </form>
                                        </c:if>
                                    </div>



                                </div>
                            </c:if>
                            <!-- private FTP files -->
                        </c:if>
                    </div>

                </div>

            </div>
        </div>
    </div>
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
                <p><input class="form-control" type="text" value="${fullContextPath}/${study.studyIdentifier}" readonly/></p>
                <c:if test="${study.studyStatus == 'INREVIEW'}">
                    <h5><spring:message code="title.study.private.link"/></h5>
                    <p><spring:message code="label.study.private.link"/></p>
                    <p><input class="inputDiscrete resizable" type="text" value="${fullContextPath}/reviewer${study.obfuscationCode}" readonly/></p>
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

<script type="text/javascript" charset="utf-8">

    $(document).ready(function() {
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

        $('.assay--tab').on('click', function(e){
            id = $(this).attr("data-assayid");
            getMAFFile(id);
        });

        function getMAFFile(id){
            wrapperDiv = $('#mafTableWrapper'+id);
            assayid = id;
            studyid = wrapperDiv.attr("data-studyid");
            var mafUrl = "/metabolights/"+ studyid +"/assay/"+ assayid + "/maf";
            $.ajax({
                url: mafUrl,
                dataType: "html",
            }).done(function(data) {
                wrapperDiv.html(data);
                $('.maf').addClass("table table-striped table-bordered")
                $('.maf').DataTable();
            });
        }

        $('.assayTable').DataTable();

        $('.assayTable').wrap('<div class="scrollStyle" />');

        $('.dataTable').DataTable();

        $('.quicklinks').click(function(e){
            e.preventDefault();
            link =  $(this).attr('data-destination');

            if ($(this).attr("data-assayid")){
                id= $(this).attr("data-assayid");
                getMAFFile(id);
            }

            activaTab(link);
            var offset = $("#mltab--content").offset();
            $('html, body').animate({
                scrollTop: offset.top,
                scrollLeft: offset.left
            });
        })

        function activaTab(tab){
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

        var asperaLoaded = false;

        $(function () {
            $('.files--tab').on('click', function(e){
                if(!asperaLoaded) {
                    loadAspera();
                }
            });

        });

        function loadAspera(){
            <c:if test="${study.publicStudy}">
            // If the new tab is NMR...

                // Adds an input element download button that uses Aspera
                var downloadButton = $('<a id="downloadButton">Aspera: Download Study</a>');

                $('#asperaDownloadWrapper').append(downloadButton);



                function downloadButtonClick(e) {
                    $('#transferDiv').show();
                    var fc = new METABOLIGHTS.FileControl( { sessionId: 'metabolights-download',
                        transferContainer: '#transferDiv',
                        messageContainer: '#noAspera',
                        id: '0' });

                    source = "studies/public/${study.studyIdentifier}";
                    fc.asperaWeb.showSelectFolderDialog( { success: function(dataTransferObj) { if (dataTransferObj.dataTransfer.files[0]) fc.download(source, dataTransferObj.dataTransfer.files[0].name); } });
                };
                downloadButton.on("click", downloadButtonClick);

                var asperaMultipleFilesDownloadButton = $('<input type="button" value="Aspera: Download Selected Files" class="submit"/>')
                $('#aspDMF').append(asperaMultipleFilesDownloadButton)


                function asperaDownloadSelectedFiles(e){
                    var fc = new METABOLIGHTS.FileControl( { sessionId: 'metabolights-download',
                        transferContainer: '#transferDiv',
                        messageContainer: '#noAspera',
                        id: '0' });

                    var selectedFilesArray = []
                    $("input:checkbox[name=file]:checked").each(function(){
                        selectedFilesArray.push('studies/public/${study.studyIdentifier}/' + $(this).val());
                    });
                    if(selectedFilesArray.length == 0){
                        alert('Please Select Files to download');
                    }else{
                        source = selectedFilesArray;
                        fc.asperaWeb.showSelectFolderDialog( { success: function(dataTransferObj) { if (dataTransferObj.dataTransfer.files[0]) fc.download(source, dataTransferObj.dataTransfer.files[0].name); } });
                    }
                    e.preventDefault();
                };
                asperaMultipleFilesDownloadButton.on("click", asperaDownloadSelectedFiles )
                asperaLoaded = true;
            </c:if>
        }
    });
</script>