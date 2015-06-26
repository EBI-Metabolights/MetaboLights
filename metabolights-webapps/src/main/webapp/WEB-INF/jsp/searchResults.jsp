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
  ~ Last modified: 7/8/14 4:27 PM
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

<script type="text/javascript" src="javascript/jquery-imtechPager.js"></script>
<script type="text/javascript" src="javascript/jquery-highlight.js"></script>
<script type="text/javascript">
    function navigate(_pageNumber) {
        filterForm = document.forms['filterForm'];
        pageNumberField = filterForm.elements["pageNumber"];
        pageNumberField.value=_pageNumber;
        filterForm.submit();
    }
</script>

<h2>
    <%-- Error --%>
    <c:if test="${!empty searcResult.err}">
        ${searchResponse.message}
    </c:if>

    <c:if test="${empty searcResult.err}">
        ${sHeader}
    </c:if>
</h2>

<%-- Model

 searchResponse.query
 searchResponse.query.text
 searchResponse.query.pagination
 searchResponse.query.pagination.page
 searchResponse.query.pagination.itemsCount
 searchResponse.query.pagination.pageSize
 searchResponse.query.pagination.getFirstPageItemNumber
 searchResponse.query.pagination.getLastPageItemNumber
 searchResponse.query.pagination.getPageCount
 searchResponse.query.boosters...
 searchResponse.query.facets

 searchResponse.results []

--%>

<c:set var="query" value="${searchResponse.content.query}"/>
<c:set var="pagination" value="${query.pagination}"/>
<c:set var="hits" value="${searchResponse.content.results}"/>
<c:set var="hits" value="${searchResponse.content.results}"/>
<c:set var="facets" value="${query.facets}"/>

<c:if test="${!empty searchResponse}">
    <c:if test="${!empty query.text}">
        <aside class="grid_6 omega shortcuts expander" id="search-extras">
            <div id="ebi_search_results">
                <h3 class="slideToggle icon icon-functional" data-icon="u"><spring:message code="msg.otherebiresults"/></h3>
            </div>
        </aside>
    </c:if>
    <section class="grid_18 push_6" id="search-results">
        <%--<div class="topSpacer"></div>--%>
        <section class="grid_23 title alpha omega" >
            <div class="grid_12">
                <strong>${pagination.itemsCount}&nbsp;results&nbsp;showing to </strong>
                <%--<strong>${pagination.itemsCount}&nbsp;results&nbsp;showing ${pagination.getFirstPageItemNumber} to ${pagination.getLastPageItemNumber}</strong>--%>
            </div>
            <div class="grid_11 omega">
                <span id="pagination" class="right">
                <c:if test="${pagination.page ne 1}">
                    <a href="#"><img ALIGN="texttop" src="img/prev.png" border=0 onClick="navigate(${pageNumber-1})" ></a>
                </c:if>
                <b><c:out value="${pagination.page}"/></b>&nbsp;
                <c:if test="${pagination.page lt pagecount}">
                    <a href="#"><img ALIGN="texttop" src="img/next.png" border=0 onClick="navigate(${pageNumber+1})" ></a>
                </c:if>
                </span>
            </div>
        </section>
        <br/>

        <div class="grid_23 alpha omega" id="highlight-plugin">
            <c:forEach var="liteStudy" items="${hits}">
                <%@include file="studySummary.jsp" %>
            </c:forEach>
        </div>

        <br/>

        <div id="paginationBottom" class="grid_23 title alpha" ></div>
        <script>$('#pagination').clone().appendTo('#paginationBottom');</script>

        <c:if test="${!empty query.text}">
            <script>
                $('#highlight-plugin').removeHighlight().highlight('${query.text}');
            </script>
        </c:if>
        <br/>
    </section>

    <section class="grid_6 pull_18 alpha" id="search-filters">
        <%@include file="searchFilter.jsp" %>
    </section>
</c:if>

<%--<c:if test="${empty searchResponses}">--%>
    <%--<script>$("body").addClass("noresults")</script>--%>
    <%--<section class="grid_16 alpha">--%>
        <%--<h4>--%>
            <%--<c:if test="${!empty welcomemessage}"> <div style="padding-left:0px"><spring:message code="msg.nothingFoundPersonal" /></div></c:if>--%>
            <%--<c:if test="${empty welcomemessage}">--%>
                <%--<br />--%>
                <%--<br />--%>
                <%--<spring:message code="msg.nothingFound" />&nbsp;<spring:message code="msg.searchSuggestions" />--%>
            <%--</c:if>--%>
        <%--</h4>--%>
        <%--<br />--%>
    <%--</section>--%>
    <%--<c:if test="${!empty userQueryClean}">--%>

        <%--<aside class="grid_8 omega shortcuts" id="search-extras">--%>
            <%--<div id="ebi_search_results" class="noresults">--%>
                <%--<h3 class=""><spring:message code="msg.otherebiresults"/></h3>--%>
            <%--</div>--%>
        <%--</aside>--%>
    <%--</c:if>--%>

<%--</c:if>--%>

<c:if test="${!empty query.text}">
    <script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search-run.js"></script>
    <script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search.js"></script>
</c:if>