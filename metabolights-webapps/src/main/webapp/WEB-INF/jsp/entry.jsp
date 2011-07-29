<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script language="javascript"> 
	function toggle(anctag,darg,showMsg,hideMsg) 
	{
	  var ele = document.getElementById(darg);
	  var text = document.getElementById(anctag);
	  if(ele.style.display == "block") 
	  {
	    ele.style.display = "none";
	    text.innerHTML = showMsg;
	  }
	  else 
	  {
	    ele.style.display = "block";
	    text.innerHTML = hideMsg;
	  }
	} 
</script>

<div id="text_header">
	<spring:message code="label.metaboLightsEntry" />
	${study.acc}
</div>

<div class="formbox">
		<span class="title">${study.title}</span>

        <c:if test="${not empty study.objective}">
            <br><br>${study.objective}
        </c:if>

        <c:if test="${not empty study.description}">
            <br><br><span style="text-align:justify">${study.description}</span>
        </c:if>

        <c:if test="${not empty study.submissionDate || not empty study.releaseDate}">
            <br><br>
            <c:if test="${not empty study.submissionDate}">Submitted: ${study.submissionDate}&nbsp;&nbsp;</c:if>
            <c:if test="${not empty study.releaseDate}">Released: ${study.releaseDate}</c:if>
        </c:if>

        <c:if test="${not empty organismNames}">
            <BR>
            <BR><b>Organism(s): </b>
            <c:forEach var="organismName" items="${organismNames}" >
                ${organismName}
            </c:forEach>
        </c:if>


        <c:if test="${not empty study.publications}">
            <br><br>
            <c:forEach var="pub" items="${study.publications}">
                <IMG src="img/book.png"/ height="18"> <a href="http://www.ebi.ac.uk/citexplore/citationDetails.do?externalId=${pub.pmid}&dataSource=MED">${pub.title}</a> 
                <BR>
            </c:forEach>
            <br>
        </c:if>

        <HR>


        <c:if test="${not empty study.protocols}">
            <br><br>
            <b>Protocols</b>
            <ul id="resultListText">
                <c:forEach var="protocol" items="${study.protocols}" begin="0" end="1" step="1">
                    <li>${protocol.name} ${protocol.version}
                    <BR><span style="color:#666666">${protocol.description}</span> </li>
                </c:forEach>
	            <c:if test="${fn:length(study.protocols) > 2 }">
	                <br>
	                <p><a href="javascript:toggle('protTag','moreProtocols','Show all protocols', 'Hide protocols below');" id="protTag">Show all protocols</a> <p>
	                <br>
	                <div id="moreProtocols" class="expandbox">
	                    <ul id="resultListText">
	                        <c:forEach var="protocol" items="${study.protocols}" begin="2" step="1">
	                            <li>${protocol.name} ${protocol.version}
	                            <BR><span style="color:#666666">${protocol.description}</span> </li>
	                        </c:forEach>
	                    </ul>
	                </div>
	            </c:if>
            </ul>
        </c:if>


        <c:if test="${not empty study.assayResults}">
            <br><br>
            <b>Assays</b>
            <ul id="resultList">
                <c:forEach var="assayResult" items="${study.assayResults}" begin="0" end="1" step="1">
                    <li>${assayResult.data.name}</li>
                    <ul id="resultList">
                    <c:set var="prev" value=""/>
                    <c:forEach var="assay" items="${assayResult.assays}">
                       <c:if test="${prev ne assay.technologyName}"> 
                            <!-- if tests prevents duplicate lines on the screen -->                               
                            <li>${assay.technologyName} - ${assay.measurement.name} -  ${assay.assayPlatform}</li>
                       </c:if>
                       <c:set var="prev" value="${assay.technologyName}"/>
                    </c:forEach>
                    </ul>
                    <ul id="resultList">
                    <c:forEach var="fv" items="${assayResult.data.factorValues}">
                            <li>${fv.type.value}: ${fv.value} ${fv.unit.value}</li>
                    </c:forEach>
                    </ul>
                    <hr>
                </c:forEach>
                <c:if test="${fn:length(study.assayResults) > 2 }">
                    <p><a href="javascript:toggle('assTag','moreAssays','Show all assays', 'Hide assays below');" id="assTag">Show all assays</a> <p>
                    <br>
                    <div id="moreAssays"  class="expandbox" >
                        <c:forEach var="assayResult" items="${study.assayResults}" begin="2" step="1">
                            <li>${assayResult.data.name}</li>
                            <ul id="resultList">
                            <c:set var="prev" value=""/>
                            <c:forEach var="assay" items="${assayResult.assays}">
                               <c:if test="${prev ne assay.technologyName}"> 
                                    <!-- if tests prevents duplicate lines on the screen -->                               
                                    <li>${assay.technologyName} - ${assay.measurement.name} -  ${assay.assayPlatform}</li>
                               </c:if>
                               <c:set var="prev" value="${assay.technologyName}"/>
                            </c:forEach>
                            </ul>
                            <c:if test="${assayResult.data.factorValues.size gt 0}">
	                            <ul id="resultList">
	                            <c:forEach var="fv" items="${assayResult.data.factorValues}">
	                                    <li>${fv.type.value}: ${fv.value} ${fv.unit.value}</li>
	                            </c:forEach>
	                            </ul>
                            </c:if>
                            <hr>
                        </c:forEach>
                    </div>
                </c:if>
            </ul>
        </c:if>

        <c:if test="${not empty study.contacts}">
            <br><br>
            <b>Contact</b>
            <ul id="resultList">
                <c:forEach var="contact" items="${study.contacts}">
                    <li>${contact.firstName} ${contact.lastName} <c:if test="${not empty contact.email}">(${contact.email})</c:if><c:if test="${not empty contact.affiliation}">- ${contact.affiliation}</c:if></li>
                </c:forEach>
            </ul>
        </c:if>

        <!-- c:if test="${not empty study.annotations}">
            <br><br>
            <b>Annotations</b>
            <ul id="resultList">
                <c:forEach var="annotation" items="${study.annotations}">
                    <li>${annotation.text} </li>
                </c:forEach>
            </ul-->
        <!--/c:if-->


</div>	