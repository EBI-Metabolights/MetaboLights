<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">  
<html>
   <head>
      <link rel="stylesheet" type="text/css" href="css/styles.css" media="all" />
      <title><tiles:insertAttribute name="title" ignore="true" /></title> 
   </head>

	<div class="wrapper">          
	    <div id="topAndBody">
	      <tiles:insertAttribute name="header" />
	       <div style="width:1000px;border:1px solid white;float:left">
    	      <tiles:insertAttribute name="body" />
           </div>
        </div>
    </div>
   <tiles:insertAttribute name="footer" />
</html>
