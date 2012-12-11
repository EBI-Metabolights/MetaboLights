
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
$(document).ready(function() {
	$("#formulae").formularize();
});
</script>

<script>
$(function() {
	$( "#tabs" ).tabs({
		cache:true,
	});
});
</script>


<script type="text/javascript">

	$(document).ajaxStart(function(){showWait();}).ajaxStop(function(){
		hideWait();
	});

	$(document).ready(function() {
		
		$("#hourglass").dialog({
		    create: function(){
		    	$('.ui-dialog-titlebar-close').removeClass('ui-dialog-titlebar-close');
		    },
		    width: 200,
		    height: 60,
		    modal: true,
		    autoOpen: false
		});
	});
	
	function showWait() {
	    document.body.style.cursor = "wait";
	    $('.ui-dialog-titlebar').hide();
		$( "#hourglass" ).dialog( "open" );
	}
	
	function hideWait(){
	    document.body.style.cursor = "default";
		$( "#hourglass" ).dialog("close");
	}
	
</script>

<script>
function color_for_atom(formulaChar)
{
// based on jmol colors: http://jmol.sourceforge.net/jscolors/
  if (formulaChar == 'C') color = "909090";
  else if (formulaChar == 'N') color = "3050F8";
  else if (formulaChar == 'O') color = "FF0D0D";
  else color = "000000";
 
  return color;
}

(function( $ ) {
	 
	  $.fn.formularize = function() {
	    return this.each(function() {
	      var formulaetext = '';
	     
	      // get the current text inside element
	      var text = $(this).text();
	 
	      // iterate the whole 360 degrees
	      for (var i = 0; i < text.length; i++)
	      {
	    	formulaetext = formulaetext + '<span style="color:#' + color_for_atom(text.charAt(i)) + '">' + text.charAt(i) + '</span>';
	        
	      }
	 
	      $(this).html(formulaetext);
	    });
	  };
	})( jQuery );
</script>

	<div id="hourglass">
   		<img src="img/wait.gif" alt="Please wait"/>&nbsp;<b><spring:message code="msg.fetchingData"/></b>
   	</div>

<%--  Place holder for the compound --%>
<div id="content" class="grid_24">
	<div class="grid_24">
		<h3>${compound.mc.accession} - ${compound.mc.name}</h3>
	</div>
	<div class="grid_6 alpha">
		<div class="grid_16 prefix_4">
			<%--<h5>Structure</h5><br>--%>
			<img src="http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId=${compound.mc.chebiId}"/>
		</div>
		<div class="grid_24">
			<p><a href="http://www.ebi.ac.uk/chebi/searchId.do?chebiId=${compound.mc.chebiId}">${compound.chebiEntity.chebiAsciiName} - (${compound.mc.chebiId})</a></p>
		</div>
	
	</div>

	<div class="grid_18 omega">
		<div id="tabs">
			<ul>
				<li>
					<a class="noLine" href="#tabs-1"><spring:message code="ref.compound.tab.characteristics"/></a>
				</li>
				<li>
					<a class="noLine" href="#tabs-2"><spring:message code="ref.compound.tab.foundin"/></a>
				</li>
				<li>
					<a class="noLine" href="#tabs-3"><spring:message code="ref.compound.tab.pathways"/></a>
				</li>
				<li>
					<a class="noLine" href="Reactions?chebiId=${compound.mc.chebiId}"><spring:message code="ref.compound.tab.reactions"/></a>
				</li>
				<li>
					<a class="noLine" href="#tabs-5"><spring:message code="ref.compound.tab.nmrspectra"/></a>
				</li>
				<li>
					<a class="noLine" href="#tabs-6"><spring:message code="ref.compound.tab.msspectra"/></a>
				</li>
				<li>
					<a class="noLine" href="Citations?mtblc=${compound.mc.accession}"><spring:message code="ref.compound.tab.literature"/></a>
				</li>
			</ul>
		
			<div id="tabs-1" class="tab">
				<c:if test="${not empty compound.chebiEntity.definition}">
				<h6><spring:message code="ref.compound.tab.characteristics.definition"/></h6>
				${compound.chebiEntity.definition}
				</c:if>
				<h6><spring:message code="ref.compound.tab.characteristics.chemicalproperties"/></h6>
				<c:forEach var="formulae" items="${compound.chebiEntity.formulae}">
				<spring:message code="ref.compound.tab.characteristics.formulae"/> - <span id="formulae">${formulae.data}</span><br/>
				</c:forEach>
				Average mass - ${compound.chebiEntity.mass}
				<br/>
				<h6><spring:message code="ref.compound.synonyms"/>:</h6>
				<c:forEach var="synonym" items="${compound.chebiEntity.synonyms}">
					<span class="tag">${synonym.data}</span>
				</c:forEach>
				<br/><br/>				
				${compound.chebiEntity.inchi}<br/>
			</div>
			<!-- Found in -->
			<div id="tabs-2" class="tab"><h4>To be implemented...</h4><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS34jyUBk-CNrKobrPxwD7F8wvH6_w86Lu9VfU5IjBXu1gKiduR"/></div>
			<!-- Path ways -->
			<div id="tabs-3" class="tab"><h4>To be implemented...</h4><img src="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQotrC_Rvg3-mRq4huTwhVn1Ku7tdElSJQTRGAt_sple7oiMMto"/>
				<h4>Options explored so far:</h4>
				<h5>iPath from EMBL? POST request with kegg ids</h5>
				<a href="http://pathways.embl.de/mapping.cgi" target="blank"><img src="http://pathways.embl.de/img/iPath2logo.png"/></a>
				<h5>Pathways projector</h5>
				<a href="http://www.g-language.org/PathwayProjector/" target="blank"><img src="http://www.g-language.org/PathwayProjector/pict/mainpage.png"/></a>
			</div>
			<!-- Reactions 
			<div id="tabs-4" class="tab">
				<h4>To be implemented...</h4>
				<img src="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTLBHrDpm9cZkyAdfU7KdQnVLVZ9MG6SByle5QQM0IpSf2hBezB"/>
			</div>-->
			<!-- NMR Spectra -->
			<div id="tabs-5" class="tab"><h4>To be implemented...</h4><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKiJmVL9LDgA1YJFz6bSMLV4jNc_unkwF5Mv9M9YxxX8ZL9guZ"/></div>
			<!-- MS Spectra -->
			<div id="tabs-6" class="tab"><h4>To be implemented...</h4><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTE143R-fv5Qf-78aSulo8vvFCTdinc-JULSdvh8i3erXA3kwFnnA"/></div>
			<!-- Literature 
			<div id="tabs-7" class="tab">
				<!--<h4>To be implemented...</h4>
				<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIS7_rCf2a4_96A56MVeTRTsvyLmohyfOwBLDrUwds5HAat6RZ"/>
				<br/>
				<c:forEach var="citation" items="${compound.chebiEntity.citations}">
					<span class="tag">${citation.data}</span><br/>
				</c:forEach>
			</div>
			-->
		</div>
	</div>
</div>
<br/>



