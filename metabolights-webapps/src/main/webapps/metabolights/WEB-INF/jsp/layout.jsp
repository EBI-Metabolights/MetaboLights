<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta name="description" content="The European Bioinformatics Institute - Metabolights" />
		<meta http-equiv="Content-Language" content="en-GB" />
		<meta http-equiv="Window-target" content="_top" />
		<meta name="no-email-collection" content="http://www.unspam.com/noemailcollection/" />
		
		<!--  EBI style sheets -->
		<link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/contents.css"	type="text/css" />
		<link rel="stylesheet" href="http://www.ebi.ac.uk/inc/css/userstyles.css" type="text/css" />
		<script src="http://www.ebi.ac.uk/inc/js/contents.js" type="text/javascript"></script>

		<!-- Metabolights stylesheet -->
		<link rel="stylesheet" type="text/css" href="css/styles.css" media="all" />
	
		<title><tiles:insertAttribute name="title" ignore="true" /></title>
	</head>

	<body onLoad="if(navigator.userAgent.indexOf('MSIE') != -1) {document.getElementById('head').allowTransparency = true;}">
		<!-- EBI header -->
		<div class="headerdiv" id="headerdiv" style="position: absolute; z-index: 1;">
			<iframe src="http://www.ebi.ac.uk/inc/head.html" name="head" id="head"
				frameborder="0" marginwidth="0px" marginheight="0px" scrolling="no"
				width="100%" style="position: relative; z-index: 1; height: 57px;"></iframe>
		</div>
		<div class="wrapper">
			<div id="topAndBody">
				<tiles:insertAttribute name="header"/>
				<div style="width: 1000px; float: left">
					<tiles:insertAttribute name="body" />
				</div>
			</div>
		</div> 

		<table class="footerpane" id="footerpane" summary="The main footer pane of the page">
			<tr>
				<td colspan="4" class="footerrow">
					<div class="footerdiv" id="footerdiv" style="z-index: 2;">
						<iframe src="http://www.ebi.ac.uk/inc/foot.html" name="foot" frameborder="0"
							marginwidth="0px" marginheight="0px" scrolling="no" height="22px"
							width="100%" style="z-index: 2;">[Your user agent does
							not support frames or is currently configured not to display
							iframes.]</iframe>
					</div></td>
			</tr>
		</table>
		</body>
</html>


</html>
