<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<c:choose>
	<c:when test="${not empty Reactions}">
		<h3>
			<a href="http://www.ebi.ac.uk/rhea/">Rhea Reactions</a>
		</h3>
		<c:forEach var="Reaction" items="${Reactions}">
				<div class="refLayerBox" style='clear: both;'>
					<c:forEach var="ReactiveMechanism"
						items="${Reaction.reactiveCentreAndMechanismAndReactantList}"
						varStatus="ReactionLoop">
						<c:if test="${ReactiveMechanism.class.simpleName eq 'ReactantList'}">
							<c:forEach var="Reactant"
								items="${ReactiveMechanism.reactantListOrReactant}"
								varStatus="ReactantLoop">
								<!--  c:forEach var="ReactantName" items="${Reactant.title}"-->
									<c:choose>
										<c:when test="${ReactantLoop.index eq 0}">
											${Reactant.title}
										</c:when>
										<c:otherwise>
											<b>&#43;</b>
											${Reactant.title}
										</c:otherwise>
									</c:choose>
								<!-- /c:forEach -->
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
								<!-- c:forEach var="ProductName" items="${Product.title}" -->
									<c:choose>
										<c:when test="${ProductLoop.index eq 0}">
											${Product.title}
										</c:when>
										<c:otherwise>
											<b>&#43;</b>
											${Product.title}
										</c:otherwise>
									</c:choose>
								<!-- /c:forEach -->
							</c:forEach>
						</c:if>
					</c:forEach>
					<br />
					<table style="border-top-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-right-width: 0px; padding-right: 0px; margin-left: 0px;">
						<tr>
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
													<td class="tdReactions">
														<c:choose>
															<c:when test="${ReactantChebiId eq 'CHEBI:15378'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:when test="${ReactantChebiId eq 'CHEBI:15379'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:when test="${ReactantChebiId eq 'CHEBI:17996'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:otherwise>
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:otherwise>
														</c:choose>
													</td>
												</c:when>
												<c:otherwise>
													<td class="tdSymbols"><b>&#43;</b></td>
													<c:if test="${StoicNumber ne 1}">
														<td class="tdSymbols"><b>${StoicNumber}</b></td>
													</c:if>
													<td class="tdReactions">
														<c:choose>
															<c:when test="${ReactantChebiId eq 'CHEBI:15378'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:when test="${ReactantChebiId eq 'CHEBI:15379'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:when test="${ReactantChebiId eq 'CHEBI:17996'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:otherwise>
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:otherwise>
														</c:choose>
													</td>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</c:forEach>
								</c:if>
								<c:if test="${ReactionLoop.index eq 0}">
									<td class="tdSymbols"><c:choose>
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
										</c:choose></td>
								</c:if>
								<c:if
									test="${ReactiveMechanism.class.simpleName eq 'ProductList'}">
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
													<td class="tdReactions">
														<c:choose>
															<c:when test="${ProductChebiId eq 'CHEBI:15378'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ProductChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:when test="${ProductChebiId eq 'CHEBI:15379'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ProductChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:when test="${ProductChebiId eq 'CHEBI:17996'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ProductChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:otherwise>
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ProductChebiId}"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:otherwise>
														</c:choose>
													</td>
												</c:when>
												<c:otherwise>
													<td class="tdSymbols"><b>&#43;</b></td>
													<c:if test="${StoicNumber ne 1}">
														<td class="tdSymbols"><b>${StoicNumber}</b></td>
													</c:if>
													<td class="tdReactions">
														<c:choose>
															<c:when test="${ProductChebiId eq 'CHEBI:15378'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ProductChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:when test="${ProductChebiId eq 'CHEBI:15379'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ProductChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:when test="${ProductChebiId eq 'CHEBI:17996'}">
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ProductChebiId}&dimensions=80&scaleMolecule=true"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:when>
															<c:otherwise>
																<a
																	href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}"><img
																	src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ProductChebiId}"
																	onerror="this.src='img/large_noImage.gif';"
																	width="100px" height="100px" /></a>
															</c:otherwise>
														</c:choose>
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
			<b>No Reactions Found!!</b>
		</p>
	</c:otherwise>
</c:choose>

<!-- <img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}"  width="20px" height="20px" /> -->