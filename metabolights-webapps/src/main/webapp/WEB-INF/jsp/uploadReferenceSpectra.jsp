<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2015-Mar-11
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2015 EMBL - European Bioinformatics Institute
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~      http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 5/22/14 12:15 PM
  ~ Modified by:   conesa
  ~
  ~
  ~ ©, EMBL, European Bioinformatics Institute, 2014.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">

<script>

    function showWait(){

    }

</script>

<script type="text/javascript">

    $(document).ready(function() {

        $("#hourglass").dialog({
            create: function(){
                $('.ui-dialog-titlebar-close').removeClass('ui-dialog-titlebar-close');
            },
            width: 400,
            height: 150,
            modal: true,
            autoOpen: false
        });

    });

    function disableSubmission() {

        document.body.style.cursor = "wait";
        $('.ui-dialog-titlebar').hide();
        $( "#hourglass" ).dialog( "open" );

    }

</script>

<c:choose>
    <c:when test="${not empty successfulUpload}">
        <div class="container">
            <div class="clear-fix">&nbsp;</div>
            <div class="clear-fix">&nbsp;</div>
            <div class="col-md-6 col-md-offset-3">
                <div class="alert alert-success" role="alert">
                    <h2>${ param.cid }</h2>
                    <p>Reference Spectra uploaded successfully. Thank you for your submission!</p>
                    <br>
                </div>
                <script>
                    window.setTimeout(function(){
                        window.location.href = "${ compoundId }";
                    }, 10000);
                </script>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="clear-fix">&nbsp;</div>
            <div class="clear-fix">&nbsp;</div>
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">${ param.cid }: Upload Reference Spectra </h3>
                    </div>
                    <div class="panel-body">
                        <form method="post" action="submitCompoundSpectra" enctype="multipart/form-data" name="uf" onsubmit="disableSubmission()">

                            <div class="form-group">
                                <label><spring:message code="label.spectraFile" />:</label>
                                <input type="file" name="file" id="fileInput" />
                            </div>
                            <input type="hidden" name="compoundid" value="${ param.cid }" >
                            <c:set var="currentUserId">
                                <sec:authorize access="hasRole('ROLE_SUBMITTER')">
                                    <sec:authentication property="principal.userId"/>
                                </sec:authorize>
                            </c:set>

                            <c:if test="${not empty users}">
                                <div class="form-group">
                                    <label><spring:message code="label.onBehalf"/>:</label>
                                    <select class="form-control" name="owner">
                                        <c:forEach var="user" items="${users}">
                                            <c:if test="${user.userId == currentUserId}">
                                                <option value="${user.userName}" SELECTED="true">${user.firstName}&nbsp;${user.lastName}</option>
                                            </c:if>
                                            <c:if test="${not (user.userId == currentUserId)}">
                                                <option value="${user.userName}">${user.firstName}&nbsp;${user.lastName}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <div id="hideableButtons">
                                    <input name="submit" type="submit" id="uploadFileBtn" class="from-control submit btn btn-primary" value="Upload Spectra">
                                </div>
                                <div id="hourglass">
                                    <img src="img/wait.gif" alt="Please wait"/>&nbsp;<b><spring:message code="msg.pleaseWaitForUpload"/></b>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>
            $(function() {
                document.getElementById("uploadFileBtn").disabled = true;
                $("#fileInput").change(function() {
                    var fileName = $(this).val();
                    if(fileName != "") {
                        document.getElementById("uploadFileBtn").disabled = false;
                    } //show the button
                });
            });
        </script>
    </c:otherwise>
</c:choose>

<c:if test="${not empty message}">
    <div class="error">
        <c:out value="${message}" />
    </div>
</c:if>
