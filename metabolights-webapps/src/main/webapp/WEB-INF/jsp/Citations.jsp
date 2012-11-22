<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
function toggle(element) {
    document.getElementById(element).style.display = (document.getElementById(element).style.display == "none") ? "" : "none";
}

</script>
<c:choose>
	<c:when test="${not empty citationList}">
		<h3>
			<b><a href="http://www.ebi.ac.uk/citexplore/">CiteXplore</a></b>
		</h3>
		<c:forEach var="citation" items="${citationList}">
			<div class="refLayerBox">
				<b>Title</b> - <a href="http://www.ebi.ac.uk/citexplore/citationDetails.do?externalId=${citation.id}&dataSource=MED">${citation.title}</a>
				<br /> 
				<b>Authors</b> - ${citation.authorString} <br />
				<a href="http://www.ncbi.nlm.nih.gov/pubmed?term=${citation.pmid}"><b>PubMed:</b>${citation.pmid}</a>
				<br />
				<a href="javascript:toggle('showAbstract${citation.id}')"><b>Abstract</b></a>
				<div id="showAbstract${citation.id}" style="display: none;">${citation.abstractText}
				</div>	
			</div>
			<br />
		</c:forEach>
	</c:when>
</c:choose>