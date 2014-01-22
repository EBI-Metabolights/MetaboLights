<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ Last modified: 22/01/14 13:12
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  --%>

<script src="http://d3js.org/d3.v3.min.js"></script>
<script type="text/javascript" src="javascript/dndTree.js"></script>
<style type="text/css">
    .node {
        cursor: pointer;
    }

    .overlay{
        background-color:#EEE;
    }

    .node circle {
        fill: #fff;
        stroke: steelblue;
        stroke-width: 1.5px;
    }

    .node text {
        font-size:10px;
        font-family:sans-serif;
    }

    .link {
        fill: none;
        stroke: #ccc;
        stroke-width: 1.5px;
    }

    .templink {
        fill: none;
        stroke: red;
        stroke-width: 3px;
    }

    .ghostCircle.show{
        display:block;
    }

    .ghostCircle, .activeDrag .ghostCircle{
        display: none;
    }

</style>

	<h2>
    	<spring:message code="menu.speciespageheader" />
    </h2>
    <p>
        <spring:message code="menu.speciespagedescription" />
    </p>
    <div class="grid_8">
        <h3><spring:message code="menu.speciesmodeltitle"/></h3>
        <ul class="species">
            <li class="icon icon-species" data-icon="H"><A href="reference?organisms=Homo sapiens (Human)">Homo sapiens (Human)</a></li>
            <li class="icon icon-species" data-icon="M"><a href="reference?organisms=Mus musculus (Mouse)">Mus musculus (Mouse)</a></li>
            <%--<li class="icon icon-species" data-icon="B"><a href="reference?Arabidopsis thaliana (thale cress)">Arabidopsis thaliana (thale cress)</a></li>--%>
            <%--<li class="icon icon-species" data-icon="L"><a href="reference?organisms=">E. coli</li>--%>
            <li class="icon icon-species" data-icon="Y"><a href="reference?organisms=Saccharomyces cerevisiae">Saccharomyces cerevisiae (Baker's yeast)</a></li>
            <li class="icon icon-species" data-icon="W"><a href="reference?organisms=Caenorhabditis elegans">Caenorhabditis elegans</a></li>
            <%--<li class="icon icon-species" data-icon="F"><a href="reference?organisms=Drosophila">Drosophila</li>--%>
        </ul>
    </div>
    <div class="grid_16">
        <h3><spring:message code="menu.speciesbrowsetitle"/></h3>
        <div id="speciescope"></div>
        <div id="tree-container"></div>
    </div>


