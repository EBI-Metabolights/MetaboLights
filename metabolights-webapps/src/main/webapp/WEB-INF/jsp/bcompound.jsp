<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script src="${pageContext.request.contextPath}/javascript/jquery.svg.js"></script>
<script src="${pageContext.request.contextPath}/javascript/jquery.svganim.js"></script>
<%--<script type="text/javascript"--%>
<%--src="<spring:url value="specbrowser/SpectrumBrowser/SpectrumBrowser.nocache.js"/>"></script>--%>
<script type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/metabolights-literature.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/metabolights-reactions.js"></script>

<script type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/st.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/Biojs.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/Biojs.Rheaction.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/wiki-pathways.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/splash.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/javascript/3Dmol-min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"  href="${pageContext.request.contextPath}/cssrl/biojs.Rheaction.css" type="text/css"/>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/st.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>


<noscript>
    <style type="text/css">
        .ml--wrapper {display:none;}
    </style>
    <div class="container">
        <div>&nbsp;</div>
        <div class="noscriptmsg well">
            You don't have javascript enabled.  Please enable javascript and refresh the page !
        </div>
    </div>
</noscript>

<%--  Place holder for the compound --%>
<div id="content" class="grid_24">
    <div class="container-fluid">
        <div class="row">
            <div class="ml--wrapper">
                <div class="col-md-12">
                <div class="metabolite-title">
                    <h2>${fn:toUpperCase(fn:substring(compound.mc.name, 0, 1))}${fn:substring(compound.mc.name, 1,fn:length(compound.mc.name))}</h2>

                    <h3 class="right text-muted"><i>${compound.mc.accession}</i></h3>
                </div>
                <div class="col-md-12 metabolite-wrapper">
                    <div class="row">
                        <div class="col-md-3">

                            <div>

                                <!-- Nav tabs -->
                                <ul class="nav nav-tabs" role="tablist">
                                    <li role="presentation" class="active"><a href="#2d" aria-controls="home" role="tab" data-toggle="tab">2D</a></li>
                                    <li role="presentation"><a href="#3d" aria-controls="profile" role="tab" data-toggle="tab">3D</a></li>
                                </ul>

                                <!-- Tab panes -->
                                <div class="tab-content" id="displayMol">
                                    <div role="tabpanel" class="tab-pane active" id="2d">
                                        <%--<h5>Structure</h5><br>--%>
                                        <img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${compound.mc.chebiId}&dimensions=600&transbg=true"
                                             class="metabolite-image thumbnail"/>

                                    </div>
                                    <div role="tabpanel" class="tab-pane" id="3d">
                                        <div id="3dDisplay" style="position: relative;"></div>
                                    </div>
                                </div>

                            </div>
                            <br>
                            <p>
                                <a href="http://www.ebi.ac.uk/chebi/searchId.do?chebiId=${compound.mc.chebiId}">${compound.chebiEntity.chebiAsciiName}
                                    - (${compound.mc.chebiId})</a>
                            </p>
                            <p><%-- <a href="${pageContext.request.contextPath}/old/${compound.mc.accession}" class="icon icon-functional" data-icon="*">OLD</a>&emsp; --%><a href="${pageContext.request.contextPath}/beta/${compound.mc.accession}" class="icon icon-functional" data-icon=")">BETA</a></p>
                            <p><a href="${pageContext.request.contextPath}/referencespectraupload?cid=${compound.mc.accession}"
                                  class="icon icon-functional" data-icon="_">Upload Reference Spectra</a></p>
                        </div>
                        <div class="col-md-9">
                            <div>

                                <!-- Nav tabs -->
                                <ul class="nav nav-tabs row" role="tablist">

                                    <li role="presentation" class="active">
                                        <a href="#chemistry" aria-controls="chemistry" role="tab"
                                           data-toggle="tab"><b><spring:message code="ref.compound.tab.chemistry"/></b></a>
                                    </li>

                                    <c:if test="${compound.mc.hasSpecies}">
                                        <li role="presentation">
                                            <a href="#biology" aria-controls="biology" role="tab"
                                               data-toggle="tab"><b><spring:message
                                                    code="ref.compound.tab.biology"/></b></a>
                                        </li>
                                    </c:if>

                                    <c:if test="${compound.mc.hasPathways}">
                                        <li role="presentation">
                                            <a href="#pathways" aria-controls="pathways" role="tab"
                                               data-toggle="tab"><b><spring:message
                                                    code="ref.compound.tab.pathways"/></b></a>
                                        </li>
                                    </c:if>

                                    <c:if test="${compound.mc.hasReactions}">
                                        <li role="presentation">
                                            <a href="#reactions" aria-controls="reactions" role="tab" data-toggle="tab"><b><spring:message
                                                    code="ref.compound.tab.reactions"/></b></a>
                                        </li>
                                    </c:if>

                                    <c:if test="${compound.mc.hasNMR}">
                                        <li role="presentation">
                                            <a href="#nmrspectra" aria-controls="nmrspectra" role="tab"
                                               data-toggle="tab"><b><spring:message
                                                    code="ref.compound.tab.nmrspectra"/></b></a>
                                        </li>
                                    </c:if>

                                    <c:if test="${compound.mc.hasMS}">
                                        <li role="presentation">
                                            <a href="#msspectra" aria-controls="msspectra" role="tab" data-toggle="tab"><b><spring:message
                                                    code="ref.compound.tab.msspectra"/></b></a>
                                        </li>
                                    </c:if>

                                    <c:if test="${compound.mc.hasLiterature}">
                                        <li role="presentation">
                                            <a href="#literature" aria-controls="literature" role="tab"
                                               data-toggle="tab"><b><spring:message
                                                    code="ref.compound.tab.literature"/></b></a>
                                        </li>
                                    </c:if>
                                </ul>

                                <!-- Tab panes -->
                                <div class="tab-content ml_mtc row">
                                    <div role="tabpanel" class="tab-pane active" id="chemistry">
                                        <c:if test="${not empty compound.chebiEntity.definition}">


                                            <h6 class="text-muted"><i><spring:message code="ref.compound.tab.characteristics.definition"/></i></h6>
                                            <div id="app">
                                            <p>${compound.chebiEntity.definition}</p><br>
                                            <div class="tabbable">
                                                <ul class="nav nav-pills nav-stacked col-md-2 ml_vnb">
                                                    <li class="active"><a href="#a" data-toggle="tab">Chemical Properties</a></li>
                                                    <li><a href="#b" data-toggle="tab">Synonyms</a></li>
                                                    <li><a href="#c" data-toggle="tab">External links</a></li>
                                                </ul>
                                                <div class="tab-content col-md-10 ml_vntc">
                                                    <div class="tab-pane active" id="a">
                                                        <div class="panel panel-default">
                                                            <div class="panel-heading">
                                                                Chemical Properties
                                                            </div>
                                                            <div class="panel-body">
                                                                <div class="ml_tb row">
                                                                <div class="col-md-12 ml_tr">
                                                                    <div class="col-md-2 ml_trc"><b>Property</b></div>
                                                                    <div class="col-md-10 ml_trc"><b>Value</b></div>
                                                                </div>
                                                                <div class="col-md-12 ml_tr">
                                                                    <div class="col-md-2 ml_trc">InChIKey</div>
                                                                    <div class="col-md-10 ml_trc">{{ inchikey }}</div>
                                                                </div>
                                                                <div class="col-md-12 ml_tr">
                                                                    <div class="col-md-2 ml_trc">InChI</div>
                                                                    <div class="col-md-10 ml_trc">{{ inchicode }}</div>
                                                                </div>
                                                                <div class="col-md-12 ml_tr">
                                                                    <div class="col-md-2 ml_trc">Formula</div>
                                                                    <div class="col-md-10 ml_trc">{{ formula }}</div>
                                                                </div>
                                                                <div class="col-md-12 ml_tr">
                                                                    <div class="col-md-2 ml_trc">Molecular Weight</div>
                                                                    <div class="col-md-10 ml_trc">{{ molweight }}</div>
                                                                </div>
                                                                <div class="col-md-12 ml_tr">
                                                                    <div class="col-md-2 ml_trc">Exact Mass</div>
                                                                    <div class="col-md-10 ml_trc">{{ exactmass }}</div>
                                                                </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="tab-pane" id="b">
                                                        <div class="panel panel-default">
                                                            <div class="panel-heading">
                                                                Synonymns
                                                            </div>
                                                            <div class="panel-body">
                                                                <ul class="list-group">
                                                                    <li v-for="synonym in synonyms" class="list-group-item">{{ synonym }}</li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="tab-pane" id="c">
                                                        <div class="panel panel-default">
                                                            <div class="panel-heading">
                                                                External Links
                                                            </div>
                                                            <div class="panel-body">
                                                                <div class="ml_tb row">
                                                                    <div class="col-md-12 ml_tr">
                                                                        <div class="col-md-3 ml_trc"><b>Property</b></div>
                                                                        <div class="col-md-9 ml_trc"><b>Value</b></div>
                                                                    </div>
                                                                    <div class="col-md-12 ml_tr" v-for="id in externalids">
                                                                        <div class="col-md-3 ml_trc">{{ $key }}</div>
                                                                        <div class="col-md-9 ml_trc">{{{ id }}}</div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>




                                            </div>

                                        </c:if>
                                        <!--<br>
                                        <table class="table table-bordered">
                                            <c:if test="${not empty compound.chebiEntity.iupacNames}">
                                                <tr>
                                                    <td>IUPAC Name</td>
                                                    <td>
                                                        <c:forEach var="iupacName"
                                                                   items="${compound.chebiEntity.iupacNames}">
                                                            ${iupacName.data}
                                                        </c:forEach>
                                                    </td>
                                                </tr>
                                            </c:if>
                                            <tr>
                                                <td>Chemical&nbsp;formula</td>
                                                <td>
                                                    <c:forEach var="formula" items="${compound.chebiEntity.formulae}">
                                                        ${formula.data}
                                                    </c:forEach>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Average mass</td>
                                                <td>${compound.chebiEntity.mass}</td>
                                            </tr>
                                            <tr>
                                                <td>InChI</td>
                                                <td>${compound.chebiEntity.inchi}</td>
                                            </tr>
                                            <tr>
                                                <td><spring:message code="ref.compound.synonyms"/></td>
                                                <td>
                                                    <c:forEach var="synonym" items="${compound.chebiEntity.synonyms}">
                                                        <p>${synonym.data}</p>
                                                    </c:forEach>
                                                </td>
                                            </tr>
                                        </table> -->

                                    </div>

                                    <c:if test="${compound.mc.hasSpecies}">
                                        <div role="tabpanel" class="tab-pane" id="biology">
                                            <br>
                                            <div class="col-md-12">
                                            <ul class="list-group">

                                                    <c:forEach var="item" items="${compound.species}">
                                                        <li class="list-group-item row">
                                                            <div class="col-md-3">
                                                                <h5>${item.key.species}</h5>
                                                            </div>
                                                            <div class="col-md-9">
                                                            <c:forEach var="xref" items="${item.value}">
                                                                <c:choose>
                                                                    <c:when test="${xref.crossReference.db.id eq 2}">
                                                                        <h5><span><b><a href='${xref.crossReference.accession}' class="ml--studyid">${xref.crossReference.accession}</a></b>
                                                                            <b class="ml--studytitle ${xref.crossReference.accession}--title"></b></span>
                                                                            <span class="${xref.crossReference.accession}--description hidden"></span>
                                                                        </h5>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                <h5><span><a href='http://www.ebi.ac.uk/chebi/searchId.do?chebiId=${xref.crossReference.accession}'>${xref.crossReference.accession}</a></span></h5>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                            </div>
                                                        </li>
                                                    </c:forEach>
                                            </ul>
                                            </div>

                                        </div>
                                    </c:if>

                                    <c:if test="${compound.mc.hasPathways}">
                                        <div role="tabpanel" class="tab-pane" id="pathways">
                                            <br>
                                            <!-- Pathways Container -->
                                            <div id="pathwayContainer" height="100%" width="100%">
                                                <p class="text-center"><br><br><br><img src="${pageContext.request.contextPath}/img/beta_loading.gif"></p>
                                            </div>
                                        </div>
                                    </c:if>

                                    <c:if test="${compound.mc.hasNMR}">
                                        <div role="tabpanel" class="tab-pane" id="nmrspectra">
                                            <!-- NMR Spectra -->
                                            <select multiple id="nmrSpectraList" class="form-control"></select>

                                            <div id="NMRSpeckTackle" class="col-md-12" style="height:500px"></div>
                                            <div id="nmrInfo" class="col-md-12"></div>
                                            <script>
                                                var nmrInfo = [
                                                    <c:set var="count" value="0" scope="page"/>
                                                    <c:forEach var="spectra" items="${compound.mc.metSpectras}" varStatus="spectraLoopStatus">
                                                    <c:if test="${spectra.spectraType == 'NMR'}">
                                                    <c:if test="${count gt 0}">,
                                                    </c:if>
                                                    {
                                                        "id":${spectra.id},
                                                        "name": "${spectra.name}",
                                                        "url": "http://www.ebi.ac.uk/metabolights/webservice/compounds/spectra/${spectra.id}/json",
                                                        "type": "${spectra.spectraType}",
                                                        "properties": [
                                                            <c:forEach var="attribute" items="${spectra.attributes}" varStatus="attributeLoopStatus">
                                                            <c:if test="${attributeLoopStatus.index gt 0}">,
                                                            </c:if>
                                                            {
                                                                "name": "${attribute.attributeDefinition.name}",
                                                                "value": "${attribute.value}"
                                                            }
                                                            </c:forEach>
                                                            <c:set var="count" value="${count + 1}" scope="page"/>
                                                        ]
                                                    }
                                                    </c:if>
                                                    </c:forEach>
                                                ];
                                            </script>
                                        </div>
                                    </c:if>
                                    <c:if test="${compound.mc.hasMS}">
                                        <div role="tabpanel" class="tab-pane" id="msspectra">

                                            <br>
                                            <div class="col-md-12">
                                                <!-- MS Spectra -->
                                                <label for="msSpectraList">&emsp;Select spectra</label>
                                                <select multiple class="form-control" id="msSpectraList"></select>
                                                <div id="MSSpeckTackle" class="grid_24" style="height: 500px"></div>
                                                <div class="col-md-12 well">
                                                    <div id="msInfo" class="grid_23 specs"></div>
                                                </div>
                                                <br>
                                                <div class="panel panel-default">
                                                    <div class="panel-body">
                                                        <p><a href="http://splash.fiehnlab.ucdavis.edu/">Splash - The Spectral Hash Identifier</a> <span class="pull-right" id="splash-container"></span></p>
                                                    </div>
                                                </div>
                                            </div>
                                            <br>
                                            <script>
                                                var msInfo = [
                                                    <c:set var="count" value="0" scope="page"/>
                                                    <c:forEach var="msspectra" items="${compound.mc.metSpectras}" varStatus="spectraLoopStat">
                                                    <c:if test="${msspectra.spectraType == 'MS'}">
                                                    <c:if test="${count gt 0}">,
                                                    </c:if>
                                                    {
                                                        "id":${msspectra.id},
                                                        "name": "${msspectra.name}",
                                                        "url": "http://www.ebi.ac.uk/metabolights/webservice/compounds/spectra/${msspectra.id}/json",
                                                        "type": "${msspectra.spectraType}",
                                                        "properties": [
                                                            <c:forEach var="attribute" items="${msspectra.attributes}" varStatus="attributeLoopStatus">
                                                            <c:if test="${attributeLoopStatus.index gt 0}">,
                                                            </c:if>
                                                            {
                                                                "name": "${attribute.attributeDefinition.name}",
                                                                "value": "${attribute.value}"
                                                            }
                                                            </c:forEach>
                                                            <c:set var="count" value="${count + 1}" />
                                                        ]
                                                    }
                                                    </c:if>
                                                    </c:forEach>
                                                ];
                                            </script>
                                        </div>
                                    </c:if>
                                    <c:if test="${compound.mc.hasReactions}">
                                        <div role="tabpanel" class="tab-pane" id="reactions">
                                            <br>
                                            <div id="reactions-content">
                                                <p class="text-center"><br><br><br><img src="${pageContext.request.contextPath}/img/beta_loading.gif"></p>
                                            </div>
                                        </div>
                                    </c:if>

                                    <c:if test="${compound.mc.hasLiterature}">
                                        <div role="tabpanel" class="tab-pane" id="literature">
                                            <div id="literature-content">
                                                <p class="text-center"><br><br><br><img src="${pageContext.request.contextPath}/img/beta_loading.gif"></p>
                                            </div>
                                        </div>
                                    </c:if>

                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="study-details-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div>Study Details</div>
            </div>
            <div class="modal-body">
                <div id="modal-info">
                    <h3 id="study--title"></h3><hr>
                    <p><label>Study Description:</label></p>
                    <p id="study--description"></p>
                </div>
            </div>
            <div class="modal-footer">
                <a class="btn btn-primary ml--button" id="study--link" target="_blank" href="">View Study</a>
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
    </div>
</div>


<%--End of content --%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>
    $('#myTabs a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    });

    var MSchart;
    var MSData;
    function initializeMSSpeckTackle() {
        if (MSchart != undefined) return;
        MSchart = st.chart.ms().xlabel("Mass-to-Charge").ylabel("Intensity").legend(true).labels(true);
        MSchart.render("#MSSpeckTackle");
        MSData = st.data.set().x("peaks.mz").y("peaks.intensity").title("spectrumId");
        MSchart.load(MSData);
        loadSpectralist("#msSpectraList", msInfo);
    }

    var NMRchart;
    var NMRarray;
    function initializeNMRSpeckTackle() {
        if (NMRchart != undefined) return;
        NMRchart = st.chart.nmr().xlabel("ppm").legend(true).margins([20, 100, 60, 0]).labels(true);
        NMRchart.render("#NMRSpeckTackle");
        NMRarray = st.data.array().xlimits(["xMin", "xMax"]).ylimits(["yMin", "yMax"]).y("data");
        NMRchart.load(NMRarray);
        loadSpectralist("#nmrSpectraList", nmrInfo);
    }
    function loadSpectralist(listSelector, spectraList) {

        // Get the SELECT ELEMENT
        var list = $(listSelector)[0];
        // Loop through the spectraList...
        $.each(spectraList, function (i, item) {
            $(list).append($('<option>', {
                value: item.id,
                text: item.name
            }));
        });

        $(list).change({"spectraList": spectraList}, function (event) {
            spectraChangeHandler(event);
        })

        // Get the first spectra and load it
        loadSpectraAndInfo([spectraList[0]], $(list).next().next());
    }
    function spectraChangeHandler(event) {
        var select = event.target;
        if ($(select).attr("multiple")) {
            spectraChangeHandlderMultiple(event);
        } else {
            spectraChangeHandlderDropDown(event);
        }
    }
    function spectraChangeHandlderMultiple(event) {
        /* Get the selected option */
        var index = event.target.selectedIndex;
        /* Get the selected spectra*/
        var selectedSpectra = getSelectedSpectra(event.target, event.data.spectraList);
        var infoDiv = $(event.target).next().next();
        loadSpectraAndInfo(selectedSpectra, infoDiv);
    }
    function getSelectedSpectra(select, spectraList) {
        var spectra = new Array();
        for (index = 0; index < select.children.length; ++index) {
            var option = select.children[index];
            if (option.selected) {
                spectra.push(spectraList[index]);
            }
        }
        return spectra;
    }
    function spectraChangeHandlderDropDown(event) {
        /* Get the selected option */
        var index = event.target.selectedIndex;
        /* Get the spectra object (json element)*/
        var spectrum = event.data.spectraList[index];
        var infoDiv = $(event.target).next().next();
        loadSpectrumAndInfo(spectrum, infoDiv);
    }
    function loadSpectrumAndInfo(spectrum, infoDiv) {
        loadSpectrum(spectrum);
        loadSpectraInfo(spectrum, infoDiv);
    }
    function loadSpectraAndInfo(spectra, infoDiv) {
        MLSpectraURL = "http://wwwdev.ebi.ac.uk/metabolights/webservice/beta/spectra/${compound.mc.accession}/" + spectra[0]['name'];
        loadSPLASH(MLSpectraURL);
        loadSpectra(spectra);

        if (spectra.length == 1) {
            loadSpectraInfo(spectra[0], infoDiv);
            $(infoDiv).show();
        } else {
            $(infoDiv).hide();
        }
    }

    function loadSPLASH(spectraURl){
        var spectralCoordinates;
        $.ajax({
            type: 'GET',
            url: spectraURl,
            success: function (data) {
                document.getElementById('splash-container').innerHTML = "";
                spectralCoordinates = JSON.parse(data);
                var ions = [];
                for (x in spectralCoordinates['peaks']) {
                    var coord = spectralCoordinates['peaks'][x];
                    ions.push('{"mass": ' + coord['mz'] + ', "intensity" : '+ coord["intensity"] +'}');
                }
                var SPLASH_JSON = '{ "ions" : [' + ions + '], "type" : "MS" }';
                generateSplash(JSON.parse(SPLASH_JSON));
            },
            error: function (xhr, status, errorThrown) {
                console.log('STATUS ' + status);
                console.log('ERROR THROWN ' + errorThrown);
                done();
            }
        });
    }


    function loadSpectra(spectra) {
//        if (spectra.length > 0)
//        {
        // Take the first one to knoe the type
        var spectrum = spectra[0];

        if (spectrum.type == "NMR") {
            data = NMRarray;
        } else {
            data = MSData;
        }
        /* Remove all the spectra */
        data.remove();

        var urls = new Array();
        // Loop through the spectra to create a list of urls
        for (index = 0; index < spectra.length; ++index) {
            urls.push(spectra[index].url);
        }
        data.add(urls);
    }

    function loadSpectrum(spectrum) {
        if (spectrum.type == "NMR") {
            data = NMRarray;
        } else {
            data = MSData;
        }
        data.remove(0);
        data.add(spectrum.url);
    }
    function loadSpectraInfo(spectra, infoDiv) {
        var html = spectra.name + "<br/> A " + spectra.type + " spectrum.";
        $.each(spectra.properties, function () {
            html = html + "<br/>" + this.name + ": "
            if (this.value.indexOf("http:") == 0) {
                html = html + "<a href=\"" + this.value + "\">" + this.value + "</a>"
            } else {
                html = html + this.value;
            }
        });
        infoDiv.html(html);
    }


    var pathwaysretrieved = false;
    var dDisplayed = false;
    var studyDataRetrieved = false;

    $( document ).ready(function() {
        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
            var target = $(e.target).attr("href") // activated tab
            loadData(target);
        });

        var url = document.location.toString();
        if (url.match('#')) {
            $('.nav-tabs a[href=#'+url.split('#')[1]+']').tab('show');
            window.scrollTo(0, 0);
            loadData(url.split('#')[1]);
        }

        $('.nav-tabs a').on('shown.bs.tab', function (e) {
            window.location.hash = e.target.hash;
        })

    });

    function loadData(target){
        if (target == '#pathways') {
            if (!studyDataRetrieved) {
                pathways.displayPathwayData("${compound.mc.chebiId}", 'pathwayContainer');
                studyDataRetrieved = true;
            }
        } else if (target == '#biology') {
            if (!pathwaysretrieved) {
                loadStudyData();
                pathwaysretrieved = true;
            }
        } else if (target == '#nmrspectra') {
            initializeNMRSpeckTackle();
        } else if (target == '#msspectra') {
            initializeMSSpeckTackle();
        } else if (target == '#reactions') {
            $('#reactions-content').html( reactions.getReactions("${compound.mc.accession}"));
        } else if (target == '#literature') {
            $('#literature-content').html( literature.getLiterature("${compound.mc.accession}"));
        } else if (target == '#3d') {
            if(!dDisplayed){
                display3DMol();
                dDisplayed = true;
            }
        }
    }


    var width = document.getElementById('displayMol').offsetWidth - 20;
    var height = document.getElementById('displayMol').offsetHeight;
    $("#3dDisplay").width(width).height(height);
    var viewer = $3Dmol.createViewer($("#3dDisplay"));
    viewer.setBackgroundColor('white');

    function display3DMol(){
        var inchikey = '${compound.chebiEntity.inchiKey}';

        var xmlhttp;
        var url ="http://cactus.nci.nih.gov/chemical/structure/"+inchikey+"/sdf";
        if (window.XMLHttpRequest)
        {// code for IE7+, Firefox, Chrome, Opera, Safari
            xmlhttp=new XMLHttpRequest();
        }
        else
        {// code for IE6, IE5
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange=function()
        {
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
                var mol = xmlhttp.responseText;
                viewer.clear();
                viewer.addAsOneMolecule(mol, "sdf");
                viewer.setStyle({},{stick: {radius:0.2}});
                viewer.zoomTo();
                viewer.render();
            }
        }
        xmlhttp.open("GET",url,true);
        xmlhttp.send();

    }



    function loadStudyData() {
        $('.ml--studyid').each(function (i, obj) {
            LoadStudyDetails(obj.text)
        });
    }

    function LoadStudyDetails(id) {
        $.ajax({
            type: 'GET',
            async: false,
            cache: false,
            url: "../metabolights/webservice/study/" + id + "/lite",
            success: function (data) {
                var studyDetails = data['content'];
                $("." + id + "--title").html(" " + studyDetails['title'] + "<a data-target='#study-details-modal' data-toggle='modal' data-studyid='" +id+ "'> <small>more...</small></a>" );
                $("." + id + "--description").text(studyDetails['description']);
            },
            error: function (xhr, status, errorThrown) {
                console.log('STATUS ' + status);
                console.log('ERROR THROWN ' + errorThrown);
                done();
            }
        });
    }


</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.10/vue.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.17/vue-resource.js"></script>


<script>
    $('#study-details-modal').on('show.bs.modal', function(e) {
        var studyid = $(e.relatedTarget).data('studyid');
        var title = $("."+studyid+"--title").text();
        var description = $("."+studyid+"--description").text();
        //console.log(title);
        //console.log(description);
        $(this).find('#study--title').text(title);
        $(this).find('#study--link').attr("href","../metabolights/"+studyid);
        $(this).find('#study--description').text(description);
    });
</script>

<script>


    window.onload = function () {

        var vm = new Vue({
            //http: {
            //    headers: {
            //     'user_token' : '6996ca30-672c-4cda-9a0e-d113d640776f'
            //    }
            //},
            el: '#app',
            data: {
                metabolight: 'MTBLC15355',
                inchikey: "",
                inchicode: "",
                formula: "",
                molweight: "",
                exactmass: "",
                synonyms: [],
                chemicalproperties: {
                    inchikey: "${compound.chebiEntity.inchiKey}"
                },
                externalids: [],
                kegg: []
            },
            methods: {
                loadMTBLCCP: function(){
                    this.$http.get('https://crossorigin.me/http://cts.fiehnlab.ucdavis.edu/service/compound/'+this.chemicalproperties.inchikey, function (data, status, request) {
                        this.synonyms = this.getNames(data.synonyms)
                        this.inchikey = data.inchikey
                        this.inchicode = data.inchicode
                        this.molweight = data.molweight
                        this.exactmass = data.exactmass
                        this.formula = data.formula
                        this.extractIds(data.externalIds)

                    }).error(function (data, status, request) {
                        alert('Cannot fetch Compound details');
                    });
                },
                extractIds: function(eids , ex){
                    var tempArray = {};
                    var arrayLength = eids.length;
                    for (var i = 0; i < arrayLength; i++) {
                        if (tempArray[eids[i].name]){
                            tempArray[eids[i].name].push(" <span><a href='" + eids[i].url + "'>" + eids[i].value + "</a></span> ");
                        }else{
                            tempArray[eids[i].name] = [" <span><a href='" + eids[i].url + "'>" + eids[i].value + "</a></span> "];
                        }
                    }
                    this.externalids = tempArray;
                    var result = eids.filter(function( obj ) {
                        return obj.name == 'KEGG';
                    });
                    this.kegg = result;
                },
                getNames: function(data) {
                    synonymns = [];
                    for (obj in data) {
                        var tempSyn= data[obj].name;
                        if( synonymns.indexOf(tempSyn)<0){
                            synonymns.push(tempSyn);
                        }
                    }
                    return synonymns;
                }
            },

            ready: function() {
                this.loadMTBLCCP();
            }
        })
    }




</script>



