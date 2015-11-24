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

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/biojs.Rheaction.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/st.css" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

<%--  Place holder for the compound --%>
<div id="content" class="grid_24">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                <div class="metabolite-title">
                    <h2>${fn:toUpperCase(fn:substring(compound.mc.name, 0, 1))}${fn:substring(compound.mc.name, 1,fn:length(compound.mc.name))}</h2>

                    <h3 class="right text-muted"><i>${compound.mc.accession}</i></h3>
                </div>
                <div class="col-md-12 metabolite-wrapper">
                    <div class="row">
                        <div class="col-md-3">
                            <%--<h5>Structure</h5><br>--%>
                            <img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${compound.mc.chebiId}"
                                 class="metabolite-image thumbnail"/>
                            <p>
                                <a href="http://www.ebi.ac.uk/chebi/searchId.do?chebiId=${compound.mc.chebiId}">${compound.chebiEntity.chebiAsciiName}
                                    - (${compound.mc.chebiId})</a>
                            </p>
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
                                <div class="tab-content row">
                                    <div role="tabpanel" class="tab-pane active" id="chemistry">
                                        <c:if test="${not empty compound.chebiEntity.definition}">
                                            <h6 class="text-muted"><i><spring:message
                                                    code="ref.compound.tab.characteristics.definition"/></i></h6>

                                            <p>${compound.chebiEntity.definition}</p>
                                        </c:if>
                                        <br>
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
                                        </table>
                                    </div>

                                    <c:if test="${compound.mc.hasSpecies}">
                                        <div role="tabpanel" class="tab-pane" id="biology">
                                            <table class="table table-bordered">
                                                <c:forEach var="item" items="${compound.species}">
                                                    <tr>
                                                        <td>${item.key.species} </td>
                                                        <td>
                                                            <c:forEach var="xref" items="${item.value}">
                                                                <c:choose>
                                                                    <c:when test="${xref.crossReference.db.id eq 2}">
                                                                        <a href='${xref.crossReference.accession}'>${xref.crossReference.accession}</a>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <a href='http://www.ebi.ac.uk/chebi/searchId.do?chebiId=${xref.crossReference.accession}'>${xref.crossReference.accession}</a>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </div>
                                    </c:if>
                                    <c:if test="${compound.mc.hasPathways}">
                                        <div role="tabpanel" class="tab-pane" id="pathways">
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
                                                        "url": "${pageContext.request.contextPath}/webservice/compounds/spectra/${spectra.id}/json",
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
                                            <!-- MS Spectra -->

                                            <select multiple class="form-control" id="msSpectraList"></select>

                                            <div id="MSSpeckTackle" class="grid_24" style="height: 500px"></div>
                                            <div id="msInfo" class="grid_23 specs"></div>
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
                                                        "url": "${pageContext.request.contextPath}/webservice/compounds/spectra/${msspectra.id}/json",
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
<%--End of content --%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
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
        loadSpectra(spectra);
        if (spectra.length == 1) {
            loadSpectraInfo(spectra[0], infoDiv);
            $(infoDiv).show();
        } else {
            $(infoDiv).hide();
        }
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

    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        var target = $(e.target).attr("href") // activated tab
        if (target == '#pathways') {
            if (!pathwaysretrieved) {
                pathways.displayPathwayData("${compound.mc.chebiId}", 'pathwayContainer');
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
        }
    });


</script>


