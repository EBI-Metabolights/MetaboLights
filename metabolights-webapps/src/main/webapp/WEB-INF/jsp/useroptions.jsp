<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize ifAnyGranted="ROLE_SUBMITTER">
<h3>Hi <sec:authentication property="principal.firstName" />, <spring:message code="msg.useroptions" /></h3>
<p></p>
<div class='grid_6 alpha prefix_1'>
    <div class="bigbutton maincolorI">
     <a href="<spring:url value="mysubmissions"/>">
         <span class="bigfont"><spring:message code="menu.myStudies" /></span><br/>
     </a>
    </div>
</div>

<div class='grid_6 prefix_2'>
    <div class="bigbutton seccolorI">
     <a href="<spring:url value="myAccount"/>">
         <span class="bigfont"><spring:message code="menu.myAccount" /></span><br/>
     </a>
    </div>
</div>

<div class='grid_6 prefix_2 omega'>
    <div class="bigbutton maincolorI">
     <a href="<spring:url value="/j_spring_security_logout"/>">
         <span class="bigfont"><spring:message code="menu.logout" /></span><br/>
     </a>
    </div>
</div>
<p></p>
<p></p>

</sec:authorize>
  
<sec:authorize ifAnyGranted="ROLE_SUPER_USER">
<h3><spring:message code="msg.useroptionscurator" /></h3>
	
<div class='grid_6 alpha prefix_1'>
    <div class="bigbutton seccolorI">
     <a href="<spring:url value="config"/>">
         <span class="bigfont"><spring:message code="menu.config" /></span><br/>
     </a>
    </div>
</div>

<div class='grid_6 prefix_2'>
    <div class="bigbutton maincolorI">
     <a href="<spring:url value="users"/>">
         <span class="bigfont"><spring:message code="menu.users" /></span><br/>
     </a>
    </div>
</div>
<div class='grid_6 prefix_2 omega'>
    <div class="bigbutton seccolorI">
     <a href="<spring:url value="ebeyehelp"/>">
         <span class="bigfont"><spring:message code="menu.Ebeye" /></span><br/>
     </a>
    </div>
</div>

</sec:authorize>