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
    <div class="dropdown ml--inline">
        <a id="dLabel" role="button" data-toggle="dropdown" class="" data-target="#" href="#">
            Actions <span class="caret"></span>
        </a>
        <ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu">

            <c:if test="${(study.studyStatus == 'SUBMITTED') || curator}">
                <c:if test="${(study.studyStatus != 'PUBLIC')}">
                    <li><a href="${pageContext.request.contextPath}/updatepublicreleasedateform?study=${study.studyIdentifier}">change release date</a></li>
                </c:if>
                <li><a href="${pageContext.request.contextPath}/updatestudyform?study=${study.studyIdentifier}">Update study</a></li>
                <li class="dropdown-submenu"><a href="#" tabindex="-1">Change status</a>
                    <ul class="dropdown-menu">
                        <c:if test="${curator && (study.studyStatus != 'SUBMITTED')}">
                            <li><a data-href="${pageContext.request.contextPath}/updatestatus?study=${study.studyIdentifier}&newStatus=SUBMITTED" data-title="Confirmation" data-info="Are you sure you want to change the status back to Submitted?" data-toggle="modal" data-target="#confirm-modal" data-class="btn-danger" data-value="Change-status">Submitted</a></li>
                        </c:if>
                            <%--<c:if test="${(study.studyStatus == 'SUBMITTED') || (curator && (study.studyStatus != 'INCURATION'))}">--%>

                            <%--<li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=INCURATION" confirmationText="Are you sure you want to send the study to curation?" onclick="return confirmAction(this);">in curation</a></li>--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${(st  udy.studyStatus == 'SUBMITTED') || (curator && (study.studyStatus != 'INCURATION'))}">--%>
                            <%--<li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=INCURATION" confirmationText="Are you sure you want to send the study to curation?" onclick="return confirmAction(this);">in curation</a></li>--%>
                            <%--</c:if>--%>
                        <c:if test="${(study.studyStatus == 'SUBMITTED' || (curator && (study.studyStatus != 'INCURATION')))}">
                            <c:if test="${(study.validations.passedMinimumRequirement == 'TRUE')}">
                                <li><a data-href="${pageContext.request.contextPath}/updatestatus?study=${study.studyIdentifier}&newStatus=INCURATION" data-title="Confirmation" data-info="Are you sure you want to send the study to curation?" data-toggle="modal" data-target="#confirm-modal" data-class="btn-danger" data-value="Change-status">In curation</a></li>
                            </c:if>
                            <c:if test="${(study.validations.passedMinimumRequirement == 'FALSE')}">
                                <c:if test="${(!curator)}">
                                    <li><a confirmationText="Please make sure the study has all the required info" onclick="return warnAction(this);">In curation</a></li>
                                </c:if>
                                <c:if test="${(curator)}">
                                    <li><a data-href="${pageContext.request.contextPath}/updatestatus?study=${study.studyIdentifier}&newStatus=INCURATION" data-title="Confirm and Proceed" data-info="Please make sure the study has all the required info" data-toggle="modal" data-target="#confirm-modal" data-class="btn-danger" data-value="Change-status">In curation</a></li>
                                </c:if>
                            </c:if>
                        </c:if>
                        <c:if test="${curator && (study.studyStatus != 'INREVIEW')}">
                            <li><a data-href="${pageContext.request.contextPath}/updatestatus?study=${study.studyIdentifier}&newStatus=INREVIEW" data-title="Confirmation" data-info="Is the study ready to be reviewed?" data-toggle="modal" data-target="#confirm-modal" data-class="btn-danger" data-value="Change-status">In review</a></li>
                        </c:if>
                        <c:if test="${curator && (study.studyStatus != 'PUBLIC')}">
                            <li><a data-href="${pageContext.request.contextPath}/updatestatus?study=${study.studyIdentifier}&newStatus=PUBLIC" data-title="Confirmation" data-info="This will make the study Publicly available to anyone. Are yo sure?" data-toggle="modal" data-target="#confirm-modal" data-class="btn-danger" data-value="Make study public">Public</a></li>
                        </c:if>
                    </ul>
                </li>
            </c:if>
            <c:if test="${curator}">
                <li><a data-href="${pageContext.request.contextPath}/deleteStudy?study=${study.studyIdentifier}" data-info="This will delete the study from the system, no way back!." data-toggle="modal" data-target="#confirm-modal" data-class="btn-danger" data-value="Delete study Anyway">Delete</a></li>
                <c:if test="${not empty study.backups}">
                    <c:if test="${fn:length(study.backups) gt 0}">
                        <li class="dropdown-submenu"><a href="#" tabindex="-1">Restore</a>
                            <ul class="dropdown-menu">
                                <c:forEach var="backup" items="${study.backups}">
                                    <li><a data-href="${pageContext.request.contextPath}/restore?study=${study.studyIdentifier}&backupidentifier=${backup.backupId}" data-title="Confirmation" data-info="Do you really want to restore the metadata files from a previous version?" data-toggle="modal" data-target="#confirm-modal"  data-class="btn-danger" data-value="Restore">${backup.backupTimeStamp}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                </c:if>
            </c:if>
        </ul>
    </div>
    &emsp;|&emsp;
</c:if>

<div class="modal fade" id="confirm-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div id="modal-title"></div>
            </div>
            <div class="modal-body">
                <div id="modal-info"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a class="btn btn-ok">Delete</a>
            </div>
        </div>
    </div>
</div>

<script>
    $('#confirm-modal').on('show.bs.modal', function(e) {
        $(this).find('#modal-title').html($(e.relatedTarget).data('title'));
        $(this).find('#modal-info').html($(e.relatedTarget).data('info'));
        $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
        $(this).find('.btn-ok').addClass($(e.relatedTarget).data('class'));
        $(this).find('.btn-ok').text($(e.relatedTarget).data('value'));
    });
</script>