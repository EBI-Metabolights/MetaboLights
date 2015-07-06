<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${hit.class.simpleName eq 'LiteStudy'}">

  <c:set var="liteStudy" value="${hit}"/>
  <%@include file="studySummary.jsp" %>
</c:if>

<c:if test="${hit.class.simpleName ne 'LiteStudy'}">
  <c:set var="compound" value="${hit}"/>
  <%@include file="compoundSummary.jsp" %>
</c:if>