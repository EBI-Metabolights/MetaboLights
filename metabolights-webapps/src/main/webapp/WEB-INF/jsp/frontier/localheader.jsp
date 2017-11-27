<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%--${localfrontierheader}--%>
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

<%--<div>--%>
    <%--<h3 class="text-muted">Project name</h3>--%>
    <%--<nav class="navbar navbar-default">--%>
        <%--<div>--%>
            <%--<div class="navbar-header" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">--%>
                <%--<button type="button" class="navbar-toggle collapsed navbar-toggle-center" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">--%>
                    <%--<span class="sr-only">Toggle navigation</span>--%>
                    <%--<span class="glyphicon glyphicon-menu-hamburger"></span>--%>
                    <%--<span class="navmenu">MENU</span>--%>
                <%--</button>--%>
            <%--</div>--%>
            <%--<div class="collapse navbar-collapse navbar-nav-justified" id="bs-example-navbar-collapse-1">--%>
                <%--<ul class="nav nav-pills nav-justified">--%>
                    <%--<li class="active"><a href="#">Home</a></li>--%>
                    <%--<li><a href="#">Projects</a></li>--%>
                    <%--<li><a href="#">Services</a></li>--%>
                    <%--<li><a href="#">Downloads</a></li>--%>
                    <%--<li><a href="#">About</a></li>--%>
                    <%--<li><a href="#">Contact</a></li>--%>
                <%--</ul>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</nav>--%>
<%--</div>--%>

<%--<div id="local-masthead" class="masthead grid_24 nomenu">--%>
    <%--<div class="grid_12 alpha logo-title" id="local-title">--%>
        <%--<a href="index" title="Back to MetaboLights homepage">--%>
            <%--<img src="/metabolights/img/MetaboLightsLogo.png" alt="MetaboLights" width="64" height="64">--%>
        <%--</a>--%>
        <%--<span>--%>
			<%--<h1>--%>
				<%--<a href="index" title="Back to MetaboLights homepage">MetaboLights</a>--%>
			<%--</h1>--%>
		<%--</span>--%>
    <%--</div>--%>
    <%--<div class="grid_12 omega">--%>
        <%--<form id="local-search" name="local-search" action="/metabolights/search" method="post">--%>
            <%--<fieldset>--%>
                <%--<div class="left">--%>
                    <%--<label>--%>
                        <%--<input type="text" name="freeTextQuery" id="local-searchbox">--%>
                    <%--</label>--%>
                    <%--<!-- Include some example searchterms - keep them short and few! -->--%>
                    <%--<span class="examples">--%>
						<%--Examples:--%>
						<%--<a href="/metabolights/search?freeTextQuery=alanine">alanine</a>, <a href="/metabolights/search?freeTextQuery=Homo sapiens">Homo sapiens</a>, <a href="/metabolights/search?freeTextQuery=urine">urine</a>, <a href="/metabolights/search?freeTextQuery=MTBLS1">MTBLS1--%>
						<%--</a>--%>
					<%--</span>--%>
                <%--</div>--%>
                <%--<div class="right">--%>
                    <%--<input type="submit" name="submit" value="Search" class="submit">--%>
                <%--</div>--%>
            <%--</fieldset>--%>
        <%--</form>--%>
    <%--</div>--%>
    <%--<!-- /local-search --><!-- local-nav -->--%>
    <%--<nav>--%>
        <%--<ul class="grid_24" id="local-nav">--%>
            <%--<li class=" first">--%>
                <%--<a href="/metabolights/index" title="Home page">Home</a>--%>
            <%--</li>--%>
            <%--<li class="">--%>
                <%--<a href="/metabolights/studies" title="Browse all Studies/Experiments">Browse Studies</a>--%>
            <%--</li>--%>
            <%--<li class="">--%>
                <%--<a href="/metabolights/reference" title="Browse all metabolites">Browse Compounds</a>--%>
            <%--</li>--%>
            <%--<li class="">--%>
                <%--<a href="/metabolights/species" title="Browse all species">Browse Species</a>--%>
            <%--</li>--%>
            <%--<li class="">--%>
                <%--<a href="/metabolights/analysis" title="Analysis tools">Analysis</a>--%>
            <%--</li>--%>
            <%--<li class="">--%>
                <%--<a href="/metabolights/download" title="Download">Download</a>--%>
            <%--</li>--%>
            <%--<li class="">--%>
                <%--<a href="help" title="Help">Help</a>--%>
            <%--</li>--%>
            <%--<li class="">--%>
                <%--<a href="/metabolights/contact" title="Please give us feedback">Give us feedback</a>--%>
            <%--</li>--%>
            <%--<li class=" last">--%>
                <%--<a href="/metabolights/about" title="About MetaboLights">About</a>--%>
            <%--</li><!-- If you need to include functional (as opposed to purely navigational) links in your local menu, add them here, and give them a class of "functional". Remember: you'll need a class of "last" for whichever one will show up last... For example: -->--%>
            <%--<li class="functional last">--%>
                <%--<a href="/metabolights/login" class="icon icon-functional" data-icon="l" title="Log in to MetaboLights">Login</a>--%>
            <%--</li>--%>
            <%--<li class="functional">--%>
                <%--<a href="/metabolights/presubmit" class="icon icon-functional" data-icon="_" title="Submit data to MetaboLights">Submit Study</a>--%>
            <%--</li>--%>
        <%--</ul>--%>
    <%--</nav><!-- /local-nav -->--%>
<%--</div>--%>
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
        $loginA.attr("href", '<spring:url value="/useroptions"/>');
        $loginA.attr("title", '<spring:message code= "msg.welcome"/><sec:authentication property="principal.firstName" />');
	</script>
</sec:authorize>
