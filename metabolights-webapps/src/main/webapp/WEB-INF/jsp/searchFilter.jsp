<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- If there isn't any result and it is due to the freetext, filter will not be printed -->
<c:if test="${!((totalHits==0) && filters.isFilterLoadNeeded)}">

	<script>
		function fillAutocomplete(id, availableTags) {
			$( "#"+id ).autocomplete({
				source: availableTags,
				change: function(event, ui)
						{
							console.log("change triggered");
						},
				select: function(event, ui)
						{
							console.log("select triggered");
							var checkedItem = $("input[value='" + $( "#"+id ).val()+ "']");
							checkedItem.prop("checked", true);
							$("#filterForm").submit();
						}
			});
			// Prevent submitting the form when keypress
			$( "#"+id ).bind('keypress', function(e) {
	            if (e.keyCode == 13) {
	            	//$("input[value='" + $( "#"+id ).val()+ "']").prop("checked", true);
	            	$("input[value='" + $( "#"+id ).val()+ "']").click();
	           }
	        });
		};
	</script>

   	<c:if test="${!empty welcomemessage}">
		<h2><spring:message code="menu.myStudiesCap" /></h2>
	</c:if>
    
    <c:if test="${empty welcomemessage}">
        <div class="topSpacerFilter noText"></div>  <!-- Add second top spacer if no heading displayed -->
    </c:if>

	<form name="searchFilter" id="filterForm" action="${action}" method="post" accept-charset="utf-8">

		<div class="grid_24 alpha omega title">
			<spring:message code="label.searchFilter"/>
		</div>	
			
		<c:forEach var="filterset" items="${filters.fss}">
			<c:if test="${filterset.value.isEnabled}">
				<div class="grid_24 alpha omega box" >
					<b><c:choose><c:when test="${filterset.key=='organism'}"><spring:message code="label.organism"/></c:when>
							<c:when test="${filterset.key=='technology'}"><spring:message code="label.technology"/></c:when>
							<c:when test="${filterset.key=='status'}"><spring:message code="label.status"/></c:when>
							<c:when test="${filterset.key=='metabolite'}"><spring:message code="label.metabolite"/></c:when>
							<c:otherwise>${filterset.key}</c:otherwise>
					</c:choose></b>
					<c:if test="${fn:length(filterset.value.filterItems) gt 5}">
						<div class="ui-widget">
							<input  class="inputDiscrete" style="width:270px" id="autocomplete_${filterset.key}"/>
							<script>var availableTags = new Array();</script>
						</div>
					</c:if>
							
					<ul class="filterset"  id="${filterset.key}">
						<c:forEach var="times" begin="0" end="1" step="1">
							<c:set var="checkedItems" value="0"/>							
							<c:forEach var="filter" items="${filterset.value.filterItems}">
								<c:if test='${(filter.value.isChecked and (times == 0)) or (!filter.value.isChecked and (times == 1))}'>									
									<c:if test='${(filter.value.isChecked and (times == 0))}'>
										<c:set var="checkedItems" value="${checkedItems + 1}"/>
									</c:if>
									<input 	type="checkbox"
										 	name="${filter.value.name}" 
										  	value="${filter.value.value}"
										  	<c:if test='${filter.value.isChecked}'>CHECKED</c:if>
										  	onclick="this.form.submit();"> 
									<c:if test="${filter.value.number<1}"><span class="dimmed">${filter.value.text}</span> </c:if>
									<c:if test="${filter.value.number>0}">${filter.value.text}</c:if>
									<br/>
									<c:if test="${fn:length(filterset.value.filterItems) gt 5}">
										<script>availableTags.push('${filter.value.value}')</script>
									</c:if>
								</c:if>	
							</c:forEach>
							<c:if test='${(times == 0) and (checkedItems gt 0)}'><hr/></c:if>
						</c:forEach>
						<c:if test="${fn:length(filterset.value.filterItems) gt 5}">
							<script>fillAutocomplete('autocomplete_${filterset.key}', availableTags);</script>
						</c:if> 
					</ul>
				</div>
			</c:if>
		</c:forEach>
		<input type="hidden" name="freeTextQuery" value="<c:out value="${freeTextQuery}"/>"/>
	    <input type="hidden" name="pageNumber" value="1"/>
	</form>
</c:if>
&nbsp;