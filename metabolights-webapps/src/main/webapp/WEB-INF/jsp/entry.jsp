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
  ~ Last modified: 4/16/14 9:30 AM
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  --%>

<%--<script type="text/javascript" src="javascript/protovis-r3.2.js" charset="utf-8"></script>--%>
<%--<script type="text/javascript" src="javascript/Biojs.js" charset="utf-8"></script>--%>
<script type="text/javascript" src="http://www.ebi.ac.uk/Tools/biojs/registry/src/Biojs.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/Biojs.ChEBICompound.js" charset="utf-8"></script>

<%--<script type="text/javascript" src="http://www.ebi.ac.uk/Tools/biojs/registry/src/Biojs.ChEBICompound.js" charset="utf-8"></script>--%>
<script type="text/javascript" src="javascript/jquery.linkify-1.0-min.js" charset="utf-8"></script>
<%--<script type="text/javascript" src="http://www.ebi.ac.uk/Tools/biojs/registry/src/Biojs.js" charset="utf-8"></script>--%>
<%--<script type="text/javascript" src="http://www.ebi.ac.uk/Tools/biojs/registry/src/Biojs.ChEBICompound.js" charset="utf-8"></script>--%>


<link rel="stylesheet"  href="css/ChEBICompound.css" type="text/css" />

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
        $("#nmrSpectra-tab").effect("highlight", {color: '#E2BD97'}, 1700);
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

$(function() {
	$( "#tabs" ).tabs();
});

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

<c:set var="readOnly" value="${!fn:contains(servletPath,study.acc)}"/>

<div class="push_1 grid_22 alpha omega">
    <h3>${study.acc}:&nbsp;${study.title}</h3>
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
        <c:if test="${(study.status ne 'PUBLIC')}">
            <span class="right">
            &nbsp;<spring:message code="label.expPrivate"/>
            <c:if test="${empty readOnly}">
                <jsp:useBean id="datenow" class="java.util.Date" scope="page" />
                <a class="noLine" href="updatepublicreleasedateform?study=${study.acc}&date=<fmt:formatDate pattern="dd-MMM-yyyy" value="${datenow}" />" title="<spring:message code="label.makeStudyPublic"/>">
                    &nbsp;<span class="icon icon-generic" data-icon="}" id="ebiicon" /><spring:message code="label.makeStudyPublic"/>
                </a>
            </c:if>
            </span>
        </c:if>
    </span>
</div>

<c:set var="stringToFind" value="${study.acc}:assay:" />

<div class="push_1 grid_22 alpha omega">

        <%--<div>--%>
            <%--<c:if test="${study.status ne 'PUBLIC'}">--%>
                <%--<jsp:useBean id="now" class="java.util.Date" scope="page" />--%>
                <%--<a href="updatepublicreleasedateform?study=${study.acc}&date=<fmt:formatDate pattern="dd-MMM-yyyy" value="${now}" />">Make it public</a>--%>
            <%--</c:if>--%>
        <%--</div>--%>

        <span class="right">
            <c:if test="${not empty study.submissionDate}">
                <spring:message code="label.subDate"/>: <strong><fmt:formatDate pattern="dd-MMM-yyyy" value="${study.submissionDate}"/></strong><br/>
            </c:if>
            <c:if test="${not empty study.releaseDate}">
                <spring:message code="label.releaseDate"/>: <strong><fmt:formatDate pattern="dd-MMM-yyyy" value="${study.releaseDate}"/></strong><br/>
            </c:if>
            <c:if test="${not empty submittedID.submittedId}">
                <spring:message code="label.submittedId"/>: ${submittedID.submittedId}
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
        </c:if>

        <c:if test="${not empty study.description}">

            <br/><br/><br/>${study.description}
	    </c:if>
        <br/><br/>

		<div id="tabs">
			<ul>
				<li><a href="#tabs-1" class="noLine"><spring:message code="label.studyDesign"/></a></li>
				<li>
					<a href="#tabs-2" class="noLine"><spring:message code="label.protocols"/></a>
				</li>
				<li>
					<a href="#tabs-3" class="noLine"><spring:message code="label.assay"/></a>
				</li>
                <c:if test="${hasMetabolites}">
                    <li>
                        <a href="#tabs-4" class="noLine"><spring:message code="label.metabolites"/></a>
                    </li>
                </c:if>
                <c:if test="${not empty files}">
                    <li>
                        <a href="#tabs-5" class="noLine"><spring:message code="label.Files"/></a>
                    </li>
                </c:if>

			</ul>
			<div id="tabs-1">
		        <c:if test="${not empty organismNames}">
		            <br/>
		            <fieldset class="box">
		            	<legend><spring:message code="label.organisms"/>:</legend>
			            <br/>
			            <c:forEach var="organismName" items="${organismNames}" >
			                ${organismName}<br/>
			            </c:forEach>
		            </fieldset>
		        </c:if>
				<c:if test="${not empty study.designs}">
		            <br/>
		            <fieldset class="box">
		            	<legend><spring:message code="label.studyDesign"/>:</legend>
			            <br/>
			            <ul id="resultList">
			            <c:forEach var="design" items="${study.designs}" >
			                <li>${design.value}
			            </c:forEach>
			            </ul>
		            </fieldset>
		        </c:if>
		        <c:if test="${not empty study.objective}">
		            <br/>
		            <fieldset class="box">
		            	<legend><spring:message code="label.studyDesign"/></legend>
			            <br/><br/>${study.objective}
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
                                <c:when test="${not empty pub.pmid}">
                                     <a href="http://europepmc.org/abstract/MED/${pub.pmid}">${pub.title}</a>
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
		        <c:if test="${not empty factors}">
		        	<br/>
		            <fieldset class="box">
		            	<legend><spring:message code="label.experimentalFactors"/></legend>
		                    <ul>
				                <c:forEach var="fv" items="${factors}">
				                	<li>${fv.key}: [
				                		<c:forEach var="value" items="${fv.value}" varStatus="loopStatus">
				                			<c:if test="${loopStatus.index ne 0}">
				                				,
				                			</c:if>
				                			${value}
				                		</c:forEach>
				                	]</li>
				                </c:forEach>
					        </ul>
			        </fieldset>
		        </c:if>
			</div>
			<div id="tabs-2">
				<c:if test="${not empty study.protocols}">
		            <table width="100%">
						<thead class='text_header'>
							<tr>
								<th>Protocol</th>
								<th>Description</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="blanks" value="0"/>
			                <c:forEach var="protocol" items="${study.protocols}" varStatus="loopStatus">
	                    		<c:choose>
	                    			<c:when test="${not empty protocol.description}">
			                    		<tr class="${(loopStatus.index+blanks) % 2 == 0 ? '' : 'coloured'}">
					                    	<td class="tableitem">${protocol.name}</td>
					                    	<td id="protocoldesc" class="tableitem">${protocol.description}</td>
					                    </tr>
				                    </c:when>
				                    <c:otherwise>
				                    	<c:set var="blanks" value="${blanks+1}"/>
				                    </c:otherwise>
				                </c:choose>
			                </c:forEach>
		            	</tbody>
		            </table>
		        </c:if>
			</div> <!-- ends tabs-2 -->
			<div id="tabs-3">
				<c:if test="${not empty assays}">
	                <c:forEach var="assay" items="${assays}" varStatus="loopStatusAssay">
                        <br/>
                        <strong><spring:message code="label.data.table.name"/>: </strong>${assay.technology} - ${assay.measurement} -  ${assay.platform}
						<br/>
                        <%--<a href="${study.acc}/files/${assay.fileName}" class="icon icon-functional" data-icon="="><spring:message code="submittedFile"/></a><br/>--%>
			            <table width="100%">
							<thead class='text_header'>
								<tr>
									<th><spring:message code="label.data.table.groupName"/></th>
									<!-- Add one column per factor -->
									<c:forEach var="factor" items="${assay.factors}">
										<th>${factor.value}</th>
									</c:forEach>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="MLAssayResult" items="${assay.MLAssayResult}" varStatus="loopStatus">
	                    		<tr class="${loopStatus.index % 2 == 0 ? '' : 'coloured'}">
			                   		<c:if test="${loopStatus.index == 10}">
		                    			<%-- <tr><td colspan=2><a href="#" class="showLink" id="data_link_${loopStatusAssay.index}">Show more</a></td></tr> --%>
		                    			</tbody><tbody id="data_${loopStatusAssay.index}" style='display:none'>
		                    		</c:if>
			                    	<%-- <td class="tableitem">${assay.fileName} ${fn:length(assayResult.assays)}</td> --%>
			                    	<td class="tableitem">
			                    		<%-- we expect only one assay per assayResult, so we can loop the assay collection and get the first --%>
			                    		<c:forEach var="assayline" items="${MLAssayResult.assayResult.assays}" varStatus="loopStatus">
                                            <%-- Replace the string <ACCESION>:assay: before displaying. NB!! only display up to first "." --%>
                                            <c:set var="stringFormated" value="${fn:substringBefore(fn:replace(assayline.acc,stringToFind,''),'.')}" />
                                            <c:set var="materialName" value="${assayline.material.name}" />
                                            <c:choose>
                                                <c:when test="${not empty stringFormated}">${stringFormated}</c:when>
                                                <c:when test="${not empty materialName}">${materialName}</c:when>
                                                <c:otherwise> </c:otherwise>
                                            </c:choose>
			                    		</c:forEach>
			                    	</td>
	                    			<c:forEach var="fv" items="${MLAssayResult.assayResult.data.factorValues}">
	                    				<td class="tableitem">${fv.value} ${fv.unit.value}</td>
	                    			</c:forEach>
			                    </tr>
			                    </c:forEach>
			            	</tbody>
			            </table>
			            <c:if test="${fn:length(assay.MLAssayResult) > 10}"><a href="#" class="showLink" id="data_link_${loopStatusAssay.index}">Show more</a></c:if>
			            <br/>
	                </c:forEach>
		        </c:if>
			</div> <!--  ends tabs-3 -->
            <c:if test="${hasMetabolites}">
            <div id="tabs-4"> <!-- Metabolites Identified -->
                <c:if test="${not empty assays}">

                    <c:forEach var="mlAssay" items="${assays}" varStatus="loopStatusAssay">

                        <c:if test="${fn:length(mlAssay.metabolitesGUI) gt 0}">
                            <div style="overflow: auto">

                                <table id="metabolites${loopStatusAssay.index}">

                                    <c:forEach var="met" items="${mlAssay.metabolitesGUI}" varStatus="loopStatusMet">

                                        <%-- Write the header, only the first time --%>
                                        <c:if test="${loopStatusMet.index == 1}">
                                        <thead class='text_header'>
                                        <tr>
                                            <th><spring:message code="label.metabolites.description"/></th>
                                            <th><spring:message code="label.metabolites.formula"/></th>

                                            <c:if test="${mlAssay.technology eq  'mass spectrometry'}">
                                                <th><spring:message code="label.metabolites.mz"/></th>
                                                <th><spring:message code="label.metabolites.retentiontime"/><a class="right icon icon-functional" data-icon="u" onclick="toggleColumn('metabolites${loopStatusAssay.index}', this, 2500)"></a></th>
                                            </c:if>
                                            <c:if test="${mlAssay.technology eq 'NMR spectroscopy'}">
                                                <th><spring:message code="label.metabolites.chemicalshift"/></th>
                                                <th><spring:message code="label.metabolites.multiplicity"/><a class="right icon icon-functional" data-icon="u" onclick="toggleColumn('metabolites${loopStatusAssay.index}', this, 2500)"></a></th>
                                            </c:if>

                                            <th><spring:message code="label.metabolites.smiles"/></th>
                                            <th><spring:message code="label.metabolites.inchi"/></th>
                                            <c:forEach var="sampleHeader" items="${met.metabolite.metaboliteSamples}" varStatus="loopStatusSamplesName" >
                                                <th>${sampleHeader.sampleName}</th>
                                            </c:forEach>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        </c:if>

                                        <%-- Show more stuff...show only ten lines by default --%>
                                        <c:if test="${loopStatusMet.index == 10}">
                                        </tbody><tbody id="met_${loopStatusAssay.index}" style='display:none'>
                                        </c:if>

                                        <%--Line itself --%>
                                        <tr class="${loopStatusMet.index % 2 == 0 ? '' : 'coloured'}">
                                            <td>${met.metabolite.description}
                                                <c:choose>
                                                    <c:when test="${empty met.identifier}"></c:when>
                                                    <c:when test="${empty met.link }"> (${met.identifier})</c:when>
                                                    <c:otherwise><a class="metLink" identifier="${met.identifier}" href="${met.link}" target="_blank">(${met.identifier})</a></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${met.metabolite.chemical_formula}</td>
                                            <c:if test="${mlAssay.technology eq 'mass spectrometry'}">
                                                <td>${met.metabolite.mass_to_charge}</td>
                                                <td>${met.metabolite.retention_time}</td>
                                            </c:if>
                                            <c:if test="${mlAssay.technology eq 'NMR spectroscopy'}">
                                                <td>${met.metabolite.chemical_shift}</td>
                                                <td>${met.metabolite.multiplicity}</td>
                                            </c:if>
                                            <td>${met.metabolite.smiles}</td>
                                            <td>${met.metabolite.inchi}</td>
                                            <%-- sampleValues --%>
                                            <c:forEach var="sample" items="${met.metabolite.metaboliteSamples}" varStatus="loopStatusSamples" >
                                                <td class="tableitem">${sample.value}</td>
                                            </c:forEach> <%-- For each sample --%>
                                        </tr>
                                    </c:forEach> <%-- For each metabolite (line)--%>
                                    </tbody>
                                </table>
                                <script>toggleColumn('metabolites${loopStatusAssay.index}',null,0) </script>
                            </div>

                            <c:if test="${fn:length(mlAssay.metabolitesGUI) > 10}"><a href="#" class="showLink" id="met_link_${loopStatusAssay.index}">Show more</a></c:if>
                            <br/>
                        </c:if>
                    </c:forEach> <!-- For each assayGroup -->
                </c:if>
            </div> <!--  ends tabs-4 metabolites-->
            </c:if>
            <c:if test="${not empty files}">

                <%--Only show token if study es private--%>
                <c:if test="${study.status ne 'PUBLIC'}">
                    <c:set var="token" value="?token=${study.id}"/>
                </c:if>

            <div id="tabs-5"> <!-- Study files -->
                 <form action="${study.acc}/files/selection" method="post">
                    <h5>
                    <a class="noLine" href="${study.acc}/files/${study.acc}${token}" title="<spring:message code="label.downloadstudy"/>">
                        <span class="icon icon-functional" data-icon="="/><spring:message code="label.downloadstudy"/>
                    </a>
                    &nbsp;|&nbsp;
                    <a class="noLine" href="${study.acc}/files/metadata${token}" title="<spring:message code="label.downloadstudyMetadata"/>">
                        <span class="icon icon-functional" data-icon="="><spring:message code="label.downloadstudyMetadata"/>
                    </a>
                    &nbsp;
                    <c:if test="${study.status eq 'PUBLIC'}">
                        |&nbsp;
                        <a class="noLine" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/studies/public/${study.acc}" title="<spring:message code="label.viewAllFiles"/>">
                            <span class="icon icon-generic" data-icon="x"/><spring:message code="label.viewAllFiles"/>
                        </a>
                    </c:if>
                    </h5>
                    <br/>
                    <h5><spring:message code="label.fileListTableExplanation"/></h5>
                    <p><input class="inputDiscrete resizable" id="fileSelector" class="" type="text" placeholder="<spring:message code='label.fileList.Input.placeholder'/>"></p>
                    <input type="hidden" name="token" value="${study.id}">
                    <table id="files">
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
                                    <a href="${study.acc}/files/${file.name}${token}">${file.name}</a>
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
            </div> <!--  ends tabs-5 files -->
            </c:if>
		</div> <!-- end tabs -->
        <c:if test="${not hasMetabolites}">
            <br/><spring:message code="msg.noMetabolitesFound"/>
        </c:if>
</div>
<div id="shareInfo">
    <h5><spring:message code="title.study.paper.link"/></h5>
    <p><spring:message code="label.study.paper.link"/></p>
    <p><input class="inputDiscrete resizable" type="text" value="${fullContextPath}/${study.acc}" readonly/></p>
    <c:if test="${(study.status ne 'PUBLIC')}">
    <h5><spring:message code="title.study.private.link"/></h5>
    <p><spring:message code="label.study.private.link"/></p>
    <p><input class="inputDiscrete resizable" type="text" value="${fullContextPath}/reviewer${study.obfuscationCode}" readonly/></p>
    </c:if>
</div>
