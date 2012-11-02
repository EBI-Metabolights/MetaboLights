<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

Welcome to Reference Layer Search Page.
<p>

<div>
	<table>
		<c:forEach var="table" items="${results}">
			<c:if test="${table.key ne 'Alanine'}">
				<tr>
					<td>
						${table.key}
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</div>


<!--<c:out value="${results}"/>-->