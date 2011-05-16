<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<br/><br/>
<img src="img/warning.png" height="100" width="100"/>

<br/><br/>


<span class="error">
<c:out value="${errormessage}" escapeXml="false"  />
</span>

<br/>
