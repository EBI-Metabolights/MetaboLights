<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"   %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 1/25/13 5:19 PM
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

<h2>${title}</h2>

<div class="grid_24 alpha omega">
  <c:forEach var="line" items="${message}">
    <p>${line}</p>
  </c:forEach>

  <c:if test="${not empty exception}">
      <h3>There's been an error:</h3>
    <p>${exception.message}</p>
  </c:if>

</div>


<script type="text/javascript">
  var oneSecond = 1000;
  var initTime =  5; // initial time before countdown
  var numSecs = 10; // number of seconds for countdown
  var dots = "";

  $( window ).load(function() {
    for (i = 0; i < numSecs; i++) {
      dots += " .";
    };
    $("#countdown").text(dots);
    $("#countdown").fadeIn(initTime * oneSecond);
    setTimeout(initCntDown, initTime * oneSecond);
  });

  function initCntDown(){
    setTimeout(goBack, numSecs * oneSecond);
    setInterval(cntDown,oneSecond);
  }

  function cntDown() {
    $("#countdown").text(
            $("#countdown").text().replace(" .","")
    );
  }

  function goBack() {
    window.location = "${pageContext.request.contextPath}/${studyId}";
  }
</script>

<div class="grid_24 alpha omega">

  <h4 class="well">
    <a class="noLine" rel="nofollow" href="${pageContext.request.contextPath}/${studyId}" title="Go Back to ${studyId}"  >
      <span class="icon icon-functional" data-icon="["/>Go Back to ${studyId}
      <span id="countdown" style="display: none;"/>
    </a>

  </h4>

</div>