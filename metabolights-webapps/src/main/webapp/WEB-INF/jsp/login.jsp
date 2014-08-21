<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


	<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 4/23/13 10:33 AM
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

<h2 class="strapline">
    	<c:if test="${empty fromsubmit}"><spring:message code="msg.credentials" /></c:if>
        <c:if test="${not empty fromsubmit}"><spring:message code="msg.submCredentials" /></c:if>
    </h2>
    
    <c:if test="${not empty fromsubmit}">
    	<p><strong><spring:message code="msg.submHeader"/></strong></p>
    </c:if>

    <form name="loginForm" action="<c:url value='j_spring_security_check'/>" method="post">
		
		<c:if test="${not empty param.login_error}">
            <p class="error">
            <!-- Your login attempt was not successful, try again.<br/>-->
            <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
            </p>
    	</c:if>
    	
        <div class="grid_4 alpha"><spring:message code="label.email" />:</div>
        <div class="grid_20 omega"><input type='text' name='j_username'/></div>
		
		<div class="grid_4 alpha"><spring:message code="label.password" />:</div>
        <div class="grid_20 omega">
           	<input type='password' name='j_password'/>
           	<a href="forgotPassword"><spring:message code="label.oopsForgot" /></a>
        </div>

        <div class="grid_4 alpha">&nbsp;</div>
		<div class="grid_20 omega">
			<input name="submit" type="submit" class="submit" value="<spring:message code="label.login"/>">
			<input name="cancel" type="button" class="submit cancel" value="<spring:message code="label.cancel"/>" onclick="location.href='index'">
        </div>


    </form>


    <p>
        <a href="newAccount" class="icon bigfont icon-functional" data-icon="7"></a>
		<a href="newAccount"><spring:message code="label.needNewAccount"/></a>
    </p>

<script type="text/javascript" language="javascript">
    document.loginForm.j_username.focus();
</script>
