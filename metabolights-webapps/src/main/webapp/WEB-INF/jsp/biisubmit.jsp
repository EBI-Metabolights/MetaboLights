<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2015-Mar-11
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2015 EMBL - European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

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
	        dateFormat: 'dd-M-yy'
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

<h2><spring:message code="msg.upload" /></h2>
<p><spring:message code="msg.upload.desc"/></p>
<p><spring:message code="msg.upload.desc3"/></p>
<p><spring:message code="label.experimentMsgPublic"/></p>


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

<form method="post" action="queueExperiment" enctype="multipart/form-data" name="uf" onsubmit="disableSubmission()">
	<hr/>&nbsp;<br/>
	<div class="grid_6 alpha prefix_1"><spring:message code="label.isatabZipFile" />:</div>
	<div class="grid_17 omega">
		<input type="file" name="file" />
    </div>

	<div class="grid_6 alpha prefix_1"><spring:message code="label.publicDate"/>:</div>
	<div class="grid_17 omega">
		<input type="text" name="pickdate" id="datepicker" readonly="readonly" size="12"/>
        <input type="image" src="img/ebi-icons/16px/calendar.png" onclick="return toggleDate()" />
    </div>
    <div class="grid_6 alpha prefix_1"><spring:message code="msg.confirmValidation" /></div>
    <div class="grid_17 omega">
        <input type="checkbox" name="validated"/>
        &nbsp;<span class="ui-state-highlight ui-corner-all"><spring:message code="label.pleaseValidate" /></span>
    </div>



    <c:set var="currentUserId">
        <sec:authorize ifAnyGranted="ROLE_SUBMITTER">
            <sec:authentication property="principal.userId"/>
        </sec:authorize>
    </c:set>

    <c:if test="${not empty users}">
        <br/>&nbsp;<br/>
        <div class="grid_6 alpha prefix_1"><spring:message code="label.onBehalf"/>:</div>
        <div class="grid_17 omega">
            <select name="owner">
                <c:forEach var="user" items="${users}">
                    <c:if test="${user.userId == currentUserId}">
                        <option value="${user.userName}" SELECTED="true">${user.firstName}&nbsp;${user.lastName}</option>
                    </c:if>
                    <c:if test="${not (user.userId == currentUserId)}">
                        <option value="${user.userName}">${user.firstName}&nbsp;${user.lastName}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
    </c:if>

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
