<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="javascript/jquery-1.6.1.min.js"></script>
<br />
		
		<form name="searchFilter" action="search" method="post" accept-charset="utf-8">				
		<c:forEach var="group" items="${filters}">
			<div style='width: 200px; border: 3px solid #D5AE60;'>
				<b>${group.key}</b>
				<ul class="filteritem" id="${group.key}">
					<c:forEach var="filter" items="${group.value}">
						<i><input	type="checkbox"
								 	name="${filter.value.name}" 
								  	value="${filter.value.value}"
								  	<c:if test='${filter.value.isChecked}'>
    								CHECKED
									</c:if>
								  	onclick="this.form.submit();">${filter.value.text}
						</i>
						<br/>
					</c:forEach>
					
				</ul>
			</div>
		</c:forEach>
		</form>
		