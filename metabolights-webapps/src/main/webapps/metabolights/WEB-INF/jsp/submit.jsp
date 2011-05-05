<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		
		<br/>
		<c:if test="${not empty message}">
           <div class="error">
              <c:out value="${message}" />
           </div>
        </c:if>
		
		<br/>
		
		<b><spring:message code="msg.upload" /></b>
		<br/><br/>
		
		<form method="post" action="uploadExperiment" enctype="multipart/form-data">
		
		    <table cellpadding="5px">
		        <tr>
		            <td><spring:message code="label.file" />:</td>
		            <td><input type="file" name="file" /></td>
		        </tr>
		        <!-- tr>
		            <td><spring:message code="label.filename" />:</td>
		            <td><input type="text" name="name" /></td>
		        </tr-->
		        <tr>
		            <td colspan='2'><input name="submit" type="submit" value="<spring:message code="label.upload"/>">
		            </td>
		        </tr>
		    </table>
			
		</form>
