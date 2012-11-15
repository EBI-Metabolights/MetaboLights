<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<c:choose>
	<c:when test="${not empty Reactions}">
		<h3>
			<b>Rhea Reactions</b>
		</h3>
		<c:forEach var="Reaction" items="${Reactions}">
			<c:forEach var="ReactiveMechanism"
				items="${Reaction.reactiveCentreAndMechanismAndReactantList}"
				varStatus="ReactionLoop">
				<c:if test="${ReactiveMechanism.class.simpleName eq 'ReactantList'}">
					<c:forEach var="Reactant"
						items="${ReactiveMechanism.reactantListOrReactant}"
						varStatus="ReactantLoop">
						<c:forEach var="ReactantName" items="${Reactant.title}">
							<c:choose>
								<c:when test="${ReactantLoop.index eq 0}">
									${ReactantName}
								</c:when>
								<c:otherwise>
									<b>&#43;</b>
									${ReactantName}
								</c:otherwise>
							</c:choose>
						</c:forEach>
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
					</c:choose>
				</c:if>
				<c:if test="${ReactiveMechanism.class.simpleName eq 'ProductList'}">
					<c:forEach var="Product"
						items="${ReactiveMechanism.productListOrProduct}"
						varStatus="ProductLoop">
						<c:forEach var="ProductName" items="${Product.title}">
							<c:choose>
								<c:when test="${ProductLoop.index eq 0}">
									${ProductName}
								</c:when>
								<c:otherwise>
									<b>&#43;</b>
									${ProductName}
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:forEach>
				</c:if>
			</c:forEach>
			<br />
			<c:forEach var="ReactiveMechanism"
				items="${Reaction.reactiveCentreAndMechanismAndReactantList}"
				varStatus="ReactionLoop">
				<c:if test="${ReactiveMechanism.class.simpleName eq 'ReactantList'}">
					<c:forEach var="Reactant"
						items="${ReactiveMechanism.reactantListOrReactant}"
						varStatus="ReactantLoop">
						<c:forEach var="ReactantChebiId" items="${Reactant.molecule.id}">
							<c:choose>
								<c:when test="${ReactantLoop.index eq 0}">
									<a
										href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}">${ReactantChebiId}</a>
								</c:when>
								<c:otherwise>
									<b>&#43;</b>
									<a
										href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ReactantChebiId}">${ReactantChebiId}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
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
					</c:choose>
				</c:if>
				<c:if test="${ReactiveMechanism.class.simpleName eq 'ProductList'}">
					<c:forEach var="Product"
						items="${ReactiveMechanism.productListOrProduct}"
						varStatus="ProductLoop">
						<c:forEach var="ProductChebiId" items="${Product.molecule.id}">
							<c:choose>
								<c:when test="${ProductLoop.index eq 0}">
									<a
										href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}">${ProductChebiId}</a>
								</c:when>
								<c:otherwise>
									<b>&#43;</b>
									<a
										href="http://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=${ProductChebiId}">${ProductChebiId}</a>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:forEach>
				</c:if>
			</c:forEach>
			<br />
			<c:forEach var="ReactiveMechanism"
				items="${Reaction.reactiveCentreAndMechanismAndReactantList}"
				varStatus="ReactionLoop">
				<c:if test="${ReactiveMechanism.class.simpleName eq 'ReactantList'}">
					<c:forEach var="Reactant"
						items="${ReactiveMechanism.reactantListOrReactant}"
						varStatus="ReactantLoop">
						<c:forEach var="ReactantChebiId" items="${Reactant.molecule.id}">
							<c:choose>
								<c:when test="${ReactantLoop.index eq 0}">
									<img
										src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}"
										onerror="this.src='img/large_noImage.gif';" width="100px"
										height="100px" />
								</c:when>
								<c:otherwise>
									<b>&#43;</b>
									<img
										src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}"
										onerror="this.src='img/large_noImage.gif';" width="100px"
										height="100px" />
								</c:otherwise>
							</c:choose>
						</c:forEach>
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
					</c:choose>
				</c:if>
				<c:if test="${ReactiveMechanism.class.simpleName eq 'ProductList'}">
					<c:forEach var="Product"
						items="${ReactiveMechanism.productListOrProduct}"
						varStatus="ProductLoop">
						<c:forEach var="ProductChebiId" items="${Product.molecule.id}">
							<c:choose>
								<c:when test="${ProductLoop.index eq 0}">
									<img
										src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ProductChebiId}"
										onerror="this.src='img/large_noImage.gif';" width="100px"
										height="100px" />
								</c:when>
								<c:otherwise>
									<b>&#43;</b>
									<img
										src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ProductChebiId}"
										onerror="this.src='img/large_noImage.gif';" width="100px"
										height="100px" />
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:forEach>
				</c:if>
			</c:forEach>
			<br />
			<br />
		</c:forEach>
	</c:when>
	<c:otherwise>
		<p align="center">
			<b>No Reactions Found</b>
		</p>
	</c:otherwise>
</c:choose>

<!-- <img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${ReactantChebiId}"  width="20px" height="20px" /> -->