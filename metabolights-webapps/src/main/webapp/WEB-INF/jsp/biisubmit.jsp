<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
	
	function showWait(){

	}

</script>

<script type="text/javascript">

	$(document).ready(function() {
		
		$("#hourglass").dialog({
		    create: function(){
		    	$('.ui-dialog-titlebar-close').removeClass('ui-dialog-titlebar-close');
		    },
		    width: 400,
		    height: 150,
		    modal: true,
		    autoOpen: false
		});

		
	});
	
	function disableSubmission() {
	    document.body.style.cursor = "wait";
	    $('.ui-dialog-titlebar').hide();
		$( "#hourglass" ).dialog( "open" );
	}

	$(function() {
		$( "#datepicker" ).datepicker( {
	          changeMonth: true,
	          changeYear: true,
	          showOtherMonths: true,
	          buttonText: 'Choose Date',
	          dateFormat: 'dd-M-yy',
	          minDate: '0',
	          maxDate: '+1y'
	      });
	});
	
	function toggleDate() {
        document.forms['uf'].elements['pickdate'].focus();
		return false; 
	}
	
</script>

<h2><spring:message code="msg.upload" /></h2>
<p><spring:message code="msg.upload.desc"/></p>
<p><spring:message code="msg.upload.desc2"/></p>

<c:if test="${not empty queueditems}">
	<spring:message code="msg.upload.queueditems" />
	<br />
	<table>
		<thead class='text_header'>
			<tr>
				<th>File name</th>
				<th>Status</th>
				<th>Type</th>
				<th>Public by</th>
			</tr>
		</thead>
		<c:forEach var="qi" items="${queueditems}">
			<tr>
				<td>${qi.originalFileName}</td>
				<td>QUEUED</td>
				<td><c:choose>
						<c:when test="${not empty qi.accession}">${qi.accession}</c:when>
						<c:otherwise>NEW</c:otherwise>
					</c:choose></td>
				<td><fmt:formatDate pattern="dd-MMM-yyyy"
						value="${qi.publicReleaseDate}" /></td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<br />
</c:if>
<br/>

<form method="post" action="queueExperiment" enctype="multipart/form-data" name="uf" onsubmit="disableSubmission()">
	<hr/>&nbsp;<br/>	
	<div class="grid_6 alpha prefix_1"><spring:message code="label.isatabZipFile" />:</div>
	<div class="grid_17 omega">
		<input type="file" name="file" />
    </div>
	<div class="grid_23 alpha omega prefix_1">
		<strong><br/><spring:message code="label.experimentMsgPublic" /></strong>
	</div>
	<br/>
	<div class="grid_6 alpha prefix_1"><spring:message code="label.publicDate"/>:</div>
	<div class="grid_17 omega">
		<input type="image" src="img/ebi-icons/16px/calendar.png" onclick="return toggleDate()" />
		<input type="text" name="pickdate" id="datepicker" readonly="readonly" size="12"/>
    </div>
	<div id="hideableButtons" class="grid_17 prefix_7 alpha omega">
		&nbsp;<br/>
		<input name="submit" type="submit" class="submit" value="<spring:message code="label.upload"/>">		
		<input name="cancel" type="button" class="submit cancel" value="<spring:message code="label.cancel"/>" onclick="location.href='index'">
    </div>

   	<div id="hourglass">
   		<img src="img/wait.gif" alt="Please wait"/>&nbsp;<b><spring:message code="msg.pleaseWaitForUpload"/></b>
   	</div>
	<hr/>
	    
</form> 

 <c:if test="${not empty message}">
    <div class="error">
       <c:out value="${message}" />
    </div>
 </c:if>