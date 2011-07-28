<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		
		
<div class="formbox">
	
	<form method="post" action="biiuploadExperiment" enctype="multipart/form-data">
           <table cellpadding="5px" cellspacing="0px">
	        <tr class="formheader">
	             <th class="tableheader" colspan="3"><spring:message code="msg.upload" />
	             </th>
	        </tr>
	        <tr>
	            <td colspan='3'>&nbsp;</td>
	        </tr>

	        <tr>
	            <td><spring:message code="label.file" />:</td>
	            <td><input type="file" name="file" /></td>
	        </tr>
	        <tr>
	            <td><spring:message code="label.experimentstatuspublic" />:</td>
	            <td><input type="checkbox" name="public" /></td>
	        </tr>
	        <tr>
	            <td colspan='2'><input name="submit" type="submit" value="<spring:message code="label.upload"/>">
	            <a href="index"><input type="button" name="cancel" value="<spring:message code="label.cancel"/>" /></a>
	            </td>
	        </tr>
	        
	   		<tr>
	   			<td colspan='3'>
			       <br/>
			       <a href="<spring:url value="submitHelp"/>"><spring:message code="menu.submitHelp"/></a>
			       <br>       
		    	</td>  	
	        </tr>
	    </table>
		
	</form> 

	 <c:if test="${not empty message}">
	    <div class="error">
	       <c:out value="${message}" />
	    </div>
	 </c:if>
	 
</div>