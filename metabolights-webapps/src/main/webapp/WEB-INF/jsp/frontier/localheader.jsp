<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

${localfrontierheader}
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 8/14/14 1:27 PM
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

<c:if test="${!empty freeTextQuery}">
	<script>
		$('[name="freeTextQuery"]').val('${freeTextQuery}');
	</script>
</c:if>

<c:if test="${pageContext.request.serverName=='www.ebi.ac.uk'}" >
	<script>
		$('[href="analysis"]').hide();
	</script>
</c:if>

<c:if test="${pageContext.request.serverName!='www.ebi.ac.uk'}" >
    <script>
        $("h1 a").css({ 'color': 'yellow'}).html("MetaboLights DEV");
    </script>
</c:if>



<sec:authorize ifAnyGranted="ROLE_SUBMITTER">

	<script>

		$loginA = $('[href="/metabolights/login"]');
		$loginA.html('<sec:authentication property="principal.firstName" />');
        $loginA.attr("href", '<spring:url value="useroptions"/>');
        $loginA.attr("title", '<spring:message code= "msg.welcome"/><sec:authentication property="principal.firstName" />');


		
	</script>
</sec:authorize>
