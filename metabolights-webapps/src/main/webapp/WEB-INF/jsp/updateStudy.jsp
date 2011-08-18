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
    //document.forms['uf'].elements['cancel'].disabled=true;
    document.body.style.cursor = "wait";
	var hglass = document.getElementById("hourglass");
	hglass.style.display = "block";		
}

function enableSubmission() {
    document.forms['uf'].elements['submit'].disabled=false;
    //document.forms['uf'].elements['cancel'].disabled=false;
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

function syncDateBox() {
	
	document.forms['uf'].elements['pickdate'].disabled=document.forms['uf'].elements['public'].checked;
	
	if (document.forms['uf'].elements['public'].checked){
		//$("#datepicker").datepicker("setDate", new Date());
		document.forms['uf'].elements['pickdate'].value = "";
	}
}
</script>	

<div class="formbox">
	<table border="0px" "cellpadding="15px" cellspacing="0px" width="90%">
	    <tr class="formheader">
	        <th class="tableheader">
	        	<c:if test="${not empty title}">
					<c:out value="${title}"/>
				</c:if>
	        </th>
	    </tr>
		<c:if test="${not empty message}">
			<tr>
				<td><br/>${message}<br/><br/><br/></td>
			</tr>
		</c:if>
		<c:if test="${not empty searchResult}">
			<tr>
				<td>
					<%@include file="entrySummary.jsp" %>
				</td>
			</tr>
		</c:if>
	</table>
	
	<c:if test="${empty updated}">
	
		<form method="post" action="${action}" name="uf" onsubmit="disableSubmission()">
	    	<input type="hidden" value="${study}" name="study"/>
		    <table cellpadding="15px" cellspacing="0px" width="90%">
				<c:if test="${isUpdateMode}">
					<tr><td>File upload stuff here</td></tr>
				</c:if>
			    <tr>
		            <td><spring:message code="label.experimentstatuspublic" />:</td>
		            <td><input type="checkbox" name="public" onClick='syncDateBox()'/></td>
		        </tr>
		        <tr>
		        	<td><spring:message code="label.publicDate"/></td>
					<td>
						<input type="text" name="pickdate" id="datepicker" readonly="readonly" />
						<input type="image" src="img/calendar.gif" onclick="return togglePublic()" style="vertical-align: middle"/>
					</td> 
		        </tr>
		        <tr>
		        	<td colspan='2'>
						<input type="submit" name="submit" value="${submitText}">
		        	</td>
		        </tr>
		        <tr>
		            <td colspan='2'>
		            <div id="hourglass">
		            <img src="img/wait.gif"/>&nbsp; <b> <spring:message code="msg.pleaseWaitForUpload"/></b>
		            </div>
		            </td>
		        </tr>
			</table>
		</form>   
	</c:if>
</div>