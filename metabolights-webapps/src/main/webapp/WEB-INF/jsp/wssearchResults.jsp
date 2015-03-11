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
                {
                    "name": "assays.measurement"
                },
                {
                    "name":"users.userName"
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
            }
        };

        return emptyQuery;
    };

    // Initialize the pager
    var pager;
    var query = getEmptyQuery();

    document.addEventListener('polymer-ready', function() {

        pager = document.querySelector("page-er");
        pager.perpage = query.pagination.pageSize;

        document.addEventListener('pager-change', function(e) {
            query.pagination.page = e.detail.page+1;
            search(true);
        });

        // Hijack the submit
        $("#local-search").attr("onsubmit", "return searchFromForm()");

        search();

    });

    function searchFromForm(){

        query = getEmptyQuery();
        var searchbox = document.querySelector('#local-searchbox') ;
        query.text= searchbox.value ;
        search();

        return false;

    }

    function search(keepPage){

        if (query.pagination != undefined){
            if (!keepPage) query.pagination.page = 1;
        }

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

            query = data.content.query;
            document.querySelector('search-result').studies = data.content.results;
            document.querySelector('metabolights-facets').query =query;

            document.querySelector('input').value = query.text;
            // Configure pager, fake whole set of data based on item count
            pager.data = new Array(query.pagination.itemsCount);
            $('html, body').animate({ scrollTop: 0 }, 'fast');

            // Hide pager if no results
            pager.hidden = (data.content.results.length == 0)


        }).fail(function() {
            console.info("ajax failed")
        });
    };
</script>

<div horizontal layout>
    <metabolights-facets></metabolights-facets>
    <search-result flex></search-result>
</div>
<%--<nav class="facets">--%>
    <%--<metabolights-facets></metabolights-facets>--%>
<%--</nav>--%>
<%--<section class="search_results">--%>
    <%--<search-result></search-result>--%>
<%--</section>--%>
<page-er perpage="10"></page-er>




<%-- TODO:
 welcome message / searched term
 Highlight
 My studies mode

--%>



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
