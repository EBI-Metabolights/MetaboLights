<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 5/21/14 12:39 PM
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

<script>
$(function() {

    $.fn.getIndex = function(){
        var $p=$(this).parent().children();
        return $p.index(this);
    }

    var hash = document.location.hash;
    // Remove the #
    hash = hash.substring(1);
    var tabToActivate = 0;

    if (hash != undefined) {
        // If it's not a number'
        if (isNaN(hash)){

            tabToActivate = $("[hash='" + hash + "']").getIndex();

            if (tabToActivate == -1) tabToActivate = 0;
        } else {
            tabToActivate= hash;
        }

    }

	$( "#tabs" ).tabs({
        activate: function (event, ui)
        {
            // to make bookmarkable
            document.location.hash =  "#"+ui.newTab.attr("hash");
        }
        ,active: tabToActivate
    });


});
</script>

<script type="text/javascript">
    function toggleQueue(instance) {
      $.ajax({
        url: "togglequeue",
        data:{
           'user_token': '${user_token}',
           'instance': instance
        },
        success: function(data) {

            $("[instance='" + instance + "']").html(data.toString());

        }
      });
    }

    function checkInstanceStatus(statusTD) {

        var instance = statusTD.getAttribute("instance");

        $.ajax({
            url: "queuestatus",
            data:{
                'user_token': '${user_token}',
                'instance': instance
            },
            success: function(data) {
                $(statusTD).html(data.toString());
            }
        });
    }

    function populateQueueStatusTable() {

        $('.instanceStatus').each(function(){

            var statusTD = this;

            checkInstanceStatus(statusTD);


        })
    }
</script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css" type="text/css"/>

<div class="container-fluid">
    <div class="col-md-12">
        <h3 class="heading">Configuration page</h3>
        <div>
            <!-- Nav tabs -->
            <ul id="configurationTabs" class="nav nav-tabs" role="tablist">
                <%-- <li role="presentation" class="active"><a href="#validations" aria-controls="validations" role="tab" data-toggle="tab">Validations</a></li> --%>
                <li role="presentation"><a href="#appProp" aria-controls="appProp" role="tab" data-toggle="tab">Application Properties</a></li>
                <%-- <li role="presentation"><a href="#queue" aria-controls="queue" role="tab" data-toggle="tab">Queue</a></li> --%>
                <li role="presentation"><a href="#studyHealth" aria-controls="studyHealth" role="tab" data-toggle="tab">Study Health</a></li>
                <%-- <li role="presentation"><a href="#indexMaintenance" aria-controls="indexMaintenance" role="tab" data-toggle="tab">Index Maintenance</a></li> --%>
                <li role="presentation"><a href="#referenceLayer" aria-controls="referenceLayer" role="tab" data-toggle="tab">Reference Layer</a></li>
                <%-- <li role="presentation"><a href="#metabolightsParameters" aria-controls="metabolightsParameters" role="tab" data-toggle="tab">MetaboLights Parameters</a></li> --%>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="validations">
                    <c:if test="${not empty validation}">
                        <br>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Validation Details
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered dataTable" cellpadding="5px" cellspacing="0px">
                                    <thead>
                                    <tr><th>Validation</th><th>Result</th></tr>
                                    </thead>
                                    <c:forEach var="test" items="${validation}">
                                        <tr>
                                            <td>
                                                    ${test.key}
                                            </td>
                                            <td <c:if test="${not test.value}">class="error"</c:if>	>
                                                    ${test.value}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div role="tabpanel" class="tab-pane" id="appProp">
                    <br>
                    <c:if test="${not empty connection}">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Data Source
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered dataTable" cellpadding="5px" cellspacing="0px">
                                    <thead>
                                    <tr><th>DataSource - metabolights</th><th>Value</th></tr>
                                    </thead>
                                    <tr>
                                        <td>url</td><td>${connection}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                    </c:if>

                    <c:if test="${not empty props}">
                        <div class="panel panel-success">
                            <div class="panel-heading">
                                Application Properties
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered dataTable" cellpadding="5px" cellspacing="0px">
                                    <thead>
                                    <tr><th>Property</th><th>Value</th></tr>
                                    </thead>
                                    <c:forEach var="property" items="${props}">
                                        <tr>
                                            <td>
                                                    ${property.key}
                                            </td>
                                            <td>
                                                    ${property.value}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty contextProps}">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Context Variables
                        </div>
                        <div class="panel-body">
                        <table class="table table-bordered dataTable" cellpadding="5px" cellspacing="0px">
                            <thead>
                                <tr><th>Variable</th><th>Value</th></tr>
                            </thead>
                            <c:forEach var="binding" items="${contextProps}">
                                <tr>
                                    <td>
                                            ${binding.name}
                                    </td>
                                    <td>
                                            ${binding.object}
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${not empty hibernateProperties}">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    Context Variables
                                </div>
                                <div class="panel-body">
                                <table class="table table-bordered dataTable" cellpadding="5px" cellspacing="0px">
                                    <thead>
                                        <tr><th>Hibernate Properties</th><th>Value</th></tr>
                                    </thead>
                                    <c:forEach var="property" items="${hibernateProperties}">
                                        <tr>
                                            <td>
                                                    ${property.key}
                                            </td>
                                            <td>
                                                    ${property.value}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div role="tabpanel" class="tab-pane" id="queue">
                    <c:if test="${not empty uploads}">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Uploads
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered dataTable" cellpadding="5px" cellspacing="0px">
                                    <thead class='text_header'>
                                    <tr>
                                        <th>Upload</th>
                                        <th>Status</th>
                                    </tr>
                                    </thead>
                                    <c:forEach var="upload" items="${uploads}">
                                        <tr>
                                            <td>${upload.lastUpdate}</td>
                                            <td>${upload.percentDone}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty queue}">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                Queue Status
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered dataTable" cellpadding="5px" cellspacing="0px">
                                    <thead class='text_header'>
                                    <tr>
                                        <th>File name</th>
                                        <th>Status</th>
                                        <th>Study</th>
                                        <th>Public by</th>
                                    </tr>
                                    </thead>
                                    <c:forEach var="qi" items="${queue}">
                                        <tr>
                                            <td>${qi.originalFileName}</td>
                                            <td>QUEUED</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty qi.accession}">${qi.accession}</c:when>
                                                    <c:otherwise>NEW</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${qi.publicReleaseDate}"/></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty processFolder}">
                        <br/>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4>Process Folder</h4>
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered dataTable" cellpadding="5px" cellspacing="0px">
                                    <thead class='text_header'>
                                    <tr>
                                        <th>File name</th>
                                    </tr>
                                    </thead>
                                    <c:forEach var="file" items="${processFolder}">
                                        <tr><td>${file.name}</td></tr>
                                    </c:forEach>
                                </table>
                            </div>

                        </div>
                    </c:if>
                    <c:if test="${not empty errorFolder}">
                        <br/>
                        <div class="panel panel-danger">
                            <div class="panel-heading">
                                <h5>Error Folder</h5>
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered dataTable" cellpadding="5px" cellspacing="0px">
                                    <thead class='text_header'>
                                    <tr>
                                        <th>File name</th>
                                    </tr>
                                    </thead>
                                    <c:forEach var="file" items="${errorFolder}">
                                        <tr><td><a href="errorFile/${file.name}">${file.name}</a></td></tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty backUpFolder}">
                        <br/>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4><b>BackUp Folder</b></h4>
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered dataTable" cellpadding="5px" cellspacing="0px">
                                    <thead class='text_header'>
                                    <tr>
                                        <th>File name</th>
                                    </tr>
                                    </thead>
                                    <c:forEach var="file" items="${backUpFolder}">
                                        <tr><td>${file.name}</td></tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty queueRunning}">
                        <c:if test="${not empty instances}">
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    Queue status
                                </div>
                                <div class="panel-body">
                                    <table class="table table-bordered dataTable" id="instances">
                                        <thead class='text_header'>
                                        <tr><th>Instance</th><th></th><th>status</th></tr>
                                        </thead>
                                        <c:forEach var="instance" items="${instances}">
                                            <tr>
                                                <td>${instance}</td>
                                                <td><button onclick="toggleQueue('${instance}')" title="Toggle queue status!">Toogle status</button></td>
                                                <td class="instanceStatus" instance="${instance}">
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </table>
                                </div>
                            </div>
                            <div id="startresponse"></div>
                            <script>populateQueueStatusTable()</script>
                        </c:if>
                    </c:if>
                </div>
                <div role="tabpanel" class="tab-pane" id="studyHealth">
                    <c:if test="${not empty studiesHealth}">
                        <br>
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                Study Health Status
                            </div>
                            <div class="panel-body">
                                <table class="table table-bordered studyStatusTable" cellpadding="5px" cellspacing="0px">
                                    <thead>
                                    <tr><th>Study</th><th>Has folder?</th>
                                        <th>In the DB</th><th>Indexed?</th><th>is Ok?</th></tr>
                                    </thead>
                                    <c:forEach var="study" items="${studiesHealth}">
                                        <tr>
                                            <td><a href="indexstudiesaction?study=${study.identifier}" title="Reindex this study">${study.identifier}</a></td>
                                            <td>${study.itInTheFileSystem}</td>
                                            <td>${study.itInTheDB}</td>
                                            <td>${study.itInTheIndex}</td>
                                            <td <c:if test="${not(study.itInTheIndex && study.itInTheDB && study.itInTheFileSystem && true)}">class="error"</c:if>>${study.itInTheIndex && study.itInTheDB && study.itInTheFileSystem && true}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div role="tabpanel" class="tab-pane" id="indexMaintenance">
                    <div class="row">
                        <br>
                        <div class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-heading">Studies</div>

                                <div class="panel-body">
                                    <p><a href="indexstudiesaction" title="Reindex all the studies">Reindex all the studies</a></p>
                                    <p><a href="resetIndex" title="Reset index: Delete, rebuild and re-apply the mappings for studies and compounds">Reset index</a> (delete, rebuild and re-apply the mappings)</p>
                                    <p><a href="deleteindexedstudies" title="Delete all indexed studies">Delete all indexed studies</a></p>
                                </div>
                                 <div class="panel-footer">
                                    <form class="form-inline" method="get" action="indexstudiesaction">
                                        <div class="form-group">
                                            <label>Study to re/index:</label>
                                            <input class="form-control" type="text" name="study" placeholder="Example: MTBLS1">
                                        </div>
                                        <input class="btn btn-default" type="submit" value="Index" name="action"> <input type="submit" value="Delete" name="action" class="btn btn-default">
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-heading">Compounds</div>
                                <div class="panel-body">
                                    <p><a href="reindexcompounds" title="Reindex all the compounds">Reindex all the compounds</a></p>
                                    <p><a href="deleteindexedcompounds" title="Delete all indexed compounds">Delete all indexed compounds</a></p>
                                    <p>&nbsp;</p>
                                </div>
                                <div class="panel-footer">
                                    <form class="form-inline" method="get" action="reindexcompounds">
                                        <div class="form-group">
                                            <label>Compound to re/index:</label>
                                            <input class="form-control" type="text" name="compound"  placeholder="Example: MTBLC15355">
                                        </div>
                                        <input class="btn btn-default" type="submit" value="Index">
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    <h4>Additional Information</h4>
                                </div>
                                <div class="panel-body">
                                    <p>Please follow the below steps to reset the elastic search parameters</p>

                                    <p>More details (Go to the elasticsearch bin of the test/dev/prod servers) <i><a href=" https://www.ebi.ac.uk/seqdb/confluence/display/STEINBECK/New+MetaboLights+configuration+%282015-09%29+New+architecture+in+the+new+filesystem" target="_blank">here</a></i></p>
                                    <ul>
                                        <li>Delete all indexes from web page (study, compound)</li>
                                        <li>Restart the elasticsearch server. Make sure no process is running after stop.</li>
                                        <li>Reapply the study mappings from the WebApp</li>
                                        <li>Reindex the studies</li>
                                        <li>Reindex the compounds</li>
                                        <li>Check elastic log files for any errors</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <h3>Index Status  - <button class="btn btn-info" data-toggle="collapse" href="#collapseIndexStatusDetials" >Show details</button></h3>
                            <br>
                            <div class="well collapse" id="collapseIndexStatusDetials">
                                <c:forEach var="message" items="${status}">
                                    <p>${message}</p>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="referenceLayer">
                    <div class="row">
                        <div class="col-md-12">
                            <br>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4>Import metabolites</h4>
                                </div>
                                <div class="panel-body">
                                    <a href="importmetabolites" class="btn btn-info">Click to import/update metabolites</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="metabolightsParameters">
                    <div class="row">
                        <div class="col-md-12">
                            <br>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4>MetaboLights parameters</h4>
                                </div>
                                <div class="panel-body">
                                    <a href="parameters" class="btn btn-info">Click here for MetaboLights Parameters</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br>
    </div>
</div>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script>
    $('#configurationTabs a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    })

    $(document).ready(function(){
        $('.dataTable').DataTable();
    });

    $(document).ready(function(){
        $('.studyStatusTable').DataTable({
            paging: false,
            order : [[4, 'asc']]
        });
    });
</script>
