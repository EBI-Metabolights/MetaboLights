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
                    <c:if test="${(study.studyStatus == 'SUBMITTED') || curator}">
                        <c:if test="${(study.studyStatus != 'PUBLIC')}">
                            <li><a href="updatepublicreleasedateform?study=${study.studyIdentifier}">change release date</a></li>
                        </c:if>
                        <li><a href="updaxtestudyform?study=${study.studyIdentifier}">update study</a></li>
                        <li><a href="#">change status to ..</a>
                            <ul>
                                <c:if test="${curator && (study.studyStatus != 'SUBMITTED')}">
                                <li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=SUBMITTED" confirmationText="Are you sure you want to change the status back to Submitted?" onclick="return confirmAction(this);">submitted</a></li>
                                </c:if>
                                <%--<c:if test="${(study.studyStatus == 'SUBMITTED') || (curator && (study.studyStatus != 'INCURATION'))}">--%>
                                    <%--<li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=INCURATION" confirmationText="Are you sure you want to send the study to curation?" onclick="return confirmAction(this);">in curation</a></li>--%>
                                <%--</c:if>--%>
                                <c:if test="${(study.studyStatus == 'SUBMITTED' || (curator && (study.studyStatus != 'INCURATION')))}">
                                    <c:if test="${(study.validations.passedMinimumRequirement == 'TRUE')}">
                                        <li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=INCURATION" confirmationText="Are you sure you want to send the study to curation?" onclick="return confirmAction(this);">in curation</a></li>
                                    </c:if>
                                    <c:if test="${(study.validations.passedMinimumRequirement == 'FALSE')}">
                                        <c:if test="${(!curator)}">
                                            <li><a confirmationText="Please make sure the study has all the required info" onclick="return warnAction(this);">in curation</a></li>
                                        </c:if>
                                        <c:if test="${(curator)}">
                                            <li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=INCURATION" confirmationText="Please make sure the study has all the required info" onclick="return warnAndProceedAction(this);">in curation</a></li>
                                        </c:if>
                                    </c:if>
                                </c:if>
                                <c:if test="${curator && (study.studyStatus != 'INREVIEW')}">
                                    <li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=INREVIEW" confirmationText="Is the study ready to be reviewed?" onclick="return confirmAction(this);">in review</a></li>
                                </c:if>
                                <c:if test="${curator && (study.studyStatus != 'PUBLIC')}">
                                    <li><a href="updatestatus?study=${study.studyIdentifier}&newStatus=PUBLIC" confirmationText="This will make the study Publcly available to anyone. Are yo sure?" onclick="return confirmAction(this);">public</a></li>
                                </c:if>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${curator}">
                        <li><a href="deleteStudy?study=${study.studyIdentifier}" confirmationText="This will delete the study from the system, no way back!." onclick="return confirmAction(this);">delete</a></li>
                        <c:if test="${not empty study.backups}">
                            <c:if test="${fn:length(study.backups) gt 0}">
                                <li><a href="#">restore ...</a>
                                    <ul>
                                        <c:forEach var="backup" items="${study.backups}">
                                            <li><a href="restore?study=${study.studyIdentifier}&backupidentifier=${backup.backupId}" confirmationText="Do you really want to restore the metadata files from a previous version?" onclick="return confirmAction(this);">${backup.backupTimeStamp}</a></li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:if>
                        </c:if>
                    </c:if>
                </ul>
            </li>
        </ul>
    </nav>
</c:if>

<div id="confirmaction" title="Are you sure..." style="display: none">
</div>
<div id="warnaction" title="The study is incomplete" style="display: none">
</div>
<script type="text/javascript">
   function confirmAction(element){

       var dialog = $("#confirmaction");

       // Fill dialog
       var targetUrl = $(element).attr("href");
       var text = $(element).attr("confirmationText");

       dialog.text(text);

       $(dialog).dialog({
//           autoOpen: false,
           modal: true,
           buttons : {
                   "Confirm" : function() {
                       window.location.href = targetUrl;
                   },
                   "Cancel" : function() {
                       $(this).dialog("close");
                   }
               }
       });

//       $(dialog).dialog("open");

       return false;
   }
   function warnAction(element){

       var dialog = $("#warnaction");

       // Fill dialog
       var targetUrl = $(element).attr("href");
       var text = $(element).attr("confirmationText");

       dialog.text(text);

       $(dialog).dialog({
//           autoOpen: false,
           modal: true,
           buttons : {
               "Check" : function() {
                  // window.location.href = targetUrl;
                  $(this).dialog("close");
                  document.getElementById("valid-tab").click();
               },
               "Cancel" : function() {
                   $(this).dialog("close");
               }
           }
       });

//       $(dialog).dialog("open");

       return false;
   }

   function warnAndProceedAction(element){

       var dialog = $("#warnaction");

       // Fill dialog
       var targetUrl = $(element).attr("href");
       var text = $(element).attr("confirmationText");

       dialog.text(text);

       $(dialog).dialog({
//           autoOpen: false,
           modal: true,
           buttons : {
               "Check" : function() {
                   // window.location.href = targetUrl;
                   $(this).dialog("close");
                   document.getElementById("valid-tab").click();
               },
               "Proceed anyway" : function() {
                   window.location.href = targetUrl;
               }
           }
       });

//       $(dialog).dialog("open");

       return false;
   }

</script>