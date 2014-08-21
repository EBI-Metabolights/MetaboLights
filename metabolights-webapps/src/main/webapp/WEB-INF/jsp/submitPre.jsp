<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 1/25/13 4:50 PM
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

<h2>
    	<c:if test="${!empty user}">Hi ${user.firstName}. </c:if>
    	<spring:message code="msg.submCredentialsShort" />
    </h2>
    <br/><br/>

    <div class='grid_9 alpha prefix_2'>
        <div class="bigbutton maincolorI">
	        <a href="submittoqueue">
	            <span class="bigfont"><spring:message code="label.submitNewStudy"/></span><br/>
		        <span><spring:message code="label.submitNewStudySub"/></span>
	        </a>
        </div>
    </div>

    <div class='grid_9 omega prefix_2 sufix_2'>
    	<div class="bigbutton seccolorI">
	        <a href="mysubmissions?status=PRIVATE">
		        <span class="bigfont"><spring:message	code="label.updateOldStudy"/></span></br>
		    	<span><spring:message code="label.updateOldStudySub"/></span>
	      	</a>
      	</div>
    </div>

    <div class="grid_24 alpha omega">
        <br/>
        <br/>
        <br/>
        <p><spring:message code="msg.metabolightsAbout11" /></p>
    </div>
