<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
~ Cheminformatics and Metabolism group
~
~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
~
~ Last modified: 9/26/14 5:22 PM
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

    <%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 8/21/14 9:46 AM
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

    <%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 5/2/13 2:34 PM
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
<h2><spring:message code="msg.importmetabolites.status.title"/></h2>

<c:if test="${empty report}">
    <p><spring:message code="msg.importmetabolites.status.noreport"/></p>
    <p><spring:message code="msg.importmetabolites.status.noreport1"/></p>
</c:if>

<c:if test="${not empty report}">
    <SCRIPT>
        $(document).ready(function(){
            $(".progressbar").each(function(){
                var value = parseInt($(this).attr("value"));
                $(this).progressbar({
                    max: 100,
                    value: value
                });
            });
        })
    </SCRIPT>
    <c:if test="${empty report.period.end}">
        <SCRIPT>
        $(document).ready(function(){
            setTimeout(function(){
                location.reload();
            },10000);

        })
        </SCRIPT>
    </c:if>
    <h3>${report.processName}</h3>
    <div class="progressbar grid_22 push_1" value="${report.percentage.percentage}"></div>
    <p>(Started at ${report.period.start}<c:if test="${not empty report.period.end}"> - finished at ${report.period.end}</c:if>)</p>
    <c:forEach var="subProcess" items="${report.subProcesses}">
        <h3>${subProcess.processName}</h3>
        <div class="progressbar  grid_22 push_1" value="${subProcess.percentage.percentage}"></div>
        <p>${subProcess.percentage.count} of ${subProcess.percentage.total} (Started at ${subProcess.period.start}<c:if test="${not empty subProcess.period.end}"> - finished at ${subProcess.period.end}</c:if>)</p>
        <c:forEach var="subSubProcess" items="${subProcess.subProcesses}">
            <h3>${subSubProcess.processName}</h3>
            <div class="progressbar  grid_22 push_1" value="${subSubProcess.percentage.percentage}"></div>
            <p>${subSubProcess.percentage.count} of ${subSubProcess.percentage.total} (Started at ${subSubProcess.period.start}<c:if test="${not empty subSubProcess.period.end}"> - finished at ${subSubProcess.period.end}</c:if>)</p>
        </c:forEach>
    </c:forEach>

    <c:if test="${not empty report.period.end}">
        <p><spring:message code="msg.importmetabolites.status.noreport1"/></p>
    </c:if>

</c:if>

