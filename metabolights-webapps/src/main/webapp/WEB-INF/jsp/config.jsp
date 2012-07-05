<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="javascript/jquery-1.6.2.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="javascript/jquery-ui-1.8.15.custom.min.js" type="text/javascript" charset="utf-8"></script>

<script>
$(function() {
	$( "#tabs" ).tabs();
});
</script>

<script type="text/javascript">
    function doAjax(command) {
      $.ajax({
        url: 'switchqueue',
        data:{'command': command},
        success: function(data) {
          $('#startresponse').html(data);
        }
      });
    }
  </script>

	<div class="text_header plain">
	     <br/><br/>
	    Configuration
	    <br/><br/><br/> <br/>
	</div>


	<div id="tabs">
		<ul>
			<li><a href="#appTab">Application Properties</a></li>
			<li><a href="#valTab">Validations</a></li>
			<li><a href="#queueTab">Queue</a></li>
			
		</ul>
	
		
		<div id="appTab">
			<br/>
			<c:if test="${not empty props}">
				<table cellpadding="5px" cellspacing="0px">
					<tr><th>Property</th><th>Value</th></tr>
					<c:forEach var="property" items="${props}">
						<tr>
							<td>
								${property.key}
							</td>
							<td>
								${property.value}
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>

		<div id="valTab">
			<br/>
			<c:if test="${not empty validation}">
				<table cellpadding="5px" cellspacing="0px">
					<tr><th>Validation</th><th>Result</th></tr>
					<c:forEach var="test" items="${validation}">
						<tr>
							<td>
								${test.key}
							</td>
							<td <c:if test="${not test.value}">class="error"</c:if>	>								
								${test.value}
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
		</div>
		<div id="queueTab">
			<br/>
			<c:if test="${not empty queue}">
				<table cellpadding="5px" cellspacing="0px">
					<thead class='text_header'>
						<tr>
							<th>File name</th>
							<th>Status</th>
							<th>Type</th>
							<th>Public by</th>
						</tr>
					</thead>			
		       	<c:forEach var="qi" items="${queue}">
					<tr>
						<td>${qi.originalFileName}</td>
						<td>QUEUED</td>
						<td>
							<c:choose>
					        	<c:when test="${not empty qi.accession}">${qi.accession}</c:when>
								<c:otherwise>NEW</c:otherwise>
							</c:choose>
						</td>
						<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${qi.publicReleaseDate}"/></td>
					</tr>
		        </c:forEach>
		        </table>
			</c:if>
			<br/>
			<br/>
			<c:if test="${not empty queuerunnig}">
				<table>
					<tr>
						<td>Queue status</td>
						<td>
							<c:choose>
								<c:when test="${queuerunnig}">RUNNING<button onclick="doAjax('off')" title="Stop it!">Stop it!</button></c:when>
								<c:otherwise>STOPPED<button onclick="doAjax('on')" title="Start it!">Start it!</button></c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
				<div id="startresponse"></div>
				
			</c:if>
			
		</div>
		
		
	</div>

