<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<script type="text/javascript" src="javascript/jquery-1.6.2.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="javascript/jquery-ui-1.8.15.custom.min.js" type="text/javascript" charset="utf-8"></script>

 
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
<script language="javascript" type="text/javascript">
/* $(function() {
	  $("#toggle").click(function() {
	    if ($("#hidden").is(":hidden")) {
	      $(this).text("Show less");
	    } else {
	      $(this).text("Show more");
	    }
	    $("#hidden").slideToggle();
	    return false;
	  });
	}); */
$(document).ready(function() {
    $("a.showLink").click(function(event) {
        var clickedId = event.target.id;
        var idClickedSplit = clickedId.split("_");
        /*id of the link is made up by 3 parts:
           part 1: name of the div (eg.: syn) this is used to distinguish the show more
            link of synonyms from the show more link in other divs
            part 2: "link" to distinguish the link for show more link from other
            ordinary links
            part 3: the order of the result item to distinguish the show more button
            in the result list is click. In case of filters of species or compounds
            the order is always 0
            */
        var idPrefixClicked = idClickedSplit[0];
        /*var itemClicked = idClickedSplit[1];*/
        var orderOfItemClicked = idClickedSplit[2];
        var idOfHiddenText = "#"+idPrefixClicked+"_"+orderOfItemClicked;
        var jqClickedId= "#"+clickedId;
        if ($(idOfHiddenText).is(":hidden")){
        	$(jqClickedId).text("Show less");
        }else{
        	$(jqClickedId).text("Show more");
        }
        $(idOfHiddenText).slideToggle();
        
    });
});
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
	//tagTextWithWhatizit();                   //TODO, fix when whatizit works again
}

</script>


<script>
$(function() {
	$( "#tabs" ).tabs();
});
</script>

<div class="text_header" style='height:46px'>
	<c:if test="${not empty ftpLocation}">
		<div style='width:20%; float:right'><IMG src="img/ebi-icons/32px/download.png" class="img_alignment_yellow"> <a href="${ftpLocation}"> <spring:message code="label.ftpDownload"/></a></div>
	</c:if>
	<div style='width:75%'>${study.acc}: ${study.title}</div>
</div>

<div class="formbox border">

       <c:if test="${not empty study.contacts}">
            <br/>
            <c:forEach var="contact" items="${study.contacts}" varStatus="loopStatus">
				<c:if test="${loopStatus.index ne 0}">,</c:if>
	            <span id="aff" 
                	<c:if test="${not empty contact.affiliation}">title="${contact.affiliation}"</c:if>
                >${contact.firstName} ${contact.lastName}</span>
            </c:forEach>
			<br/>
        </c:if>

        <c:if test="${not empty study.submissionDate || not empty study.releaseDate}">
            <br/>
            <c:if test="${not empty study.submissionDate}"><spring:message code="label.subDate"/>: <fmt:formatDate pattern="dd-MMM-yyyy" value="${study.submissionDate}"/>&nbsp;&nbsp;</c:if>
            <c:if test="${not empty study.releaseDate}"><spring:message code="label.releaseDate"/>: <fmt:formatDate pattern="dd-MMM-yyyy" value="${study.releaseDate}"/></c:if>
        </c:if>
        
	    <c:if test="${not empty study.description}">
   		     <br/><br/><span style="text-align:justify"><div id="description">${study.description}</div></span>
	    </c:if>
 		<br/>
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1"><spring:message code="label.studyDesign"/></a></li>
				<li>
					<a href="#tabs-2"><spring:message code="label.protocols"/>
						<c:if test="${not empty study.protocols}">
							(${fn:length(study.protocols)})
						</c:if>
					</a>
				</li>
				<li title="sdsds">
					<a href="#tabs-3"><spring:message code="label.data"/></a>
				</li>
				<li>
					<a href="#tabs-4"><spring:message code="label.metabolites"/>
					<c:if test="${not empty metabolites}">
						(${fn:length(metabolites)})
					</c:if>
					</a>
				</li>
			</ul>
			<div id="tabs-1">
		        <c:if test="${not empty organismNames}">
		            <br/>
		            <fieldset class="filterbox">
		            	<legend><spring:message code="label.organisms"/>:</legend>
			            <br/>
			            <c:forEach var="organismName" items="${organismNames}" >
			                ${organismName}<br/>
			            </c:forEach>
		            </fieldset>
		        </c:if>
				<c:if test="${not empty study.designs}">
		            <br/>
		            <fieldset class="filterbox">
		            	<legend><spring:message code="label.studyDesign"/>:</legend>
			            <br/>
			            <ul>
			            <c:forEach var="design" items="${study.designs}" >
			                <li>${design.value}
			            </c:forEach>
			            </ul>
		            </fieldset>
		        </c:if>
		        <c:if test="${not empty study.objective}">
		            <br/>
		            <fieldset class="filterbox">
		            	<legend><spring:message code="label.studyDesign"/></legend>
			            <br/><br/>${study.objective}
			       </fieldset>
		        </c:if>

		        <c:if test="${not empty study.publications}">
					<br/>
		            <fieldset class="filterbox">
		            	<legend><spring:message code="label.publications"/></legend>
						<c:forEach var="pub" items="${study.publications}">
							<br/>
		                	<IMG src="img/ebi-icons/32px/book.png" class="img_alignment_green"> <a href="http://www.ebi.ac.uk/citexplore/citationDetails.do?externalId=${pub.pmid}&dataSource=MED">${pub.title}</a> 
		            	</c:forEach>
		            	<br/>
		            </fieldset>
		        </c:if>
		        <c:if test="${not empty factors}">
		        	<br/>
		            <fieldset class="filterbox">
		            	<legend><spring:message code="label.experimentalFactors"/></legend>
		                    <ul>
				                <c:forEach var="fv" items="${factors}">
				                	<li>${fv.key}: [
				                		<c:forEach var="value" items="${fv.value}" varStatus="loopStatus">
				                			<c:if test="${loopStatus.index ne 0}">
				                				, 
				                			</c:if>
				                			${value}
				                		</c:forEach>
				                	]</li>
				                </c:forEach>
					        </ul>
			        </fieldset>
		        </c:if>
			</div>
			<div id="tabs-2">
				<c:if test="${not empty study.protocols}">
		            <table width="100%">
						<thead class='text_header'>
							<tr>
								<th>Protocol</th>
								<th>Description</th>
							</tr>
						</thead>
						<tbody>	
			                <c:forEach var="protocol" items="${study.protocols}" varStatus="loopStatus">
	                    		<tr style="background: ${loopStatus.index % 2 == 0 ? '' : '#eef5f5'}">
			                    	<td class="tableitem">${protocol.name}</td>
			                    	<td class="tableitem">${protocol.description}</td>
			                    </tr>
			                </c:forEach>
		            	</tbody>
		            </table>
		        </c:if>
			</div> <!-- ends tabs-2 -->
			<div id="tabs-3">
				<c:if test="${not empty assays}">
	                <c:forEach var="assay" items="${assays}" varStatus="loopStatusAssay">
						<br/>
						<br/>
			            <table width="100%">
							<thead class='text_header'>
								<tr>
									<th><spring:message code="label.data.table.groupName"/></th>
									<th><spring:message code="label.data.table.name"/></th>
									<!-- Add one column per factor -->
									<c:forEach var="factor" items="${assay.factors}">
										<th>${factor.value}</th>
									</c:forEach>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="assayResult" items="${assay.assayResult}" varStatus="loopStatus">	
	                    		<tr style="background: ${loopStatus.index % 2 == 0 ? '' : '#eef5f5'}">
			                   		<c:if test="${loopStatus.index == 10}">
		                    			<%-- <tr><td colspan=2><a href="#" class="showLink" id="data_link_${loopStatusAssay.index}">Show more</a></td></tr> --%>
		                    			</tbody><tbody id="data_${loopStatusAssay.index}" style='display:none'>
		                    		</c:if>
			                    	<%-- <td class="tableitem">${assay.fileName} ${fn:length(assayResult.assays)}</td> --%>
			                    	<td class="tableitem">
			                    		<!-- we expect only one assay per assayResult, so we can loop the assay collection and get the first -->
			                    		<c:forEach var="assayline" items="${assayResult.assays}" varStatus="loopStatus">
			                    			${assayline.material.name}
			                    		</c:forEach>
			                    		</td>
	                    			<td class="tableitem">${assay.technology} - ${assay.measurement} -  ${assay.platform}</td>
	                    			<c:forEach var="fv" items="${assayResult.data.factorValues}">
	                    				<td class="tableitem">${fv.value} ${fv.unit.value}</td>
	                    			</c:forEach>
			                    </tr>
			                    </c:forEach>
			            	</tbody>
			            </table>
			            <c:if test="${fn:length(assay.assayResult) > 10}"><a href="#" class="showLink" id="data_link_${loopStatusAssay.index}">Show more</a></c:if>
			            <br/>
	                </c:forEach>
		        </c:if>
			</div> <!--  ends tabs-3 -->
			<div id="tabs-4">
			        
				<c:if test="${not empty assays}">

					<c:forEach var="mlAssay" items="${assays}" varStatus="loopStatusAssay">
					
						<br/>
						<br/>
			            <table width="100%">
							<thead class='text_header'>
								<tr>
									<th><spring:message code="label.metabolites.description"/> - "${mlAssay.fileName}"</th>
									<th>Formula</th>
								</tr>
							</thead>
							<tbody>			
			                <c:forEach var="met" items="${mlAssay.metabolitesGUI}" varStatus="loopStatusMet">
	                    		
	                    		<c:if test="${loopStatusMet.index == 10}">
	                    			</tbody><tbody id="met_${loopStatusAssay.index}" style='display:none'>
	                    		</c:if>
	                    		<tr style="background: ${loopStatusMet.index % 2 == 0 ? '' : '#eef5f5'}">
			                    	<td class="tableitem">
			                    		${met.metabolite.description}
	                    				<c:choose>
	                    					<c:when test="${empty met.link }"> (${met.metabolite.identifier})</c:when>
	                    					<c:otherwise><a href="${met.link}" target="_blank">(${met.metabolite.identifier})</a></c:otherwise>
	                    				</c:choose>
		                    		</td>
	                    			<td class="tableitem">
			                    		${met.metabolite.chemical_formula}
	                    			</td>
										                    			
			                    </tr>
			                </c:forEach>
			                </tbody>
			            </table>
			            
			             <c:if test="${fn:length(mlAssay.metabolitesGUI) > 10}"><a href="#" class="showLink" id="met_link_${loopStatusAssay.index}">Show more</a></c:if>
			             <br/>
			            
			        </c:forEach> <!-- For each assayGroup -->					
		        </c:if>		        
		        
		        
			</div> <!--  ends tabs-4 -->
		</div> <!-- end tabs -->
 </div>	

