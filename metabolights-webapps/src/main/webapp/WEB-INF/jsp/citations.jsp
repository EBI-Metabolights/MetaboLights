<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
function toggle(element) {
    document.getElementById(element).style.display = (document.getElementById(element).style.display == "none") ? "" : "none";
}

</script>
<c:choose>
	<c:when test="${not empty citationList}">
		<h3>
			<b><a href="http://europepmc.org/">Europe PMC</a></b>
		</h3>
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