<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

${localfrontierheader}
<c:if test="${!empty freeTextQuery}">
	<script>
		$('[name="freeTextQuery"]').val('${freeTextQuery}');
	</script>
</c:if>

<%--
<c:if test="${pageContext.request.serverName=='www.ebi.ac.uk'}" >
	<script>
		$('[href="reference"]').hide();
	</script>
</c:if>
--%>

<c:if test="${pageContext.request.serverName!='www.ebi.ac.uk'}" >
    <script>
        $("h1 a").css({ 'color': 'yellow'}).html("MetaboLights DEV");
    </script>
</c:if>



<sec:authorize ifAnyGranted="ROLE_SUBMITTER">
	
	<script>
	
		$loginA = $('[href="login"]');
		$loginA.html('<sec:authentication property="principal.firstName" />');
        $loginA.attr("href", '<spring:url value="useroptions"/>');
        $loginA.attr("title", '<spring:message code= "msg.welcome"/><sec:authentication property="principal.firstName" />');

	</script>
</sec:authorize>
