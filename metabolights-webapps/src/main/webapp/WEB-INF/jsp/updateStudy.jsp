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
        document.body.style.cursor = "wait";
        var hglass = document.getElementById("hourglass");
        hglass.style.display = "block";
        var buttons = document.getElementById("hideableButtons");
        buttons.style.display = "none";
    }

    function enableSubmission() {
        document.body.style.cursor = "default";
        var hglass = document.getElementById("hourglass");
        hglass.style.display = "none";
        var buttons = document.getElementById("hideableButtons");
        buttons.style.display = "block";
    }

	$(function() {
		$( "#datepicker" ).datepicker( {
	          changeMonth: true,
	          changeYear: true,
	          showOtherMonths: true,
	          buttonText: 'Choose Date',
	          dateFormat: 'dd-M-yy',
	          minDate: '0',
	          maxDate: '+5y'
	      });
	});

	function toggleDate() {
        document.forms['uf'].elements['pickdate'].focus();
		return false;
	}

	function toggle(anctag,darg,showMsg,hideMsg) 
	{
	  var ele = document.getElementById(darg);
	  var text = document.getElementById(anctag);
	  if(ele.style.display == "block") 
	  {
	    ele.style.display = "none";
	    text.innerHTML = showMsg;
	  }
	  else 
	  {
	    ele.style.display = "block";
	    text.innerHTML = hideMsg;
	  }
	} 
	
</script>


<div class="formbox">
	<div>

	    <div class="text_header plain">
	        <c:if test="${not empty title}">
			    <c:out value="${title}"/>
			</c:if>
	    </div>

		<c:if test="${not empty message}">
			<div>
				<br/>${message}<br/><br/>
			</div>
		</c:if>
		
		<c:if test="${not empty ftpLocation}">
            <div>
                <div class='iscell left'>
                    <div class='multi-line-button highlight'>
                        <IMG src="img/ebi-icons/16px/download.png">
                        <a href="${ftpLocation}"  style="text-decoration: none; color: #000000;font-size: 14px;">
                            <spring:message code="label.ftpDownload"/></a>
                    </div>
                </div>
                <br/><br/><br/>
            </div>
		</c:if>

        <br/><br/>

		<c:if test="${not empty searchResult}">
			<div>
			    <br/>
				<c:set var="nopublish" value="true"/>
				<%@include file="entrySummary.jsp" %>
			</div>
		</c:if>

	</div>
	
	<c:if test="${empty updated}">
		<br/><br/>
		<form method="post" action="${action}" enctype="multipart/form-data" name="uf" onsubmit="disableSubmission()">
	    	<input type="hidden" value="${study}" name="study"/>
		    <div>

				<c:if test="${isUpdateMode}">

				    <div>

                        <div class='iscell left'>
					        <spring:message code="label.isatabZipFile" />:
                        </div>

                        <div class='iscell'>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        </div>

                        <div class='iscell'>
                            <input type="file" name="file"/>
                        </div>

					</div>

				</c:if>

                <br/><br/>

		        <div>

                    <div class='iscell left'>
                        <spring:message code="label.publicDate" />:
                    </div>

                    <div class='iscell'>
                        &nbsp;&nbsp;&nbsp;
                    </div>

                    <div class='iscell'>
                        <input type="image" src="img/ebi-icons/16px/calendar.png" style="vertical-align: middle" onclick="return toggleDate()"/>
						<input type="text" name="pickdate" id="datepicker" readonly="readonly" size="12" value="<fmt:formatDate pattern="dd-MMM-yyyy" value="${defaultDate}"/>"/>
                    </div>

		        </div>

                <br/><br/><br/><br/>

		        <div>
		        	<div>
			            <div id="hideableButtons" style="display:none;">
			          		<div class='iscell left'>
		                        <input name="submit" type="submit" class="multi-line-button main" value="${submitText}">
		                    </div>
		                    <div class='iscell'>
		                        <br/><a href="index" name="cancel"><spring:message code="label.cancel"/></a>
		                    </div>
						</div>
		        	</div>
		        	<div>
		        		<c:if test="${not empty validationmsg}">
		        			<span class="error">${validationmsg}</span>
		        		</c:if>

		        		<c:if test="${not empty isatablog}">
		        			<br/>
		        			<a href="javascript:toggle('logTag','isatablog','Show log', 'Hide log');" id="logTag">Show log</a>
			                <br/>
			                <div id="isatablog" class="expandbox">
			                    <ul id="resultListText">
			                        <c:forEach var="log" items="${isatablog}">
			                        	<c:set var="event" value="${log.logEvent}"/> 
			                        	<c:set var="level" value="${event.level}"/> 
			                        	<c:if test="${level eq 'ERROR'}">
			                            <li>${event.message}</li>
			                            </c:if>
			                        </c:forEach>
			                    </ul>
			                </div>
						</c:if>
		        	</div>
		        </div>
		        <div>
		            <div>
                        <div class='iscell left' id="hourglass">
                            <img src="img/wait.gif"/>&nbsp; <b> <spring:message code="msg.pleaseWaitForUpload"/></b>
                        </div>
		            </div>
		        </div>
			</div>
		</form>   
	</c:if>
    <br/><br/><br/><br/>
</div>
