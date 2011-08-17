<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="javascript/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="javascript/jquery-ui-1.8.15.custom.min.js"></script>

<script type="text/javascript">
	function onloadAction() {
		//standard call from layout.jsp
		enableSubmission();		
	}
	
	function disableSubmission() {
		document.forms['uf'].elements['submit'].disabled=true;
	    document.forms['uf'].elements['cancel'].disabled=true;
	    document.body.style.cursor = "wait";
		var hglass = document.getElementById("hourglass");
		hglass.style.display = "block";		
	}
	
	function enableSubmission() {
	    document.forms['uf'].elements['submit'].disabled=false;
	    document.forms['uf'].elements['cancel'].disabled=false;
	    document.body.style.cursor = "default";
		var hglass = document.getElementById("hourglass");
		hglass.style.display = "none";
	}
	
	$(function() {
		$( "#datepicker" ).datepicker( {
	          changeMonth: true,
	          changeYear: true,
	          showOtherMonths: true,
	          showButtonPanel: true,
	          //showAnim: 'fold',
	          //showOn: 'both',
	          buttonText: 'Choose Date',
	          dateFormat: 'yy-mm-dd',
	          minDate: '+1d',
	          maxDate: '+5y',
	          hideIfNoPrevNext: true
	      });
	});
	
	function togglePublic() {
		document.forms['uf'].elements['public'].checked=false;
		document.forms['uf'].elements['pickdate'].disabled=false;
		document.forms['uf'].elements['pickdate'].focus();
		return false; 
	}
	
	
</script>		
		
<div class="formbox">
	<form method="post" action="biiuploadExperiment" enctype="multipart/form-data" name="uf" onsubmit="disableSubmission()">
           <table border="0" cellpadding="5px" cellspacing="0px">
	        <tr class="formheader">
	             <th class="tableheader" colspan="2"><spring:message code="msg.upload" /> </th>
	        </tr>
	        <tr>
	            <td colspan='2'>&nbsp;</td>
	        </tr>

	        <tr>
	            <td><spring:message code="label.isatabZipFile" />:</td>
	            <td><input type="file" name="file" /></td>
	        </tr>
	       
	        <tr>
	        	<td colspan='2'> <hr/> </td>
	        </tr>
	        <tr>
	            <td colspan='2'><b><spring:message code="label.experimentMsgPublic" /> </b></td>
	        </tr>
	        <tr>
	            <td><spring:message code="label.experimentstatuspublic" />:</td>
	            <td><input type="checkbox" name="public" onClick="this.form.pickdate.disabled=this.checked"/></td>
	        </tr>
	        <tr>
	        	<td><spring:message code="label.publicDate"/></td>
				<td>
					<input type="text" name="pickdate" id="datepicker" readonly="readonly" />
					<input type="image" src="img/calendar.gif" onclick="return togglePublic()" />
				</td> 
	        </tr>
	       	<tr>
	        	<td colspan='2'> <hr/> </td>
	        </tr>
	        
	        <tr>
	            <td height="100px" colspan='2'><input name="submit" type="submit" value="<spring:message code="label.upload"/>">
	            <a href="index"><input type="button" name="cancel" value="<spring:message code="label.cancel"/>" /></a>
	            </td>
	        </tr>
	      
	       
	        <tr>
	            <td colspan='2'>
	            <div id="hourglass">
	            <img src="img/wait.gif"/>&nbsp; <b> <spring:message code="msg.pleaseWaitForUpload"/></b>
	            </div>
	            </td>
	        </tr>
	        
	   		<tr>
	   			<td colspan='2'>
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

