<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<h3>
    	<c:if test="${!empty user}">Hi ${user.firstName}. </c:if>
    	<spring:message code="msg.submCredentialsShort" />
    </h3>
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
        <br/>
        <spring:message code="msg.metabolightsAbout11" />
    </div>
