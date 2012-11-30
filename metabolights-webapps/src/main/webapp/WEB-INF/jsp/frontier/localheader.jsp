<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
${localfrontierheader}
<c:if test="${!empty freeTextQuery}">
<script>
	$('[name="freeTextQuery"]').val('${freeTextQuery}');
</script>
</c:if>

<script>
	/* For the functional ones, right alingment....*/
	$loginA = $('[href="login"]'); 
	$loginA.parent().addClass("functional");
	$loginA.addClass("icon icon-functional").attr("data-icon","l");
	$('[href="presubmit"]').parent().addClass("functional");
</script>

<sec:authorize ifAnyGranted="ROLE_SUBMITTER">
	
<%--
 	<div id="loginmenu" class="prefix_10">
	<nav>
		<ul class="" id="local-nav">
			<li class="first"><a href="<spring:url value="mysubmissions"/>"><spring:message code="menu.myStudies" /></a></li>
			<li class=""><a href="<spring:url value="myAccount"/>"><spring:message code="menu.myAccount" /></a></li>
			<sec:authorize ifAnyGranted="ROLE_SUPER_USER">
				<li class=""><a href="<spring:url value="/config"/>"><spring:message	code="menu.config" /></a></li>
				<li class=""><a href="<spring:url value="/users"/>"><spring:message code="menu.users" /></a></li>
				<li class=""><a href="<spring:url value="/ebeyehelp"/>"><spring:message code="menu.Ebeye" /></a></li>
			</sec:authorize>
			<li class=""><a href="<spring:url value="/j_spring_security_logout"/>"><spring:message code="menu.logout" /></a></li>
			<li class="last" id="closeloginmenu"><a href="#">X</a></li>
		</ul>
	</nav>
	</div>
--%>
	<script>
	
		
		$loginA.html('<sec:authentication property="principal.firstName" />');
		$loginA.attr("href", '<spring:url value="useroptions"/>');		
		//$loginA.html('<sec:authentication property="principal.firstName" /><span id="showloginmenu" class="smallArrow"></span>');
		//$loginA.attr("title", '<spring:message code="menu.myStudies" />');
		//$loginA.attr("href", '<spring:url value="mysubmissions"/>');
		//$loginA.attr("href", '#');
		/* 	
		$loginmenu = $("#loginmenu");
		$loginmenu.appendTo($('#local-masthead'));
		//$loginmenu.appendTo($loginA);
		$loginmenu.hide();
		
		
		$loginA.hover(
			function() {
				//$("#loginmenu").show();
			},
			function() {
				//$("#loginmenu").hide();
			});

		$loginA.click(
				function() {
					$("#loginmenu").show();
				});
		
		
		$loginmenu.hover(
				function() {
					//$("#loginmenu").show();
				},
				function() {
					$("#loginmenu").hide();
				});
		
		$closeitem = $('#closeloginmenu');
		$closeitem.click(function(){
			$loginmenu.hide();
		});
		*/
		
		
	</script>
</sec:authorize>
