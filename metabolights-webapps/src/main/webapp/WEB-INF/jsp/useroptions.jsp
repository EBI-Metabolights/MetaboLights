<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize ifAnyGranted="ROLE_SUBMITTER">
<h3>Hi <sec:authentication property="principal.firstName" />, <spring:message code="msg.useroptions" /></h3>
<p></p>
<div class='grid_6 alpha prefix_1'>
    <a href="<spring:url value="mysubmissions"/>">
    <div class="bigbutton maincolorI">
         <span class="bigfont"><spring:message code="menu.myStudies" /></span><br/>
    </div>
    </a>
</div>

<div class='grid_6 prefix_2'>
     <a href="<spring:url value="myAccount"/>">
     <div class="bigbutton seccolorI">
         <span class="bigfont"><spring:message code="menu.myAccount" /></span><br/>
     </div>
     </a>
</div>

<div class='grid_6 prefix_2 omega'>
    <a href="<spring:url value="/j_spring_security_logout"/>">
    <div class="bigbutton maincolorI">

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
    <a href="<spring:url value="config"/>">
    <div class="bigbutton seccolorI">
         <span class="bigfont"><spring:message code="menu.config" /></span><br/>
    </div>
    </a>
</div>

<div class='grid_6 prefix_2'>
    <a href="<spring:url value="users"/>">
    <div class="bigbutton maincolorI">
         <span class="bigfont"><spring:message code="menu.users" /></span><br/>
    </div>
    </a>
</div>
<div class='grid_6 prefix_2 omega'>
    <a href="<spring:url value="ebeyehelp"/>">
    <div class="bigbutton seccolorI">
         <span class="bigfont"><spring:message code="menu.Ebeye" /></span><br/>
    </div>
    </a>
</div>

</sec:authorize>