<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 7/1/13 10:08 AM
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

<script type="text/javascript">
function toggle(element) {
    document.getElementById(element).style.display = (document.getElementById(element).style.display == "none") ? "" : "none";
}

</script>
<c:choose>
	<c:when test="${not empty citationList}">
		<h4>
			<b><a href="http://europepmc.org/">Europe PubMed Central results</a></b>
		</h4>
		<c:forEach var="citation" items="${citationList}">
			<div class="refLayerBox">
				<b><spring:message code="ref.msg.CitationTitle"/></b>&nbsp;&#45;&nbsp;<a href="http://europepmc.org/abstract/MED/${citation.id}">${citation.title}</a>
				<br /> 
				<b><spring:message code="ref.msg.CitationAuthors"/></b>&nbsp;&#45;&nbsp;${citation.authorString} <br />
				<b><spring:message code="ref.msg.CitationPubMed"/></b>&nbsp;&#45;&nbsp;<a href="http://www.ncbi.nlm.nih.gov/pubmed?term=${citation.pmid}">${citation.pmid}</a>
				<br />
				<a href="javascript:toggle('showAbstract${citation.id}')"><b><spring:message code="ref.msg.CitationAbstract"/></b></a>
				<div id="showAbstract${citation.id}" style="display: none;">${citation.abstractText}
				</div>	
			</div>
			<br />
		</c:forEach>
	</c:when>
	<c:otherwise>
		<b><spring:message code="ref.msg.noLiterature"/> </b>
        <c:if test="${not empty errortext}">
            </p> ${errortext}
        </c:if>
	</c:otherwise>
</c:choose>