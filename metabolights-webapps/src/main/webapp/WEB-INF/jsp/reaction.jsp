<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 6/21/13 4:21 PM
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

<c:choose>
	<c:when test="${not empty reactions}">
		<h4>
			<a href="http://www.ebi.ac.uk/rhea/">Rhea Reactions</a>
		</h4>
        <c:set var="chebiUrl" value="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&amp;chebiId=" />
        <c:set var="chebiDimension" value="&amp;dimensions=200&amp;scaleMolecule=true"/>
		<c:forEach var="Reaction" items="${reactions}">
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
                                                <b><span class="icon icon-chemistry forReactChemistry" data-icon="U"/></b>
                                            </c:when>
                                            <c:when test="${Reaction.convention eq 'rhea:direction.LR'}">
                                                <b><span class="icon icon-chemistry forReactChemistry" data-icon="l"/></b>
                                            </c:when>
                                            <c:when test="${Reaction.convention eq 'rhea:direction.BI'}">
                                                <b><span class="icon icon-chemistry forReactChemistry" data-icon="R"/></b>
                                            </c:when>
                                            <c:when test="${Reaction.convention eq 'rhea:direction.RL'}">
                                                <b><span class="icon icon-chemistry forReactChemistry" data-icon="r"/></b>
                                            </c:when>
                                            <c:otherwise>
                                                <b><span class="icon icon-chemistry forReactChemistry" data-icon="U"/></b>
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