<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>


<div class="formbox">

	<form:form name="mySubmForm" action="mysubmissions">
    	<table cellpadding="5px" cellspacing="0px" width="90%">
        	<tr class="text_header plain">
            	<th colspan="3"><spring:message code="menu.myAccountCaps" /></th>
            </tr>
            <tr>
            	<td></td>
            	<td height="100px" colspan='2'>
                    <input type="submit" name="submit" class="multi-line-button highlight" value=" <spring:message code="label.viewMySubmissions"/> ">
                </td>
            </tr>
            <tr>
            	<td colspan='3'> <hr/> 	</td>
            </tr>
		</table>
    </form:form>

    <form:form name="accountForm" action="updateAccount" method="post" commandName="metabolightsUser">        
        <form:hidden path="userId" />
        <form:hidden path="userName" />
        <sec:authorize ifNotGranted="ROLE_SUPER_USER" >
        	<form:hidden path="status" />
        </sec:authorize>
        
        <form:hidden path="email" />
        
        <table cellpadding="5px" cellspacing="0px">
            <tr class="formheader">
                <th class="text_header plain" colspan="2"><spring:message code="msg.updateAccount" /> </th>
                <th><spring:message code="msg.starRequired"/></th>
            </tr>
            <tr>
                <td colspan='3'>&nbsp;</td>
            </tr>
            <tr>
                <td><spring:message code="label.email" />:</td>
                <td><c:out value="${metabolightsUser.email}" /> </td>
                <td></td>
            </tr>

            <jsp:include page="accountFormFields.jsp" />

            <tr>
                <td></td>
                <td colspan='2'>
                    <br/><br/>
                    <div class='iscell left'>
                        <input type="submit" name="submit" class="multi-line-button main" value=" <spring:message code="label.update"/> ">
                    </div>
                    <div class='iscell'>
                	    <br/><a href="index"><spring:message code="label.cancel"/></a>
                    </div>
                </td>
            </tr>
 
            
        </table>
    </form:form>

    <script type="text/javascript" language="javascript">
       document.reminderForm.email.focus();
    </script>

    <c:if test="${not empty message}">
        <span class="error"> <c:out value="${message}" /> </span>
        <br/>
    </c:if>

</div>