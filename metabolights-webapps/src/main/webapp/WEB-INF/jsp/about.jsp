<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <div class="text_header plain">
        <spring:message code="msg.metabolights" />
    </div>
       
    <div class="text-column">
        <spring:message code="msg.metabolightsAbout1" />
        <spring:message code="msg.metabolightsAbout2" />
    </div>

    <div class="text-column">
        <spring:message code="msg.metabolightsAbout3" />
    </div>

    <div class="text-column">
        <spring:message code="msg.metabolightsAbout4" />
    </div>
      
    <div class="text-fullwidth bold">
  		<a href="<spring:url value="contact"/>"><spring:message code="label.contact"/></a>
   	</div>

    <div class="text-fullwidth bold">
        <a href="<spring:url value="statistics"/>"><spring:message code="label.stats"/></a>
    </div>

    <div class="text-fullwidth">
        <hr/>
        <a href="<spring:message code="url.isatools"/>"><img src="img/softwaresuitelogo2.png" alt="isatab"/></a>
        <p><spring:message code="msg.metabolightsAbout6" /></p>
        <br/>
        <p><spring:message code="msg.metabolightsAbout10" /></p>
    </div>

    
    <div class="text-fullwidth">
       <a href="http://github.com/ISA-tools/OntoMaton" target="_blank"><img src="http://isatools.files.wordpress.com/2012/07/ontomaton.png?w=250"/></a>
       <a href="<spring:message code="url.isatools"/>" target="_blank"><img src="http://isatab.sourceforge.net/assets/img/tools/tools-table-images/isacreator.png"/></a>
       <spring:message code="msg.metabolightsAbout15" />
    </div>
      
    <div class="text-fullwidth">
        <hr/>
        <br/>
        <a href="http://www.bbsrc.ac.uk/pa/grants/AwardDetails.aspx?FundingReference=BB/I000933/1"><img src="img/bbsrcLarge.png" alt="BBSCR"/></a>
        &nbsp;&nbsp;&nbsp; <img src="img/embl.png" alt="EMBL"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="http://www.metabolomics.bioc.cam.ac.uk/metabolomics/"><img src="img/camLogo.png" alt="UC"/></a>
        <br/>
            <spring:message code="msg.metabolightsAbout5"/><br/>
        <br/>

    </div>