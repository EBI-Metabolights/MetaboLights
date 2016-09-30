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

<%--<script type="text/javascript" src="javascript/jquery-imtechPager.js"></script>--%>
<script type="text/javascript" src="javascript/jquery.jqpagination.min.js"></script>
<link rel="stylesheet" href="cssrl/jqpagination.css" type="text/css" />
<script type="text/javascript" src="javascript/jquery-highlight.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-12">
            <h4>
                <%-- Error --%>
                <c:if test="${!empty searcResult.err}">
                    ${searchResponse.message}
                </c:if>

                <c:if test="${empty searcResult.err}">
                    ${sHeader}
                </c:if>
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
                <c:set var="facets" value="${query.facets}"/>
                <script type="text/javascript">
                    $(document).ready(function() {
                        $('.pagination').jqPagination({
                            paged: function (page) {
                                // do something with the page variable
                                filterForm = document.forms['filterForm'];
                                pageNumberField = filterForm.elements["pageNumber"];
                                pageNumberField.value= page;
                                filterForm.submit();

                            },
                            current_page: ${pagination.page},
                            max_page: ${pagecount}
                        });
                    });
                </script>

            </h4>
        </div>
        <c:if test="${!empty searchResponse}">
            <c:if test="${!empty query.text}">
                <aside class="grid_6 omega shortcuts expander right" id="search-extras">
                    <div id="ebi_search_results">
                        <h3 class="slideToggle icon icon-functional" data-icon="u"><spring:message code="msg.otherebiresults"/></h3>
                    </div>
                </aside>
                <script>
                    $("[name='freeTextQuery']").val("${query.text}");
                </script>
            </c:if>
            <div class="col-md-3" id="search-filters">
                <%@include file="searchFilter.jsp" %>
            </div>
            <div class="col-md-9" id="search-results">
                <div class="sub-heading col-md-12">
                        <div class="pull-left">
                            <h4>${pagination.itemsCount}&nbsp;result<c:if test="${pagination.itemsCount ne 1}">s</c:if><%--
                    --%><c:if test="${pagecount gt 1}"><%--
                        --%>,&nbsp;showing ${firstPageItemNumber} to ${lastPageItemNumber}
                                </c:if>
                            </h4>
                        </div>
                        <div class="pull-right">
                            <c:if test="${pagecount gt 1}">
                                <div class="right pagination ml-pagination">
                                    <a href="#" data-action="first">&laquo;</a>
                                    <a href="#" data-action="previous">&lsaquo;</a>
                                    <input type="text" readonly="readonly" />
                                    <a href="#" data-action="next">&rsaquo;</a>
                                    <a href="#" data-action="last">&raquo;</a>
                                </div>
                            </c:if>
                        </div>
                </div>
                <div class="col-md-12" id="highlight-plugin">
                    <c:forEach var="hit" items="${hits}">
                        <%@include file="summaryWrapper.jsp" %>
                    </c:forEach>
                </div>
                <c:if test="${pagecount gt 1}">
                    <div class="sub-heading col-md-12">
                        <div id="paginationBtm"></div>
                    </div>
                    <script>$('.pagination').clone().appendTo('#paginationBtm');</script>
                </c:if>

                <c:if test="${!empty query.text}">
                    <script>
                        $('#highlight-plugin').removeHighlight().highlight('${query.text}');
                    </script>
                </c:if>
                <br/>
            </div>
        </c:if>
    </div>
</div>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<script>
    $(".grey").on('shown.bs.collapse', function ()
    {
        var active = $(this).attr('id');
        $.cookie(active, "1");
        var panels=$.cookie(); //get all cookies
        console.log(panels);
    });

    $(".grey").on('hidden.bs.collapse', function ()
    {
        var active = $(this).attr('id');
        $.removeCookie(active);
        var panels=$.cookie(); //get all cookies
        console.log(panels);
    });

    $(document).ready(function(){
        var panels=$.cookie(); //get all cookies
        console.log(panels);
        for (var panel in panels){ //<-- panel is the name of the cookie
            if ($("#"+panel).hasClass('grey')) // check if this is a panel
            {
                $("#"+panel).collapse("show");
            }
        }
    });
</script>

<c:if test="${!empty query.text}">
    <script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search-run.js"></script>
    <script src="//www.ebi.ac.uk/web_guidelines/js/ebi-global-search.js"></script>
</c:if>