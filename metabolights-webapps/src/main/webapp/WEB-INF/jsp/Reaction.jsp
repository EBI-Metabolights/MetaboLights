<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:choose>
	<c:when test="${not empty Reactions}">
		<c:forEach var="ReactionsList" items="${Reactions}">
			<h6>${ReactionsList}</h6>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<h6>No Reactions Found</h6>
	</c:otherwise>
</c:choose>
