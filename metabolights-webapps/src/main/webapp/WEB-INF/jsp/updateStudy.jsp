<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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

	function submitStudy(){
		if (validate()){
			disableSubmission();
			return true;
		} else {
			return false;
		}
		
	}
	
	function validate(){
    	

    	isValid = true;

		$fileErrorSpan = $("#fileError")[0];
    	
    	// If we are uptading a study...other mode only have datepicker (update public release date.)
    	if ($fileErrorSpan != null){
        	$fileErrorSpan.innerText = "";

        	if ($("#studyfile").val() ==''){
        		$fileErrorSpan.innerText = '<spring:message code="BIISubmit.fileEmpty"/>';
        		isValid = false;
        	}
    	}

		$dateErrorSpan = $("#dateError")[0];
    	
		$dateErrorSpan.innerText = "";
		
    	if ($("#datepicker").val() ==''){
    		isValid = false;
    		$dateErrorSpan.innerText = '<spring:message code="BIISubmit.dateEmpty"/>';
    	}
    	
    	return isValid;
    }
	
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


<c:if test="${not empty title}">
    <h2><c:out value="${title}"/></h2>
</c:if>
<c:if test="${not empty message}">
	<p>${message}</p><br/>
</c:if>

<c:if test="${not empty ftpLocation}">
	<div class="grid_8 prefix_1 alpha omega">
        <a href="${ftpLocation}">
    		<div class="bigbutton maincolorI">
  				<span class="bigfont icon icon-functional" data-icon="=""> <spring:message code="label.ftpDownload" />
				</span>
        	</div>
        </a>
    </div>
    <p/>
</c:if>

<c:if test="${not empty searchResult}">
    <br/>
	<c:set var="nopublish" value="true"/>
	<div class="grid_22 prefix_1 alpha omega">
		<%@include file="entrySummary.jsp" %>
	</div>
	<p/>
</c:if>

<c:if test="${empty updated}">
	<form method="post" action="${action}" enctype="multipart/form-data" name="uf" id="updateStudyForm" onsubmit="return submitStudy()">
		&nbsp;<br/>	
	    <input type="hidden" value="${study}" name="study"/>
		<c:if test="${isUpdateMode}">
			<div class="grid_6 alpha prefix_1"><spring:message code="label.isatabZipFile" />:</div>
			<div class="grid_17 omega">
				<input type="file" name="file" id="studyfile" />
				<span id="fileError" class="error"></span>
		    </div>
		</c:if>
		<br/>

		<div class="grid_6 alpha prefix_1"><spring:message code="label.publicDate"/>:</div>
		<div class="grid_17 omega">
            <input type="hidden" name="owner" value="${searchResult.submitter.userName}">
			<input type="image" src="img/ebi-icons/16px/calendar.png" onclick="return toggleDate()" />
			<input type="text" name="pickdate" id="datepicker" readonly="readonly" size="12" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${defaultDate}"/>"/>
			<span id="dateError" class="error"></span>
	    </div>
		<div id="hideableButtons" class="grid_17 prefix_7 alpha omega">
			&nbsp;<br/>
			<input name="submit" type="submit" class="submit" value="${submitText}">		
			<input name="cancel" type="button" class="submit cancel" value="<spring:message code="label.cancel"/>" onclick="location.href='index'">
	    </div>
	
	   	<div id="hourglass">
	   		<img src="img/wait.gif" alt="Please wait"/>&nbsp;<b><spring:message code="msg.pleaseWaitForUpload"/></b>
	   	</div>
	</form> 

	<c:if test="${not empty validationmsg}">
		<span class="error">${validationmsg}</span>
	</c:if>

</c:if>
