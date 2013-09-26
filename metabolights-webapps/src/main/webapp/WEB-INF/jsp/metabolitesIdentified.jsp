<%--
  Created by IntelliJ IDEA.
  User: tejasvi
  Date: 24/09/13
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

Test Metabolite

<c:forEach var="metaboliteLine" items="${metAssgnmtLines}" varStatus="loopStatus">
    metaboliteLine - ${metaboliteLine}
</c:forEach>