    <%--
      Created by IntelliJ IDEA.
      Authour: tejasvi
      Date: 13/03/13
    --%>

    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
    <%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
    <%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    <%@page contentType="text/html;charset=UTF-8"%>
    <%@page pageEncoding="UTF-8"%>

    <script type="text/javascript" src="javascript/jquery-imtechPager.js"></script>
    <script type="text/javascript" src="javascript/jquery-highlight.js"></script>

    <link rel="stylesheet" href="cssrl/iconfont/font_style.css" type="text/css" />

    <script type="text/javascript">

        function toggle(element) {
            document.getElementById(element).style.display = (document.getElementById(element).style.display == "none") ? "" : "none";
        }

        function navigate(_pageNumber) {
            filterForm = document.forms['filterForm'];
            pageNumberField = filterForm.elements["PageNumber"];
            pageNumberField.value=_pageNumber;
            userActionField = filterForm.elements["userAction"];
            userActionField.value="pageClicked";
            filterForm.submit();
        }

        $("#local-search").attr("action", "reflayersearch");

        $(document).ready(function(){
            $("a[href^='search?']").each(function(){
                this.href = this.href.replace('search?', 'reflayersearch?');
            });
        });

        $('[name="freeTextQuery"]').val('${rffl.freeText}');

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
                            <b>${rffl.MTBLNumOfResults}&nbsp;<spring:message code="ref.msg.searchResult">&nbsp;${rffl.freeText}</spring:message></b>
                        </h6>
                    </c:otherwise>
                </c:choose>
            </div>

            <div>
                <c:if test="${!empty rffl.freeText}">
                    <aside class="grid_6 omega shortcuts expander" id="search-extras">
                        <div id="ebi_search_results">
                            <h3 class="slideToggle icon icon-functional" data-icon="u"><spring:message code="msg.otherebiresults"/></h3>
                        </div>
                    </aside>
                </c:if>
            </div>

        </div>

        <div class="grid_24">
            <br/>
        </div>

        <div class="grid_24">

            <div class="grid_6 alpha">
                <br/>
            </div>

            <div class="grid_18">

                <div class="grid_23">

                    <div class="grid_24 title alpha">

                        <div class="grid_4 aplha">
                            <b>Page&nbsp;:&nbsp;${rffl.currentPage}</b>
                        </div>

                        <div class="grid_20 omega">

                        <span id="pagination" class="right">

                            <%--This code is for the first page and the 'previous' image--%>
                            <c:if test="${rffl.currentPage gt 1}">
                                <a href="#" class="icon icon-functional" data-icon="&lt;" onClick="navigate(${rffl.currentPage - 1})"></a><%--<img ALIGN="texttop" src="img/prev.png" border=0 onClick="navigate(${rffl.currentPage - 1})"></a>--%>
                                <a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${1})"><c:out value="${1}" /></span></a>
                            </c:if>

                            <c:if test="${rffl.currentPage eq 1}">
                                <b>${rffl.currentPage}</b>
                            </c:if>

                            <%--Below code is to make it look like '1 2 3 4 5 .... 561'.--%>
                                <c:if test="${rffl.currentPage lt 5}">
                                    <c:if test="${rffl.totalNumOfPages gt 1}">
                                        <c:if test="${rffl.totalNumOfPages gt 5}">
                                            <c:set var="end" value="5"/>
                                        </c:if>
                                        <c:if test="${rffl.totalNumOfPages eq 5}">
                                            <c:set var="end" value="4"/>
                                        </c:if>
                                        <c:if test="${rffl.totalNumOfPages lt 5}">
                                            <c:set var="end" value="${rffl.totalNumOfPages - 1}"/>
                                        </c:if>

                                        <c:forEach var="page" begin="2" end="${end}" step="1" varStatus="loopStatus">
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
                                </c:if>

                                <c:if test="${rffl.currentPage gt 4 && rffl.currentPage lt rffl.totalNumOfPages}">
                                    ....<a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${rffl.currentPage - 1})"><c:out value="${rffl.currentPage - 1}" /></span></a>
                                    <b>${rffl.currentPage}</b>
                                    <c:if test="${rffl.currentPage lt rffl.totalNumOfPages-1}">
                                        <a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${rffl.currentPage + 1})"><c:out value="${rffl.currentPage + 1}" /></span></a><c:if test="${rffl.currentPage lt rffl.totalNumOfPages-2}">....</c:if>
                                    </c:if>
                                </c:if>

                            <%--This code is for the last page and the 'next' image--%>
                            <c:if test="${rffl.currentPage lt rffl.totalNumOfPages}">
                                <a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${rffl.totalNumOfPages})"><c:out value="${rffl.totalNumOfPages}" /></span></a>
                                <a href="#" class="icon icon-functional" data-icon="&gt;" onClick="navigate(${rffl.currentPage + 1})"></a> <%--<img ALIGN="texttop" src="img/next.png" border=0 onClick="navigate(${rffl.currentPage + 1})">--%>
                            </c:if>
                            <c:if test="${rffl.currentPage gt 1}">
                                <c:if test="${rffl.currentPage eq rffl.totalNumOfPages}">
                                    <c:if test="${rffl.totalNumOfPages gt 4}">
                                        ....<a href="#" style="text-decoration: none"> <span style="font-weight: normal" onClick="navigate(${rffl.currentPage - 1})"><c:out value="${rffl.currentPage - 1}" /></span></a>
                                    </c:if>
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

        </div>

        <div class="grid_24 clearfix" />

        <div class="grid_24">
            <br />
        </div>

        <div class="grid_24">

            <div class="grid_6">

                <form name="Filters" id="filterForm" action="reflayersearch" method="post">

                    <%--technology filter--%>
                    <div class="grid_24 refLayerBox" id="technologyFilter">
                        <b><spring:message code="ref.msg.technology"/></b>

                        <c:forEach var="times" begin="0" end="2" step="1">
                            <c:forEach var="technology" items="${rffl.technologyFacet}">
                                <c:if test="${((technology.value eq 'checked') and times == 0) or ((technology.value eq 'unchecked') and times == 1) or ((technology.value eq 'dimmed') and times == 2)}">
                                    <ul style="max-height: 400px; overflow: auto" id="organisms">
                                        <input type="checkbox"
                                               name="technology"
                                               value="${technology.key}"
                                               <c:if test="${technology.value eq 'checked'}">CHECKED</c:if>
                                               onclick="this.form.submit();">
                                        <c:if test="${technology.value eq 'dimmed'}">
                                            <span class="dimmed">${technology.key}</span>
                                        </c:if>
                                        <c:if test="${technology.value ne 'dimmed'}">
                                            ${technology.key}
                                        </c:if>
                                    </ul>
                                </c:if>
                            </c:forEach>
                        </c:forEach>

                    </div>

                    <br />

                    <%--organism filter--%>
                    <div class="grid_24 refLayerBox" id="organismFilter">
                        <b><spring:message code="ref.msg.organism"/></b>

                        <c:forEach var="times" begin="0" end="2" step="1">

                            <c:forEach var="organism" items="${rffl.organismFacet}">

                                <c:if test="${((organism.value eq 'checked') and times == 0) or ((organism.value eq 'unchecked') and times == 1) or ((organism.value eq 'dimmed') and times == 2)}">

                                    <ul style="max-height: 400px; overflow: auto" id="organisms">

                                        <input type="checkbox"
                                               name="organisms"
                                               value="${organism.key}"
                                               <c:if test="${organism.value eq 'checked'}">CHECKED</c:if>
                                               onclick="this.form.submit();">

                                            <c:if test="${organism.value eq 'dimmed'}">
                                                <span class="dimmed">${organism.key}</span>
                                            </c:if>

                                            <c:if test="${organism.value ne 'dimmed'}">
                                                ${organism.key}
                                            </c:if>
                                    </ul>

                                </c:if>

                            </c:forEach>

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

                    <div class="grid_23 refLayerBox">

                        <c:if test="${fn:contains(entry.accession, 'MTBLS')}">

                            <div class="grid_24">
                                <%--<b><spring:message code="ref.compound.title"/>:&nbsp;</b>--%>
                                <h5 class="summary">${entry.name} (<a href="${entry.accession}">${entry.accession}</a>)</h5>
                                <b><spring:message code="label.releaseDate"/>:&nbsp;</b>
                                ${entry.last_modification_date}<br/><br/>

                                <div class="grid_14 alpha">
                                    <b><spring:message code="label.technology"/></b>
                                    <ul>
                                        <c:forEach var="technology" items="${entry.technology_type}" varStatus="loopStatus">
                                            <li>${technology}</li>
                                        </c:forEach>
                                    </ul>

                                </div>

                                <div class="grid_10 right omega">
                                    <b><spring:message code="label.subm"/></b>
                                        ${entry.submitter}&nbsp;
                                </div>

                                <div style='clear: both;'></div>
                                <b><spring:message code="label.organisms"/></b>

                                <c:forEach var="organism" items="${entry.organism}" varStatus="loopStatus">
                                    <ul>
                                        <li>${organism}</li>
                                    </ul>
                                </c:forEach>


                                <c:if test="${not empty entry.description}">
                                    <b><spring:message code="ref.compound.description"/></b>
                                </c:if>
                                <c:set var="descLen" value="${fn:length(entry.description)}"/>
                                <c:if test="${descLen lt 250}">
                                    ${entry.description}
                                </c:if>
                                <c:if test="${descLen gt 250}">
                                    ${fn:substring(entry.description,0 , 200 )}<a href="javascript:toggle('showCompleteDesc${entry.accession}')" onclick="this.style.display='none';">&nbsp;more...</a><span id="showCompleteDesc${entry.accession}" style="display: none;">${fn:substring(entry.description, 200 , descLen )}</span>
                                </c:if>
                                <br/>
                            </div>

                        </c:if>


                        <c:if test="${not fn:contains(entry.accession, 'MTBLS')}">

                            <div class="grid_6 alpha">

                                <a href="${entry.accession}">
                                    <img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${entry.chebiURL}&amp;dimensions=200&amp;scaleMolecule=true"
                                         onerror="this.src='img/large_noImage.gif';" width="100px"
                                         height="100px" />
                                </a>

                            </div>

                            <div class="grid_18">

                                <span id="icons" class="right noLine">

                                    <c:if test="${entry.hasSpecies eq 1}">
                                        <a href="${entry.accession}"><span class="icon icon-species forSpecies" data-icon="R" title="Species"></span></a>
                                    </c:if>
                                    <%--<c:if test="${entry.hasSpecies eq 0}">--%>
                                        <%--<span class="icon icon-species" data-icon="R"></span>--%>
                                    <%--</c:if>--%>

                                    <c:if test="${entry.hasPathways eq 1}">
                                        <a href="${entry.accession}"><span class="icon icon-conceptual forConceptual" data-icon="y" title="Pathways"></span></a>
                                    </c:if>
                                    <%--<c:if test="${entry.hasPathways eq 0}">--%>
                                        <%--<span class="icon icon-conceptual" data-icon="y"></span>--%>
                                    <%--</c:if>--%>

                                    <c:if test="${entry.hasReactions eq 1}">
                                        <a href="${entry.accession}"><span class="icon icon-chemistry forChemistry" data-icon="R" title="Reactions"></span></a>
                                    </c:if>
                                    <%--<c:if test="${entry.hasReactions eq 0}">--%>
                                        <%--<span class="icon icon-chemistry" data-icon="U"></span>--%>
                                    <%--</c:if>--%>

                                    <c:if test="${entry.hasNMR eq 1}">
                                        <a href="${entry.accession}"><span class="icon2-NMRLogo icon2-biggerFonts" data-icon="*" title="NMR"></span></a>
                                    </c:if>
                                    <%--<c:if test="${entry.hasNMR eq 0}">--%>
                                        <%--<span class="icon2-NMRLogo dimmed" data-icon="*"></span>--%>
                                    <%--</c:if>--%>

                                    <c:if test="${entry.hasMS eq 1}">
                                        <a href="${entry.accession}"><span class="icon2-MSLogo icon2-biggerFonts" data-icon=")" title="MS"></span></a>
                                    </c:if>
                                    <%--<c:if test="${entry.hasMS eq 0}">--%>
                                        <%--<span class="icon2-MSLogo dimmed" data-icon=")"></span>--%>
                                    <%--</c:if>--%>

                                    <c:if test="${entry.hasLiterature eq 1}">
                                        <a href="${entry.accession}"><span class="icon icon-conceptual forConceptual" data-icon="l" title="Literature"></span></a>
                                    </c:if>
                                    <%--<c:if test="${entry.hasLiterature eq 0}">--%>
                                        <%--<span class="icon icon-conceptual" data-icon="l"></span>--%>
                                    <%--</c:if>--%>

                                </span>

                                <div class="grid_24">

                                    <h5>
                                    <%--<b><spring:message code="ref.compound.name"/></b>--%>
                                    ${entry.name} (<a href="${entry.accession}">${entry.accession}</a>)
                                    <%--<c:set var="ChebiSplit" value="${entry.chebiId}"/>--%>
                                    <%--<c:set var="split" value="${fn:split(ChebiSplit, 'CHEBI:')}"/>--%>

                                    <%--<c:forEach var="chebiId" items="${split}" varStatus="loopStatus">--%>
                                        <%--<c:if test="${loopStatus.index ne 0}">--%>
                                            <%--,--%>
                                        <%--</c:if>--%>
                                        <%--<a href='<spring:message code="ref.msg.chebi.url"/>CHEBI:${chebiId}'>CHEBI:${chebiId}</a>--%>
                                    <%--</c:forEach>--%>
                                    </h5>
                                    <c:if test="${not empty entry.description}">
                                        <p>
                                        <%--<b><spring:message code="ref.compound.description"/>:&nbsp</b>--%>
                                        ${entry.description}
                                        </p>
                                    </c:if>
                                    <p>
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
                                    </p>
                                </div>

                            </div>

                        </c:if>

                    </div>

                </c:forEach>

            </div>

        </div>

        <div class="grid_24 ">

            <div class="grid_6 alpha">
                <br/>
            </div>

            <div class="grid_18">

                <div class="grid_22">

                    <div class="grid_24 title alpha">
                        <div class="grid_4">
                            <b>Page:&nbsp;${rffl.currentPage}</b>
                        </div>

                        <div id="paginationBottom" class="grid_20"></div>
                        <script>$('#pagination').clone().appendTo('#paginationBottom');</script>
                    </div>

                </div>

            </div>

            <%--<div class="grid_2 omega">--%>
                <%--<br/>--%>
            <%--</div>--%>

        </div>

    </c:if>

    <c:if test="${rffl.MTBLNumOfResults eq 0}">

        <script>$("body").addClass("noresults")</script>

        <div class="grid_24">
            <div class="grid_15 alpha">

                <h3><spring:message code="msg.nothingFound">&nbsp;<strong>${rffl.freeText}</strong></spring:message></h3>
                <b><spring:message code="ref.msg.noResultSearch"/>&nbsp;<a href="MTBLC15366">Acetic acid</a>, <a href="MTBLC16449">Alanine</a>, <a href="MTBLC27897">Tryptophan</a> and so on...</b>

            </div>

            <c:if test="${!empty rffl.freeText}">

                <aside class="grid_8 omega shortcuts" id="search-extras">
                    <div id="ebi_search_noResults" class="noresults">
                        <h3 class=""><spring:message code="msg.otherebiresults"/></h3>
                    </div>
                </aside>

            </c:if>
        </div>

    </c:if>

    <c:if test="${!empty rffl.freeText}">

        <script src="http://www.ebi.ac.uk/web_guidelines/js/ebi-global-search-run.js"></script>
        <script src="http://www.ebi.ac.uk/web_guidelines/js/ebi-global-search.js"></script>

    </c:if>