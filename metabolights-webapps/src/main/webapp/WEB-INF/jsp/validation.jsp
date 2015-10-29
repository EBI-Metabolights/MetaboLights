
<c:if test="${validationstatus == 'GREEN'}">
  <span class="ok"></span>
</c:if>
<c:if test="${validationstatus == 'RED'}">
  <span class="wrong mandatory"></span>
</c:if>
<c:if test="${validationstatus == 'ORANGE'}">
  <span class="wrong optional"></span>
</c:if>