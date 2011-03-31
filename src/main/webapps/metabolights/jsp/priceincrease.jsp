<%@ include file="include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><fmt:message key="title"/></title>
  <style>
    .error { color: red; }
  </style>  
</head>
<body>
<h1><fmt:message key="priceincrease.heading"/></h1>

<form:form method="post" commandName="priceIncrease"> <!--  "priceIncrease" is a command name in the file springapp-servlet.xml. Resolved to PriceIncrease class. -->

  <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right" width="20%">Increase (%):</td>
        <td width="20%">
          <form:input path="percentage"/>
        </td>
        <td width="60%">
          <form:errors path="percentage" cssClass="error"/>
        </td>
    </tr>
  </table>

  <br>

  <input type="submit" align="center" value="Execute">

</form:form>
<a href="<c:url value="hello.htm"/>">Home</a>
</body>
</html>