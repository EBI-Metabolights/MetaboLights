<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <div class="grid_24">
        <h3><spring:message code="msg.metabolights" /></h3>
    </div>
    
    <div class="grid_24">
	    <div class="grid_8 alfa">
	        <p>
	        <spring:message code="msg.metabolightsAbout1" />
	        <spring:message code="msg.metabolightsAbout2" />
	        </p>
	    </div>
	
	    <div class="grid_8">
	        <p><spring:message code="msg.metabolightsAbout3" /></p>
	    </div>
	
	    <div class="grid_8 omega">
	        <p><spring:message code="msg.metabolightsAbout4" /></p>
	    </div>
    </div>   
    <div class="grid_24"">
    	<p/>
  		<a href="<spring:url value="contact"/>"><strong><spring:message code="label.contact"/></strong></a>
   	</div>

    <div class="grid_24">
    	<p/>
        <a href="<spring:url value="statistics"/>"><strong><spring:message code="label.stats"/></strong></a>
    </div>

    <div class="grid_24">
        <hr/>
        <a class="noLine" href="<spring:message code="url.isatools"/>"><img src="img/softwaresuitelogo2.png" alt="isatab"/></a>
        <p><spring:message code="msg.metabolightsAbout6" /></p>
        <br/>
        <p><spring:message code="msg.metabolightsAbout10" /></p>
    </div>

    
    <div class="grid_24">
       <a class="noLine" href="http://github.com/ISA-tools/OntoMaton" target="_blank"><img src="http://isatools.files.wordpress.com/2012/07/ontomaton.png?w=250"/></a>
       <a class="noLine" href="<spring:message code="url.isatools"/>" target="_blank"><img src="http://isatab.sourceforge.net/assets/img/tools/tools-table-images/isacreator.png"/></a>
       <spring:message code="msg.metabolightsAbout15" />
    </div>
      
    <div class="grid_24">
        <hr/>
        <br/>
        <a class="noLine" href="http://www.bbsrc.ac.uk/pa/grants/AwardDetails.aspx?FundingReference=BB/I000933/1"><img src="img/bbsrcLarge.png" alt="BBSCR"/></a>
        &nbsp;&nbsp;&nbsp; <img src="img/embl.png" alt="EMBL"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="http://www.metabolomics.bioc.cam.ac.uk/metabolomics/"><img src="img/camLogo.png" alt="UC"/></a>
        <br/>
            <spring:message code="msg.metabolightsAbout5"/><br/>
        <br/>

    </div>