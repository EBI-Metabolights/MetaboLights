<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

	
	<h3>Configuration page</h3>
	

	<div id="tabs">
		<ul>
			<li><a href="#appTab">Application Properties</a></li>
			<li><a href="#valTab">Validations</a></li>
			<li><a href="#queueTab">Queue</a></li>
			<li><a href="#studyHealthTab">Study Health Tab</a></li>
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
						<th>Study</th>
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
		<c:if test="${not empty processFolder}">
			<br/><b>Process Folder</b>
			<table cellpadding="5px" cellspacing="0px">
				<thead class='text_header'>
					<tr>
						<th>File name</th>
					</tr>
				</thead>			
	       	<c:forEach var="file" items="${processFolder}">
				<tr><td>${file.name}</td></tr>
	        </c:forEach>
	        </table>
		</c:if>
		
		<c:if test="${not empty errorFolder}">
			<br/><b>Error Folder</b>
			<table cellpadding="5px" cellspacing="0px">
				<thead class='text_header'>
					<tr>
						<th>File name</th>
					</tr>
				</thead>			
	       	<c:forEach var="file" items="${errorFolder}">
				<tr><td>${file.name}</td></tr>
	        </c:forEach>
	        </table>
		</c:if>
		
		<c:if test="${not empty backUpFolder}">
			<br/><b>BackUp Folder</b>
			<table cellpadding="5px" cellspacing="0px">
				<thead class='text_header'>
					<tr>
						<th>File name</th>
					</tr>
				</thead>			
	       	<c:forEach var="file" items="${backUpFolder}">
				<tr><td>${file.name}</td></tr>
	        </c:forEach>
	        </table>
		</c:if>
		<br/><br/>
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
	<div id="studyHealthTab">
		<br/>
		<c:if test="${not empty studiesHealth}">
			<table cellpadding="5px" cellspacing="0px">
				<tr><th>Study</th><th>is Public?</th><th>Must be under</th><th>is it there?</th></tr>
				<c:forEach var="study" items="${studiesHealth}">
					<tr>
						<td>${study.identifier}</td>
						<td>${study.isPublic}</td>
						<td>${study.studyPath}</td>
						<td <c:if test="${not study.isThere}">class="error"</c:if> >
							${study.isThere}
						</td>
						
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
	</div>

