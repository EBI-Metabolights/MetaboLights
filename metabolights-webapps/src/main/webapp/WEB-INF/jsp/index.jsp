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
	<div id="boxes" class="grid_14 alpha omega mb-wrapper">
	 	<c:forEach var="item" items="${gallery}">

            <c:set var="title" value="${item.title}"/>
            <c:set var="description" value="${item.description}"/>

            <div>
			<a href="${item.url}">
			<table>
                <tr>
                    <c:if test="${not empty item.imgUrl}">
                        <c:if test="${fn:length(title) gt 45}"><c:set var="title" value="${fn:substring(title, 0, 42)}..."/></c:if>
                        <c:if test="${fn:length(description) gt 90}"><c:set var="description" value="${fn:substring(description, 0, 87)}..."/></c:if>
                        <td style="width:150px;"><img src="${item.imgUrl}" onerror="this.src='img/large_noImage.gif';"/></td>
                        <td>
                            <h6>${title}</h6>
                            <p>${description}</p>
                        </td>
                    </c:if>
                    <c:if test="${empty item.imgUrl}">
                        <c:if test="${fn:length(title) gt 40}"><c:set var="title" value="${fn:substring(title, 0, 37)}..."/></c:if>
                        <c:if test="${fn:length(description) gt 120}"><c:set var="description" value="${fn:substring(description, 0, 117)}..."/></c:if>
                        <td>
                            <h6>${title}</h6>
                            <p>${description}</p>
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
	   <h2><spring:message code="title.serviceName" /></h2>
       <br/>
        <p><spring:message code="msg.metabolightsAbout1" /> <spring:message code="msg.metabolightsAbout" /></p>
	</div>
</div>

<div class="grid_8">
	<div class="grid_24">
			<h2><spring:message code="title.download" /></h2>
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
	
		<h2><spring:message code="title.submit"/> </h2>

	    <div class='grid_20 alpha omega prefix_2'>
            <a href="submittoqueue">
                <div class="bigbutton maincolorI">
                    <span class="bigfont"><spring:message code="label.submitNewStudy"/></span><br/>
                    <span><spring:message code="label.submitNewStudySub"/></span>
                </div>
            </a>
	    </div>
		<div class="grid_24"><p>&nbsp;</p></div>
	    <div class='grid_20 alpha omega prefix_2'>
            <a href="mysubmissions?status=PRIVATE">
    	    	<div class="bigbutton seccolorI">

			        <span class="bigfont"><spring:message	code="label.updateOldStudy"/></span></br>
			    	<span><spring:message code="label.updateOldStudySub"/></span>
		      	</div>
            </a>
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

 
