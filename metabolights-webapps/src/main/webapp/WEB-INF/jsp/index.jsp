<%@taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page language="java" contentType="text/html; charset=UTF-8"%>

<script type="text/javascript" src="javascript/jquery-1.6.2.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="javascript/jquery-ui-1.8.15.custom.min.js" type="text/javascript" charset="utf-8"></script>

<script>

    // increase the default animation speed to exaggerate the effect
    $.fx.speeds._default = 1500;
    $(function() {
        $( "#dialog" ).dialog({
            autoOpen: true,
            show: "slide",
            hide: "explode",
            position: ['center',200]
        });

    });

</script>


<c:if test="${not empty message}">
    <div id="dialog" title="MetaboLights message">
        <p><b></b><c:out value="${message}" /></b></p>
    </div>
   <!-- <div class="messageBox"><c:out value="${message}" /></div>   -->
</c:if>

<c:if test="${empty message}">
   <div class="messageBox" style="border:0px; background:white">&nbsp;</div>
</c:if>

<div style="margin-bottom:40px; padding-left:120px; padding-top:30px">
   <img src="img/litehouseLogoBeta.png" alt="beta"/>
</div>

<div class="text_header" style="text-align:center">
    <spring:message code="msg.metabolights" />
</div>
 
<div class="text-fullwidth center">
    <br/>
    <spring:message code="msg.metabolightsAbout1" /> <spring:message code="msg.metabolightsAbout" />
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
</div>
