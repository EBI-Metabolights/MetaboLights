<%@taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core"   %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${not empty message}">
   <div class="messageBox">
      <c:out value="${message}" />
   </div>
</c:if>
<c:if test="${empty message}">
   <div class="messageBox" style="border:0px">&nbsp;</div>
</c:if>

<div style="margin-bottom:40px; padding-left:120px; padding-top:30px">
   <img src="img/litehouseLogo.png" ></img> 
</div>

<div id="text_header" style="text-align:center">
    <spring:message code="msg.metabolights" />
</div>
 
<div id="text-fullwidth" style="text-align:center">
    <br>
    <spring:message code="msg.metabolightsAbout1" />
    <div id="homepageLinks">
	  <a href="about"><spring:message code="label.aboutMetab"/></a>
	  <br>  
	  <a href="contact"><spring:message code="label.contact"/></a>
	</div>
	  
    <br>
    <br>
    <br>
    <br>
    <br>

</div>
