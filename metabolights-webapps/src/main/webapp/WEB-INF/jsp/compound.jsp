<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 8/20/14 5:00 PM
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
<script type="text/javascript" src="javascript/pathways.js"></script>
<script type="text/javascript" src="javascript/formularize.js"></script>
<script type="text/javascript" src="javascript/spectra.js"></script>
<script type="text/javascript" src="javascript/lazyload.js"></script>
<script type="text/javascript" src="javascript/jquery.hc-sticky.min.js"></script>
<script type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>
<script type="text/javascript" src="javascript/st.min.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/Biojs.js" charset="utf-8"></script>
<script type="text/javascript" src="javascript/Biojs.Rheaction.js"></script>
<link rel="stylesheet"  href="cssrl/biojs.Rheaction.css" type="text/css"/>
<link rel="stylesheet"  href="css/st.css" type="text/css" />


<script>

    $(document).ready(function () {

        $('#slide').hcSticky({
            top:10,
            bottomEnd: 100,
            wrapperClassName:"grid_4"
        });
        $("#formulae").formularize();

        $.fn.getIndex = function () {
            var $p = $(this).parent().children();
            return $p.index(this);
        }

        $("#hourglass").dialog({
            create: function () {
                $('.ui-dialog-titlebar-close').removeClass('ui-dialog-titlebar-close');
            },
            width: 200,
            height: 60,
            modal: true,
            autoOpen: false
        });

        initializeMSSpeckTackle();
        initializeNMRSpeckTackle();
        bindPathways();
        lazyload();

    });

    $(document).ajaxStart(function () {
        showWait();
    }).ajaxStop(function () {
        hideWait();
    }).ajaxError(function () {
        hideWait();

    });


    function showWait() {
        document.body.style.cursor = "wait";
        $('.ui-dialog-titlebar').hide();
        $("#hourglass").dialog("open");
    }

    function hideWait() {
        document.body.style.cursor = "default";
        $("#hourglass").dialog("close");
    }

</script>

<div id="hourglass">
    <img src="img/wait.gif" alt="Please wait"/>&nbsp;<b><spring:message code="msg.fetchingData"/></b>
</div>

<h3>${compound.mc.accession} - ${compound.mc.name}</h3>

<div class="grid_20">
    <section>
        <h3 class="chemistry"><a id="chemistry""><spring:message code="ref.compound.tab.chemistry"/></a></h3>
        <c:if test="${not empty compound.chebiEntity.definition}">
            <%--<h6><spring:message code="ref.compound.tab.characteristics.definition"/></h6>--%>
            ${compound.chebiEntity.definition}
        </c:if>
        <c:if test="${not empty compound.chebiEntity.iupacNames}">
            <h5>IUPAC Name</h5>
            <c:forEach var="iupacName" items="${compound.chebiEntity.iupacNames}">
                ${iupacName.data}
            </c:forEach>
        </c:if>
        <h5><spring:message code="ref.compound.tab.characteristics.chemicalproperties"/></h5>
        <c:forEach var="formula" items="${compound.chebiEntity.formulae}">
            <spring:message code="ref.compound.tab.characteristics.formula"/> - <span
                id="formulae">${formula.data}</span><br/>
        </c:forEach>
        Average mass - ${compound.chebiEntity.mass}
        <br/>
        <h5><spring:message code="ref.compound.synonyms"/>:</h5>
        <c:forEach var="synonym" items="${compound.chebiEntity.synonyms}">
            <span class="tag">${synonym.data}</span>
        </c:forEach>
        <br/><br/>
        ${compound.chebiEntity.inchi}<br/>

    </section>

    <c:if test="${compound.mc.hasSpecies}">
    <section>
        <h3 class="biology"><a id="biology"><spring:message code="ref.compound.tab.biology"/></a></h3>
        <!-- Found in -->
        <c:forEach var="item" items="${compound.species}">
            <br/>${item.key.species} :
            <c:forEach var="xref" items="${item.value}">
                <c:choose>
                    <c:when test="${xref.crossReference.db.id eq 2}">
                        <a class="tag" href='${xref.crossReference.accession}'>${xref.crossReference.accession}</a>
                    </c:when>
                    <c:otherwise>
                        <a class="tag"
                           href='http://www.ebi.ac.uk/chebi/searchId.do?chebiId=${xref.crossReference.accession}'>${xref.crossReference.accession}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:forEach>
    </section>
    </c:if>
    <c:if test="${compound.mc.hasPathways}">
    <section>
        <!-- Pathways -->
        <h3 class="pathways"><a id="pathways"><spring:message code="ref.compound.tab.pathways"/></a></h3>
        <select id="pathwayList">
            <c:forEach var="pathway" items="${compound.mc.metPathways}">
                <option value="${pathway.id}">${pathway.name}</option>
            </c:forEach>
        </select>

        <div id="pathwayInfo" class="specs"></div>
        <div id="pathwayContainer" height="100%" width="100%">
            <%--<object id="pathwayContainer" height="100%" width="100%" type="image/svg+xml">--%>
            <%--</object>--%>
            <%--<iframe   type="image/svg+xml">--%>
            <%--</iframe>--%>

        </div>
    </section>

    <script>
        currentMTBLC = "${compound.mc.accession}";
        <c:forEach var="pathway" items="${compound.mc.metPathways}" varStatus="pathwayLoopStatus">
            pathwaysInfo.push(
                    {   "id":${pathway.id},
                        "source": "${pathway.database.name}",
                        "species": "${pathway.speciesAssociated.species}",
                        "properties": [
                            <c:forEach var="attribute" items="${pathway.attributes}" varStatus="attributeLoopStatus">
                                <c:if test="${attributeLoopStatus.index gt 0}">,</c:if>
                                {"name": "${attribute.attributeDefinition.name}", "value": "${attribute.value}"}
                            </c:forEach>
                        ]
                    }
                )
        </c:forEach>
    </script>
    </c:if>

    <c:if test="${compound.mc.hasReactions}">
        <section>
            <!-- Reactions -->
            <h3 class="reactions" lazyLoad="reactions?compoundId=${compound.mc.accession}"><a id="reactions"><spring:message code="ref.compound.tab.reactions"/></a></h3>
        </section>
    </c:if>

    <c:if test="${compound.mc.hasNMR}">
    <section>
        <!-- NMR Spectra -->
        <h3 class="nmrSpectra"><a id="nmrSpectra"><spring:message code="ref.compound.tab.nmrspectra"/></a></h3>
        <div class="grid_24 spectraList" id="nmrSpectraList"></div>
        <div id="NMRSpeckTackle" class="grid_24" style="height: 500px"></div>
        <div id="nmrInfo" class="grid_23 specs"></div>
    </section>
    <script>
        <c:forEach var="spectra" items="${compound.mc.metSpectras}" varStatus="spectraLoopStatus">
            <c:if test="${spectra.spectraType == 'NMR'}">
                nmrInfo.push
                (
                    {
                        "id":${spectra.id},
                        "name": "${spectra.name}",
                        "url": "spectra/${spectra.id}/json",
                        "type": "${spectra.spectraType}",
                        "properties":
                            [
                                <c:forEach var="attribute" items="${spectra.attributes}" varStatus="attributeLoopStatus">
                                    <c:if test="${attributeLoopStatus.index gt 0}">,</c:if>
                                    {"name": "${attribute.attributeDefinition.name}", "value": "${attribute.value}"}
                                </c:forEach>
                            ]
                    }
                );
            </c:if>
        </c:forEach>
    </script>
    </c:if>
    <c:if test="${compound.mc.hasMS}">
    <section>
        <!-- MS Spectra -->
        <h3 class="msSpectra"><a id="msSpectra"><spring:message code="ref.compound.tab.msspectra"/></a></h3>
        <div class="grid_24 spectraList" id="msSpectraList"></div>
        <div id="MSSpeckTackle" class="grid_24" style="height: 500px"></div>
        <div id="msInfo" class="grid_23 specs"></div>
    </section>
    <script>
        <c:forEach var="msspectra" items="${compound.mc.metSpectras}" varStatus="spectraLoopStat">
            <c:if test="${msspectra.spectraType == 'MS'}">
                msInfo.push
                (
                    {
                        "id":${msspectra.id},
                        "name": "${msspectra.name}",
                        "url": "spectra/${msspectra.id}/json",
                        "type": "${msspectra.spectraType}",
                        "properties":
                                [
                                    <c:forEach var="attribute" items="${msspectra.attributes}" varStatus="attributeLoopStatus">
                                        <c:if test="${attributeLoopStatus.index gt 0}">,</c:if>
                                        {"name": "${attribute.attributeDefinition.name}", "value": "${attribute.value}"}
                                    </c:forEach>
                                ]
                    }
                );
            </c:if>
        </c:forEach>
    </script>
    </c:if>

    <c:if test="${compound.mc.hasLiterature}">
    <section>
        <!-- Literature -->
        <h3 lazyLoad="citations?compoundId=${compound.mc.accession}" class="literature"><a id="literature"><spring:message code="ref.compound.tab.literature"/></a></h3>
    </section>
    </c:if>
</div>

<div id="slide" class="grid_4 omega">
    <div>
        <img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${compound.mc.chebiId}"/>
        <p>
            <a href="http://www.ebi.ac.uk/chebi/searchId.do?chebiId=${compound.mc.chebiId}">${compound.chebiEntity.chebiAsciiName} - (${compound.mc.chebiId})</a>
        </p>
        <ul>
        <li class="chemistry">
            <a href="#chemistry"><spring:message code="ref.compound.tab.chemistry"/></a>
        </li>
        <c:if test="${compound.mc.hasSpecies}">
            <li class="biology">
                <a href="#biology"><spring:message code="ref.compound.tab.biology"/></a>
            </li>
        </c:if>
        <c:if test="${compound.mc.hasPathways}">
            <li class="pathways">
                <a href="#pathways"><spring:message code="ref.compound.tab.pathways"/></a>
            </li>
        </c:if>
        <c:if test="${compound.mc.hasReactions}">
            <li class="reactions">
                <a href="#reactions"><spring:message code="ref.compound.tab.reactions"/></a>
            </li>
        </c:if>
        <c:if test="${compound.mc.hasNMR}">
            <li class="nmrSpectra">
                <a href="#nmrSpectra"><spring:message code="ref.compound.tab.nmrspectra"/></a>
            </li>
        </c:if>
        <c:if test="${compound.mc.hasMS}">
            <li class="msSpectra">
                <a href="#msSpectra"><spring:message code="ref.compound.tab.msspectra"/></a>
            </li>
        </c:if>
        <c:if test="${compound.mc.hasLiterature}">
            <li class="literature">
                <a href="#literature"><spring:message code="ref.compound.tab.literature"/></a>
            </li>
        </c:if>
    </ul>
    </div>
</div> <%-- End of slide --%>
<br/>