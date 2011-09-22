<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" src="javascript/jquery-1.6.2.min.js"></script>

<!-- If there isn't any result and it is due to the freetext, filter will not be printed -->
<c:if test="${!((totalHits==0) && filters.isFilterLoadNeeded)}">
   	<c:if test="${!empty welcomemessage}">
		<div class="text_header yellow">
            <span class="title"><spring:message code="menu.myStudiesCap" /></span>
		</div>
	</c:if>

    <div class="topSpacerFilter"></div>             <!-- Add first top spacer -->
    <c:if test="${empty welcomemessage}">
        <div class="topSpacerFilter noText"></div>  <!-- Add second top spacer if no heading displayed -->
    </c:if>


	<legend><b><spring:message code="label.searchFilter"/></b></legend>
	<form name="searchFilter" id="filterForm" action="${action}" method="post" accept-charset="utf-8">				
		<c:forEach var="filterset" items="${filters.fss}">
			<c:if test="${filterset.value.isEnabled}">
				<div style="width:200px" class="filterbox" >
					<b>
						<c:choose>
							<c:when test="${filterset.key=='organisms'}"><spring:message code="label.organism"/></c:when>
							<c:when test="${filterset.key=='technology'}"><spring:message code="label.technology"/></c:when>
							<c:when test="${filterset.key=='status'}"><spring:message code="label.status"/></c:when>
							<c:otherwise>${filterset.key}</c:otherwise>
						</c:choose>
					</b><br/><br/>				
					<ul class="filteritem" id="${filterset.key}">
						<c:forEach var="filter" items="${filterset.value.filterItems}">
							<input	type="checkbox"
								 	name="${filter.value.name}" 
								  	value="${filter.value.value}"
								  	<c:if test='${filter.value.isChecked}'>
	    								CHECKED
									</c:if>
								  	onclick="this.form.submit();"> 
							<c:if test="${filter.value.number<1}"><span class="gray">${filter.value.text}</span> </c:if>
							<c:if test="${filter.value.number>0}">${filter.value.text}</c:if>
							<br/>
							<br/>
						</c:forEach>
					</ul>
				</div>
			</c:if>
		</c:forEach>
		<input type="hidden" name="freeTextQuery" value="<c:out value="${freeTextQuery}"/>"/>
	       <input type="hidden" name="pageNumber" value="1"/>
	
	</form>
</c:if>