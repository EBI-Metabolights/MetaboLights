<%--Default values--%>
<c:set var="readOnly" scope="page" value="true"/>
<c:set var="curator" value="false"/>

<c:if test="${fn:contains(servletPath,study.studyIdentifier)}">
    <c:set var="readOnly" value="false"/>
</c:if>

<%--If the study is public it will be readonly--%>
<c:if test="${study.studyStatus  != 'PROVISIONAL'}">
    <c:set var="readOnly" value="true"/>
</c:if>

<sec:authorize access="hasRole('ROLE_SUPER_USER')">
    <c:set var="curator" value="true"/>
    <c:set var="readOnly" value="false"/>
</sec:authorize>

<%--If editable--%>
<c:if test="${readOnly eq false}">
    <span class="dropdown">
        <button id="dLabel" type="button" class="btn btn-primary nbr mt10" data-toggle="dropdown" data-target="#" href="#">
            <i class="fa fa-cogs"></i> Actions <span class="caret"></span>
        </button>&nbsp;
        <ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu">
            <c:if test="${(study.studyStatus == 'PROVISIONAL') || curator}">
                <c:if test="${(study.studyStatus != 'PUBLIC')}">
                    <li><a href="${pageContext.request.contextPath}/updatepublicreleasedateform?study=${study.studyIdentifier}">change release date</a></li>
                </c:if>
                <li><a href="${pageContext.request.contextPath}/updatestudyform?study=${study.studyIdentifier}">Update study</a></li>
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
    </span>
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
