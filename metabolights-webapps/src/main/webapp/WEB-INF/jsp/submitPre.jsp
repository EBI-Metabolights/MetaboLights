<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


    <div class="text_header plain">
	    <c:if test="${!empty user}">Hi ${user.firstName}. </c:if><spring:message code="msg.submCredentialsShort" />
        <br/> <br/>
    </div>

    <div class='iscell'>
        <a class="multi-line-button main" href="biisubmit"
           style="width:310px; height:100px; text-decoration: none; color: #ffffff;">
            <span class="titlemlb"><spring:message code="label.submitNewStudy"/></span>
	        <span class="subtitlemlb"><spring:message code="label.submitNewStudySub"/></span>
        </a>
    </div>

    <div class='iscell'>
        &nbsp;&nbsp;&nbsp;
    </div>

    <div class='iscell'>
        <a class="multi-line-button highlight" href="mysubmissions?status=PRIVATE"
           style="width:310px; height:100px; text-decoration: none; color: #000000;">
	        <span class="titlemlb"><spring:message	code="label.updateOldStudy"/></span>
	    	<span class="subtitlemlb"><spring:message code="label.updateOldStudySub"/></span>
      	</a>
    </div>
