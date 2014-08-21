<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 1/25/13 5:11 PM
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

<c:if test="${not empty usersMap}">
	<br/>
    <script type='text/javascript' src='http://www.google.com/jsapi'></script>
   	<script type='text/javascript'>
    		google.load('visualization', '1', {'packages': ['geochart']});
    		google.setOnLoadCallback(drawRegionsMap);

	      function drawRegionsMap() {
	        var data = google.visualization.arrayToDataTable([
	          ['Country', 'Users']
	          <c:forEach var="country" items="${usersMap}">,['${country.key}', ${country.value}]</c:forEach>
	        ]);
	
	        var options = {
	                  colorAxis: {minValue:0, colors: ['#EEF5F5','#006666','#FFD98F', '#feba12']}
	                };
	
	        var chart = new google.visualization.GeoChart(document.getElementById('visualization'));
	        chart.draw(data, options);
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

	<h2>Users</h2>
	<div style="max-height:300px; overflow: auto">
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
						<td><a href="userAccount?usrId=${user.userId}">${user.userName}</a></td>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>
							<c:choose>
								<c:when test="${not empty user.affiliationUrl}">
									<a href="http://${user.affiliationUrl}" target="_blank">${user.affiliation}</a>
								</c:when>
								<c:otherwise>${user.affiliation}</c:otherwise>
							</c:choose>
						</td>
						<td>${user.country}</td>
						<td>${user.status}</td>
						<td><fmt:formatDate pattern="dd-MMM-yyyy" value="${user.joinDate}"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
	</div>
	<div id="visualization" style="width: 900px; height: 500px;">
		
	</div>
