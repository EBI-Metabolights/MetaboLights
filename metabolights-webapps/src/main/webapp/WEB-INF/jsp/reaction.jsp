<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<c:choose>
	<c:when test="${not empty Reactions}">
		<h3>
			<a href="http://www.ebi.ac.uk/rhea/">Rhea Reactions</a>
		</h3>
        <c:set var="chebiUrl" value="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&amp;chebiId=" />
        <c:set var="chebiDimension" value="&amp;dimensions=200&amp;scaleMolecule=true"/>
		<c:forEach var="Reaction" items="${Reactions}">
				<div class="refLayerBox" style='clear: both;'>
                    <ul>
                        <li>
                            <a href="http://www.ebi.ac.uk/rhea/reaction.xhtml?id=${Reaction.id}">${Reaction.title}</a>
                        </li>
                    </ul>
                    <c:forEach var="ReactiveMechanism"
                                           items="${Reaction.reactiveCentreAndMechanismAndReactantList}"
                                           varStatus="ReactionLoop">
                        <c:if test="${ReactiveMechanism.class.simpleName eq 'ReactantList'}">
                            <c:forEach var="Reactant"
                                       items="${ReactiveMechanism.reactantListOrReactant}"
                                       varStatus="ReactantLoop">
                                <c:choose>
                                    <c:when test="${ReactantLoop.index eq 0}">
                                        ${Reactant.title}
                                    </c:when>
                                    <c:otherwise>
                                        <b>&#43;</b>
                                        ${Reactant.title}
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>
                        <c:if test="${ReactionLoop.index eq 0}">
                            <c:choose>
                                <c:when test="${Reaction.convention eq 'rhea:direction.UN'}">
                                    <b>&#60;&#63;&#62;</b>
                                </c:when>
                                <c:when test="${Reaction.convention eq 'rhea:direction.LR'}">
                                    <b>&#61;&#62;</b>
                                </c:when>
                                <c:when test="${Reaction.convention eq 'rhea:direction.BI'}">
                                    <b>&#60;&#61;&#62;</b>
                                </c:when>
                                <c:when test="${Reaction.convention eq 'rhea:direction.RL'}">
                                    <b>&#60;&#61;</b>
                                </c:when>
                                <c:otherwise>
                                    <b>&#60;&#63;&#62;</b>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <c:if test="${ReactiveMechanism.class.simpleName eq 'ProductList'}">
                            <c:forEach var="Product"
                                       items="${ReactiveMechanism.productListOrProduct}"
                                       varStatus="ProductLoop">
                                <c:choose>
                                    <c:when test="${ProductLoop.index eq 0}">
                                        ${Product.title}
                                    </c:when>
                                    <c:otherwise>
                                        <b>&#43;</b>
                                        ${Product.title}
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>
                    </c:forEach>

					<br />
                    <table class="reacTab">
                        <tr>
                            <td>
                                <b></b>
                            </td>
                            <c:forEach var="ReactiveMechanism"
                                       items="${Reaction.reactiveCentreAndMechanismAndReactantList}"
                                       varStatus="ReactionLoop">
                                <c:if
                                        test="${ReactiveMechanism.class.simpleName eq 'ReactantList'}">
                                    <c:forEach var="Reactant"
                                               items="${ReactiveMechanism.reactantListOrReactant}"
                                               varStatus="ReactantLoop">
                                        <c:set var="Stoich" value="${Reactant.count}" />
                                        <c:set var="StoicNumber" value="${fn:replace(Stoich, '.0','') }" />
                                        <c:forEach var="ReactantChebiId"
                                                   items="${Reactant.molecule.id}">
                                            <c:choose>
                                                <c:when test="${ReactantLoop.index eq 0}">
                                                    <c:if test="${StoicNumber ne 1}">
                                                        <td class="tdSymbols"><b>${StoicNumber}</b></td>
                                                    </c:if>
                                                    <td>
                                                        <b></b>
                                                    </td>
                                                    <td class="tdReactions">
                                                        <a href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}">
                                                            <img src="${chebiUrl}${ReactantChebiId}${chebiDimension}" onerror="this.src='img/large_noImage.gif';" />
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <b></b>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <b></b>
                                                    </td>
                                                    <td class="icon forReactions" data-icon="+"/>
                                                    <td>
                                                        <b></b>
                                                    </td>
                                                    <c:if test="${StoicNumber ne 1}">
                                                        <td class="tdSymbols"><b>${StoicNumber}</b></td>
                                                    </c:if>
                                                    <td class="tdReactions">
                                                        <a href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}">
                                                            <img src="${chebiUrl}${ReactantChebiId}${chebiDimension}" onerror="this.src='img/large_noImage.gif';" />
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <b></b>
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${ReactionLoop.index eq 0}">
                                    <td>
                                        <b></b>
                                    </td>
                                    <td class="tdSymbols">
                                        <c:choose>
                                            <c:when test="${Reaction.convention eq 'rhea:direction.UN'}">
                                                <b><span class="icon icon-chemistry" data-icon="U"/></b>
                                            </c:when>
                                            <c:when test="${Reaction.convention eq 'rhea:direction.LR'}">
                                                <b><span class="icon icon-chemistry" data-icon="l"/></b>
                                            </c:when>
                                            <c:when test="${Reaction.convention eq 'rhea:direction.BI'}">
                                                <b><span class="icon icon-chemistry" data-icon="R"/></b>
                                            </c:when>
                                            <c:when test="${Reaction.convention eq 'rhea:direction.RL'}">
                                                <b><span class="icon icon-chemistry" data-icon="r"/></b>
                                            </c:when>
                                            <c:otherwise>
                                                <b><span class="icon icon-chemistry" data-icon="U"/></b>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </c:if>
                                <c:if
                                        test="${ReactiveMechanism.class.simpleName eq 'ProductList'}">
                                    <td>
                                        <b></b>
                                    </td>
                                    <c:forEach var="Product"
                                               items="${ReactiveMechanism.productListOrProduct}"
                                               varStatus="ProductLoop">
                                        <c:set var="Stoich" value="${Product.count}" />
                                        <c:set var="StoicNumber" value="${fn:replace(Stoich, '.0','') }" />
                                        <c:forEach var="ProductChebiId" items="${Product.molecule.id}">
                                            <c:choose>
                                                <c:when test="${ProductLoop.index eq 0}">
                                                    <c:if test="${StoicNumber ne 1}">
                                                        <td class="tdSymbols"><b>${StoicNumber}</b></td>
                                                    </c:if>
                                                    <td>
                                                        <b></b>
                                                    </td>
                                                    <td class="tdReactions">
                                                        <a href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}">
                                                            <img src="${chebiUrl}${ProductChebiId}${chebiDimension}" onerror="this.src='img/large_noImage.gif';" />
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <b></b>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>
                                                        <b></b>
                                                    </td>
                                                    <td class="icon forReactions" data-icon="+"/>
                                                    <td>
                                                        <b></b>
                                                    </td>
                                                    <c:if test="${StoicNumber ne 1}">
                                                        <td class="tdSymbols"><b>${StoicNumber}</b></td>
                                                    </c:if>
                                                    <td class="tdReactions">
                                                        <a href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}">
                                                            <img src="${chebiUrl}${ProductChebiId}${chebiDimension}" onerror="this.src='img/large_noImage.gif';" />
                                                        </a>
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </tr>
                    </table>
				</div>
			<br />
		</c:forEach>
	</c:when>
	<c:otherwise>
		<p align="center">
			<b><spring:message code="ref.msg.noReactions"/></b>
		</p>
	</c:otherwise>
</c:choose>

<%-- <img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}"  width="20px" height="20px" /> --%>