<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2/10/14 4:54 PM
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

<!-- If there isn't any result and it is due to the freetext, filter will not be printed -->

<%-- Facets model

facets[].facet
facets[].facet.name
facets[].facet.lines[]
facets[].facet.lines[].Facetline
facets[].facet.lines[].FacetLine.count
facets[].facet.lines[].FacetLine.value
facets[].facet.lines[].FacetLine.checked

--%>


<%--<c:if test="${!((totalHits==0) && filters.isFilterLoadNeeded)}">--%>

<script>
    function fillAutocomplete(id, availableTags) {

        // this was not catching all the occurrences in the string, just the first one
        // id = id.replace(".", "\\.");
        // lets use regular expressions, instead
        id = id.replace(/\./g, "\\.");

        $("#" + id).autocomplete({
            source: availableTags,
            select: function (event, ui) {
                console.log("select triggered");
                var checkedItem = $("input[value='" + ui.item.value + "']");
                checkedItem.prop("checked", true);
                $("#filterForm").submit();
            }
        });
        // Prevent submitting the form when keypress
        $("#" + id).bind('keypress', function (e) {
            if (e.keyCode == 13) {
                $("input[value='" + $("#" + id).val() + "']").click();
            }
        });
    }
    ;

//    $('body').on('click', '.input', function (e) {
//        if (!$(this).hasClass('active')) {
//            $(this).addClass('active');
//            $(this).attr("checked", "checked");
//            $(this).blur();
//            document.getElementById("filterForm").submit();
//        }
//        else {
//            $(this).removeClass('active');
//            $(this).blur();
//            $(this).removeAttr('checked');
//            $(this).attr("disabled", "disabled");
//            document.getElementById("filterForm").submit();
//        }
//    });

    $( document ).ready(function() {
        var QueryString = function () {
            var query = window.location.search.substring(1);
            return query;
        }();

        if (QueryString !== undefined || QueryString !== ""){
            console.log(QueryString);
            $('#filterForm').attr('action', $('#filterForm').attr('action') + "?" + QueryString);
        }
    });



</script>
<div class="row">
    <form name="searchFilter" id="filterForm" action="${action}" method="post" accept-charset="utf-8">
        <div class="panel panel-default nbr cpanel">
            <div class="panel-heading">
                <h5><spring:message code="label.searchFilter"/></h5>
            </div>
            <div class="panel-body np">
                <c:forEach varStatus="loop"  var="facet" items="${facets}">

                    <c:if test="${facet.name ne 'factors.name'
                && facet.name ne 'users.fullName'
                &&  facet.name ne 'descriptors.description'
                &&  facet.name ne 'compound.hasSpecies'
                &&  facet.name ne 'compound.hasPathways'
                &&  facet.name ne 'compound.hasReactions'
                &&  facet.name ne 'compound.hasNMR'
                &&  facet.name ne 'compound.hasMS'
                }"
                    >

                        <c:if test="${fn:length(facet.lines) gt 1}">
                            <c:set var="caption">
                                <c:choose>
                                    <c:when test="${facet.name=='ObjectType'}"><spring:message code="label.entrytype"/></c:when>
                                    <c:when test="${facet.name=='assays.technology'}"><spring:message
                                            code="label.technology"/></c:when>
                                    <c:when test="${facet.name=='studyStatus'}"><spring:message code="label.facetStatus"/></c:when>
                                    <c:when test="${facet.name=='organism.organismName'}"><spring:message
                                            code="label.organism"/></c:when>
                                    <c:when test="${facet.name=='organism.organismPart'}"><spring:message
                                            code="label.organismPart"/></c:when>
                                    <c:when test="${facet.name=='validations.status'}"><spring:message
                                            code="label.validationsStatus"/></c:when>
                                    <c:when test="${facet.name=='validations.entries.statusExt'}"><spring:message
                                            code="label.validationsEntriesStatusExt"/></c:when>
                                    <c:otherwise>${facet.name}</c:otherwise>
                                </c:choose>
                            </c:set>
                            <c:if test="${facet.name!='validations.entries.statusExt'}">
                                <c:if test="${facet.name!='validations.status'}">
                                <a data-toggle="collapse" href="#filter_${loop.index}" class="facet-category" aria-expanded="false" aria-controls="filter_${loop.index}">
                                    <h5 class="category-heading">
                                            ${caption}
                                        <span class="pull-right text-muted">
                                    <i class="fa fa-lg fa-arrow-circle-o-down"></i>
                                    </span>
                                    </h5>
                                </a>
                                </c:if>
                            </c:if>
                            <div class="${loop.index == 0 ? 'grey' : 'grey collapse'}" id="filter_${loop.index}" >
                                <c:if test="${fn:length(facet.lines) gt 5}">
                                    <div class="ui-widget">
                                        <input
                                                class="inputDiscrete resizable form-control input-sm"
                                                id="autocomplete_${facet.name}"
                                                placeholder="Find ${caption}"
                                        />
                                    </div>
                                    <script>var availableTags = new Array();</script>
                                </c:if>

                                <ul class="filterset" id="${facet.name}">
                                    <c:forEach var="times" begin="0" end="1" step="1">
                                        <c:set var="checkedItems" value="0"/>
                                        <c:forEach var="line" items="${facet.lines}">
                                            <c:if test="${ not empty line.value and line.value != 'NA' and line.value != 'N/A' and line.value !='blank' and line.value !='Blank' }">
                                                <c:if test='${(line.checked and (times == 0)) or (!line.checked and (times == 1))}'>
                                                    <c:if test='${(line.checked and (times == 0))}'>
                                                        <c:set var="checkedItems" value="${checkedItems + 1}"/>
                                                    </c:if>
                                                    <input type="checkbox"
                                                           name="${facet.name}"
                                                           value="${line.value}"
                                                           <c:if test='${line.checked}'>CHECKED</c:if>
                                                           onclick="this.form.submit();">

                                                    <span class="capitalize">
                                                    <c:if test="${line.count<1}"><span class="dimmed">${line.value}</span> </c:if>
                                                    <c:if test="${line.count>0}">${line.value}</c:if>
                                                    <c:if test="${line.value=='compound' && facet.name=='ObjectType'}">
                                                        <c:if test='${line.checked}'>
                                                            <br/>
                                                            <%@include file="compoundFilter.jsp" %>
                                                        </c:if>
                                                    </c:if>
                                                </span>
                                                    <br/>
                                                    <c:if test="${fn:length(facet.lines) gt 5}">
                                                        <script>availableTags.push("${line.value}")</script>
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                    <c:if test="${fn:length(facet.lines) gt 5}">
                                        <script>fillAutocomplete('autocomplete_${facet.name}', availableTags);</script>
                                    </c:if>
                                </ul>
                            </div>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <input type="hidden" name="freeTextQuery" value="${searchResponse.content.query.text}"/>
        <input type="hidden" name="pageNumber" value="1"/>
    </form>
</div>
<%--</c:if>--%>
