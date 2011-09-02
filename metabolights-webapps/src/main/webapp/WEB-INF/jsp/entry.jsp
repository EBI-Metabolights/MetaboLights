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

<script language="Javascript" type="text/javascript">

function createRequestObject() {
    var tmpXmlHttpObject;
    //depending on what the browser supports, use the right way to create the XMLHttpRequest object
    if (window.XMLHttpRequest) { 
        // Mozilla, Safari would use this method ...
        tmpXmlHttpObject = new XMLHttpRequest();
    } else if (window.ActiveXObject) { 
        // IE would use this method ...
        tmpXmlHttpObject = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return tmpXmlHttpObject;
}

//call the above function to create the XMLHttpRequest object
var http = createRequestObject();

function tagTextWithWhatizit() {
    //make a connection to the server ... specifying that you intend to make a GET request 
    //to the server. Specifiy the page name and the URL parameters to send
    //
    //Did not work in IE, needed an extra dummy refresh enforcer. See: http://weblogs.asp.net/pleloup/archive/2006/06/08/451583.aspx
    http.open('get', 'tagText?dummy='+ new Date().getTime());
    //assign a handler for the response
    http.onreadystatechange = processResponse;
    //actually send the request to the server
    http.send(null);
}

function processResponse() {
    //check if the response has been received from the server
    if(http.readyState == 4){
        //read and assign the response from the server
        var response = http.responseText;
        //do additional parsing of the response, if needed
        //in this case simply assign the response to the contents of the <div> on the page.
        document.getElementById('description').innerHTML = response;
        //If the server returned an error message like a 404 error, that message would be shown within the div tag!!. 
        //So it may be worth doing some basic error before setting the contents of the <div>
    }
}

function onloadAction() {
	tagTextWithWhatizit();
}
</script>

<div id="text_header">
	<spring:message code="label.metaboLightsEntry" />
	${study.acc}
</div>

<div class="formbox">
		<br/><br/>
		<span class="title">${study.title}</span>

        <c:if test="${not empty study.objective}">
            <br><br>${study.objective}
        </c:if>

        <c:if test="${not empty study.description}">
            <br><br><span style="text-align:justify"><div id="description">${study.description}</div></span>
        </c:if>

        <c:if test="${not empty study.submissionDate || not empty study.releaseDate}">
            <br><br>
            <c:if test="${not empty study.submissionDate}"><spring:message code="label.subDate"/>: ${study.submissionDate}&nbsp;&nbsp;</c:if>
            <c:if test="${not empty study.releaseDate}"><spring:message code="label.releaseDate"/>: ${study.releaseDate}</c:if>
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
                <IMG src="img/ebi-icons/32px/book.png" class="img_alignment_green"> <a href="http://www.ebi.ac.uk/citexplore/citationDetails.do?externalId=${pub.pmid}&dataSource=MED">${pub.title}</a> 
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
                            <c:if test="${not empty assayResult.data.factorValues}">
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
        
        <c:if test="${not empty ftpLocation}">
            <hr/><br>
            <IMG src="img/ebi-icons/32px/download.png" class="img_alignment_yellow"> <a href="${ftpLocation}"> <spring:message code="label.ftpDownload"/></a>
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