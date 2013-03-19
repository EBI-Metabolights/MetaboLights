    <%--
      Created by IntelliJ IDEA.
      User: tejasvi
      Date: 13/03/13
    --%>

    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@page contentType="text/html;charset=UTF-8"%>
    <%@page pageEncoding="UTF-8"%>
    <script type="text/javascript" src="javascript/jquery-imtechPager.js"></script>
    <script type="text/javascript" src="javascript/jquery-highlight.js"></script>

    <script type="text/javascript">
        function navigate(_pageNumber) {
            filterForm = document.forms['filterForm'];
            pageNumberField = filterForm.elements["PageNumber"];
            pageNumberField.value=_pageNumber;
            userActionField = filterForm.elements["userAction"];
            userActionField.value="pageClicked";
            filterForm.submit();
        }

        $("#local-search").attr("action", "refLayerSearch");

        $(document).ready(function(){
            $("a[href^='search?']").each(function(){
                this.href = this.href.replace('search?', 'refLayerSearch?');
            });
        });

    </script>

    <c:if test="${rffl.MTBLNumOfResults ne 0}">
        <div class="grid_24">

            <div class="grid_6 alpha">
                <h6><b><spring:message code="ref.msg.filterResults"></spring:message></b></h6>
            </div>

            <%--Below code displays message about the number of results found for the query--%>
            <div class="grid_12">
                <c:choose>
                    <c:when test="${empty rffl.freeText}">
                        <h6>
                            <b>${rffl.MTBLNumOfResults}&nbsp;<spring:message code="ref.msg.emptyBrowse"/></b>
                        </h6>
                    </c:when>
                    <c:otherwise>
                        <h6>
                            <b>${rffl.MTBLNumOfResults} <spring:message code="ref.msg.searchResult">${rffl.freeText}</spring:message></b>
                        </h6>
                    </c:otherwise>
                </c:choose>
            </div>

            <div>
                <c:if test="${not empty rffl.freeText}">
                    <aside class="grid_6 omega shortcuts expander" id="search-extras">
                        <div id="ebi_search_results">
                            <h3 class="slideToggle icon icon-functional" data-icon="u"><spring:message code="msg.otherebiresults"/></h3>
                        </div>
                    </aside>
                </c:if>
            </div>

        </div>

        <div class="grid_24">

            <div class="grid_6 alpha">
                <br/>
            </div>

            <div class="grid_16">

                <div class="grid_24 title alpha">

                    <div class="grid_4 aplha">
                        <b>Page&nbsp;:&nbsp;${rffl.currentPage}</b>
                    </div>

                    <div class="grid_20 omega">

                        <span id="pagination" class="right">

                            <%--This code is for the first page and the 'previous' image--%>
                            <c:if test="${rffl.currentPage gt 1}">
                                <a href="#"><img ALIGN="texttop" src="img/prev.png" border=0 onClick="navigate(${rffl.currentPage - 1})"></a>
                                <a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${1})"><c:out value="${1}" /></span></a>
                            </c:if>

                            <c:if test="${rffl.currentPage eq 1}">
                                <b>${rffl.currentPage}</b>
                            </c:if>

                            <%--Below code is to make it look like '1 2 3 4 5 .... 561'.--%>
                                <c:if test="${rffl.currentPage lt 5}">

                                    <c:forEach var="page" begin="2" end="5" step="1" varStatus="loopStatus">
                                        <c:if test="${rffl.currentPage eq page}">
                                            <b>${page}</b>
                                        </c:if>
                                        <c:if test="${rffl.currentPage ne page}">
                                            <a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${page})"><c:out value="${page}" /></span></a>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${rffl.totalNumOfPages gt 6}">
                                        ....
                                    </c:if>
                                </c:if>

                                <c:if test="${rffl.currentPage gt 4 && rffl.currentPage lt rffl.totalNumOfPages}">
                                    .....<a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${rffl.currentPage - 1})"><c:out value="${rffl.currentPage - 1}" /></span></a>
                                    <b>${rffl.currentPage}</b>
                                    <c:if test="${rffl.currentPage lt rffl.totalNumOfPages-1}">
                                        <a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${rffl.currentPage + 1})"><c:out value="${rffl.currentPage + 1}" /></span></a><c:if test="${rffl.currentPage lt rffl.totalNumOfPages-2}">.....</c:if>
                                    </c:if>
                                </c:if>

                            <%--This code is for the last page and the 'next' image--%>
                            <c:if test="${rffl.currentPage lt rffl.totalNumOfPages}">
                                <a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${rffl.totalNumOfPages})"><c:out value="${rffl.totalNumOfPages}" /></span></a>
                                <a href="#"><img ALIGN="texttop" src="img/next.png" border=0 onClick="navigate(${rffl.currentPage + 1})"></a>
                            </c:if>
                            <c:if test="${rffl.currentPage gt 1}">
                                <c:if test="${rffl.currentPage eq rffl.totalNumOfPages}">
                                    .....<a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${rffl.currentPage - 1})"><c:out value="${rffl.currentPage - 1}" /></span></a>
                                    <b>${rffl.totalNumOfPages}</b>
                                </c:if>
                            </c:if>

                        </span>

                        <span id="showingInfo" class="center">
                            <c:if test="${rffl.currentPage eq rffl.totalNumOfPages}">
                                <b>Showing results ${((rffl.currentPage * 10) - 10) +1} to ${(rffl.MTBLNumOfResults)}</b>
                            </c:if>
                            <c:if test="${rffl.currentPage ne rffl.totalNumOfPages}">
                                <b>Showing results ${((rffl.currentPage * 10) - 10) +1} to ${(rffl.currentPage * 10)}</b>
                            </c:if>
                        </span>

                    </div>

                </div>

            </div>

        </div>

        <div class="grid_24 clearfix" />

        <div class="grid_24">
            <br />
        </div>

        <div class="grid_24">

            <div class="grid_6">

                <form name="Filters" id="filterForm" action="refLayerSearch" method="post">

                    <%--technology filter--%>
                    <div class="grid_24 refLayerBox" id="technologyFilter">
                        <b><spring:message code="ref.msg.technology"/></b>

                        <c:forEach var="technology" items="${rffl.technologyFacet}">
                            <ul style="max-height: 400px; overflow: auto" id="organisms">
                                <input type="checkbox"
                                       name="technology"
                                       value="${technology.key}"
                                       <c:if test="${technology.value eq 'checked'}">CHECKED</c:if>
                                       onclick="this.form.submit();">
                                    ${technology.key}
                            </ul>
                        </c:forEach>

                    </div>

                    <br />

                    <%--organism filter--%>
                    <div class="grid_24 refLayerBox" id="organismFilter">
                        <b><spring:message code="ref.msg.organism"/></b>

                        <c:forEach var="organism" items="${rffl.organismFacet}">
                            <ul style="max-height: 400px; overflow: auto" id="organisms">
                                <input type="checkbox"
                                       name="organisms"
                                       value="${organism.key}"
                                       <c:if test="${organism.value eq 'checked'}">CHECKED</c:if>
                                       onclick="this.form.submit();">
                                    ${organism.key}
                            </ul>
                        </c:forEach>

                    </div>

                    <input type="hidden" name="freeTextQuery" value="<c:out value="${rffl.freeText}"/>" />
                    <input type="hidden" name="PageNumber" value="1" />
                    <input type="hidden" name="userAction" value="facetClicked"/>

                </form>

            </div>

            <div class="grid_18">

                <div style='clear: both;'></div>

                <c:forEach var="entry" items="${entries}">

                    <div class="grid_22 refLayerBox">
                        <div class="grid_8 alpha">
                            <a href="${entry.accession}"><img
                                    src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${entry.chebiURL}&amp;dimensions=200&amp;scaleMolecule=true"
                                    onerror="this.src='img/large_noImage.gif';" width="100px"
                                    height="100px" /></a>
                        </div>
                        <div class="grid_14">
                            <div class="grid_24">
                                <b><spring:message code="ref.compound.name"/></b> ${entry.name}
                                (<a href="${entry.accession}">${entry.accession}</a>)
                                <a href='<spring:message code="ref.msg.chebi.url"></spring:message>${entry.chebiURL}'>${entry.chebiId}</a>
                            </div>
                        <br />
                        <br />
                        <div class="grid_24">
                            <c:if test="${not empty entry.description}">
                                <b><spring:message code="ref.compound.description"/></b>${entry.description}
                            </c:if>
                        </div>
                        <br /> <br /> <br />
                        <div class="grid_24">
                            <c:forEach var="MTBLStudiesList" items="${entry.MTBLStudies}"
                                       varStatus="loopStatus">
                                <c:choose>
                                    <c:when test="${loopStatus.index eq 0}">
                                        <b><spring:message code="ref.msg.mtbl.studies" /></b>
                                    </c:when>
                                    <c:otherwise>
                                        ,
                                    </c:otherwise>
                                </c:choose>
                                <a href="${MTBLStudiesList}">${MTBLStudiesList}</a>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                </c:forEach>
            </div>

        </div>

    </c:if>

    <c:if test="${rffl.MTBLNumOfResults eq 0}">
        <div class="grid_12">
            <h3><spring:message code="ref.msg.noResult">${rffl.freeText}</spring:message></h3>
            <b><spring:message code="ref.msg.noResultSearch"/>&nbsp;<a href="MTBLC1358">Acetic acid</a>, <a href="MTBLC1402">Alanine</a>, <a href="MTBLC1547">Benzene</a> and so on...</b>
        </div>
        <aside class="grid_8 omega shortcuts" id="search-extras">
            <div id="ebi_search_noResults" class="noresults">
                <h3 class=""><spring:message code="msg.otherebiresults"/></h3>
            </div>
        </aside>
    </c:if>

    <c:if test="${!empty rffl.freeText}">
        <script src="http://www.ebi.ac.uk/web_guidelines/js/ebi-global-search-run.js"></script>
        <script src="http://www.ebi.ac.uk/web_guidelines/js/ebi-global-search.js"></script>
    </c:if>