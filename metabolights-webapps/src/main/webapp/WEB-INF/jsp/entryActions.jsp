<%--Default values--%>
<c:set var="readOnly" scope="page" value="true"/>
<c:set var="curator" value="false"/>

<c:if test="${fn:contains(servletPath,study.studyIdentifier)}">
    <c:set var="readOnly" value="false"/>
</c:if>

<%--If the study is public it will be readonly--%>
<c:if test="${study.studyStatus  != 'SUBMITTED'}">
    <c:set var="readOnly" value="true"/>
</c:if>

<sec:authorize ifAnyGranted="ROLE_SUPER_USER">
    <c:set var="curator" value="true"/>
    <c:set var="readOnly" value="false"/>
</sec:authorize>

<%--If editable--%>
<c:if test="${readOnly eq false}">
    <nav id="cssmenu">
        <ul>
            <li><a href="#">actions</a>
                <ul>
                    <li><a href="updatestudyform?study=${study.studyIdentifier}">change release date</a></li>
                    <c:if test="${(study.studyStatus == 'SUBMITTED') || curator}">
                        <li><a href="#">change status to ..</a>
                            <ul>
                                <c:if test="${curator && (study.studyStatus != 'SUBMITTED')}">
                                <li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=SUBMITTED">submitted</a></li>
                                </c:if>
                                <c:if test="${(study.studyStatus == 'SUBMITTED') || (curator && (study.studyStatus != 'INCURATION'))}">
                                    <li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=INCURATION">in curation</a></li>
                                </c:if>
                                <c:if test="${curator && (study.studyStatus != 'INREVIEW')}">
                                    <li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=INREVIEW">in review</a></li>
                                </c:if>
                                <c:if test="${curator && (study.studyStatus != 'PUBLIC')}">
                                    <li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=PUBLIC">public</a></li>
                                </c:if>
                            </ul>
                        </li>
                    </c:if>
                </ul>
            </li>
        </ul>
    </nav>
</c:if>
