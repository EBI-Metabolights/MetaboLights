<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ Last modified: 4/8/14 9:54 AM
  ~ Modified by:   kenneth
  ~
  ~ Copyright 2014 - European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  --%>

<h2><spring:message code="msg.metabolights" /></h2>

    <div class="grid_8 alpha">
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

    <p>
    <a href="<spring:url value="contact"/>"><strong><spring:message code="label.contact"/></strong></a>
    </p>

   	<p><a href="<spring:url value="statistics"/>"><strong><spring:message code="label.stats"/></strong></a></p>

<%--    <p><a href="<spring:url value="acknowledgements"/>"><strong>Acknowledgements</strong></a></p>   --%>

    <p><strong>Follow us on </strong>
        <span class="bigfont">
            <a href="http://metabolights.blogspot.co.uk" class="icon icon-socialmedia" data-icon="B"></a>,
            <a href="http://www.facebook.com/MetaboLights" class="icon icon-socialmedia" data-icon="F"></a>,
            <a href="https://twitter.com/metabolights" class="icon icon-socialmedia" data-icon="T"></a>.
        </span>
    </p>

    <div class="grid_22 alpha omega publication">
        <h3>Citing MetaboLights</h3>
        <span class="pubauthor">Kenneth Haug, Reza M. Salek, Pablo Conesa, Janna Hastings, Paula de Matos, Mark Rijnbeek, Tejasvi Mahendrakar, Mark Williams, Steffen Neumann, Philippe Rocca-Serra, Eamonn Maguire, Alejandra Gonz&aacute;lez-Beltr&aacute;n, Susanna-Assunta Sansone, Julian L. Griffin and Christoph Steinbeck.</span><br/>
        <a href="http://nar.oxfordjournals.org/content/41/D1/D781" class="pubtitle">MetaboLights-- an open-access general-purpose repository for metabolomics studies and associated meta-data.</a><br/>
        <span class="pubjournal">Nucl. Acids Res. (2013) doi: 10.1093/nar/gks1004</span>
    </div>

    <hr/>
    <a href="http://www.cosmos-fp7.eu/"><img src="img/cosmosSmall_0.png" style = "width: 200px;" alt="COSMOS"/></a>
    <p><spring:message code="msg.about.cosmos"/></p>

    <hr/>
    <a class="noLine" href="<spring:message code="url.isatools"/>"><img src="img/softwaresuitelogo2.png" alt="isatab"/></a>
    <p><spring:message code="msg.metabolightsAbout6" /></p>
    <br/>
    <p><spring:message code="msg.metabolightsAbout10" /></p>


    <a class="noLine" href="http://github.com/ISA-tools/OntoMaton" target="_blank"><img src="http://isatools.files.wordpress.com/2012/07/ontomaton.png?w=250"/></a>
    <a class="noLine" href="<spring:message code="url.isatools"/>" target="_blank"><img src="http://isatab.sourceforge.net/assets/img/tools/tools-table-images/isacreator.png"/></a>
    <p><spring:message code="msg.metabolightsAbout15" /></p>

    <hr/>
    <br/>
    <a class="noLine" href="http://www.bbsrc.ac.uk/pa/grants/AwardDetails.aspx?FundingReference=BB/I000933/1"><img src="img/bbsrcLarge.png" alt="BBSCR"/></a>
        &nbsp;&nbsp;&nbsp; <img src="img/embl.png" alt="EMBL"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="http://www.metabolomics.bioc.cam.ac.uk/metabolomics/"><img src="img/camLogo.png" alt="UC"/></a>
    <br/>
    <p><spring:message code="msg.metabolightsAbout5"/></p>
