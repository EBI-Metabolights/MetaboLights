<%@taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${not empty message}">
   <div class="messageBox">
      <c:out value="${message}" />
   </div>
</c:if>

<div style="margin-bottom:40px; padding-left:120px; padding-top:30px">
   <img src="img/litehouseLogo.png" ></img> 
</div>

<div id="text_header">
    <spring:message code="msg.metabolights" />
</div>
 
<div id="text-fullwidth">
    <br>
    <spring:message code="msg.metabolightsLong" />
    <br>
    <br>
    <br>
    <br>
    <br>

</div>
