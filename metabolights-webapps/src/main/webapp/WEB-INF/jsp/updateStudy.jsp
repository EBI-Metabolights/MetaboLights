<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 6/5/14 3:10 PM
  ~ Modified by:   conesa
  ~
  ~
  ~ ©, EMBL, European Bioinformatics Institute, 2014.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

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
		var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
		var d = new Date("${releaseDate}");
		var prevDate = "" + d.getDate()+"-"+ monthNames[d.getMonth()]+"-"+ d.getFullYear();

		$( "#datepicker" ).datepicker( {
	        changeMonth: true,
	        changeYear: true,
	        showOtherMonths: true,
	        buttonText: 'Choose Date',
	        dateFormat: 'dd-M-yy',
			setDate: prevDate,
			defaultDate: prevDate

			<sec:authorize access="!hasRole('ROLE_SUPER_USER')">
				,minDate: '+7',
				maxDate: '+1y'
			</sec:authorize>
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
	<p>${message}</p>
</c:if>

<c:if test="${not empty ftpLocation}">
	<div class="grid_8 prefix_1 alpha omega">
        <a href="${ftpLocation}">
    		<div class="bigbutton maincolorI">
  				<span class="bigfont icon icon-functional" data-icon="="> <spring:message code="label.ftpDownload" />
				</span>
        	</div>
        </a>
    </div>
    <p/>
</c:if>

<p><span class="ui-state-highlight ui-corner-all"><spring:message code="msg.contactMetabolightsAboutReleaseDate" /></span></p>

<c:if test="${not empty liteStudy}">
    <br/>
	<c:set var="nopublish" value="true"/>
	<div class="grid_22 prefix_1 alpha omega">
		<%@include file="studySummary.jsp" %>
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
            <%--<input type="hidden" name="owner" value="${searchResult.submitter.userName}">--%>
            <input type="text" name="pickdate" id="datepicker" readonly="readonly" size="12" placeholder="<fmt:formatDate pattern="dd-MMM-yyyy" value="${releaseDate}"/>"/>
			<input type="image" src="img/ebi-icons/16px/calendar.png" onclick="return toggleDate()" />
			<span id="dateError" class="error"></span>
	    </div>
        <div class="grid_6 alpha prefix_1"><spring:message code="msg.confirmValidation" /></div>
        <div class="grid_17 omega">
            <input type="checkbox" name="validated"/>
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
