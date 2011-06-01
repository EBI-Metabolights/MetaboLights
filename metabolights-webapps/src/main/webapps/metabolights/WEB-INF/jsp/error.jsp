<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br/><br/>
<img src="img/error.png"/>

<br/><br/>
<span class="error">
  <spring:message code="msg.error.general" />
  <c:out value="${errorMainMessage}"/>

</span>
<br>

<span class="white">
<c:out value="${errorMessage}" escapeXml="false"  />
</span>

<br/>
