<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 12/17/13 12:38 PM
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

<sec:authorize ifAnyGranted="ROLE_SUBMITTER">
<h2>Hi <sec:authentication property="principal.firstName" />, <spring:message code="msg.useroptions" /></h2>
<p></p>
<div class='grid_6 alpha prefix_1'>
    <a href="<spring:url value="mysubmissions"/>">
    <div class="bigbutton maincolorI">
         <span class="bigfont"><spring:message code="menu.myStudiesCap" /></span><br/>
    </div>
    </a>
</div>

<div class='grid_6 prefix_2'>
     <a href="<spring:url value="myAccount"/>">
     <div class="bigbutton seccolorI">
         <span class="bigfont"><spring:message code="menu.myAccountCaps" /></span><br/>
     </div>
     </a>
</div>

<div class='grid_6 prefix_2 omega'>
    <a href="<spring:url value="/j_spring_security_logout"/>">
    <div class="bigbutton maincolorI">
         <span class="bigfont"><spring:message code="menu.logoutCaps" /></span><br/>
    </div>
    </a>
</div>
<p></p>
<p></p>

</sec:authorize>
  
<sec:authorize ifAnyGranted="ROLE_SUPER_USER">
<h2><spring:message code="msg.useroptionscurator" /></h2>
	
<div class='grid_6 alpha prefix_1'>
    <a href="<spring:url value="config"/>">
    <div class="bigbutton seccolorI">
         <span class="bigfont"><spring:message code="menu.configCaps" /></span><br/>
    </div>
    </a>
</div>

<div class='grid_6 prefix_2'>
    <a href="<spring:url value="users"/>">
    <div class="bigbutton maincolorI">
         <span class="bigfont"><spring:message code="menu.usersCaps" /></span><br/>
    </div>
    </a>
</div>

</sec:authorize>