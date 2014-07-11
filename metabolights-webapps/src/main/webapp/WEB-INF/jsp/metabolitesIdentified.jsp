<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ Last modified: 7/11/14 3:51 PM
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div style="overflow: auto">
    <table class="maf clean display" assay="${assayNumber}">
        <thead class='text_header'>
            <tr>
                <th>Metabolite identification</th>
                <th>Chemical formula</th>
                <th>Species</th>
                <th>Smiles</th>
                <th>InChI</th>
                <th>Chemical Shift</th>
                <th>Multiplicity</th>
                <th>m/z</th>
                <th>Fragmentation</th>
                <th>Modifications</th>
                <th>Charge</th>
                <th>Retention time</th>
                <th>Taxonomy</th>
                <th>Database</th>
                <th>Reliability</th>
                <th>URI</th>
                <th>Search engine</th>
                <th>Abundance</th>

            </tr>
        </thead>
        <tbody>
            <c:forEach var="metaboliteLine" items="${metaboliteAssignment.metaboliteAssignmentLines}" varStatus="loopMetabolites">
                <c:if test="${not empty metaboliteLine.metaboliteIdentification}">
                    <tr>
                        <td>${metaboliteLine.metaboliteIdentification}
                            <c:choose>
                                <c:when test="${empty metaboliteLine.databaseIdentifier}"></c:when>
                                <c:when test="${fn:contains(metaboliteLine.databaseIdentifier,'CHEBI:')}">
                                    <a class="metLink" identifier="${metaboliteLine.databaseIdentifier}" href="http://www.ebi.ac.uk/chebi/searchId.do?chebiId=${metaboliteLine.databaseIdentifier}" target="_blank"> (${metaboliteLine.databaseIdentifier})</a>
                                </c:when>
                                <c:otherwise>(${metaboliteLine.databaseIdentifier})</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${metaboliteLine.chemicalFormula}</td>
                        <td>${metaboliteLine.species}</td>
                        <td>${metaboliteLine.smiles}</td>
                        <td>${metaboliteLine.inchi}</td>
                        <td>${metaboliteLine.chemicalShift}</td>
                        <td>${metaboliteLine.multiplicity}</td>
                        <td>${metaboliteLine.massToCharge}</td>
                        <td>${metaboliteLine.fragmentation}</td>
                        <td>${metaboliteLine.modifications}</td>
                        <td>${metaboliteLine.charge}</td>
                        <td>${metaboliteLine.retentionTime}</td>
                        <td>${metaboliteLine.taxid}</td>
                        <td>${metaboliteLine.database}
                            <c:if test="${not empty metaboliteLine.databaseVersion}">
                                (${metaboliteLine.databaseVersion})
                            </c:if>
                        </td>
                        <td>${metaboliteLine.reliability}</td>
                        <td>${metaboliteLine.uri}</td>
                        <td>${metaboliteLine.searchEngine}
                            <c:if test="${not empty metaboliteLine.searchEngineScore}">
                                (${metaboliteLine.searchEngineScore})
                            </c:if>
                        </td>
                        <td>${metaboliteLine.smallmoleculeAbundanceSub} ${metaboliteLine.smallmoleculeAbundanceStdevSub} ${metaboliteLine.smallmoleculeAbundanceStdErrorSub}</td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
     </table>
</div>

<%--<script language="javascript" type="text/javascript">--%>
<%--//    scanForChebiIdLinks();--%>
<%--</script>--%>
