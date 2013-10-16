<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ Last modified: 08/10/13 11:33
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2013 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  --%>

<%--
  Created by IntelliJ IDEA.
  User: tejasvi
  Date: 24/09/13
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div style="overflow: auto">
    <table>
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
            <c:forEach var="metaboliteLine" items="${metaboliteAssignment.metaboliteAssignmentLines}" varStatus="loopStatus">
                <c:choose>
                    <c:when test="${not empty metaboliteLine.metaboliteIdentification}">
                        <%--Done using css now<tr class="${(loopStatus.index+blanks) % 2 == 0 ? '' : 'coloured'}">--%>
                        <tr>
                            <td>${metaboliteLine.metaboliteIdentification}(${metaboliteLine.databaseIdentifier})</td>
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
                            <td>${metaboliteLine.database}(${metaboliteLine.databaseVersion})</td>
                            <td>${metaboliteLine.reliability}</td>
                            <td>${metaboliteLine.uri}</td>
                            <td>${metaboliteLine.searchEngine}(${metaboliteLine.searchEngineScore})</td>
                            <td>${metaboliteLine.smallmoleculeAbundanceSub} ${metaboliteLine.smallmoleculeAbundanceStdevSub} ${metaboliteLine.smallmoleculeAbundanceStdErrorSub}</td>
                        </tr>
                    </c:when>
                </c:choose>
            </c:forEach>

        </tbody>
     </table>
</div>
