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

<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>


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

<div class="container-fluid" id="app">
	<div class="col-md-12">
		<c:if test="${not empty title}">
			<h2><c:out value="${title}"/></h2>
		</c:if>
		<c:if test="${not empty message}">
			<p>${message}</p>
		</c:if>
	</div>
	<div class="col-md-12">
		<div class="col-md-4">
			<c:if test="${not empty ftpLocation}">
				<button href="${ftpLocation}" class="btn btn-primary">
					<i class="fa fa-download"></i>&nbsp;<spring:message code="label.ftpDownload" />
				</button>
			</c:if>
		</div>
	</div>
	<div class="col-md-12">
		<br>
		<p><span class="ui-state-highlight ui-corner-all"><spring:message code="msg.contactMetabolightsAboutReleaseDate" /></span></p>
	</div>
	<div class="col-md-12">
		<div class="col-md-12">
			<c:if test="${not empty liteStudy}">
				<br/>
				<c:set var="nopublish" value="true"/>
				<%@include file="studySummary.jsp" %>
			</c:if>
		</div>
	</div>
	<div class="col-md-12">
		<br><br>
		<div class="col-md-6 col-md-offset-3 well">
			<c:if test="${empty updated}">
				<form method="post" action="${action}" enctype="multipart/form-data" name="uf" id="updateStudyForm" onsubmit="return submitStudy()">

					<input type="hidden" value="${study}" name="study"/>

					<c:if test="${isUpdateMode}">

						<div class="form-group">
							<label for="studyfile"><spring:message code="label.isatabZipFile" /></label>
							<input type="file" class="form-control" name="file" id="studyfile" />
							<span id="fileError" class="error"></span>
						</div>
					</c:if>

					<div class="form-group">
						<label for="studyfile"><spring:message code="label.publicDate"/>:</label>
						<input type="text" name="pickdate" id="datepicker" readonly="readonly" size="12" placeholder="<fmt:formatDate pattern="dd-MMM-yyyy" value="${releaseDate}"/>"/>
						<input type="image" src="img/ebi-icons/16px/calendar.png" onclick="return toggleDate()" />
						<span id="dateError" class="error"></span>
					</div>

					<div class="form-group">
						<label for="studyfile"><spring:message code="msg.confirmValidation" />:</label>
						<input type="checkbox" name="validated"/>
					</div>

					<div id="hideableButtons" class="form-group">
						<input name="submit" type="submit" class="submit" value="${submitText}">
						<input name="cancel" type="button" class="submit cancel" value="<spring:message code="label.cancel"/>" onclick="location.href='index'">
					</div>

					<div id="hourglass">
						<img src="img/wait.gif" alt="Please wait"/>&nbsp;<b><spring:message code="msg.pleaseWaitForUpload"/></b>
					</div>

					<c:if test="${not empty validationmsg}">
						<span class="error">${validationmsg}</span>
					</c:if>
				</form>
			</c:if>
		</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
							aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="myModalLabel">{{ study.studyIdentifier }}:&nbsp;{{ study.title }}</h4>
				</div>
				<div class="modal-body">
					<p>
						<b>Description: </b><br>
						{{ study.description }}
					</p><br>
					<div class="row">
						<div class="col-md-12">
							<div class="row">
								<div class="study--infopanel">
									<div class="col-md-5 no--padding">
										<small><i class="fa fa-user"></i>&nbsp;<spring:message code="ref.msg.CitationAuthors"/>:
                                                <span id="aff" v-for="contact in study.contacts" >
                                                   <strong>{{contact.firstName}}&nbsp;{{contact.lastName}}</strong>
                                                </span>
										</small>
									</div>
									<div class="col-md-7 no--padding">
										<small class="pull-right">
											<i class="fa fa-calendar"></i>&nbsp;
											<spring:message code="label.subDate"/>:
											<strong>{{study.studySubmissionDate | formatDate}}</strong>
											<spring:message code="label.releaseDate"/>: <strong>{{study.studyPublicReleaseDate | formatDate}}</strong>
											<spring:message code="label.updateDate"/>: <strong>{{study.updateDate | formatDate}}</strong>
										</small><br>
										<small class="pull-right" id="mStudyStatus">
											<i class="fa fa-user">&nbsp;</i><spring:message code="label.subm"/>:&nbsp;
                                                   <span v-for="user in study.users">
                                                       <a href="mailto:{{user.userName}}?subject=<spring:message code="msg.emailStudyLinkSubject"/>&nbsp;">{{user.fullName}}</a>
                                                   </span>
											&nbsp;|&nbsp;
											<i class="fa fa-bookmark"></i>&nbsp;<spring:message
												code="ref.msg.status"/>:&nbsp;{{ study.studyStatus }}
											<%-- &emsp;
                                            <button type="button" class="btn btn-default btn-xs" data-toggle="tooltip" data-placement="bottom" id="tourButton" title="Study Tour"><i class="fa fa-compass"></i></button>--%>
										</small>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-12">&nbsp;</div>
						<div class="col-md-4">
							<div class="panel panel-default">
								<div class="panel-heading"><span class="glyphicon glyphicon-globe"
																 aria-hidden="true"></span>&nbsp;
									<spring:message code="label.organisms"/></div>
								<div class="panel-body">
                                           <span v-for="organism in study.organism">
                                            <span>
                                                {{ organism.organismName }};
                                            </span>
                                           </span>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="panel panel-default">
								<div class="panel-heading"><span class="glyphicon glyphicon-list"
								<div class="panel-heading"><span class="glyphicon glyphicon-list"
																 aria-hidden="true"></span>&nbsp;
									<spring:message code="label.studyDesign"/></div>
								<div class="panel-body">
                                           <span v-for="descriptor in study.descriptors">
                                            <span>
                                                {{ descriptor.description }};
                                            </span>
                                           </span>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="panel panel-default">
								<div class="panel-heading"><span class="glyphicon glyphicon-tags"
																 aria-hidden="true"></span>&nbsp;
									<spring:message code="label.experimentalFactors"/></div>
								<div class="panel-body">
                                    <span v-for="factor in study.factors">
                                            <span>
                                                {{ factor.name }};
                                            </span>
                                    </span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</div>


<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/1.0.10/vue.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue-resource/0.1.17/vue-resource.js"></script>

<script>
	var app = new Vue({
		el: '#app',
		data: {
			selectedStudy: null,
			study: null
		},
		methods: {
			studyQuickView: function (id) {
				this.selectedStudy = id;
				this.getStudyDetails();
			},
			getStudyDetails: function () {
				this.$http.get('${pageContext.request.contextPath}/webservice/study/' + this.selectedStudy, function (data, status, request) {
					this.study = data.content;
					console.log(data.content);
					$('#myModal').modal('show');
				}).error(function (data, status, request) {
					console.log(data);
					console.log(status);
					console.log(request);
				});
			}
		},
		filters: {
			formatDate: function (value) {
				var a = new Date(value);
				var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
				var year = a.getFullYear();
				var month = months[a.getMonth()];
				var date = a.getDate();
				var hour = a.getHours();
				var min = a.getMinutes();
				var sec = a.getSeconds();
				var time = date + ' ' + month + ' ' + year ;
				return time;
			}
		}
	})
</script>
