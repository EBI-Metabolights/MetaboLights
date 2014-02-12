<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

${localfrontierheader}
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ Last modified: 12/02/14 12:53
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  --%>

<c:if test="${!empty freeTextQuery}">
	<script>
		$('[name="freeTextQuery"]').val('${freeTextQuery}');
	</script>
</c:if>

<c:if test="${pageContext.request.serverName=='www.ebi.ac.uk'}" >
	<script>
		$('[href="reference"]').hide();
        $('[href="species"]').hide();
	</script>
</c:if>


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
        $loginA.attr("title", '<spring:message code= "msg.welcome"/>&nbsp;<sec:authentication property="principal.firstName" />');

	</script>
</sec:authorize>
