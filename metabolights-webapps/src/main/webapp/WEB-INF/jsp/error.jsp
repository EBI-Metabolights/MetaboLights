<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br/><br/>
<img src="img/error2.png"/>
<!-- img src="img/ebi-icons/64px/stop.png"-->

<br/><br/>
<span class="error">
  <spring:message code="msg.error.general" />
  <c:out value="${errorMainMessage}"/>

</span>
<br>

<span class="white">
<c:out value="${errorStack}" escapeXml="false"  />
</span>

<br/>
