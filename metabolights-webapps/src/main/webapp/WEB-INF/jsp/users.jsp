<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="javascript/jquery-1.6.2.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="javascript/jquery-ui-1.8.15.custom.min.js" type="text/javascript" charset="utf-8"></script>


<c:if test="${not empty usersMap}">
	<br/>
	<br/>
    <script type='text/javascript' src='https://www.google.com/jsapi'></script>
   	<script type='text/javascript'>
    		google.load('visualization', '1', {'packages': ['geochart']});
    		google.setOnLoadCallback(drawRegionsMap);

	      function drawRegionsMap() {
	        var data = google.visualization.arrayToDataTable([
	          ['Country', 'Users']
	          <c:forEach var="country" items="${usersMap}">
	          	,['${country.key}', '${country.value}']
	          </c:forEach>
	        ]);
	
	        var options = {
	                colorAxis: {minValue:0, colors: ['white', 'blue']},
	                magnifyingGlass:{enable: true, zoomFactor: 7.5}
	              };
	
	        var chart = new google.visualization.GeoChart(document.getElementById('usersMapDiv'));
	        chart.draw(data);
	    };
    </script>

</c:if>	

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
	    Users
	    <br/> <br/>
	</div>
	<div>
		<br/>
		<c:if test="${not empty users}">
			<table cellpadding="5px" cellspacing="0px">
				<tr>
					<th><spring:message code="label.userName"/></th>
					<th><spring:message code="label.firstName"/></th>
					<th><spring:message code="label.lastName"/></th>
					<th><spring:message code="label.affili"/></th>
					<th><spring:message code="label.country"/></th>
					<th><spring:message code="label.userStatus"/></th>
					<th><spring:message code="label.userJoinDate"/></th>
				</tr>
				<c:forEach var="user" items="${users}">
					<tr>
						<td>${user.userName}</td>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>
							<c:choose>
								<c:when test="${not empty user.affiliationUrl}">
									<a href="${user.affiliationUrl}" target="_blank">${user.affiliation}</a>
								</c:when>
								<c:otherwise>${user.affiliation}</c:otherwise>
							</c:choose>
						</td>
						<td>${user.country}</td>
						<td>${user.status}</td>
						<td>${user.joinDate}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
	</div>
	<div id="usersMapDiv" style="width: 900px; height: 500px;">
		
	</div>
