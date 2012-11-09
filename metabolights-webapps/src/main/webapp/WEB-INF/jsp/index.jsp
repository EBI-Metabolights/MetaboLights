<%@taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

<c:if test="${not empty message}">
    <div id="dialog" title="MetaboLights message">
        <p><b><c:out value="${message}"/></b></p>
    </div>
   <!-- <div class="messageBox"><c:out value="${message}" /></div>   -->
</c:if>

<c:if test="${empty message}">
   <div class="messageBox" style="border:0px; background:white">&nbsp;</div>
    <div class="grid_24 alfa center">
        <spring:message code="msg.searchSuggestions" />
    </div>
</c:if>

<div class="grid_24 alfa center">
   <img src="img/litehouseLogo.png"/>
</div>

<div class="grid_24 alfa center" style="text-align:center">
    <spring:message code="msg.metabolights" />
</div>
 
<div class="grid_24 alfa center">
    <br/>
    <spring:message code="msg.metabolightsAbout1" /> <spring:message code="msg.metabolightsAbout" />
    <br/>
 
    <br/>
    <br/>
</div>
