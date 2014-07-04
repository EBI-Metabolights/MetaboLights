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
  ~ Last modified: 7/4/14 9:00 AM
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
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

<div id="deletedialog" title=<spring:message code="msg.deleteStudyDialog.title"/> >
    <spring:message code="msg.deleteStudyDialog.body"/>
</div>

<section class="grid_18 alpha">
    <h2>
        <c:if test="${!empty userQueryClean}">
            <spring:message code="msg.searchedInfo"/> <span class="searchterm">${userQueryClean}</span>
        </c:if>
        <c:if test="${empty userQueryClean and empty welcomemessage}">
            <spring:message code="msg.browsingInfo"/>
        </c:if>
    </h2>
</section>

<c:if test="${!empty searchResults}">
    <c:if test="${!empty userQueryClean}">
        <aside class="grid_6 omega shortcuts expander" id="search-extras">
            <div id="ebi_search_results">
                <h3 class="slideToggle icon icon-functional" data-icon="u"><spring:message code="msg.otherebiresults"/></h3>
            </div>
        </aside>
    </c:if>
    <section class="grid_18 push_6" id="search-results">
        <c:if test="${!empty welcomemessage}">
            <div class="topSpacer"></div>
        </c:if>
        <section class="grid_23 title alpha omega" >
            <div class="grid_12">
                <strong>
                    <c:if test="${empty welcomemessage}"> <!-- Not show this part if called from "my submissions" -->
                        ${totalHits}&nbsp;<spring:message code="msg.searchResults"/>&nbsp;
                    </c:if>
                    <c:if test="${totalHits gt 1}">
                        <spring:message code="msg.showing"/>&nbsp;${1+((pageNumber-1)*pageSize)}&nbsp;<spring:message code="msg.to"/>&nbsp;
                        <c:if test="${((pageNumber-1)*pageSize)+pageSize lt totalHits }">
                            ${((pageNumber-1)*pageSize)+pageSize}
                        </c:if>
                        <c:if test="${((pageNumber-1)*pageSize)+pageSize ge totalHits }">
                            ${totalHits}
                        </c:if>
                    </c:if>
                    <c:if test="${!empty welcomemessage}"> <!-- Show this part if called from "my submissions" -->
                        of ${totalHits} <spring:message code="msg.studies" />
                    </c:if>
                </strong>
            </div>
            <div class="grid_11 omega">
                <span id="pagination" class="right">
                <c:if test="${pageNumber ne 1}">
                    <a href="#"><img ALIGN="texttop" src="img/prev.png" border=0 onClick="navigate(${pageNumber-1})" ></a>
                </c:if>
                <c:if test="${totalNumberOfPages > 1}">
                    <c:forEach var="i" begin="${pagerLeft}" end="${pagerRight}" step="1" varStatus ="status">
                        <c:if test="${pageNumber eq (i)}">
                            <b><c:out value="${i}"/></b>&nbsp;
                        </c:if>
                        <c:if test="${pageNumber ne (i)}">
                            <a href="#" style="text-decoration:none" > <span style="font-weight:normal" onClick="navigate(${i})"><c:out value="${i}"/></span></a>&nbsp;
                        </c:if>
                    </c:forEach>
                </c:if>
                <c:if test="${(((pageNumber-1)*pageSize)+pageSize) lt totalHits}">
                    <a href="#"><img ALIGN="texttop" src="img/next.png" border=0 onClick="navigate(${pageNumber+1})" ></a>
                </c:if>
                </span>
            </div>
        </section>
        <br/>

        <!-- curators can make a study private -->
        <sec:authorize ifAnyGranted="ROLE_SUPER_USER">
            <c:set var="curator" value="true"/>
        </sec:authorize>

        <!-- reviewers can not change a study -->
        <sec:authorize ifAnyGranted="ROLE_REVIEWER">
            <c:set var="reviewer" value="true"/>
        </sec:authorize>

        <div class="grid_23 alpha omega" id="highlight-plugin">
            <c:forEach var="searchResult" items="${searchResults}">
                <%@include file="entrySummary.jsp" %>
            </c:forEach>
        </div>

        <br/>

        <div id="paginationBottom" class="grid_23 title alpha" ></div>
        <script>$('#pagination').clone().appendTo('#paginationBottom');</script>

        <c:if test="${!empty userQueryClean}">
            <script>
                $('#highlight-plugin').removeHighlight().highlight('${userQueryClean}');
            </script>
        </c:if>
        <br/>
    </section>

    <section class="grid_6 pull_18 alpha" id="search-filters">
        <%@include file="searchFilter.jsp" %>
    </section>
</c:if>

<c:if test="${empty searchResults}">
    <script>$("body").addClass("noresults")</script>
    <section class="grid_16 alpha">
        <h4>
            <c:if test="${!empty welcomemessage}"> <div style="padding-left:0px"><spring:message code="msg.nothingFoundPersonal" /></div></c:if>
            <c:if test="${empty welcomemessage}">
                <br />
                <br />
                <spring:message code="msg.nothingFound" />&nbsp;<spring:message code="msg.searchSuggestions" />
            </c:if>
        </h4>
        <br />
    </section>
    <c:if test="${!empty userQueryClean}">

        <aside class="grid_8 omega shortcuts" id="search-extras">
            <div id="ebi_search_results" class="noresults">
                <h3 class=""><spring:message code="msg.otherebiresults"/></h3>
            </div>
        </aside>
    </c:if>

</c:if>

<c:if test="${!empty userQueryClean}">
    <script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search-run.js"></script>
    <script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search.js"></script>
</c:if>

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
