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
  ~ Last modified: 6/16/14 2:32 PM
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  --%>

<script type="text/javascript" src="http://www.ebi.ac.uk/Tools/biojs/registry/src/Biojs.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/Biojs.ChEBICompound.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/jquery.linkify-1.0-min.js" charset="utf-8"></script>
<script type="text/javascript" src="http://cdn.datatables.net/1.10.0/js/jquery.dataTables.js" charset="utf-8"></script>

<link rel="stylesheet" href="cssrl/iconfont/font_style.css" type="text/css" />
<link rel="stylesheet"  href="css/ChEBICompound.css" type="text/css" />
<link rel="stylesheet"  href="http://cdn.datatables.net/1.10.0/css/jquery.dataTables.css" type="text/css" />

<script language="javascript" type="text/javascript">

    $(document).ready(function() {

        $("[id='protocoldesc']").linkify();

        $("body").append('<div id="chebiInfo"></div>');
//	var chebiInfoDiv = new Biojs.ChEBICompound({target: 'chebiInfo',width:'400px', height:'300px',proxyUrl:'./proxy'});
        var chebiInfoDiv = new Biojs.ChEBICompound({target: 'chebiInfo',width:'400px', height:'300px',proxyUrl:undefined, chebiDetailsUrl: './ebi/webservices/chebi/2.0/test/getCompleteEntity?chebiId='});
        $('#chebiInfo').hide();

        $("a.showLink").click(function(event) {
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
            var idOfHiddenText = "#"+idPrefixClicked+"_"+orderOfItemClicked;
            var jqClickedId= "#"+clickedId;
            if ($(idOfHiddenText).is(":hidden")){
                $(jqClickedId).text("Show less");
            }else{
                $(jqClickedId).text("Show more");
            }
            $(idOfHiddenText).slideToggle();

        });

        // Listen to click in MAF file title
        // Like this: <h5 class="maf" mafurl=....
        $("h5.maf").click(function(event) {

            var headerClicked = event.target;
            var mafUrl = headerClicked.getAttribute("mafurl");
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

                    makeCoolTable("table.maf[assay='" + $(this).attr("assay") + "']");

                });

            }
        });

        $(function() {
            $( ".accordion" ).accordion({
                collapsible: true
                ,heightStyle: "fill"
            });
        });

        var metLinkTimer = 0; // 0 is a safe "no timer" value

        $('.metLink').live('mouseenter', function(e) {
            // I'm assuming you don't want to stomp on an existing timer
            if (!metLinkTimer) {
                metLinkTimer = setTimeout(function(){loadMetabolite(e);}, 500); // Or whatever value you want
            }
        }).live('mouseleave', function() {
            // Cancel the timer if it hasn't already fired
            if (metLinkTimer) {
                clearTimeout(metLinkTimer);
                metLinkTimer = 0;

            }
            $('#chebiInfo').fadeOut('slow');
        });

        function loadMetabolite(e) {
            // Clear this as flag there's no timer outstanding
            metLinkTimer = 0;

            var metlink;
            metlink = $(e.target);
            var metaboliteId = metlink.attr('identifier');



            // If its a chebi id
            if (metaboliteId.indexOf("CHEBI:")==0){

                //var mouseX = metlink.left + metlink.offsetParent.offsetLeft + metlink.offsetWidth + 80;
                //var mouseY = metlink.top + metlink.offsetParent.offsetTop + metlink.offsetParent.offsetParent.offsetTop;
                var offset = metlink.offset();
                var mouseX = offset.left + metlink.outerWidth() + 20;
                var mouseY = offset.top;

                chebiId = metaboliteId;

                $('#chebiInfo img:last-child').remove;

                $('#chebiInfo').css({'top':mouseY,'left':mouseX,'float':'left','position':'absolute','z-index':10});
                $('#chebiInfo').fadeIn('slow');

                chebiInfoDiv.setId(chebiId);
            }
        }

        $("#shareInfo").hide();

        $("a#share").click(function(e) {
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
            $("#tabs").tabs( "option", "active", -1 );
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

        makeCoolTable();


    });

    $(function() {
        $( "#tabs" ).tabs();
    });

    function makeCoolTable(selector){
        if (selector === undefined){
            selector = "table.display";
        }

        $(selector).dataTable( {
//                "scrollY": 350,
            "scrollX": true,
            "language": {
                "search": "Filter:"
            }
        } );

    }

    function toggleColumn(tableId, anchor, duration ) {

        dataIcon = $(anchor).attr('data-icon');

        // if collapsed
        if (dataIcon == 'u'){
            dataIcon = 'w';
            $('#' + tableId + ' tr *:nth-child(1n+5)').show();


            // else expanded
        }else{
            dataIcon = 'u';
            $('#' + tableId + ' tr *:nth-child(1n+5)').hide();
        }

        $(anchor).attr('data-icon', dataIcon);
    }

</script>

<c:set var="readOnly" value="${!fn:contains(servletPath,study.studyIdentifier)}"/>

<div class="push_1 grid_22 alpha omega">
    <h3>${study.studyIdentifier}:&nbsp;${study.title}</h3>
</div>

<div class="push_1 grid_22 subtitle alpha omega">
    <span>
        <a id="share" class="noLine" href="#" title="<spring:message code="label.study.share"/>">
            <span class="icon icon-generic" data-icon="L"><spring:message code="label.study.share"/>
        </a>
        &nbsp;|&nbsp;
        <a id="viewFiles" class="noLine" href="#" title="<spring:message code="label.viewAllFiles"/>">
            <span class="icon icon-functional" data-icon="b"/><spring:message code="label.viewAllFiles"/>
        </a>
        <c:if test="${!(study.publicStudy)}">
            <span class="right">
            &nbsp;<spring:message code="label.expPrivate"/>
            <c:if test="${!readOnly}">
                <jsp:useBean id="datenow" class="java.util.Date" scope="page" />
                <a class="noLine" href="updatepublicreleasedateform?study=${study.studyIdentifier}&date=<fmt:formatDate pattern="dd-MMM-yyyy" value="${datenow}" />" title="<spring:message code="label.makeStudyPublic"/>">
                    &nbsp;<span class="icon icon-generic" data-icon="}" id="ebiicon" /><spring:message code="label.makeStudyPublic"/>
                </a>
            </c:if>
            </span>
        </c:if>
    </span>
</div>

<c:set var="stringToFind" value="${study.studyIdentifier}:assay:" />


<div class="push_1 grid_22 alpha omega">

    <span class="right">
            <c:if test="${not empty study.studySubmissionDate}">
                <spring:message code="label.subDate"/>: <strong><fmt:formatDate pattern="dd-MMM-yyyy" value="${study.studySubmissionDate}"/></strong>
            </c:if>
            <c:if test="${not empty study.studyPublicReleaseDate}">
                ,<spring:message code="label.releaseDate"/>: <strong><fmt:formatDate pattern="dd-MMM-yyyy" value="${study.studyPublicReleaseDate}"/></strong>
            </c:if>
            <c:if test="${not empty studyXRefs}">
                <br/><spring:message code="label.StudyXrefs"/>:
                <c:forEach var="studyXref" items="${studyXRefs}" varStatus="loopIndex">
                    <c:if test="${loopIndex.index > 0}"> , </c:if>
                    <c:if test="${ not empty studyXref.XRefType.pattern_url}">
                        <a href="${studyXref.XRefType.pattern_url}${studyXref.submittedId}">${studyXref.submittedId}</a>
                    </c:if>
                    <c:if test="${empty studyXref.XRefType.pattern_url}">
                        ${studyXref.submittedId}
                    </c:if>
                </c:forEach>
            </c:if>
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
            <li><a href="#tabs-1" class="noLine"><spring:message code="label.studyDesign"/></a></li>
            <li><a href="#tabs-2" class="noLine"><spring:message code="label.protocols"/></a></li>
            <li><a href="#tabs-3" class="noLine"><spring:message code="label.sample"/></a></li>
            <c:if test="${not empty study.assays}">
                <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                    <li>
                        <a href="#tabs-${loopAssays.index + 4}" class="noLine" title="${assay.fileName}"><spring:message code="label.assays"/>
                            <c:if test="${fn:length(study.assays) gt 1}">&nbsp;${assay.assayNumber}</c:if>
                            <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                                &nbsp;<span class="icon icon-conceptual" data-icon="b" title="<spring:message code="label.mafFileFound"/>"/>
                            </c:if>
                        </a>
                    </li>
                </c:forEach>
            </c:if>
            <c:if test="${not empty files}">
                <li>
                    <a href="#tabs-files" class="noLine"><spring:message code="label.Files"/></a>
                </li>
            </c:if>
        </ul>


        <!-- TAB1: INFO-->
        <div id="tabs-1" class="tab">
            <c:if test="${not empty study.organism}">
                <br/>
                <fieldset class="box">
                    <legend><spring:message code="label.organisms"/>:</legend>
                    <br/>
                    <c:forEach var="org" items="${study.organism}" >
                        ${org.organismName}<br/>
                    </c:forEach>
                </fieldset>
            </c:if>
            <c:if test="${not empty study.descriptors}">
                <br/>
                <fieldset class="box">
                    <legend><spring:message code="label.studyDesign"/>:</legend>
                    <br/>
                    <ul id="resultList">
                        <c:forEach var="design" items="${study.descriptors}" >
                        <li>${design.description}
                        </c:forEach>
                    </ul>
                </fieldset>
            </c:if>

            <c:if test="${not empty study.publications}">
                <br/>
                <fieldset class="box">
                    <legend><spring:message code="label.publications"/></legend>
                    <c:forEach var="pub" items="${study.publications}">
                        <br/>
                        <div class="ebiicon book"></div>
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
                                    <a href="http://${DOIValue}">${pub.title}</a>
                                </c:if>
                                <c:if test="${not fn:contains(DOIValue, 'dx.doi')}">
                                    <a href="http://dx.doi.org/${DOIValue}">${pub.title}</a>
                                </c:if>
                            </c:when>
                            <c:otherwise>${pub.title}</c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <br/>
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

        <!-- TAB2: Protocols-->
        <div id="tabs-2" class="tab">
            <c:if test="${not empty study.protocols}">
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
            </c:if>
        </div>
        <!-- ends tabs-2 -->

        <!-- TAB3: Sample-->
        <div id="tabs-3" class="tab">
            <c:if test="${not empty study.samples}">
                <table class="display clean">
                    <c:forEach var="sample" items="${study.samples}" varStatus="loopSampleLines">
                        <c:if test="${loopSampleLines.index == 0}">
                            <thead class='text_header'>
                            <tr>
                                <th><spring:message code="label.sampleName"/></th>
                                <th><spring:message code="label.organism"/></th>
                                <th><spring:message code="label.organismPart"/></th>
                                <c:forEach var="factor" items="${sample.factors}">
                                    <th>${factor.factorKey}</th>
                                </c:forEach>
                            </tr>
                            </thead>
                            <tbody>
                        </c:if>
                        <c:if test="${not empty sample}">
                            <%--<c:if test="${loopSampleLines.index == -10}">--%>
                                <%--</tbody><tbody id="sample_0" style='display:none'>--%>
                            <%--</c:if>--%>
                            <tr>
                                <td>${sample.sampleName}</td>
                                <td>${sample.charactersticsOrg}</td>
                                <td>${sample.charactersticsOrgPart}</td>
                                <c:forEach var="factor" items="${sample.factors}">
                                    <td>${factor.factorValue}</td>
                                </c:forEach>
                                <%--<td>${sample.protocolRef}</td>--%>
                                <%--<td class="tableitem">${sample.sourceName}</td>--%>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
                <%--<c:if test="${fn:length(study.samples) > 10}"><a href="#" class="showLink" id="sample_link_0">Show more</a></c:if>--%>
            </c:if>
        </div>


        <!-- TAB4+: Assays-->
            <c:if test="${not empty study.assays}">
                <c:forEach var="assay" items="${study.assays}" varStatus="loopAssays">
                    <div id="tabs-${loopAssays.index + 4}" class="tab">
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
                        <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                            <div class="accordion">
                        </c:if>

                            <h5><spring:message code="label.data"/></h5>
                            <div>
                                <table class="display clean">
                                    <c:forEach var="assayLine" items="${assay.assayLines}" varStatus="loopAssayLines">
                                        <c:if test="${loopAssayLines.index == 0}">
                                            <thead class='text_header'>
                                                <tr>
                                                    <th>Source</th>
                                                    <c:forEach var="factor" items="${assayLine.factors}">
                                                        <th>${factor.factorKey}</th>
                                                    </c:forEach>
                                                </tr>
                                            </thead>
                                            <tbody>
                                        </c:if>
                                        <c:if test="${not empty assayLine.sampleName}">
                                            <%--<c:if test="${loopAssayLines.index == 10}">--%>
                                                <%--</tbody><tbody id="assay_${loopAssays.index}" style='display:none'>--%>
                                            <%--</c:if>--%>
                                            <tr>
                                            <td class="tableitem">${assayLine.sampleName}</td>
                                            <c:forEach var="factor" items="${assayLine.factors}">
                                                <td>${factor.factorValue}</td>
                                            </c:forEach>
                                            </tr>
                                        </c:if>
                                    </c:forEach>

                                    </tbody>
                                </table>
                                <%--<c:if test="${fn:length(assay.assayLines) > 10}"><a href="#" class="showLink" id="assay_link_${loopAssays.index}">Show more</a></c:if>--%>
                            </div>
                        <c:if test="${(not empty assay.metaboliteAssignment) and (not empty assay.metaboliteAssignment.metaboliteAssignmentFileName) }">
                            <br/><br/>
                            <h5 class="maf" assay="${assay.assayNumber}" mafurl="${servletPath}/assay/${assay.assayNumber}/maf"><span class="icon icon-conceptual" data-icon="b"></span><spring:message code="label.mafFileFound"/></h5>

                            <div></div>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </c:if> <!-- end if assays-->

        <c:if test="${not empty files}">

            <%--Only show token if study es private--%>
            <c:if test="${!study.publicStudy}">
                <c:set var="token" value="?token=${studyDBId}"/>
            </c:if>

            <div id="tabs-files" class="tab"> <!-- Study files -->
                <form action="${study.studyIdentifier}/files/selection" method="post">
                    <h5>
                        <a class="noLine" href="${study.studyIdentifier}/files/${study.studyIdentifier}${token}" title="<spring:message code="label.downloadstudy"/>">
                            <span class="icon icon-functional" data-icon="="/><spring:message code="label.downloadstudy"/>
                        </a>
                        &nbsp;|&nbsp;
                        <a class="noLine" href="${study.studyIdentifier}/files/metadata${token}" title="<spring:message code="label.downloadstudyMetadata"/>">
                        <span class="icon icon-functional" data-icon="="><spring:message code="label.downloadstudyMetadata"/>
                        </a>
                        &nbsp;
                        <c:if test="${study.publicStudy}">
                            |&nbsp;
                            <a class="noLine" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/studies/public/${study.studyIdentifier}" title="<spring:message code="label.viewAllFiles"/>">
                                <span class="icon icon-generic" data-icon="x"/><spring:message code="label.viewAllFiles"/>
                            </a>
                        </c:if>
                    </h5>
                    <br/>
                    <h5><spring:message code="label.fileListTableExplanation"/></h5>
                    <p><input class="inputDiscrete resizable" id="fileSelector" class="" type="text" placeholder="<spring:message code='label.fileList.Input.placeholder'/>"></p>
                    <input type="hidden" name="token" value="${study.studyIdentifier}">
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
                                    <a href="${study.studyIdentifier}/files/${file.name}${token}">${file.name}</a>
                                </td>
                            </tr>
                            <%--</c:if>--%>
                        </c:forEach>
                        </tbody>
                    </table>

                    <p><input name="submit" type="submit" class="submit" value="<spring:message code="label.downloadSelectedFiles"/>"/></p>

                        <%--Show instructions--%>
                    <div class="ui-state-highlight ui-corner-all">
                        <p><strong>Info:</strong><spring:message code="label.fileListTableInstructions"/></p>
                    </div>
                </form>
            </div> <!--  ends tabs-files files -->
        </c:if>

    </div> <!-- end configuring tabs -->
</div>
<div id="shareInfo">
    <h5><spring:message code="title.study.paper.link"/></h5>
    <p><spring:message code="label.study.paper.link"/></p>
    <p><input class="inputDiscrete resizable" type="text" value="${fullContextPath}/${study.studyIdentifier}" readonly/></p>
    <c:if test="${!study.publicStudy}">
        <h5><spring:message code="title.study.private.link"/></h5>
        <p><spring:message code="label.study.private.link"/></p>
        <p><input class="inputDiscrete resizable" type="text" value="${fullContextPath}/altreviewer${obfuscationcode}" readonly/></p>
    </c:if>
</div>
