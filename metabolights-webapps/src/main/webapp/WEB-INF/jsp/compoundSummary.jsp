<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>

<%--<script type="text/javascript" src="javascript/jquery-imtechPager.js"></script>--%>
<link rel="stylesheet" href="cssrl/iconfont/font_style.css" type="text/css" />


<div class="panel panel-default cpanel nbr">
    <div class="panel-heading pb10 nbr">
        <div class="row">
            <h4>
                <div class="col-md-9">
                    <a class="capitalize" href="${compound.accession}">${compound.name}</a>
                </div>
                <div class="col-md-3">
                    <div>
                        <span class="pull-right">
                        <div class="btn-group" role="group" aria-label="...">
                            <c:if test="${compound.hasSpecies}">
                                <a href="${pageContext.request.contextPath}/${compound.accession}#biology" type="button" class="btn btn-default btn-xs"><span class="icon icon-species forSpecies" data-icon="R" title="Species"></span></a>
                            </c:if>
                                <c:if test="${compound.hasPathways}">
                                    <a href="${pageContext.request.contextPath}/${compound.accession}#pathways" type="button" class="btn btn-default btn-xs"><span class="icon icon-conceptual forConceptual" data-icon="y" title="Pathways"></span></a>
                                </c:if>

                                <c:if test="${compound.hasReactions}">
                                    <a href="${pageContext.request.contextPath}/${compound.accession}#reactions" type="button" class="btn btn-default btn-xs"><span class="icon icon-chemistry forChemistry" data-icon="R" title="Reactions"></span></a>
                                </c:if>

                                <c:if test="${compound.hasNMR}">
                                    <a href="${pageContext.request.contextPath}/${compound.accession}#NMR" type="button" class="btn btn-default btn-xs"><span class="icon2-NMRLogo" data-icon="*" title="NMR"></span></a>
                                </c:if>

                                <c:if test="${compound.hasMS}">
                                    <a href="${pageContext.request.contextPath}/${compound.accession}#MS" type="button" class="btn btn-default btn-xs"><span class="icon2-MSLogo" data-icon=")" title="MS"></span></a>
                                </c:if>

                                <c:if test="${compound.hasLiterature}">
                                    <a href="${pageContext.request.contextPath}/${compound.accession}#literature" type="button" class="btn btn-default btn-xs"><span class="icon icon-conceptual forConceptual" data-icon="l" title="Literature"></span></a>
                                </c:if>
                            </div>
                        </span>
                    </div>
                </div>
            </h4>
        </div>
    </div>
    <div class="panel-body">
        <div class="col-md-3">
            <div class="compound-thumbnail">
                <a href="${compound.accession}">
                    <img class="media-object img-responsive" src="//www.ebi.ac.uk/chebi/backend/api/public/compound/${fn:replace(compound.accession, "MTBLC", "")}/structure/?width=300&height=300"
                         onerror="this.src='img/large_noImage.gif';"/>
                </a>
            </div>
        </div>
        <div class="col-md-9">
            <div class="row">
                <h4 class="blpl">
                    <p>
                        <span class="compound-label">COMPOUND ACCESSION</span>
                        <br>
                        <span class="label label-default">${compound.accession}</span>
                    </p><br>
                    <c:if test="${not empty compound.description}">
                        <p>
                            <span class="compound-label">DESCRIPTION</span><br>
                            <small>
                                    ${compound.description}
                            </small>
                        </p><br>
                    </c:if>
                    <c:if test="${not empty compound.chebiId}">
                    <p class="">
                    <b><spring:message code="ref.msg.mtbl.chebiId" /> : </b>
                    <a class="crossRef" href="https://www.ebi.ac.uk/chebi/searchId.do?chebiId=${compound.chebiId}" target="_blank">${compound.chebiId}</a>
                    </c:if>
                    </p>
                    <p class="">
                        <c:forEach var="specie" items="${compound.metSpecies}"
                                   varStatus="loopStatus">
                            <c:choose>
                                <c:when test="${loopStatus.index eq 0}">
                                    <b><spring:message code="ref.msg.mtbl.studies" /></b>
                                </c:when>
                                <c:otherwise>
                                    <span class="separator">,</span>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty specie.species.taxon}">
                                    <c:set var="len" value="${fn:length(specie.species.taxon)}"/>
                                    <c:set var = "taxid" value = "${fn:substring(specie.species.taxon, 5, len)}" />
                                    <a class="crossRef" href="https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?mode=Info&id=txid${taxid}" target="_blank">${specie.species.species}</a>
                                </c:when>
                                <c:otherwise>
                                    ${specie.species.species}
                                </c:otherwise>
                            </c:choose>

                        </c:forEach>

                    </p>
            </div>
        </div>
    </div>
</div>