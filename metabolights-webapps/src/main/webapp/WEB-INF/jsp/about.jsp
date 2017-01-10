<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

    <%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 2014-Dec-17
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

<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/metabolights.css" type="text/css"/>

<div class="container-fluid">
        <div class="col-md-12">
            <h3 class="heading"><spring:message code="msg.metabolights" /></h3>
        </div>
        <div class="row">
            <div class="col-md-4">
                <p class="description">
                    <spring:message code="msg.metabolightsAbout1" />
                    <spring:message code="msg.metabolightsAbout2" />
                </p>
            </div>
            <div class="col-md-4">
                <p class="description"><spring:message code="msg.metabolightsAbout3" /></p>
            </div>
            <div class="col-md-4">
                <p class="description"><spring:message code="msg.metabolightsAbout4" /></p>
            </div>
        </div>
        <div class="row">
            <div>&nbsp;</div>
        </div>
        <div class="row">
                <div class="col-md-8">
                    <div class="well">
                        <p><a href="<spring:url value="contact"/>"><strong><spring:message code="label.contact"/></strong></a></p>
                        <p><a href="<spring:url value="statistics"/>"><strong><spring:message code="label.stats"/></strong></a></p>
                        <p><a href="<spring:url value="advisoryboard"/>"><strong><spring:message code="label.sab"/></strong></a></p>
                        <p><a href="<spring:url value="acknowledgements"/>"><strong>Acknowledgements</strong></a></p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="well">
                        <p><strong>Follow us on </strong><br><br>
                            <span class="bigfont">
                                <a href="http://metabolights.blogspot.co.uk" class="icon icon-socialmedia" data-icon="B"></a>&nbsp;
                                <a href="http://www.facebook.com/MetaboLights" class="icon icon-socialmedia" data-icon="F"></a>&nbsp;
                                <a href="https://twitter.com/metabolights" class="icon icon-socialmedia" data-icon="T"></a>
                            </span>
                        </p>
                        <br><br>
                    </div>
                </div>
            </div>

        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-success">
                    <div class="panel-heading">Citing MetaboLights</div>
                    <div class="panel-body">
                        <span class="pubauthor">Kenneth Haug, Reza M. Salek, Pablo Conesa, Janna Hastings, Paula de Matos, Mark Rijnbeek, Tejasvi Mahendrakar, Mark Williams, Steffen Neumann, Philippe Rocca-Serra, Eamonn Maguire, Alejandra Gonz&aacute;lez-Beltr&aacute;n, Susanna-Assunta Sansone, Julian L. Griffin and Christoph Steinbeck.</span><br/>
                        <a target="_blank" href="http://nar.oxfordjournals.org/content/41/D1/D781" class="pubtitle">MetaboLights-- an open-access general-purpose repository for metabolomics studies and associated meta-data.</a><br/>
                        <span class="pubjournal">Nucl. Acids Res. (2013) doi: 10.1093/nar/gks1004</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <hr>
    <div class="row">
            <div class="col-md-12">
                <div class="col-md-6">
                    <a href="http://www.cosmos-fp7.eu/"><img src="img/cosmosSmall_0.png" style = "height: 60px;" alt="COSMOS"/></a>
                    <p><spring:message code="msg.about.cosmos"/></p>
                </div>
                <div class="col-md-6">
                    <a class="noLine" href="<spring:message code="url.isatools"/>"><img src="img/softwaresuitelogo2.png" style = "height: 60px;" alt="isatab"/></a>
                    <p><spring:message code="msg.metabolightsAbout6" /></p>
                </div>
            </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-12">
                <p><spring:message code="msg.metabolightsAbout10" /></p>
                <a class="noLine" href="http://github.com/ISA-tools/OntoMaton" target="_blank"><img src="http://isatools.files.wordpress.com/2012/07/ontomaton.png?w=250"/></a>
                <a class="noLine" href="<spring:message code="url.isatools"/>" target="_blank"><img src="http://isatab.sourceforge.net/assets/img/tools/tools-table-images/isacreator.png"/></a>
                <p><spring:message code="msg.metabolightsAbout15" /></p>
            </div>
        </div>
    </div>
    <hr/>
    <div class="row" id="support-journals" href="#publishers">
        <div class="col-md-12">
            <div class="col-md-12">
                <a href="http://www.nature.com/sdata/policies/repositories"><img src="img/support-journals/scientificData.png" style = "height: 40px;" alt="Nature Scientific Data"/></a>
                <%--<a href="https://academic.oup.com/gigascience/pages/instructions_to_authors"><img src="img/support-journals/gigaScience.png" style = "height: 40px;" alt="Giga Science"/></a>--%>
                <a href="https://www.biomedcentral.com/"><img src="img/support-journals/biomedCentral.png" style = "height: 40px;" alt="BioMed Central"/></a>
                <a href="http://journals.plos.org/plosbiology/s/data-availability#loc-recommended-repositories"><img src="img/support-journals/Plos-biology.png" style = "height: 40px;" alt="PLOS Biology"/></a>
                <a href="http://msb.embopress.org/authorguide#datadeposition"><img src="img/support-journals/EMBO-press.png" style = "height: 40px;" alt="EMBO press"/></a>
                <a href="https://wellcomeopenresearch.org/for-authors/data-guidelines"><img src="img/support-journals/wellcome.png" style = "height: 100px;" alt="Wellcome Trust Open Research"/></a>
                <a href="https://f1000research.com/for-authors/data-guidelines"><img src="img/support-journals/F1000-research.png" style = "height: 40px;" alt="F1000 Research"/></a>
                <a href="http://link.springer.com/journal/11306"><img src="img/support-journals/metabolomics.png" style = "height: 40px;" alt="Springer Link - Metabolomics"/></a>
                <a href="http://journal.frontiersin.org/journal/molecular-biosciences/section/metabolomics#author-guidelines"><img src="img/support-journals/frontiers.png" style = "height: 40px;" alt="Frontiers"/></a>
                <a href="http://www.mdpi.com/journal/metabolites"><img src="img/support-journals/metabolites.png" style = "height: 60px;" alt="Metabolites"/></a>
                <p><spring:message code="msg.metaboLightsAbout17"/></p>
                <p><spring:message code="msg.metaboLightsAbout18"/></p>
            </div>
        </div>
    </div>
    <hr/>
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-12">
                <a class="noLine" href="http://www.bbsrc.ac.uk/research/grants-search/AwardDetails/?FundingReference=BB%2fL024152%2f1"><img src="img/bbsrcLarge.png" alt="BBSCR"/></a>
                &nbsp;&nbsp;&nbsp; <img src="img/EMBL_EBI_logo_180pixels_RGB.jpg" alt="EMBL"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="http://www.metabolomics.bioc.cam.ac.uk/metabolomics/"><img src="img/camLogo.png" alt="UC"/></a>
                <br/>
                <p><spring:message code="msg.metabolightsAbout5"/></p>
            </div>
        </div>
    </div>
