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

<script type="text/javascript" src="javascript/Biojs.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/Biojs.ChEBICompound.js" charset="utf-8"></script>
<%--<script type="text/javascript" src="http://www.ebi.ac.uk/Tools/biojs/registry/src/Biojs.ChEBICompound.js" charset="utf-8"></script>--%>
<script type="text/javascript" src="javascript/jquery.linkify-1.0-min.js" charset="utf-8"></script>
<script type="text/javascript" src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/chebicompoundpopup.js" charset="utf-8"></script>

<link rel="stylesheet" href="cssrl/iconfont/font_style.css" type="text/css" />
<link rel="stylesheet"  href="css/ChEBICompound.css" type="text/css" />
<link rel="stylesheet"  href="css/metabolights.css" type="text/css" />
<%--<link rel="stylesheet"  href="http://cdn.datatables.net/1.10.4/css/jquery.dataTables.css" type="text/css" />--%>
<link rel="stylesheet"  href="cssrl/dataTable.css" type="text/css" />

<script language="javascript" type="text/javascript">

    $(document).ready(function() {

        $("[id='protocoldesc']").linkify();

        // Listen to click in MAF file title
        // Like this: <h5 class="maf" mafurl=....
        $("h5.maf").click(function(event) {


            var headerClicked = event.target;

            if (!$(headerClicked).is("h5")){
                headerClicked = $(headerClicked).parent()[0];
            }

            var mafUrl = headerClicked.getAttribute("mafurl");

            if (mafUrl == undefined) return;

            $(headerClicked).removeAttr("mafurl");

            var assay = headerClicked.getAttribute("assay");
            var tablePlaceholder = $(headerClicked).next()[0];

            // If the placeholder (DIV) is empty
            if (tablePlaceholder.innerHTML == ""){

                $.ajax({
                    url: mafUrl,
                    dataType: "html",
                    context: headerClicked
                }).done(function(data) {

                    var tablePlaceHolder = $(this).next()[0];
                    $(tablePlaceHolder).html(data);

                    scanCompoundLinks()

                    initOrRefreshCoolTable("table.maf[assay='" + $(this).attr("assay") + "']");

                });

            }
        });

        $(function() {
            $( ".accordion" ).accordion({
                collapsible: true
                ,active:1
                ,heightStyle: "content"
            });
        });

        $(function() {
            $('#accordion h5.none').accordion({
                collapsible: false
            });
        });


        $("#shareInfo").hide();

        $("a.share").click(function(e) {
            e.preventDefault();
            $("#shareInfo").dialog({
                height:400,
                width: "60%",
                modal: true
            });
        });

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




        $( "#tabs" ).tabs({
            "activate": function(event , ui) {


                initOrRefreshCoolTable(undefined, ui.newPanel);

            }
        });



    });


    function initOrRefreshCoolTable(selector, context){

        var table;

        if (selector === undefined) {
            selector = "table.display";
        }


        if (context){
            table = $(context).find(selector);
        } else {
            table =$(selector);
        }



        // For those not initialized yet
        var toInitialise = $.grep(table, function (table,index){
            return !$(table).hasClass("dataTable");
        })


        if (toInitialise.length >0){

            // Get attributes
            var order = eval($(toInitialise).attr("order"));

            if (typeof order === typeof undefined || order === false) {
                order = [];
            }

            $(toInitialise).dataTable( {
                "scrollX": true,
                "order": [],
                "language": {
                    "search": "Filter:"
                },
                "pageLength": 25,
                "order": order
            } );

        }

//        var toResize = $.grep(table, function (table,index){
//            return $(table).hasClass("dataTable");
//        })
//
//        if (toResize.length >0){
//
//            $(toResize).dataTable().draw(false);
//
//        }



    }
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

<br/>

<div class="push_1 grid_22 alpha omega">
    <h3>${study.studyIdentifier}:&nbsp;${study.title}
        <span class="right Share_study">
            <a class="noLine share" href="#" title="<spring:message code="label.study.share"/>">
            <span class="icon icon-generic" data-icon="L"><spring:message code="label.study.share"/>
            </a>
        </span>
    </h3>
</div>

<div class="push_1 grid_22 subtitle alpha omega">
    <span>
        <a class="noLine share" href="#" title="<spring:message code="label.study.share"/>">
            <span class="icon icon-generic" data-icon="L"><spring:message code="label.study.share"/>
        </a>
        &nbsp;|&nbsp;
        <a id="viewFiles" class="noLine" href="#" title="<spring:message code="label.viewAllFiles"/>">
            <span class="icon icon-functional" data-icon="b"/><spring:message code="label.viewAllFiles"/>
        </a>
        &nbsp;|&nbsp;
        <a href="${pageContext.request.contextPath}/beta/${study.studyIdentifier}" class="icon icon-generic" data-icon="&lt;">BETA</a>
        &nbsp;|&nbsp;
        <%@include file="entryActions.jsp" %>
        <c:if test="${!(study.publicStudy)}">
            <span class="right">
            &nbsp;<spring:message code="label.expPrivate"/>
            </span>
        </c:if>
    </span>
</div>

<%--<c:if test="${!readOnly}">--%>
    <%--<jsp:useBean id="datenow" class="java.util.Date" scope="page" />--%>
    <%--<a class="noLine" href="updatepublicreleasedateform?study=${study.studyIdentifier}&date=<fmt:formatDate pattern="dd-MMM-yyyy" value="${datenow}" />" title="<spring:message code="label.makeStudyPublic"/>">--%>
        <%--&nbsp;<span class="icon icon-generic" data-icon="}" id="ebiicon" /><spring:message code="label.makeStudyPublic"/>--%>
    <%--</a>--%>
<%--</c:if>--%>


<%--<c:set var="stringToFind" value="${study.studyIdentifier}:assay:" />--%>
<div class="push_1 grid_22 alpha omega">

    <span class="right">
        <c:if test="${not empty study.studySubmissionDate}">
            <spring:message code="label.subDate"/>: <strong title="<fmt:formatDate pattern="dd-MMM-yyyy hh:mm" value="${study.studySubmissionDate}"/>"><fmt:formatDate pattern="dd-MMM-yyyy" value="${study.studySubmissionDate}"/></strong>
        </c:if>
        <c:if test="${not empty study.studyPublicReleaseDate}">
            ,<spring:message code="label.releaseDate"/>: <strong><fmt:formatDate pattern="dd-MMM-yyyy" value="${study.studyPublicReleaseDate}"/></strong>
        </c:if>
        <c:if test="${not empty study.updateDate}">
            ,<spring:message code="label.updateDate"/>: <strong title="${study.updateDate}"><fmt:formatDate pattern="dd-MMM-yyyy" value="${study.updateDate}"/></strong>
        </c:if>

        <br/><spring:message code="ref.msg.status"/>:${study.studyStatus.descriptiveName}
    </span>

    <c:if test="${not empty study.contacts}">
        <br/>
        <c:forEach var="contact" items="${study.contacts}" varStatus="loopStatus">
            <c:if test="${loopStatus.index ne 0}">, </c:if>
                    <span id="aff"
                          <c:if test="${not empty contact.affiliation}">title="${contact.affiliation}"</c:if>
                            >${contact.firstName}&nbsp;${contact.lastName}</span>
        </c:forEach>
        <br/>
    </c:if>

    <c:if test="${not empty study.description}">
        <br/><br/><span style="text-align:justify"><div id="description">${study.description}</div></span>
    </c:if>
    <br/>


    <!-- Configuring tabs -->
    <div id="tabs" class="tabContainer">

        <!-- Setting up tab list-->
        <ul class="tabHeader">
            <%--So far if any collection, like factors is empty, it means there was an error loading isatab file, and conversion never happened.--%>
            <c:if test="${study.factors != null}">
                <li><a href="#tabs-1" class="noLine"><spring:message code="label.studyDesign"/></a></li>
            </c:if>

            <c:if test="${not empty study.protocols}">
                <li><a href="#tabs-2" class="noLine"><spring:message code="label.protocols"/></a></li>
            </c:if>
            <c:if test="${not empty study.sampleTable}">
                <li><a href="#tabs-3" class="noLine"><spring:message code="label.sample"/></a></li>
            </c:if>
            <c:if test="${not empty study.assays}">
                <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                    <li>
                        <a href="#tabs-${loopAssays.index + 4}" class="noLine <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                                metabolights
                            </c:if>" title="${assay.fileName}"><spring:message code="label.assays"/>
                            <c:if test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                        </a>
                    </li>
                </c:forEach>
            </c:if>
            <c:if test="${not empty files}">
                <li>
                    <a href="#tabs-files" class="noLine"><spring:message code="label.Files"/></a>
                </li>
            </c:if>

            <li>
                <a id="valid-tab" href="#tabs-validations" class="noLine"><spring:message code="label.studyvalidation"/>&nbsp;
                    <c:set var="validationstatus" value="${study.validations.status}"/>
                    <c:set var="validationoverridden" value="${study.validations.overriden}"/>
                    <%@include file="validations.jsp" %>
                </a>
            </li>

        </ul>


        <!-- TAB1: INFO-->
        <c:if test="${study.factors != null}">
            <div id="tabs-1" class="tab">
                <c:if test="${not empty study.organism}">
                    <br/>
                    <fieldset class="box">
                        <legend><spring:message code="label.organisms"/>:</legend>
                        <br/>
                        <c:forEach var="org" items="${study.organism}" >
                            <p>${org.organismName}</p>
                        </c:forEach>
                    </fieldset>
                </c:if>
                <c:if test="${not empty study.descriptors}">
                    <br/>
                    <fieldset class="box">
                        <legend><spring:message code="label.studyDesign"/>:</legend>
                        <br/>
                        <ul>
                            <c:forEach var="design" items="${study.descriptors}" >
                            <li>${design.description}
                            </c:forEach>
                        </ul>
                    </fieldset>
                </c:if>

                <c:if test="${not empty study.publications}">
                    <br/>
                    <fieldset class="box">
                        <legend><span class="ebiicon book"></span>&nbsp;<spring:message code="label.publications"/></legend>
                        <c:forEach var="pub" items="${study.publications}" varStatus="loopPublications">
                            <p>
                            [${loopPublications.index+1}]&nbsp;
                            <c:set var="DOIValue" value="${pub.doi}"/>
                            <c:choose>
                                <c:when test="${not empty pub.pubmedId}">
                                    <a href="http://europepmc.org/abstract/MED/${pub.pubmedId}">${pub.title}</a>
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
                        </c:forEach>
                        </p>
                    </fieldset>
                </c:if>
                <c:if test="${not empty study.factors}">
                    <br/>
                    <fieldset class="box">
                        <legend><spring:message code="label.experimentalFactors"/></legend>
                        <ul>
                            <c:forEach var="fv" items="${study.factors}">
                                <li>
                                    ${fv.name}
                                </li>
                            </c:forEach>
                        </ul>
                    </fieldset>
                </c:if>
            </div>
        </c:if>

        <!-- TAB2: Protocols-->
        <c:if test="${not empty study.protocols}">
            <div id="tabs-2" class="tab">
                <table class="clean">
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
            </div>
        </c:if>
        <!-- ends tabs-2 -->

        <!-- TAB3: Sample-->
        <c:if test="${not empty study.sampleTable}">
            <div id="tabs-3" class="tab">
                <table class="display clean">
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
            </div>
        </c:if>
        <!-- TAB4+: Assays-->
        <c:if test="${not empty study.assays}">
            <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                <!-- start of an assay tab -->
                <div id="tabs-${loopAssays.index + 4}" class="tab">
                    <!-- Assay info section -->
                    <div class="specs">
                        <spring:message code="label.assayName"/>: <a href="${study.studyIdentifier}/files/${assay.fileName}"><span class="icon icon-fileformats" data-icon="v">${assay.fileName}</span></a><br/>
                        <spring:message code="label.measurement"/>: ${assay.measurement}<br/>
                        <spring:message code="label.technology"/>:  ${assay.technology}&nbsp;
                            <c:if test="${fn:contains(assay.technology,'NMR')}">
                                <span aria-hidden="true" class="icon2-NMRLogo"></span>
                            </c:if>
                            <c:if test="${fn:contains(assay.technology,'mass')}">
                                <span aria-hidden="true" class="icon2-MSLogo"></span>
                            </c:if>
                            <br>
                        <spring:message code="label.platform"/>: ${assay.platform}<br>

                    </div>
                    <br/>

                    <!-- Start of assay data: metabolites + data -->
                    <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                    <div class="accordion">

                        <h5 class="maf" assay="${assay.assayNumber}" mafurl="${servletPath}/assay/${assay.assayNumber}/maf"><span class="icon icon-conceptual" data-icon="b"></span><spring:message code="label.mafFileFound"/></h5>
                        <div></div>
                    </div>
                    <br/>
                    </c:if>
                    <!-- start of assay data -->
                    <h5><span class="icon icon-functional" data-icon="A"></span><spring:message code="label.data"/></h5>
                    <div>
                        <c:if test="${not empty assay.assayTable}">
                            <table class="display clean">
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
                        </c:if>
                    </div> <!-- end of an assay data -->
                </div> <!-- end of an assay tab -->
            </c:forEach>
        </c:if> <!-- end if assays-->

        <c:if test="${not empty files}">

            <%--Only show token if study es private--%>
            <c:if test="${!study.publicStudy}">
                <c:set var="token" value="?token=${study.obfuscationCode}"/>
            </c:if>

            <div id="tabs-files" class="tab"> <!-- Study files -->
                <form id="selFilesForm" action="${study.studyIdentifier}/files/downloadSelFiles" method="post">
                    <h5>
                        <!--  Request FTP folder -->
                        <c:if test="${(study.studyStatus == 'SUBMITTED') and !hasPrivateFtpFolder }">
                            <sec:authorize access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                                &nbsp;
                                <a class="noLine" rel="nofollow" href="${study.studyIdentifier}/files/requestFtpFolder" title="<spring:message code="label.requestFtpFolder"/>">
                                    <span class="icon icon-functional" data-icon="D"/><spring:message code="label.requestFtpFolder"/>
                                </a>
                            </sec:authorize>
                            &nbsp;|&nbsp;
                        </c:if>
                        <!--  Request FTP folder -->
                        <a class="noLine" rel="nofollow" href="${study.studyIdentifier}/files/${study.studyIdentifier}${token}" title="<spring:message code="label.downloadstudy"/>">
                            <span class="icon icon-functional" data-icon="="/><spring:message code="label.downloadstudy"/>
                        </a>
                        &nbsp;|&nbsp;
                        <a class="noLine" rel="nofollow" href="${study.studyIdentifier}/files/metadata${token}" title="<spring:message code="label.downloadstudyMetadata"/>">
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

                    <div id="transferDiv" class="transferDiv"></div>
                    <div id="noAspera" class="noAspera"></div>

                    <h5><spring:message code="label.fileListTableExplanation"/></h5>
                    <p><input class="inputDiscrete resizable" id="fileSelector" class="" type="text" placeholder="<spring:message code='label.fileList.Input.placeholder'/>"></p>
                    <c:if test="${!study.publicStudy}">
                        <input type="hidden" name="token" value="${study.obfuscationCode}">
                    </c:if>
                    <table id="files" class="clean">
                        <tr>
                            <th>Select</th>
                            <th>File</th>
                        </tr>
                        <tbody>
                        <c:forEach var="file" items="${files}">
                            <%--<c:if test="${!file.directory}">--%>
                            <tr>
                                <td><input type="checkbox" name="file" value="${file.name}"/></td>
                                <td>
                                    <a rel="nofollow" href="${study.studyIdentifier}/files/${file.name}${token}">${file.name}</a>
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

                                   <table id="privFtpFiles" class="ftpFiles clean">
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

            </div> <!--  ends tabs-files files -->
        </c:if>


        <!-- TAB: Validations-->
        <div id="tabs-validations" class="tab">
            <c:if test="${not empty study.validations.entries}">
                <!-- Send report to submitter via email -->
                <h5>
                    <sec:authorize access="hasAnyRole('ROLE_SUPER_USER', 'ROLE_SUBMITTER')">
                        &nbsp;
                        <a class="noLine" rel="nofollow" href="${pageContext.request.contextPath}/${study.studyIdentifier}/validations/statusReportByMail" title="<spring:message code="label.sendValidationsStatusReport"/>">
                            <span class="icon icon-generic" data-icon="E"/><spring:message code="label.sendValidationsStatusReport"/></a>
                    </sec:authorize>
                </h5>

                <div class="specs">
                Validations marked with (*) are specially approved by the MetaboLights Curators
                </div>
                <c:choose>
                    <c:when test="${curator}">
                        <%@include file="validationOverride.jsp"%>
                    </c:when>
                    <c:otherwise>
                        <c:set var="validations" value="${study.validations.entries}"/>
                        <%@include file="validationsTable.jsp"%>
                    </c:otherwise>
                </c:choose>


            </c:if>
        </div>
        <!-- ends tabs-Validations -->

        <!-- confirmation dialog, just for deleting selected files -->
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

    </div> <!-- end configuring tabs -->
</div>
<div id="shareInfo">
    <h5><spring:message code="title.study.paper.link"/></h5>
    <p><spring:message code="label.study.paper.link"/></p>
    <p><input class="inputDiscrete resizable" type="text" value="${fullContextPath}/${study.studyIdentifier}" readonly/></p>
    <c:if test="${study.studyStatus == 'INREVIEW'}">
        <h5><spring:message code="title.study.private.link"/></h5>
        <p><spring:message code="label.study.private.link"/></p>
        <p><input class="inputDiscrete resizable" type="text" value="${fullContextPath}/reviewer${study.obfuscationCode}" readonly/></p>
    </c:if>
</div>

<script type="text/javascript" src="javascript/aspera/asperaweb-4.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/aspera/connectinstaller-4.js" charset="utf-8"></script>

<script type="text/javascript" src="javascript/aspera/jquery-ui.js" charset="utf-8"></script>

<script type="text/javascript" src="javascript/aspera/jquery-namespace.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/aspera/ml-aspera-config.js" charset="utf-8"></script>

<script type="text/javascript" src="javascript/aspera/ml-aspera.js" charset="utf-8"></script>

<script type="text/javascript" src="javascript/aspera/install.js" charset="utf-8"></script>

<script>
    $(document).ready(function () {

        $(function () {
            var hash = document.location.hash;
            // Remove the #
            hash = hash.substring(1);
            if (hash != undefined) {
                // If it's not a number'
                if (isNaN(hash)){
                    tabToActivate = $("[hash='" + hash + "']").getIndex();
                    if (tabToActivate == -1) tabToActivate = 0;
                } else {
                    tabToActivate= hash;
                }
            }
            $("#tabs").tabs({
                cache: true,
                activate: function (event, ui) {

                    <c:if test="${study.publicStudy}">
                    // If the new tab is NMR...
                    if ($(ui.newTab.children('a')[0]).attr('href') == "#tabs-files") {



                        // Adds an input element download button that uses Aspera
                        var downloadButton = $('<a id="downloadButton">Aspera: Download Study</a>')

                        $('#asperaDownloadWrapper').append(downloadButton);

                        function downloadButtonClick(e) {
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

                    }
                    </c:if>
                    //document.location.hash =  "#"+ui.newTab.attr("hash");
                }
            });
        });
    });
</script>