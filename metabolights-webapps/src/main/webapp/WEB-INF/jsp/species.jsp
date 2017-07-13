<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2015-Mar-31
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript" src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script type="text/javascript" src="http://d3js.org/d3.v3.min.js"></script>
<script type="text/javascript" src="javascript/dndTree.js"></script>

<script type="text/javascript">

    var speciesAutocomplete = [];

    function configAutocomplete(){
        if(typeof speciesData !== "undefined"){

            //variable exists, do what you want
            extractSpecies(speciesData);

            $("#searchspecies").autocomplete({
                    source:speciesAutocomplete,
                    minLength: 3,
                    select: function(event, ui)
                        {
                            $(location).attr('href', "search?organism.organismName=" + ui.item.value);

                        }
            }).attr('autocomplete','on').attr("z-index", 1000);

            // Add number of species to the tree title
            $("#treeTitle").append(" (" + speciesAutocomplete.length + " species)");

        }
    }

    function extractSpecies(node){

        // For some weird reason children array's name change into _children???
        var children = node.children == undefined? node._children: node.children;

        if (children){

            for (var index in children){
                extractSpecies(children[index]);
            }
        } else {

            speciesAutocomplete.push (node.name);
        }
    }

</script>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-6">
                    <h3>
                        <spring:message code="menu.speciespageheader" />
                    </h3>
                    <p>
                        <spring:message code="menu.speciespagedescription" />
                    </p><br>
                    <h4><spring:message code="menu.speciesTypeSearch"/></h4>
                    <div class="col-md-12">
                        <div class="form-group">
                            <input class="form-control" id="searchspecies" placeholder="Start typing the first 3 letters of the species name">
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h5><spring:message code="menu.speciesmodeltitle"/></h5>
                        </div>
                        <div class="panel-body">
                            <ul class="species">
                                <li class="icon icon-species" data-icon="H"><A href="search?organism.organismName=Homo sapiens">Homo sapiens (Human)</a></li>
                                <li class="icon icon-species" data-icon="M"><a href="search?organism.organismName=Mus musculus">Mus musculus (Mouse)</a></li>
                                <li class="icon icon-species" data-icon="B"><a href="search?organism.organismName=Arabidopsis thaliana">Arabidopsis thaliana (thale cress)</a></li>
                                <li class="icon icon-species" data-icon="L"><a href="search?organism.organismName=Escherichia coli">Escherichia coli</a></li>
                                <li class="icon icon-species" data-icon="Y"><a href="search?organism.organismName=Saccharomyces cerevisiae">Saccharomyces cerevisiae (Baker's yeast)</a></li>
                                <li class="icon icon-species" data-icon="W"><a href="search?organism.organismName=Caenorhabditis elegans">Caenorhabditis elegans</a></li>
                                <%--<li class="icon icon-species" data-icon="F"><a href="search?organisms=Drosophila">Drosophila</li>--%>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-12">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 id="treeTitle"><spring:message code="menu.speciesbrowsetitle"/></h4>
                    </div>
                    <div class="panel-body">
                        <div id="tree-container"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
