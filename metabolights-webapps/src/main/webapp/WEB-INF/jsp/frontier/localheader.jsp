<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
${localfrontierheader}
<c:if test="${!empty freeTextQuery}">
	<script>
		$('[name="freeTextQuery"]').val('${freeTextQuery}');
	</script>
</c:if>

<c:if test="${pageContext.request.serverName=='www.ebi.ac.uk'}" >
	<script>
		$('[href="refLayerSearch"]').hide();
	</script>
</c:if>


<sec:authorize ifAnyGranted="ROLE_SUBMITTER">
	
	<script>
	
		$loginA = $('[href="login"]'); 
		$loginA.html('<sec:authentication property="principal.firstName" />');
		$loginA.attr("href", '<spring:url value="useroptions"/>');		
		
	</script>
</sec:authorize>
