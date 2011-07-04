<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="javascript/jquery-1.6.1.min.js"></script>
<br />

		<div style='width: 200px; border: 3px solid #D5AE60;'>
			<b><spring:message code="label.organism" /> </b>
			<ul id="organisms">
				<c:forEach var="species" items="${organisms}">
					<i><input type="checkbox" name="organisms" value="${species}"> ${species}</i>
					<br />
				</c:forEach>
			</ul>
		</div>

		<div style='width: 200px; border: 3px solid #D5AE60;'>
			<b><spring:message code="label.technology" /> </b>
			<ul id="technology">
				<c:forEach var="techs" items="${technology}">
					<i><input type="checkbox" name="technology" value="${techs}"> ${techs}</i>
					<br />
				</c:forEach>
			</ul>
		</div>
		
<div style='width: 200px; border: 3px solid #D5AE60;'>
	<b><spring:message code="label.organism" /> </b>
	<ul id="organisms">
		<c:forEach var="species" items="${organisms}">
			<i><input type="checkbox" name="organisms" value="${species}">
				${species}</i>
			<br />
		</c:forEach>
	</ul>
</div>
