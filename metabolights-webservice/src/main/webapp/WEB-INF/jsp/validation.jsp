
<%--<c:if test="${validationstatus == 'GREEN'}">--%>
  <%--<c:if test="${validationoverridden == 'true'}">--%>
  <%--<span class="ok">*</span>--%>
  <%--</c:if>--%>
  <%--<c:if test="${validationoverridden == 'false'}">--%>
    <%--<span class="ok"></span>--%>
  <%--</c:if>--%>
<%--</c:if>--%>
<%--<c:if test="${validationstatus == 'RED'}">--%>
  <%--<c:if test="${validationoverridden == 'true'}">--%>
    <%--<span class="wrong mandatory">*</span>--%>
  <%--</c:if>--%>
  <%--<c:if test="${validationoverridden == 'false'}">--%>
    <%--<span class="wrong mandatory"></span>--%>
  <%--</c:if>--%>
<%--</c:if>--%>
<%--<c:if test="${validationstatus == 'ORANGE'}">--%>
  <%--<c:if test="${validationoverridden == 'true'}">--%>
    <%--<span class="wrong optional">*</span>--%>
  <%--</c:if>--%>
  <%--<c:if test="${validationoverridden == 'false'}">--%>
    <%--<span class="wrong optional"></span>--%>
  <%--</c:if>--%>
<%--</c:if>--%>


<c:if test="${validationPassedRequirement == 'true'}">
  <c:if test="${validationOverridden == 'true'}">
    <span class="ok override">*</span>
  </c:if>
  <c:if test="${validationOverridden == 'false'}">
    <span class="ok"></span>
  </c:if>
</c:if>
<c:if test="${validationPassedRequirement == 'false'}">
  <c:if test="${validationType == 'MANDATORY'}">
    <c:if test="${validationOverridden == 'true'}">
      <span class="wrong mandatory">*</span>
    </c:if>
    <c:if test="${validationOverridden == 'false'}">
      <span class="wrong mandatory"></span>
    </c:if>
  </c:if>
  <c:if test="${validationType == 'OPTIONAL'}">
    <c:if test="${validationOverridden == 'true'}">
      <span class="wrong optional">*</span>
    </c:if>
    <c:if test="${validationOverridden == 'false'}">
      <span class="wrong optional"></span>
    </c:if>
  </c:if>
</c:if>