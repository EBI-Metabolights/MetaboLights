<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2014-Aug-21
  ~ Modified by:   conesa
  ~
  ~
  ~ Copyright 2015 EMBL-European Bioinformatics Institute.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>


<script src="polymer/bower_components/webcomponentsjs/webcomponents.js"></script>
<%--<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>--%>
<link rel="import" href="elements/study_search_result.html">
<link rel="import" href="elements/metabolights_facets.html">
<link rel="import" href="polymer/bower_components/page-er/page-er.html">
<%--<link rel="stylesheet" href="polymer/bower_components/page-er/page-er.css">--%>
<%--<link rel="stylesheet" href="http://addyosmani.github.io/page-er/components/page-er/demos/demos.css">--%>
<link rel="stylesheet" href="elements/study_search.css">
<script>
    // Initial query object to send to the search request
    function getEmptyQuery(){
        var emptyQuery = {
            "text":"",
            "facets":[
                {
                    "name": "assays.technology"
                },
                {
                    "name": "studyStatus"
                },
                {
                    "name": "organism.organismName"
                },
                {
                    "name": "organism.organismPart"
                },
//                {
//                    "name": "assays.measurement"
//                },
                {
                    "name":"users.fullName"
                    <c:if test="${not empty usersFullName}">
                        ,"lines":[{"value":"${usersFullName}","checked":true}]
                    </c:if>
                },
                {
                    "name":"factors.name"
                },
                {
                    "name":"descriptors.description"
                }
            ],
            "pagination":{
                "page":1,
                "pageSize":10
            },
            "boosters":[
                {
                    "fieldName": "title",
                    "boost": 1
                }
            ]
        };


        <c:if test="${not empty freeTextQuery}">
            emptyQuery.text = "${freeTextQuery}";
        </c:if>

        return emptyQuery;
    };


    function getQuery() {

        // Get the hash...
        var hash = window.location.hash;

        if (hash){

            // Remove the hash
            hash = hash.substring(1);

            return JSON.parse(hash);

        } else {
            //If there is something
            return getEmptyQuery();
        }

    };

    // Initialize the pager
    var pager;
    var query = getQuery();
    var asideText;
    var searchActive = false;

    document.addEventListener('polymer-ready', function() {

        pager = document.querySelector("page-er");
        pager.perpage = query.pagination.pageSize;

        asideText = document.querySelector("#search-extras-info");

        document.addEventListener('pager-change', function(e) {
            query.pagination.page = e.detail.page+1;
            search(true);
        });

        // Hijack the submit
        $("#local-search").attr("onsubmit", "return searchFromForm()");

        search();

    });

    window.onpopstate = function(event) {
        query = getQuery();
        search(true);
    };


    function searchFromForm(){

        query = getEmptyQuery();
        var searchbox = document.querySelector('#local-searchbox') ;
        query.text= searchbox.value ;
        search();

        return false;

    };

    function cleanQuery(){

        for (var f = 0; f<query.facets.length; f++){

            var facet = query.facets[f];

            if (facet.lines !== undefined){
                for (var l=facet.lines.length-1;l>=0;l--){
                    if (!facet.lines[l].checked){
                        facet.lines.splice(l,1);
                    }
                }
            }
        }

    };


    function writeQueryToUrl(){
        var queryS = JSON.stringify(query);
        window.location.hash = queryS;
    };

    function search(keepPage){


        if (searchActive) {
            return;
        } else {

            // Indicate we are searching...
            searchActive = true;

        }


        if (query.pagination != undefined){
            if (!keepPage) query.pagination.page = 1;
        }

        cleanQuery();
        writeQueryToUrl();

        $.ajax({
            beforeSend: function(xhrObj){
                xhrObj.setRequestHeader("Content-Type","application/json");
                xhrObj.setRequestHeader("Accept","application/json");
            },
            url: "/metabolights/webservice/search",
            headers: {"user_token": "${user_token}"},
            type: "POST",
            dataType: "json",
            data: JSON.stringify(query)

        }).done(function(data) {

            var results = document.querySelector('search-result');
            var facets = document.querySelector('metabolights-facets');

            if (data.err != undefined){
                pager.hidden = true;
                facets.hidden = true;
                results.noresultmessage = data.message;
            }

            results.searchresponse = data.content;

            query = data.content.query;


            var input = document.querySelector('input');

            if (input != undefined) input.value = query.text;

            $('html, body').animate({ scrollTop: 0 }, 'fast');

            // Hide element when no if no results
            if (data.content.results.length == 0) {

                pager.hidden = true;
                facets.hidden = true;

            } else {

                // Configure pager, fake whole set of data based on item count
                pager.data = new Array(query.pagination.itemsCount);
                pager.changePage(query.pagination.page-1);
                pager.hidden = query.pagination.itemsCount <= query.pagination.pageSize;

                facets.query =query
                facets.hidden = false;

            }

            // If there is any text
            if (query.text != ""){
                asideText.innerText = 'Other results for "' + query.text + '"';
                asideText.parentElement.parentElement.hidden = false;

            } else {
                asideText.parentElement.parentElement.hidden = true;
            }

            results.hidden=false;

            searchActive = false;

        }).fail(function() {
            console.info("ajax failed, can't search.");
            searchActive = false;

        });


    };
</script>

<aside class="grid_6 omega shortcuts expander" id="search-extras">
    <div id="ebi_search_results">
        <h3 class="slideToggle icon icon-functional" data-icon="u">Show more data from EMBL-EBI</h3><p id="search-extras-info" style="display: none;">Other results for "tom"</p><ul id="global-search-results" style="display: none;"><li><a href="/ebisearch/search.ebi?db=allebi&amp;t=tom">All results (1,040,623)</a></li><li><a href="/ebisearch/search.ebi?db=genomes&amp;t=tom">Genomes (167)</a></li><li><a href="/ebisearch/search.ebi?db=nucleotideSequences&amp;t=tom">Nucleotide sequences (995,157)</a></li><li><a href="/ebisearch/search.ebi?db=proteinSequences&amp;t=tom">Protein sequences (5,533)</a></li><li><a href="/ebisearch/search.ebi?db=macromolecularStructures&amp;t=tom">Macromolecular structures (50)</a></li><li><a href="/ebisearch/search.ebi?db=smallMolecules&amp;t=tom">Small molecules (1)</a></li><li><a href="/ebisearch/search.ebi?db=geneExpression&amp;t=tom">Gene expression (232)</a></li><li><a href="/ebisearch/search.ebi?db=molecularInteractions&amp;t=tom">Molecular interactions (24)</a></li><li><a href="/ebisearch/search.ebi?db=reactionsPathways&amp;t=tom">Reactions, pathways &amp; diseases (80)</a></li><li><a href="/ebisearch/search.ebi?db=proteinFamilies&amp;t=tom">Protein families (20)</a></li><li><a href="/ebisearch/search.ebi?db=proteinExpressionData&amp;t=tom">Protein expression data (0)</a></li><li><a href="/ebisearch/search.ebi?db=enzymes&amp;t=tom">Enzymes (0)</a></li><li><a href="/ebisearch/search.ebi?db=literature&amp;t=tom">Literature (38,552)</a></li><li><a href="/ebisearch/search.ebi?db=ontologies&amp;t=tom">Samples &amp; ontologies (764)</a></li><li><a href="/ebisearch/search.ebi?db=ebiweb&amp;t=tom">EBI web (43)</a></li></ul>
    </div>
</aside>
<div layout horizontal>
    <metabolights-facets auto-vertical hidden></metabolights-facets>
    <search-result auto-vertical hidden header="${sHeader}" noresultmessage='${noresultsmessage}'></search-result>
</div>
<page-er hidden perpage="10"></page-er>


<script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search-run.js"></script>
<script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search.js"></script>


<%--TODO EBI search integration

    <c:if test="${!empty userQueryClean}">

        <aside class="grid_8 omega shortcuts" id="search-extras">
            <div id="ebi_search_results" class="noresults">
                <h3 class=""><spring:message code="msg.otherebiresults"/></h3>
            </div>
        </aside>
    </c:if>


<c:if test="${!empty userQueryClean}">
    <script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search-run.js"></script>
    <script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search.js"></script>
</c:if>
--%>
<%--TODO Delete confirmation
<script type="text/javascript">
    $(document).ready(function() {
        $("#deletedialog").dialog({
            autoOpen: false,
            modal: true
        });

        $(".confirmLink").click(function(e) {
            e.preventDefault();
            var targetUrl = $(this).attr("href");

            $("#deletedialog").dialog({
                buttons : {
                    "Confirm" : function() {
                        window.location.href = targetUrl;
                    },
                    "Cancel" : function() {
                        $(this).dialog("close");
                    }
                }
            });

            $("#deletedialog").dialog("open");
        });

    });

</script>
--%>