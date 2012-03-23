<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="javascript/jquery-1.6.2.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="javascript/jquery-ui-1.8.15.custom.min.js" type="text/javascript" charset="utf-8"></script>

<script>
$(function() {
	$( "#tabs" ).tabs();
});
</script>

	<div class="text_header plain">
	    Configuration
	    <br/> <br/>
	</div>


	<div id="tabs">
		<ul>
			<li><a href="#hibTab">Hibernate Properties</a></li>
			<li><a href="#appTab">Application Properties</a></li>
			<li><a href="#valTab">Validations</a></li>
			
		</ul>
		<div id="hibTab">
			<br/>
			<c:if test="${not empty hibProps}">
				<table cellpadding="5px" cellspacing="0px">
					<tr><th>Property</th><th>Value</th></tr>
					<c:forEach var="property" items="${hibProps}">
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
		
		<div id="appTab">
			<br/>
			<c:if test="${not empty appProps}">
				<table cellpadding="5px" cellspacing="0px">
					<tr><th>Property</th><th>Value</th></tr>
					<c:forEach var="property" items="${appProps}">
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
		
	</div>

