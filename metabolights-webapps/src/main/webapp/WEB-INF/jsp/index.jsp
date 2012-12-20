<%@taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>


<script>

    // increase the default animation speed to exaggerate the effect
    $.fx.speeds._default = 1500;
    $(function() {
        $( "#dialog" ).dialog({
            autoOpen: true,
            show: "slide",
            position: ['center',200]
        });

    });

</script>
<br/>
<c:if test="${not empty message}">
    <div id="dialog" title="MetaboLights message">
        <p><b><c:out value="${message}"/></b></p>
    </div>
</c:if>


<c:if test="${not empty gallery}">
	<br/>
	<div id="boxes" class="grid_16 alpha omega">
	 	<c:forEach var="item" items="${gallery}">
			<div> 
			<a href="${item.url}">
			<table>
                <tr>
                    <c:if test="${not empty item.imgUrl}">
                        <td style="width:150px;"><img src="${item.imgUrl}" onerror="this.src='img/large_noImage.gif';"/></td>
                        <td>
                            <h4>${item.title}</h4>
                            <p>${fn:substring(item.description, 0, 90)}...</p>
                        </td>
                    </c:if>
                    <c:if test="${empty item.imgUrl}">
                        <td>
                            <h4>${item.title}</h4>
                            <p>${fn:substring(item.description, 0, 120)}...</p>
                        </td>
                    </c:if>
                </tr>
			</table>

			</a>
			</div>
	 	</c:forEach>
	</div>
	
</c:if>

<br/><br/>
<div class="grid_8 alpha">
	<div class="grid_24">
	   <h3><spring:message code="title.serviceName" /></h3>
	   <p><strong><spring:message code="msg.metabolights" /></strong></p><p><spring:message code="msg.metabolightsAbout1" /> <spring:message code="msg.metabolightsAbout" /></p>
	</div>
</div>

<div class="grid_8">
	<div class="grid_24">
			<h3><spring:message code="title.download" /></h3>
	  		<p>
	  			<a class="noLine" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/submissionTool/ISAcreatorMetaboLights.zip"><div class="ebiicon clock"></div></a>
	    	    <spring:message code="msg.metabolightsAbout12" />
	        </p>
	   		<p>
		    	<a class="noLine" href="ftp://ftp.ebi.ac.uk/pub/databases/metabolights/"><div class="ebiicon download"></div></a>
	    		<spring:message code="msg.metabolightsAbout7" />
	    	</p>
	</div>
</div>

<div class="grid_8 omega">
	<div class="grid_24">
	
		<h3><spring:message code="title.submit"/> </h3>
		<div class="grid_24"><p>&nbsp;</p></div>
	    <div class='grid_20 alpha omega prefix_2'>
	        <div class="bigbutton maincolorI">
		        <a href="submittoqueue">
		            <span class="bigfont"><spring:message code="label.submitNewStudy"/></span><br/>
			        <span><spring:message code="label.submitNewStudySub"/></span>
		        </a>
	        </div>
	    </div>
		<div class="grid_24"><p>&nbsp;</p></div>
	    <div class='grid_20 alpha omega prefix_2'>
	    	<div class="bigbutton seccolorI">
		        <a href="mysubmissions?status=PRIVATE">
			        <span class="bigfont"><spring:message	code="label.updateOldStudy"/></span></br>
			    	<span><spring:message code="label.updateOldStudySub"/></span>
		      	</a>
	      	</div>
	    </div>
	</div>
</div>



<script>
	/* $('.coolframe').shadow('lifted'); */
	
	$(function(){
	 $('#boxes').movingBoxes({
		 startPanel: 1,
		 currentPanel: 'current',
		 speed: 0,
		 initialized: function(e, slider, tar){
		 	slider.options.speed = 500;
		 },
		 reducedSize:1,
		 wrap: true,
		 fixedHeight: true
	 }); // add any non-default options inside here
	}); 
	
</script>

 
