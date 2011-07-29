<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="javascript/jquery-1.6.1.min.js"></script>
		<div class="topSpacer"></div>

		<form name="searchFilter" id="filterForm" action="search" method="get" accept-charset="utf-8">				
			<c:forEach var="group" items="${filters}" varStatus ="status">
				<div style="width:200px" class="formbox" >
				<b>
					<c:choose>
						<c:when test="${group.key=='organisms'}"><spring:message code="msg.organisms"/></c:when>
						<c:when test="${group.key=='technology'}"><spring:message code="msg.technologies"/></c:when>
						<c:otherwise>${group.key}</c:otherwise>
					</c:choose>
					</b><br/></br>				
					<ul class="filteritem" id="${group.key}">
						<c:forEach var="filter" items="${group.value}">
							<input	type="checkbox"
								 	name="${filter.value.name}" 
								  	value="${filter.value.value}"
								  	<c:if test='${filter.value.isChecked}'>
	    								CHECKED
									</c:if>
								  	onclick="this.form.submit();"
								  	
								  	
								  	
							> ${filter.value.text} 
								  	<c:if test="${filter.value.number>0}">(${filter.value.number})</c:if>
								  	
							<br/>
							<br/>
						</c:forEach>
						
					</ul>
				</div>
				<br>
			</c:forEach>
			<input type="hidden" name="freeTextQuery" value="<c:out value="${freeTextQuery}"/>"/>
	        <input type="hidden" name="pageNumber" value="1"/>
		
		</form>
