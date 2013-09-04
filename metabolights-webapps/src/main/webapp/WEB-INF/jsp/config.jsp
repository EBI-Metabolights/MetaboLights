<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script>
$(function() {
	$( "#tabs" ).tabs();
});
</script>

<script type="text/javascript">


    function toggleQueue(instance) {
      $.ajax({
        url: "proxy?url=http://" + instance + '/metabolights/togglequeue',
        success: function(data) {

            $("[instance='" + instance + "']").html(data);

        }
      });
    }

    function checkInstanceStatus(statusTD) {

        var instance = statusTD.getAttribute("instance");

        $.ajax({
            url: "proxy?url=http://" + instance + '/metabolights/queuestatus',
            success: function(data) {
                $(statusTD).html(data);
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

	
	<h2>Configuration page</h2>
	

	<div id="tabs">
		<ul>
			<li><a class="noLine" href="#appTab">Application Properties</a></li>
			<li><a class="noLine" href="#valTab">Validations</a></li>
			<li><a class="noLine" href="#queueTab">Queue</a></li>
			<li><a class="noLine" href="#studyHealthTab">Study Health Tab</a></li>
            <li><a class="noLine" href="#clearRefCache">Clear Ref Cache</a></li>
            <li><a class="noLine" href="#MetaboLightsParameters">MetaboLights Parameters</a></li>
            <li><a class="noLine" href="#reindexStudies">Reindex Studies</a></li>
		</ul>
		
        <div id="appTab">
            <br/>
            <c:if test="${not empty props}">
                <table cellpadding="5px" cellspacing="0px">
                    <tr><th>Property</th><th>Value</th></tr>
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
            </c:if>
        </div>

        <div id="valTab">
            <br/>
            <c:if test="${not empty validation}">
                <table cellpadding="5px" cellspacing="0px">
                    <tr><th>Validation</th><th>Result</th></tr>
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
            </c:if>
        </div>

        <div id="queueTab">
            <br/>
            <c:if test="${not empty queue}">
                <table cellpadding="5px" cellspacing="0px">
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
            </c:if>
            <br/>
            <br/>
            <c:if test="${not empty processFolder}">
                <br/><b>Process Folder</b>
                <table cellpadding="5px" cellspacing="0px">
                    <thead class='text_header'>
                        <tr>
                            <th>File name</th>
                        </tr>
                    </thead>
                <c:forEach var="file" items="${processFolder}">
                    <tr><td>${file.name}</td></tr>
                </c:forEach>
                </table>
            </c:if>

            <c:if test="${not empty errorFolder}">
                <br/><b>Error Folder</b>
                <table cellpadding="5px" cellspacing="0px">
                    <thead class='text_header'>
                        <tr>
                            <th>File name</th>
                        </tr>
                    </thead>
                <c:forEach var="file" items="${errorFolder}">
                    <tr><td>${file.name}</td></tr>
                </c:forEach>
                </table>
            </c:if>

            <c:if test="${not empty backUpFolder}">
                <br/><b>BackUp Folder</b>
                <table cellpadding="5px" cellspacing="0px">
                    <thead class='text_header'>
                        <tr>
                            <th>File name</th>
                        </tr>
                    </thead>
                <c:forEach var="file" items="${backUpFolder}">
                    <tr><td>${file.name}</td></tr>
                </c:forEach>
                </table>
            </c:if>
            <br/><br/>
            <c:if test="${not empty queuerunnig}">

                <c:if test="${not empty instances}">
                    <table id="instances">
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
                    <div id="startresponse"></div>
                    <script>populateQueueStatusTable()</script>
                </c:if>
            </c:if>

        </div>

        <div id="studyHealthTab">
            <br/>
            <form method="post" action="setgalleryItems">
                <label>Gallery Items (comma separated ID):</label>
                <input type=text value="${galleryIds}" name="galleryitems" size="60"/>
                <input name="submit" type="submit" class="submit" value="Change gallery Items">
            </form>
            <br/>
            <c:if test="${not empty studiesHealth}">
                <table cellpadding="5px" cellspacing="0px">
                    <tr><th>Study</th><th>is Public?</th><th>Must be under</th><th>is it there?</th></tr>
                    <c:forEach var="study" items="${studiesHealth}">
                        <tr>
                            <td><a href="reindex?study=${study.identifier}" title="Reindex this study">${study.identifier}</a></td>
                            <td>${study.isPublic}</td>
                            <td>${study.studyPath}</td>
                            <td <c:if test="${not study.isThere}">class="error"</c:if> >
                                ${study.isThere}
                            </td>

                        </tr>
                    </c:forEach>
                </table>
            </c:if>

            <c:if test="${not empty publicFtpLocation}">
                <br/><b>publicFtpLocation</b>
                <table cellpadding="5px" cellspacing="0px">
                    <thead class='text_header'>
                    <tr>
                        <th>File name</th>
                    </tr>
                    </thead>
                    <c:forEach var="file" items="${publicFtpLocation}">
                        <tr><td>${file.name}</td></tr>
                    </c:forEach>
                </table>
            </c:if>

            <c:if test="${not empty publicFtpStageLocation}">
                <br/><b>publicFtpStageLocation</b>
                <table cellpadding="5px" cellspacing="0px">
                    <thead class='text_header'>
                    <tr>
                        <th>File name</th>
                    </tr>
                    </thead>
                    <c:forEach var="file" items="${publicFtpStageLocation}">
                        <tr><td>${file.name}</td></tr>
                    </c:forEach>
                </table>
            </c:if>

            <c:if test="${not empty privateFtpStageLocation}">
                <br/><b>privateFtpStageLocation</b>
                <table cellpadding="5px" cellspacing="0px">
                    <thead class='text_header'>
                    <tr>
                        <th>File name</th>
                    </tr>
                    </thead>
                    <c:forEach var="file" items="${privateFtpStageLocation}">
                        <tr><td>${file.name}</td></tr>
                    </c:forEach>
                </table>
            </c:if>

        </div>

        <div class="refLayerBox" id="clearRefCache">
            <div class="grid_24 ">
                <div class="grid_24 title">
                    Clear cache
                </div>
                <div class="grid_24">
                    <a href="clearreflayercache">Click to clear cache</a>
                </div>
            </div>
        </div>

        <div class="refLayerBox" id="MetaboLightsParameters">

            <div class="grid_24 ">
                <div class="grid_24 title">
                    MetaboLights parameters
                </div>
                <div class="grid_24">
                    <a href="parameters">Click here for MetaboLights Parameters</a>
                </div>
            </div>
        </div>

        <div class="refLayerBox" id="reindexStudies">
            <div class="grid_24">
                <div class="grid_24 title">
                    Reindex studies
                </div>
                <div class="grid_24">
                    <spring:message code="msg.studies.reindex"/>
                    <br/>
                    <br/>
                    <ul id="reindexStudiesatOnce">
                        <li><a href="reindex" title="Reindex one-by-one">Reindex studies one-by-one</a></li>
                        <li><a href="reindexall" title="Reindex all">Reindex all studies</a></li>
                    </ul>
                </div>
            </div>
        </div>

	</div>

