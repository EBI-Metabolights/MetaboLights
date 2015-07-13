<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2/10/14 4:54 PM
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

<!-- If there isn't any result and it is due to the freetext, filter will not be printed -->

<%-- Facets model

facets[].facet
facets[].facet.name
facets[].facet.lines[]
facets[].facet.lines[].Facetline
facets[].facet.lines[].FacetLine.count
facets[].facet.lines[].FacetLine.value
facets[].facet.lines[].FacetLine.checked

--%>



<%--<c:if test="${!((totalHits==0) && filters.isFilterLoadNeeded)}">--%>

	<script>
		function fillAutocomplete(id, availableTags) {

			id = id.replace(".", "\\.");

			$( "#"+id ).autocomplete({
				source: availableTags,
				select: function(event, ui)
						{
							console.log("select triggered");
                            var checkedItem = $("input[value='" + ui.item.value + "']");
							checkedItem.prop("checked", true);
							$("#filterForm").submit();
						}
			});
			// Prevent submitting the form when keypress
			$( "#"+id ).bind('keypress', function(e) {
	            if (e.keyCode == 13) {
	            	$("input[value='" + $( "#"+id ).val()+ "']").click();
	           }
	        });
		};
	</script>

	<form name="searchFilter" id="filterForm" action="${action}" method="post" accept-charset="utf-8">

		<h3><spring:message code="label.searchFilter"/></h3>

		<c:forEach var="facet" items="${facets}">

			<c:if test="${fn:length(facet.lines) gt 1}">
				<c:set var="caption">
					<c:choose>
						<c:when test="${facet.name=='assays.technology'}"><spring:message code="label.technology"/></c:when>
						<c:when test="${facet.name=='studyStatus'}"><spring:message code="label.facetStatus"/></c:when>
						<c:when test="${facet.name=='organism.organismName'}"><spring:message code="label.organism"/></c:when>
						<c:when test="${facet.name=='users.fullName'}"><spring:message code="label.subm"/></c:when>
						<c:when test="${facet.name=='factors.name'}"><spring:message code="label.factor"/></c:when>
						<c:when test="${facet.name=='descriptors.description'}"><spring:message code="label.descriptors"/></c:when>
						<c:when test="${facet.name=='organism.organismPart'}"><spring:message code="label.organismPart"/></c:when>
						<c:otherwise>${facet.name}</c:otherwise>
					</c:choose>
				</c:set>
				<h4>${caption}</h4>
				<c:if test="${fn:length(facet.lines) gt 5}">
					<div class="ui-widget">
						<input
								class="inputDiscrete resizable"
								id="autocomplete_${facet.name}"
								placeholder= "Find your ${caption}"
						/>
						<script>var availableTags = new Array();</script>
					</div>
				</c:if>

				<ul class="filterset"  id="${facet.name}">
					<c:forEach var="times" begin="0" end="1" step="1">
						<c:set var="checkedItems" value="0"/>
						<c:forEach var="line" items="${facet.lines}">
							<c:if test='${(line.checked and (times == 0)) or (!line.checked and (times == 1))}'>
								<c:if test='${(line.checked and (times == 0))}'>
									<c:set var="checkedItems" value="${checkedItems + 1}"/>
								</c:if>
								<input 	type="checkbox"
										name="${facet.name}"
										value="${line.value}"
										<c:if test='${line.checked}'>CHECKED</c:if>
										onclick="this.form.submit();">
								<c:if test="${line.count<1}"><span class="dimmed">${line.value}</span> </c:if>
								<c:if test="${line.count>0}">${line.value}</c:if>
								<br/>
								<c:if test="${fn:length(facet.lines) gt 5}">
									<script>availableTags.push("${line.value}")</script>
								</c:if>
							</c:if>
						</c:forEach>
					</c:forEach>
					<c:if test="${fn:length(facet.lines) gt 5}">
						<script>fillAutocomplete('autocomplete_${facet.name}', availableTags);</script>
					</c:if>
				</ul>
			</c:if>
		</c:forEach>

		<input type="hidden" name="freeTextQuery" value="${searchResponse.content.query.text}"/>
	    <input type="hidden" name="pageNumber" value="1"/>
	</form>
<%--</c:if>--%>
&nbsp;